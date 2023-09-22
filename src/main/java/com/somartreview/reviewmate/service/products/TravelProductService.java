package com.somartreview.reviewmate.service.products;

import com.somartreview.reviewmate.domain.product.TravelProductRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class TravelProductService {

    private final TravelProductRepository travelProductRepository;


    public void validateExistTravelProduct(Long id) {
        if (!travelProductRepository.existsById(id)) {
            throw new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND);
        }
    }
}
