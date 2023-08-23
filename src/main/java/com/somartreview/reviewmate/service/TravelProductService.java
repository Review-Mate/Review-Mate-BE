package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProductRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class TravelProductService {

    private final TravelProductRepository travelProductRepository;

    public TravelProduct findTravelProductById(Long id) {
        return travelProductRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND));
    }

    public TravelProduct findTravelProductByPartnerTravelProductId(String partnerTravelProductId) {
        return travelProductRepository.findByPartnerTravelProductId(partnerTravelProductId)
                .orElseThrow(() -> new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND));
    }
}
