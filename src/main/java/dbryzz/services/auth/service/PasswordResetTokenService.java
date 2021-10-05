package dbryzz.services.auth.service;

import dbryzz.services.auth.model.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenService {
    PasswordResetToken save(PasswordResetToken passwordResetToken);

    Optional<PasswordResetToken> findByToken(String token);

    PasswordResetToken createToken();

    void verifyExpiration(PasswordResetToken token);
}
