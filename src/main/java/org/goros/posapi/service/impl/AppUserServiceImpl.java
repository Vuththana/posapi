package org.goros.posapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.goros.posapi.exception.ForbiddenException;
import org.goros.posapi.exception.UserNotFoundException;
import org.goros.posapi.model.entity.AppUser;
import org.goros.posapi.model.request.AppUserRequest;
import org.goros.posapi.repository.AppUserRepository;
import org.goros.posapi.service.AppUserService;
import org.goros.posapi.utils.CheckRole;
import org.jspecify.annotations.NullMarked;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        AppUser foundUser = appUserRepository.findByEmailOrUsername(identifier);

        if(foundUser == null) {
            throw new UsernameNotFoundException("Invalid username, ,email or password. Please check your credentials and try again.");
        }

        return User.builder()
                .username(foundUser.getUsername())
                .password(foundUser.getPassword())
                    .build();
    }

    @Override
    public List<AppUser> getAllUsers() {
        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
        assert authenticated != null;
        if (CheckRole.isSuperAdmin(appUserRepository.findByEmailOrUsername(authenticated.getName()))) {
            throw new ForbiddenException("Unauthorized: You have no access to use this service.");
        }
        return appUserRepository.findAll();
    }

    public AppUser getUserById(UUID userId) {
        return appUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void deleteUserById(UUID userId) {
        if(appUserRepository.existsById(userId)) {
            appUserRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public void updateUserById(UUID userId, AppUserRequest request) {
        AppUser user = appUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        modelMapper.map(request, user);
        appUserRepository.save(user);
    }

    @Override
    public void verifyUserById(UUID userId) {
        if(appUserRepository.existsById(userId)) {
            appUserRepository.verifyUserById(userId);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
}