package dbryzz.services.auth.model;

import dbryzz.services.auth.model.audit.DateAudit;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity(name = "refresh_token_tbl")
public class RefreshToken extends DateAudit {

    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq")
    @SequenceGenerator(name = "refresh_token_seq", allocationSize = 1)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    @NaturalId(mutable = true)
    private String token;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_device_id", unique = true)
    private UserDevice userDevice;

    @Column(name = "refresh_count")
    private Long refreshCount;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;
}
