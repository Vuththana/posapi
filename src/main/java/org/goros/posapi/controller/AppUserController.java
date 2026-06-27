package org.goros.posapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.goros.posapi.model.entity.AppUser;
import org.goros.posapi.model.response.ApiResponse;
import org.goros.posapi.model.response.AppUserResponse;
import org.goros.posapi.service.AppUserService;
import org.goros.posapi.utils.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@RequestMapping("/api/v1")
public class AppUserController {
    private final AppUserService appUserService;
    private final ModelMapper modelMapper;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<AppUserResponse>>> getAllUsers() {
        List<AppUserResponse> users = appUserService.getAllUsers().stream().map(user -> modelMapper.map(user, AppUserResponse.class)).toList();
        ApiResponse<List<AppUserResponse>> response = ResponseUtil.success("Users fetched successfully", users);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/user/{user-id}")
    public ResponseEntity<ApiResponse<AppUserResponse>> getUserById(@PathVariable("user-id") UUID userId) {
        ApiResponse<AppUserResponse> response = ResponseUtil.success("Users fetched successfully", modelMapper.map(appUserService.getUserById(userId), AppUserResponse.class));
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
