package com.somartreview.reviewmate.domain.Customer;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor
public class Customer extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 255;
    private static final Pattern ONLY_NUMBER_PATTERN = Pattern.compile("[0-9]+");


    @Id @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String clientSideUserId;

    @Column(nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String kakaoId;

    @Builder
    public Customer(String clientSideUserId, String name, String phoneNumber, String kakaoId) {
        this.clientSideUserId = clientSideUserId;
        validateName(name);
        this.name = name;
        validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
        this.kakaoId = kakaoId;
    }

    private void validateName(final String name) {
        if (name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            throw new DomainLogicException(ErrorCode.CUSTOMER_NAME_ERROR);
        }
    }

    private void validatePhoneNumber(final String phoneNumber) {
        if (phoneNumber.isBlank() || !ONLY_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new DomainLogicException(ErrorCode.CUSTOMER_PHONE_NUMBER_ERROR);
        }
    }
}
