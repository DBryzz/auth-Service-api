package dbryzz.services.auth.service;

import dbryzz.services.auth.dto.payload.request.*;
import dbryzz.services.auth.model.EmailVerificationToken;
import dbryzz.services.auth.model.PasswordResetToken;
import dbryzz.services.auth.model.RefreshToken;
import dbryzz.services.auth.model.User;
import dbryzz.services.auth.security.service.UserDetailsImpl;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface AuthService {

    Optional<User> registerUser(RegistrationRequest newRegistrationRequest);

    Boolean emailAlreadyExists(String email);

    Boolean usernameAlreadyExists(String username);

    Optional<Authentication> authenticateUser(LoginRequest loginRequest);

    Optional<User> confirmEmailRegistration(String emailToken);

    Optional<EmailVerificationToken> recreateRegistrationToken(String existingToken);

    Boolean currentPasswordMatches(User currentUser, String password);

    Optional<User> updatePassword(Long id,
                                  UpdatePasswordRequest updatePasswordRequest);

    String generateToken(UserDetailsImpl userDetails);

    String generateTokenFromUserId(Long userId);

    Optional<RefreshToken> createAndPersistRefreshTokenForDevice(Authentication authentication, LoginRequest loginRequest);

    Optional<String> refreshJwtToken(TokenRefreshRequest tokenRefreshRequest);

    Optional<PasswordResetToken> generatePasswordResetToken(PasswordResetLinkRequest passwordResetLinkRequest);

    Optional<User> resetPassword(PasswordResetRequest passwordResetRequest);
}
