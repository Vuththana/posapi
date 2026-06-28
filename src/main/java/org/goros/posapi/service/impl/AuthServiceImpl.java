package org.goros.posapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.goros.posapi.exception.AlreadyExistsException;
import org.goros.posapi.jwt.JwtService;
import org.goros.posapi.model.entity.AppUser;
import org.goros.posapi.model.request.AppUserRequest;
import org.goros.posapi.model.request.AuthRequest;
import org.goros.posapi.model.response.AuthResponse;
import org.goros.posapi.repository.AppRoleRepository;
import org.goros.posapi.repository.AppUserRepository;
import org.goros.posapi.service.AppUserService;
import org.goros.posapi.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser register(AppUserRequest appUserRequest) {
        Optional<AppUser> findEmail = Optional.ofNullable(appUserRepository.findByEmailOrUsername(appUserRequest.getUsername()));
        Optional<AppUser> findUsername = Optional.ofNullable(appUserRepository.findByEmailOrUsername(appUserRequest.getUsername()));

        if(findEmail.isPresent() || findUsername.isPresent()) {
            throw new AlreadyExistsException("Email already exists");
        }

        appUserRequest.setPassword(passwordEncoder.encode(appUserRequest.getPassword()));

        AppUser appUser = modelMapper.map(appUserRequest, AppUser.class);
        UUID ownerRoleId = appRoleRepository.getRoleOwnerID();

        appUser.getRole().setRoleId(ownerRoleId);
        return appUserRepository.save(appUser);
    }

    @Override
    public AuthResponse login(AuthRequest request, JwtService jwtService, AppUserService appUserService, AuthenticationManager authenticationManager) throws Exception {
        authenticate(request.getIdentifier(), request.getPassword(), authenticationManager);
        final UserDetails userDetails = appUserService.loadUserByUsername(request.getIdentifier());
        final String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }

    private void authenticate(String identifier, String password, AuthenticationManager authenticationManager) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(identifier, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username, email, or password. Please check your credentials and try again.", e);
        }
    }
}
