package com.somartreview.reviewmate.domain.PartnerCompany;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.PartnerManager.PartnerManager;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import com.somartreview.reviewmate.dto.request.PartnerCompanyUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PartnerCompany extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 255;


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_company_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String domain;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isWithdrawn = false;

    @Column(nullable = false)
    @OneToMany(mappedBy = "partnerCompany")
    private List<PartnerManager> partnerManagers = new ArrayList<>();

    @Builder
    public PartnerCompany(String domain, String name) {
        this.domain = domain;
        validateName(name);
        this.name = name;
    }

    private void validateName(final String name) {
        if (name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            throw new DomainLogicException(ErrorCode.PARTNER_COMPANY_NAME_ERROR);
        }
    }

    public void addPartnerManager(PartnerManager partnerManager) {
        this.partnerManagers.add(partnerManager);
    }

    public void update(PartnerCompanyUpdateRequest request) {
        this.domain = domain;
        validateName(request.getName());
        this.name = request.getName();
    }
}
