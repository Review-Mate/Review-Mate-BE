package com.somartreview.reviewmate.domain.PartnerCompany;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.PartnerManager.PartnerManager;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PartnerCompany extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "partner_company_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "partnerCompany")
    private List<TravelProduct> travelProducts = new ArrayList<>();

    @OneToMany(mappedBy = "partnerCompany")
    private List<PartnerSeller> partnerSellers = new ArrayList<>();

    @Column(nullable = false)
    @OneToMany(mappedBy = "partnerCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartnerManager> partnerManagers = new ArrayList<>();

    public PartnerCompany(String name) {
        this.name = name;
    }

    public void addTravelProduct(TravelProduct travelProduct) {
        this.travelProducts.add(travelProduct);
    }

    public void addPartnerSeller(PartnerSeller partnerSeller) {
        this.partnerSellers.add(partnerSeller);
    }

    public void addPartnerManager(PartnerManager partnerManager) {
        this.partnerManagers.add(partnerManager);
    }
}
