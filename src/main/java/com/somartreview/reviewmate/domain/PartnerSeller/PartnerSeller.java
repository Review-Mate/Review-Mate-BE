package com.somartreview.reviewmate.domain.PartnerSeller;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor
public class PartnerSeller extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 255;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    private static final Pattern ONLY_NUMBER_PATTERN = Pattern.compile("[0-9]+");
    private static final int MIN_PASSWORD_LENGTH = 8;


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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_company_id", nullable = false)
    private PartnerCompany partnerCompany;

    public PartnerSeller(String name, String email, String phoneNumber, String kakaoId, String password, PartnerCompany partnerCompany) {
        validateName(name);
        this.name = name;
        validateEmail(email);
        this.email = email;
        validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
        this.kakaoId = kakaoId;
        validatePassword(password);
        this.password = password;
        this.partnerCompany = partnerCompany;
    }

    private void validateName(final String name) {
        if (name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            throw new DomainLogicException(ErrorCode.PARTNER_SELLER_NAME_ERROR);
        }
    }

    private void validateEmail(final String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new DomainLogicException(ErrorCode.PARTNER_SELLER_EMAIL_ERROR);
        }
    }

    private void validatePhoneNumber(final String phoneNumber) {
        if (phoneNumber.isBlank() || !ONLY_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new DomainLogicException(ErrorCode.CUSTOMER_PHONE_NUMBER_ERROR);
        }
    }

    private void validatePassword(final String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new DomainLogicException(ErrorCode.PARTNER_SELLER_PASSWORD_ERROR);
        }
    }
}