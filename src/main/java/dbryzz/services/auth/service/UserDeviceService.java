package dbryzz.services.auth.service;

import dbryzz.services.auth.dto.payload.DeviceInfo;
import dbryzz.services.auth.model.RefreshToken;
import dbryzz.services.auth.model.UserDevice;

import java.util.Optional;

public interface UserDeviceService {

    Optional<UserDevice> findByUserId(Long userId);

    Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken);

    UserDevice createUserDevice(DeviceInfo deviceInfo);

    void verifyRefreshAvailability(RefreshToken refreshToken);
}
