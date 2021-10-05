package dbryzz.services.auth.service.impl;

import dbryzz.services.auth.dto.payload.DeviceInfo;
import dbryzz.services.auth.exception.TokenRefreshException;
import dbryzz.services.auth.model.RefreshToken;
import dbryzz.services.auth.model.UserDevice;
import dbryzz.services.auth.repository.UserDeviceRepository;
import dbryzz.services.auth.service.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDeviceServiceImpl implements UserDeviceService {

    private final UserDeviceRepository userDeviceRepository;

    @Autowired
    public UserDeviceServiceImpl(UserDeviceRepository userDeviceRepository) {
        this.userDeviceRepository = userDeviceRepository;
    }

    /**
     * Find the user device info by user id
     */
    @Override
    public Optional<UserDevice> findByUserId(Long userId) {
        return userDeviceRepository.findByUserId(userId);
    }

    /**
     * Find the user device info by refresh token
     */
    @Override
    public Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken) {
        return userDeviceRepository.findByRefreshToken(refreshToken);
    }

    /**
     * Creates a new user device and set the user to the current device
     */
    @Override
    public UserDevice createUserDevice(DeviceInfo deviceInfo) {
        UserDevice userDevice = new UserDevice();
        userDevice.setDeviceId(deviceInfo.getDeviceId());
        userDevice.setDeviceType(deviceInfo.getDeviceType());
        userDevice.setNotificationToken(deviceInfo.getNotificationToken());
        userDevice.setRefreshActive(true);
        return userDevice;
    }

    /**
     * Check whether the user device corresponding to the token has refresh enabled and
     * throw appropriate errors to the client
     */
    @Override
    public void verifyRefreshAvailability(RefreshToken refreshToken) {
        UserDevice userDevice = findByRefreshToken(refreshToken)
                .orElseThrow(() -> new TokenRefreshException(refreshToken.getToken(), "No device found for the matching token. Please login again"));

        if (!userDevice.getRefreshActive()) {
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh blocked for the device. Please login through a different device");
        }
    }
}
