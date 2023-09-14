package com.somartreview.reviewmate.service.products;

import com.somartreview.reviewmate.domain.product.SingleTravelProduct;
import com.somartreview.reviewmate.domain.product.SingleTravelProductCategory;
import com.somartreview.reviewmate.domain.product.SingleTravelProductRepository;
import com.somartreview.reviewmate.dto.product.SingleTravelProductCreateRequest;
import com.somartreview.reviewmate.dto.product.SingleTravelProductUpdateRequest;
import com.somartreview.reviewmate.dto.product.SingleTravelProductConsoleElementResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.service.ReservationService;
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
    private final ReservationService reservationService;


    @Transactional
    public SingleTravelProduct retreiveSingleTravelProduct(SingleTravelProductCreateRequest singleTravelProductCreateRequest, MultipartFile thumbnailFile, String partnerDomain) {

        create(singleTravelProductCreateRequest, thumbnailFile, partnerDomain);
        return findByPartnerDomainAndPartnerCustomId(partnerDomain, singleTravelProductCreateRequest.getPartnerCustomId());
    }

    @Transactional
    public Long create(SingleTravelProductCreateRequest request, MultipartFile thumbnailFile, String partnerDomain) {
        validateUniquePartnerCustomId(partnerDomain, request.getPartnerCustomId());

        String thumbnailUrl = uploadThumbnailOnS3(thumbnailFile);

        return singleTravelProductRepository.save(request.toEntity(thumbnailUrl)).getId();
    }

    private void validateUniquePartnerCustomId(String partnerDomain, String partnerCustomId) {
        if (existsByPartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId))
            throw new DomainLogicException(TRAVEL_PRODUCT_NOT_UNIQUE_PARTNER_CUSTOM_ID);
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
        //  Impl Uploading thumbnail to S3 and get the url
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

        reservationService.deleteAllByTravelProductId(travelProductId);
        singleTravelProductRepository.deleteById(travelProductId);
    }

    public void validateExistTravelProductId(Long travelProductId) {
        if (!singleTravelProductRepository.existsById(travelProductId))
            throw new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND);
    }

    public void deleteAllByPartnerSellerId(Long partnerSellerId) {
        List<SingleTravelProduct> singleTravelProducts = singleTravelProductRepository.findAllByPartnerSeller_Id(partnerSellerId);
        singleTravelProducts.forEach(singleTravelProduct -> {
            reservationService.deleteAllByTravelProductId(singleTravelProduct.getId());
            singleTravelProductRepository.deleteById(singleTravelProduct.getId());
        });
    }
}
