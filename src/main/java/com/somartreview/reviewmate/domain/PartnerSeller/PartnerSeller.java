package com.somartreview.reviewmate.domain.PartnerSeller;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PartnerSeller extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "partner_seller_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String kakaoId;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "partnerSeller")
    private List<TravelProduct> travelProducts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_company_id", nullable = false)
    private PartnerCompany partnerCompany;

    public PartnerSeller(String name, String email, String phoneNumber, String kakaoId, String password, PartnerCompany partnerCompany) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.kakaoId = kakaoId;
        this.password = password;
        partnerCompany.addPartnerSeller(this);
        this.partnerCompany = partnerCompany;
    }

    public void addTravelProduct(TravelProduct travelProduct) {
        this.travelProducts.add(travelProduct);
    }
}