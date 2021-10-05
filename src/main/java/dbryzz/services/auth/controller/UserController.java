package dbryzz.services.auth.controller;

import dbryzz.services.auth.dto.UserNameDTO;
import dbryzz.services.auth.dto.UserNameEmailDTO;
import dbryzz.services.auth.dto.payload.request.LogoutRequest;
import dbryzz.services.auth.dto.payload.request.UpdatePasswordRequest;
import dbryzz.services.auth.dto.payload.response.ApiResponse;
import dbryzz.services.auth.event.OnUserAccountChangeEvent;
import dbryzz.services.auth.event.OnUserLogoutSuccessEvent;
import dbryzz.services.auth.exception.UpdatePasswordException;
import dbryzz.services.auth.service.AuthService;
import dbryzz.services.auth.service.AuthUserService;
import dbryzz.services.auth.service.UserService;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    private final AuthService authService;

    private final UserService userService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final AuthUserService authUserService;

    @Autowired
    public UserController(AuthService authService, UserService userService, ApplicationEventPublisher applicationEventPublisher, AuthUserService authUserService) {
        this.authService = authService;
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.authUserService = authUserService;
    }

    /**
     * Gets the current user profile of the logged in user
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getUserProfile() {
        System.out.println(authUserService.getAuthUserId());
        return ResponseEntity.ok(authUserService.getAuthUserId());
    }

    @GetMapping("/info")
    public ResponseEntity<UserNameEmailDTO> getUserInformation() {
        return ResponseEntity.ok(userService.getUserInformation(authUserService.getAuthUserId()));
    }

    @PutMapping("/name/update")
    public ResponseEntity<UserNameEmailDTO> UpdateUserName(@RequestBody UserNameDTO userNameDTO){
        return ResponseEntity.ok(userService.UpdateUserName(authUserService.getAuthUserId(), userNameDTO));
    }

    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getAllAdmins() {
        logger.info("Inside secured resource with admin");
        return ResponseEntity.ok("Hello. This is about admins");
    }

    /**
     * Updates the password of the current logged in user
     */
    @PostMapping("/password/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateUserPassword(@ApiParam(value = "The UpdatePasswordRequest payload") @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {

        return authService.updatePassword(authUserService.getAuthUserId(), updatePasswordRequest)
                .map(updatedUser -> {
                    OnUserAccountChangeEvent onUserPasswordChangeEvent = new OnUserAccountChangeEvent(updatedUser, "Update Password", "Change successful");
                    applicationEventPublisher.publishEvent(onUserPasswordChangeEvent);
                    return ResponseEntity.ok(new ApiResponse(true, "Password changed successfully"));
                })
                .orElseThrow(() -> new UpdatePasswordException("--Empty--", "No such user present."));
    }

    /**
     * Log the user out from the app/device. Release the refresh token associated with the
     * user device.
     */
    @PostMapping("/logout")
    public ResponseEntity logoutUser(@ApiParam(value = "The LogoutRequest payload") @Valid @RequestBody LogoutRequest logoutRequest) {
        userService.logoutUser(authUserService.getAuthUserId(), logoutRequest);
        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();

        OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(userService.findById(authUserService.getAuthUserId()).get().getEmail(), credentials.toString(), logoutRequest);
        applicationEventPublisher.publishEvent(logoutSuccessEvent);
        return ResponseEntity.ok(new ApiResponse(true, "Log out successful"));
    }
}
