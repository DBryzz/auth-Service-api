package dbryzz.services.auth.service.impl;

import dbryzz.services.auth.exception.InvalidTokenRequestException;
import dbryzz.services.auth.model.PasswordResetToken;
import dbryzz.services.auth.repository.PasswordResetTokenRepository;
import dbryzz.services.auth.service.PasswordResetTokenService;
import dbryzz.services.auth.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${app.token.password.reset.duration}")
    private Long expiration;

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    /**
     * Saves the given password reset token
     */
    @Override
    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    /**
     * Finds a token in the database given its naturalId
     */
    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    /**
     * Creates and returns a new password token to which a user must be associated
     */
    @Override
    public PasswordResetToken createToken() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        String token = Util.generateRandomUuid();
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(Instant.now().plusMillis(expiration));
        return passwordResetToken;
    }

    /**
     * Verify whether the token provided has expired or not on the basis of the current
     * server time and/or throw error otherwise
     */
    @Override
    public void verifyExpiration(PasswordResetToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new InvalidTokenRequestException("Password Reset Token", token.getToken(),
                    "Expired token. Please issue a new request");
        }
    }
}
