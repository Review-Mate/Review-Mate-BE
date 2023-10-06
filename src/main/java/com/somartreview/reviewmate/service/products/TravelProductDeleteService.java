package com.somartreview.reviewmate.service.products;

import com.somartreview.reviewmate.domain.product.SingleTravelProduct;
import com.somartreview.reviewmate.domain.product.TravelProduct;
import com.somartreview.reviewmate.domain.product.TravelProductRepository;
import com.somartreview.reviewmate.domain.reservation.Reservation;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.service.ReservationDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.somartreview.reviewmate.exception.ErrorCode.TRAVEL_PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TravelProductDeleteService {

    private final TravelProductRepository travelProductRepository;
    private final TravelProductService travelProductService;
    private final ReservationDeleteService reservationDeleteService;


    @Transactional
    public void delete(Long id) {
        validateExistId(id);

        reservationDeleteService.deleteAllByTravelProductId(id);
        travelProductRepository.deleteById(id);
    }

    private void validateExistId(Long id) {
        if (!travelProductRepository.existsById(id))
            throw new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND);
    }


    public void deleteAllByPartnerDomain(String partnerDomain) {
        travelProductRepository.deleteAllByPartnerCompany_PartnerDomain(partnerDomain);
    }

    @Transactional
    public void delete(String partnerDomain, String partnerCustomId) {
        Long id = travelProductService.findByPartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId).getId();

        delete(id);
    }

    public void deleteAllByPartnerSellerId(Long partnerSellerId) {
        List<Long> ids = travelProductRepository.findTravelProductIdsByPartnerSeller_Id(partnerSellerId);

        ids.forEach(id -> {
            reservationDeleteService.deleteAllByTravelProductId(id);
            travelProductRepository.deleteById(id);
        });
    }
}
