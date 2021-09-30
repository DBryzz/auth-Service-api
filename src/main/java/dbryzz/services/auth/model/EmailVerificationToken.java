package dbryzz.services.auth.model;

import dbryzz.services.auth.constant.TokenStatus;
import dbryzz.services.auth.model.audit.DateAudit;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity(name = "email_verification_token_tbl")
public class EmailVerificationToken extends DateAudit {

    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_token_seq")
    @SequenceGenerator(name = "email_token_seq", allocationSize = 1)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "token_status")
    @Enumerated(EnumType.STRING)
    private TokenStatus tokenStatus;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;
}
