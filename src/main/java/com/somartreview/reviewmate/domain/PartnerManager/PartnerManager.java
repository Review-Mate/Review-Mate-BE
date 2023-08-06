package com.somartreview.reviewmate.domain.PartnerManager;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerCompany.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;

@Entity
@Getter
@NoArgsConstructor
public class PartnerManager extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "partner_manager_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(STRING)
    private Role role;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_company_id", nullable = false)
    private PartnerCompany partnerCompany;

    public PartnerManager(String name, String email, String password, PartnerCompany partnerCompany) {
        this.role = Role.ADMIN;
        this.name = name;
        this.email = email;
        this.password = password;
        partnerCompany.addPartnerManager(this);
        this.partnerCompany = partnerCompany;
    }

    public PartnerManager(Role role, String name, String email, String password, PartnerCompany partnerCompany) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
        partnerCompany.addPartnerManager(this);
        this.partnerCompany = partnerCompany;
    }
}
