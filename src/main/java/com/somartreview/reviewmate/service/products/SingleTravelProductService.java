package com.somartreview.reviewmate.service.products;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.domain.TravelProduct.*;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductCreateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductUpdateRequest;
import com.somartreview.reviewmate.dto.response.travelProduct.SingleTravelProductConsoleElementResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.service.partners.PartnerCompanyService;
import com.somartreview.reviewmate.service.partners.PartnerSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.somartreview.reviewmate.exception.ErrorCode.*;
import static com.somartreview.reviewmate.exception.ErrorCode.TRAVEL_PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SingleTravelProductService {

    private final SingleTravelProductRepository singleTravelProductRepository;
    private final TravelProductService travelProductService;
    private final PartnerCompanyService partnerCompanyService;
    private final PartnerSellerService partnerSellerService;


    @Transactional
    public Long create(String partnerDomain, SingleTravelProductCreateRequest request, MultipartFile thumbnailFile) {
        validateUniquePartnerCustomId(partnerDomain, request.getPartnerCustomId());

        final PartnerCompany partnerCompany = partnerCompanyService.findByPartnerDomain(partnerDomain);
        final PartnerSeller partnerSeller = partnerSellerService.findById(request.getPartnerSellerId());

        String thumbnailUrl = uploadThumbnailOnS3(thumbnailFile);

        return singleTravelProductRepository.save(request.toEntity(thumbnailUrl, partnerCompany, partnerSeller)).getId();
    }

    private void validateUniquePartnerCustomId(String partnerDomain, String partnerCustomId) {
        if (existsByPartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId))
            throw new DomainLogicException(TRAVEL_PRODUCT_NOT_UNIQUE_PARTNER_CUSTOM_ID);
    }

    @Transactional
    public SingleTravelProduct retreiveSingleTravelProduct(String partnerDomain, SingleTravelProductCreateRequest singleTravelProductCreateRequest, MultipartFile thumbnailFile) {
        if (existsByPartnerDomainAndPartnerCustomId(partnerDomain, singleTravelProductCreateRequest.getPartnerCustomId())) {
            return findByPartnerDomainAndPartnerCustomId(partnerDomain, singleTravelProductCreateRequest.getPartnerCustomId());
        }

        create(partnerDomain, singleTravelProductCreateRequest, thumbnailFile);
        return findByPartnerDomainAndPartnerCustomId(partnerDomain, singleTravelProductCreateRequest.getPartnerCustomId());
    }

    public boolean existsByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        return singleTravelProductRepository.existsByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);
    }

    @Transactional
    public void updateByTravelProductId(Long travelProductId, SingleTravelProductUpdateRequest request, MultipartFile thumbnailFile) {
        SingleTravelProduct foundTravelProduct = findByTravelProductId(travelProductId);
        String thumbnailUrl = uploadThumbnailOnS3(thumbnailFile);

        foundTravelProduct.update(request, thumbnailUrl);
    }

    private String uploadThumbnailOnS3(MultipartFile thumbnail) {
        //  TODO: Upload thumbnail to S3 and get the url
        if (thumbnail != null) {
            return null;
        }

        return "https://www.testThumbnailUrl.com";
    }

    public SingleTravelProduct findByTravelProductId(Long travelProductId) {
        return singleTravelProductRepository.findById(travelProductId)
                .orElseThrow(() -> new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND));
    }

    public SingleTravelProduct findByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        return singleTravelProductRepository.findByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId)
                .orElseThrow(() -> new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND));
    }

    public List<SingleTravelProduct> findAllByPartnerDomainAndTravelProductCategory(String partnerDomain, SingleTravelProductCategory singleTravelProductCategory) {
        return singleTravelProductRepository.findAllByPartnerCompany_PartnerDomainAndSingleTravelProductCategory(partnerDomain, singleTravelProductCategory);
    }

    public SingleTravelProductConsoleElementResponse getSingleTravelProductConsoleElementResponseByTravelProductId(Long travelProductId) {
        SingleTravelProduct singleTravelProduct = findByTravelProductId(travelProductId);

        return new SingleTravelProductConsoleElementResponse(singleTravelProduct);
    }

    public SingleTravelProductConsoleElementResponse getSingleTravelProductConsoleElementResponseByPartnerCustomId(String partnerDomain, String partnerCustomId) {
        SingleTravelProduct singleTravelProduct = findByPartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);

        return new SingleTravelProductConsoleElementResponse(singleTravelProduct);
    }

    public List<SingleTravelProductConsoleElementResponse> getSingleTravelProductConsoleElementResponsesByPartnerDomainAndTravelProductCategory(String partnerDomain, SingleTravelProductCategory singleTravelProductCategory) {
        return findAllByPartnerDomainAndTravelProductCategory(partnerDomain, singleTravelProductCategory)
                .stream()
                .map(SingleTravelProductConsoleElementResponse::new)
                .toList();
    }

    @Transactional
    public void deleteByTravelProductId(Long travelProductId) {
        validateExistTravelProductId(travelProductId);

        singleTravelProductRepository.deleteById(travelProductId);
    }

    public void validateExistTravelProductId(Long travelProductId) {
        if (!singleTravelProductRepository.existsById(travelProductId))
            throw new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND);
    }
}
