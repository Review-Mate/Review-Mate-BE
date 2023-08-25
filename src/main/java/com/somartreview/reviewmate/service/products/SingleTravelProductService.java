package com.somartreview.reviewmate.service.products;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.domain.TravelProduct.*;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductCreateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductUpdateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.TravelProductIdDto;
import com.somartreview.reviewmate.dto.response.travelProduct.SingleTravelProductConsoleElementResponse;
import com.somartreview.reviewmate.dto.response.travelProduct.SingleTravelProductResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.service.partners.PartnerCompanyService;
import com.somartreview.reviewmate.service.partners.PartnerSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.somartreview.reviewmate.exception.ErrorCode.TRAVEL_PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SingleTravelProductService {

    private final SingleTravelProductRepository singleTravelProductRepository;
    private final TravelProductService travelProductService;
    private final PartnerCompanyService partnerCompanyService;
    private final PartnerSellerService partnerSellerService;


    @Transactional
    public SingleTravelProduct create(String partnerDomain, SingleTravelProductCreateRequest request, MultipartFile thumbnail) {
        partnerCompanyService.validateExistPartnerDomain(partnerDomain);

        final PartnerCompany partnerCompany = partnerCompanyService.findByDomain(partnerDomain);
        final PartnerSeller partnerSeller = partnerSellerService.findById(request.getPartnerSellerId());

        String thumbnailUrl = uploadThumbnailOnS3(thumbnail);

        return singleTravelProductRepository.save(request.toEntity(partnerDomain, thumbnailUrl, partnerCompany, partnerSeller));
    }

    public SingleTravelProduct findByTravelProductId(TravelProductIdDto travelProductIdDto) {
        return singleTravelProductRepository.findByTravelProductId(travelProductIdDto.toEntity())
                .orElseThrow(() -> new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND));
    }

    public List<SingleTravelProduct> findAllByPartnerDomainAndTravelProductCategory(String partnerDomain, TravelProductCategory travelProductCategory) {
        return singleTravelProductRepository.findAllByTravelProductId_PartnerDomainAndTravelProductCategory(partnerDomain, travelProductCategory);
    }

    public SingleTravelProductResponse getSingleTravelProductResponseByTravelProductId(TravelProductIdDto travelProductIdDto) {
        SingleTravelProduct singleTravelProduct = findByTravelProductId(travelProductIdDto);

        return new SingleTravelProductResponse(singleTravelProduct);
    }

    public SingleTravelProductConsoleElementResponse getSingleTravelProductConsoleElementResponseByTravelProductId(TravelProductIdDto travelProductIdDto) {
        SingleTravelProduct singleTravelProduct = findByTravelProductId(travelProductIdDto);

        return new SingleTravelProductConsoleElementResponse(singleTravelProduct);
    }

    public List<SingleTravelProductResponse> getSingleTravelProductResponsesByPartnerDomainAndCategory(String partnerDomain, TravelProductCategory travelProductCategory) {
        return findAllByPartnerDomainAndTravelProductCategory(partnerDomain, travelProductCategory)
                .stream()
                .map(SingleTravelProductResponse::new)
                .toList();
    }

    public List<SingleTravelProductConsoleElementResponse> getSingleTravelProductConsoleElementResponsesByPartnerDomainAndCategory(String partnerDomain, TravelProductCategory travelProductCategory) {
        return findAllByPartnerDomainAndTravelProductCategory(partnerDomain, travelProductCategory)
                .stream()
                .map(SingleTravelProductConsoleElementResponse::new)
                .toList();
    }

    @Transactional
    public void updateByTravelProductId(TravelProductIdDto travelProductIdDto, SingleTravelProductUpdateRequest request, MultipartFile thumbnail) {
        SingleTravelProduct foundTravelProduct = findByTravelProductId(travelProductIdDto);
        String thumbnailUrl = uploadThumbnailOnS3(thumbnail);

        foundTravelProduct.update(request, thumbnailUrl);
    }

    private String uploadThumbnailOnS3(MultipartFile thumbnail) {
        //  TODO: Upload thumbnail to S3 and get the url
        if (thumbnail != null) {
            return null;
        }

        return "https://www.testThumbnailUrl.com";
    }

    @Transactional
    public void deleteByTravelProductId(TravelProductIdDto travelProductIdDto) {
        validateExistTravelProductId(travelProductIdDto);

        singleTravelProductRepository.deleteByTravelProductId(travelProductIdDto.toEntity());
    }

    public void validateExistTravelProductId(TravelProductIdDto travelProductIdDto) {
        if (!singleTravelProductRepository.existsByTravelProductId(travelProductIdDto.toEntity()))
            throw new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND);
    }
}
