package com.somartreview.reviewmate.service.partners.console;

import com.somartreview.reviewmate.domain.partner.console.ConsoleTimeSeriesUnit;
import com.somartreview.reviewmate.domain.partner.console.PartnerConsoleConfig;
import com.somartreview.reviewmate.domain.partner.console.PartnerConsoleConfigRepository;
import com.somartreview.reviewmate.dto.partner.console.PartnerConsoleConfigUpdateRequest;
import com.somartreview.reviewmate.service.partners.company.PartnerCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PartnerConsoleConfigService {

    private final PartnerConsoleConfigRepository partnerConsoleConfigRepository;
    private final PartnerCompanyService partnerCompanyService;


    public Long create(String partnerDomain) {
        PartnerConsoleConfig partnerConsoleConfig = new PartnerConsoleConfig(partnerDomain);

        return partnerConsoleConfigRepository.save(partnerConsoleConfig).getId();
    }

    @Transactional
    public Long update(String partnerDomain, PartnerConsoleConfigUpdateRequest request) {
        partnerCompanyService.validateExistPartnerDomain(partnerDomain);

        PartnerConsoleConfig partnerConsoleConfig = partnerConsoleConfigRepository.findByPartnerDomain(partnerDomain);
        partnerConsoleConfig.update(request);

        return partnerConsoleConfig.getId();
    }

    public Float getTargetReviewingRate(String partnerDomain) {
        return partnerConsoleConfigRepository.findTargetReviewingRateByPartnerDomain(partnerDomain);
    }

    public ConsoleTimeSeriesUnit getAchievementTimeSeriesUnit(String partnerDomain) {
        return partnerConsoleConfigRepository.findAchievementTimeSeriesUnitByPartnerDomain(partnerDomain);
    }

    public void delete(String partnerDomain) {
        partnerConsoleConfigRepository.deleteByPartnerDomain(partnerDomain);
    }
}
