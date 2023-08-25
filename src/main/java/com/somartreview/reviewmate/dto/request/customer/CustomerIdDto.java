package com.somartreview.reviewmate.dto.request.customer;

import com.somartreview.reviewmate.domain.Customer.CustomerId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerIdDto {

    String partnerCustomId;

    String partnerDomain;

    public CustomerId toEntity() {
        return CustomerId.builder()
                .partnerCustomId(partnerCustomId)
                .partnerDomain(partnerDomain)
                .build();
    }
}
