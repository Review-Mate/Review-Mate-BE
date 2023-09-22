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
    private final ReservationDeleteService reservationDeleteService;


    public void deleteAllByPartnerDomain(String partnerDomain) {
        travelProductRepository.deleteAllByPartnerCompany_PartnerDomain(partnerDomain);
    }

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

    @Transactional
    public void delete(String partnerDomain, String partnerCustomId) {
        validateExistPartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);

        travelProductRepository.deleteByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);
    }

    private void validateExistPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        if (!travelProductRepository.existsByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId))
            throw new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND);
    }

    public void deleteAllByPartnerSellerId(Long partnerSellerId) {
        List<TravelProduct> travelProducts = travelProductRepository.findAllByPartnerSeller_Id(partnerSellerId);

        travelProducts.forEach(travelProduct -> {
            reservationDeleteService.deleteAllByTravelProductId(travelProduct.getId());
            travelProductRepository.deleteById(travelProduct.getId());
        });
    }
}
