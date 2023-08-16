package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.domain.TravelProduct.SingleTravelProduct;
import com.somartreview.reviewmate.domain.TravelProduct.SingleTravelProductRepository;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductCreateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.somartreview.reviewmate.exception.ErrorCode.TRAVEL_PRODUCT_DUPLICATED_PARTNER_ID;
import static com.somartreview.reviewmate.exception.ErrorCode.TRAVEL_PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SingleTravelProductService {

    private final SingleTravelProductRepository singleTravelProductRepository;
    private final PartnerCompanyService partnerCompanyService;
    private final PartnerSellerService partnerSellerService;

    @Transactional
    public Long createSingleTravelProduct(SingleTravelProductCreateRequest request) {
        final PartnerCompany partnerCompany = partnerCompanyService.findPartnerCompanyById(request.getPartnerCompanyId());
        final PartnerSeller partnerSeller = partnerSellerService.findPartnerSellerById(request.getPartnerSellerId());

        return singleTravelProductRepository.save(request.toEntity(partnerCompany, partnerSeller)).getId();
    }

    @Transactional
    public void updateSingleTravelProductById(Long id, SingleTravelProductUpdateRequest request, MultipartFile thumbnail) {
        SingleTravelProduct foundTravelProduct = singleTravelProductRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND));

        updateSingleTravelProduct(foundTravelProduct, request, thumbnail);
    }

    @Transactional
    public void updateSingleTravelProductByPartnerTravelProductId(String partnerProductId, SingleTravelProductUpdateRequest request, MultipartFile thumbnail) {
        SingleTravelProduct foundTravelProduct = singleTravelProductRepository.findByPartnerTravelProductId(partnerProductId)
                .orElseThrow(() -> new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND));

        updateSingleTravelProduct(foundTravelProduct, request, thumbnail);
    }

    private void updateSingleTravelProduct(SingleTravelProduct foundTravelProduct, SingleTravelProductUpdateRequest request, MultipartFile thumbnail) {
        validateUpdatePartnerTravelProductId(request.getPartnerSingleTravelProductId());

        final PartnerCompany partnerCompany = partnerCompanyService.findPartnerCompanyById(request.getPartnerCompanyId());
        final PartnerSeller partnerSeller = partnerSellerService.findPartnerSellerById(request.getPartnerSellerId());


        String thumbnailUrl = uploadThumbnailOnS3(thumbnail);

        foundTravelProduct.update(request, thumbnailUrl, partnerCompany, partnerSeller);
    }

    private void validateUpdatePartnerTravelProductId(String partnerTravelProductId) {
        if (singleTravelProductRepository.countByPartnerTravelProductId(partnerTravelProductId) >= 2) {
            throw new DomainLogicException(TRAVEL_PRODUCT_DUPLICATED_PARTNER_ID);
        }
    }

    private String uploadThumbnailOnS3(MultipartFile thumbnail) {
        //  TODO: Upload thumbnail to S3 and get the url
        return "";
    }

    @Transactional
    public void deleteSingleTravelProductById(Long id) {
        SingleTravelProduct foundTravelProduct = singleTravelProductRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND));

        singleTravelProductRepository.delete(foundTravelProduct);
    }

    @Transactional
    public void deleteSingleTravelProductByPartnerProductId(String partnerProductId) {
        SingleTravelProduct foundTravelProduct = singleTravelProductRepository.findByPartnerTravelProductId(partnerProductId)
                .orElseThrow(() -> new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND));

        singleTravelProductRepository.delete(foundTravelProduct);
    }
}
