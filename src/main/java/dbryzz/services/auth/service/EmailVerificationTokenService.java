package dbryzz.services.auth.service;

import dbryzz.services.auth.model.EmailVerificationToken;
import dbryzz.services.auth.model.User;

import java.util.Optional;

public interface EmailVerificationTokenService {
    void createVerificationToken(User user, String token);

    EmailVerificationToken updateExistingTokenWithNameAndExpiry(EmailVerificationToken existingToken);

    Optional<EmailVerificationToken> findByToken(String token);

    EmailVerificationToken save(EmailVerificationToken emailVerificationToken);

    String generateNewToken();

    void verifyExpiration(EmailVerificationToken token);
}
