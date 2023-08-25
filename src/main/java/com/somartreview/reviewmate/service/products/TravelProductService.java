package com.somartreview.reviewmate.service.products;

import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProductRepository;
import com.somartreview.reviewmate.dto.request.travelProduct.TravelProductIdDto;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class TravelProductService {

    private final TravelProductRepository travelProductRepository;


    public void validateExistTravelProduct(TravelProductIdDto travelProductIdDto) {
        if (!travelProductRepository.existsByTravelProductId(travelProductIdDto.toEntity())) {
            throw new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND);
        }
    }

    public TravelProduct findByTravelProductId(TravelProductIdDto travelProductIdDto) {
        return travelProductRepository.findByTravelProductId(travelProductIdDto.toEntity())
                .orElseThrow(() -> new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND));
    }
}
