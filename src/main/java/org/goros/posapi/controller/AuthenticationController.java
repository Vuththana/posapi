package org.goros.posapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.goros.posapi.jwt.JwtService;
import org.goros.posapi.model.entity.AppUser;
import org.goros.posapi.model.request.AppUserRequest;
import org.goros.posapi.model.request.AuthRequest;
import org.goros.posapi.model.response.ApiResponse;
import org.goros.posapi.model.response.ApiResponseVoid;
import org.goros.posapi.model.response.AppUserResponse;
import org.goros.posapi.model.response.AuthResponse;
import org.goros.posapi.service.AppUserService;
import org.goros.posapi.service.AuthService;
import org.goros.posapi.utils.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Validated
public class AuthenticationController implements AuthService {
    private final AppUserService appUserService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<ApiResponse<AppUserResponse>> register(@Valid @RequestBody AppUserRequest request) throws Exception {
       AppUser registeredUser = appUserService.register(request);

        ApiResponse<AppUserResponse> response = ResponseUtil.success("Registered successfully, you will need to verify your email to login", modelMapper.map(registeredUser, AppUserResponse.class) );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) throws Exception {
        authenticate(request.getIdentifier(), request.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getIdentifier());
        final String token = jwtService.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);

        ApiResponse<AuthResponse> response = ResponseUtil.success("Login Successfully", authResponse);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    private  void authenticate(String identifier, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(identifier, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username, email, or password. Please check your credentials and try again.", e);
        }
    }
}
