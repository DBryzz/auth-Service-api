package dbryzz.services.auth.model;

import dbryzz.services.auth.constant.DeviceType;
import dbryzz.services.auth.model.audit.DateAudit;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "user_device_tbl")
public class UserDevice extends DateAudit {

    @Id
    @Column(name = "user_device_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_device_seq")
    @SequenceGenerator(name = "user_device_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "device_type")
    @Enumerated(value = EnumType.STRING)
    private DeviceType deviceType;

    @Column(name = "notificattion_token")
    private String notificationToken;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @OneToOne(optional = false, mappedBy = "userDevice")
    private RefreshToken refreshToken;

    @Column(name = "is_refresh_active")
    private Boolean isRefreshActive;


}
