package org.goros.posapi.service;

import org.goros.posapi.model.entity.AppUser;
import org.goros.posapi.model.request.AppUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AppUserService extends UserDetailsService {
    List<AppUser> getAllUsers();
    AppUser register(AppUserRequest appUserRequest);
}
