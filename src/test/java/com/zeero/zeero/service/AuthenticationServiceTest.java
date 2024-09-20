package com.zeero.zeero.service;

import com.zeero.zeero.TestUtil;
import com.zeero.zeero.dto.request.SignInRequest;
import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.dto.response.SignInResponse;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.repository.UsersRepository;
import com.zeero.zeero.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UsersRepository usersRepositoryMock;
    @Mock
    private PasswordEncoder passwordEncoderMock;
    @Mock
    private AuthenticationManager authenticationManagerMock;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authService;

    @Test
    void createUser() {

        final UserDetailRequest request = TestUtil.getRequest();

        when(usersRepositoryMock.save(any(Users.class))).thenReturn(any(Users.class));

        final Users result = authService.createUser(request);

        assertNotNull(result);

        verify(usersRepositoryMock, times(2)).save(any(Users.class));
    }

    @Test
    void authenticateUser() {
        SignInRequest request = new SignInRequest("johndeo@gmail.com", "password");
        UserDetailRequest request1 = TestUtil.getRequest();
        Users users= TestUtil.getUsers(request1);
        String jwt = "mock_jwt_token";

        when(usersRepositoryMock.findByEmail(request.getEmail())).thenReturn(Optional.of(users));
        when(passwordEncoderMock.matches(request.getPassword(), users.getPassword())).thenReturn(true);
        when(jwtService.generateToken(users)).thenReturn(jwt);

        SignInResponse response = authService.authenticateUser(request);

        verify(jwtService).generateToken(users);

        assertNotNull(response);
        assertEquals("Log in successful", response.getMessage());
        assertEquals(jwt, response.getToken());
    }

}