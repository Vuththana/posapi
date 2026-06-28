package org.goros.posapi.service;

import org.goros.posapi.jwt.JwtService;
import org.goros.posapi.model.entity.AppUser;
import org.goros.posapi.model.request.AppUserRequest;
import org.goros.posapi.model.request.AuthRequest;
import org.goros.posapi.model.response.AuthResponse;
import org.springframework.security.authentication.AuthenticationManager;

public interface AuthService {
    AppUser register(AppUserRequest appUserRequest);
    AuthResponse login(AuthRequest request, JwtService jwtService, AppUserService appUserService, AuthenticationManager authenticationManager) throws Exception;
}
