package org.goros.posapi.service;

import org.goros.posapi.model.entity.AppUser;
import org.goros.posapi.model.request.AppUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface AppUserService extends UserDetailsService {
    List<AppUser> getAllUsers();
    AppUser getUserById(UUID userId);
    void deleteUserById(UUID userId);
    void updateUserById(UUID userId, AppUserRequest appUserRequest);
}
