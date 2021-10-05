package dbryzz.services.auth.service;

import dbryzz.services.auth.dto.UserNameDTO;
import dbryzz.services.auth.dto.UserNameEmailDTO;
import dbryzz.services.auth.dto.payload.request.LogoutRequest;
import dbryzz.services.auth.dto.payload.request.RegistrationRequest;
import dbryzz.services.auth.model.Role;
import dbryzz.services.auth.model.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long Id);

    User save(User user);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    User getUserById(Long Id);

    User createUser(RegistrationRequest registerRequest);

    Set<Role> getRolesForNewUser(Boolean isToBeMadeAdmin);

    void logoutUser(Long userId, LogoutRequest logOutRequest);

    UserNameEmailDTO getUserInformation(Long userId);

    UserNameEmailDTO UpdateUserName(Long userId, UserNameDTO userNameDTO);
}
