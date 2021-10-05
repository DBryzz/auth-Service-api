package dbryzz.services.auth.service.impl;

import dbryzz.services.auth.exception.TokenRefreshException;
import dbryzz.services.auth.model.RefreshToken;
import dbryzz.services.auth.repository.RefreshTokenRepository;
import dbryzz.services.auth.service.RefreshTokenService;
import dbryzz.services.auth.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.token.refresh.duration}")
    private Long refreshTokenDurationMs;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    /**
     * Find a refresh token based on the natural id i.e the token itself
     */
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Persist the updated refreshToken instance to database
     */
    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Creates and returns a new refresh token
     */
    @Override
    public RefreshToken createRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(Util.generateRandomUuid());
        refreshToken.setRefreshCount(0L);
        return refreshToken;
    }

    /**
     * Verify whether the token provided has expired or not on the basis of the current
     * server time and/or throw error otherwise
     */
    @Override
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new TokenRefreshException(token.getToken(), "Expired token. Please issue a new request");
        }
    }

    /**
     * Delete the refresh token associated with the user device
     */
    @Override
    public void deleteById(Long id) {
        refreshTokenRepository.deleteById(id);
    }

    /**
     * Increase the count of the token usage in the database. Useful for
     * audit purposes
     */
    @Override
    public void increaseCount(RefreshToken refreshToken) {
        refreshToken.incrementRefreshCount();
        save(refreshToken);
    }
}
