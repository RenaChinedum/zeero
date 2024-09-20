package com.zeero.zeero.controller;


import com.zeero.zeero.dto.request.SignInRequest;
import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.dto.response.SignInResponse;
import com.zeero.zeero.dto.response.UnifiedResponse;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public UnifiedResponse<Users> createUser(@RequestBody UserDetailRequest request) {
       Users users = authenticationService.createUser(request);
        return new UnifiedResponse<>(users);
    }

    @PostMapping("/authenticate")
    public UnifiedResponse<SignInResponse> signIn(@RequestBody SignInRequest request) {
        SignInResponse response = authenticationService.authenticateUser(request);
        return new UnifiedResponse<>(response);
    }
}
