package dbryzz.services.auth.service.impl;

import dbryzz.services.auth.dto.UserNameDTO;
import dbryzz.services.auth.dto.UserNameEmailDTO;
import dbryzz.services.auth.dto.payload.request.LogoutRequest;
import dbryzz.services.auth.dto.payload.request.RegistrationRequest;
import dbryzz.services.auth.exception.ResourceNotFoundException;
import dbryzz.services.auth.exception.UserLogoutException;
import dbryzz.services.auth.model.Role;
import dbryzz.services.auth.model.User;
import dbryzz.services.auth.model.UserDevice;
import dbryzz.services.auth.repository.UserRepository;
import dbryzz.services.auth.service.RefreshTokenService;
import dbryzz.services.auth.service.RoleService;
import dbryzz.services.auth.service.UserDeviceService;
import dbryzz.services.auth.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserDeviceService userDeviceService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleService roleService, UserDeviceService userDeviceService, RefreshTokenService refreshTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userDeviceService = userDeviceService;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Finds a user in the database by username
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Finds a user in the database by email
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Find a user in db by id.
     */
    @Override
    public Optional<User> findById(Long Id) {
        return userRepository.findById(Id);
    }

    /**
     * Save the user to the database
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Check is the user exists given the email: naturalId
     */
    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Check is the user exists given the username: naturalId
     */
    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User getUserById(Long Id){
        Optional<User> userOptional = findById(Id);
        if(!userOptional.isPresent()){
            throw  new ResourceNotFoundException("user","id",Id.toString());
        }
        return userOptional.get();
    }
    /**
     * Creates a new user from the registration request
     */
    @Override
    public User createUser(RegistrationRequest registerRequest) {
        User newUser = new User();
        Boolean isNewUserAsAdmin = registerRequest.getRegisterAsAdmin();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setUsername(registerRequest.getUsername());
        newUser.addRoles(getRolesForNewUser(isNewUserAsAdmin));
        newUser.setActive(true);
        newUser.setEmailVerified(false);
        return newUser;
    }

    /**
     * Performs a quick check to see what roles the new user could be assigned to.
     *
     * @return list of roles for the new user
     */
    @Override
    public Set<Role> getRolesForNewUser(Boolean isToBeMadeAdmin) {
        Set<Role> newUserRoles = new HashSet<>(roleService.findAll());
        if (!isToBeMadeAdmin) {
            newUserRoles.removeIf(Role::isAdminRole);
        }
        logger.info("Setting user roles: " + newUserRoles);
        return newUserRoles;
    }

    /**
     * Log the given user out and delete the refresh token associated with it. If no device
     * id is found matching the database for the given user, throw a log out exception.
     */
    @Override
    public void logoutUser(Long userId, LogoutRequest logOutRequest) {
        String deviceId = logOutRequest.getDeviceInfo().getDeviceId();
        UserDevice userDevice = userDeviceService.findByUserId(userId)
                .filter(device -> device.getDeviceId().equals(deviceId))
                .orElseThrow(() -> new UserLogoutException(logOutRequest.getDeviceInfo().getDeviceId(), "Invalid device Id supplied. No matching device found for the given user "));

        logger.info("Removing refresh token associated with device [" + userDevice + "]");
        refreshTokenService.deleteById(userDevice.getRefreshToken().getId());
    }

    @Override
    public UserNameEmailDTO getUserInformation(Long userId){
        User user = getUserById(userId);
        UserNameEmailDTO userNameEmailDTO  = new UserNameEmailDTO();
        userNameEmailDTO.setUsername(user.getUsername());
        userNameEmailDTO.setEmail(user.getEmail());
        return userNameEmailDTO;
    }

    @Override
    public UserNameEmailDTO UpdateUserName(Long userId, UserNameDTO userNameDTO){
        User user = getUserById(userId);
        user.setUsername(userNameDTO.getUserName());
        User savedUser = userRepository.save(user);

        UserNameEmailDTO userNameEmailDTO  = new UserNameEmailDTO();
        userNameEmailDTO.setUsername(savedUser.getUsername());
        userNameEmailDTO.setEmail(savedUser.getEmail());
        return userNameEmailDTO;

    }

}
