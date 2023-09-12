package com.somartreview.reviewmate.domain.partner.manager;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.dto.partner.manager.PartnerManagerUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

import static javax.persistence.EnumType.*;

@Entity
@Getter
@NoArgsConstructor
public class PartnerManager extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 255;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    private static final int MIN_PASSWORD_LENGTH = 8;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_manager_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(STRING)
    private PartnerManagerRole role;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_company_id", nullable = false)
    private PartnerCompany partnerCompany;

//    @Builder
    public PartnerManager(String name, String email, String password, PartnerCompany partnerCompany) {
        this.role = PartnerManagerRole.ADMIN;
        validateName(name);
        this.name = name;
        validateEmail(email);
        this.email = email;
        validatePassword(password);
        this.password = password;
        partnerCompany.addPartnerManager(this);
        this.partnerCompany = partnerCompany;
    }

    @Builder
    public PartnerManager(PartnerManagerRole role, String name, String email, String password, PartnerCompany partnerCompany) {
        this.role = role;
        validateName(name);
        this.name = name;
        validateEmail(email);
        this.email = email;
        validatePassword(password);
        this.password = password;
        partnerCompany.addPartnerManager(this);
        this.partnerCompany = partnerCompany;
    }

    private void validateName(final String name) {
        if (name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            throw new DomainLogicException(ErrorCode.PARTNER_MANAGER_NAME_ERROR);
        }
    }

    private void validateEmail(final String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new DomainLogicException(ErrorCode.PARTNER_MANAGER_EMAIL_ERROR);
        }
    }

    private void validatePassword(final String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new DomainLogicException(ErrorCode.PARTNER_MANAGER_PASSWORD_ERROR);
        }
    }

    public void update(PartnerManagerUpdateRequest request) {
        validateName(request.getName());
        this.name = request.getName();
        validateEmail(request.getEmail());
        this.email = request.getEmail();
        validatePassword(request.getPassword());
        this.password = request.getPassword();
    }
}
