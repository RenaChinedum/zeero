package com.zeero.zeero.service;

import com.zeero.zeero.TestUtil;
import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.exceptions.TodoAppException;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UsersRepository usersRepositoryMock;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private UsersService usersService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void updatePatronDetail() {
        UserDetailRequest request = TestUtil.getRequest();
        Users expected = TestUtil.getUsers(request);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(expected);
        SecurityContextHolder.setContext(securityContext);

        when(usersRepositoryMock.save(any(Users.class))).thenReturn(expected);

       Users result = usersService.updatePatronDetail(request);

        assertNotNull(result);
        assertEquals(expected, result);

        verify(usersRepositoryMock).save(any(Users.class));
        verify(securityContext).getAuthentication();
        verify(authentication).getPrincipal();
    }

    @Test
    void getAllPatron() {
        UserDetailRequest request = TestUtil.getRequest();
        Users expected = TestUtil.getUsers(request);
        expected.setId(1L);
        Page<Users> expectedPatronPage = mock(Page.class);

        when(usersRepositoryMock.findAll(pageable)).thenReturn(expectedPatronPage);

        Page<Users> result = usersService.getAllPatron(pageable);

        assertNotNull(result);

        verify(usersRepositoryMock).findAll(pageable);
    }

    @Test
    void getAPatron() {
        UserDetailRequest request = TestUtil.getRequest();
        Users expected = TestUtil.getUsers(request);
        expected.setId(1L);

        when(usersRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(expected));

       Users result = usersService.getAPatron(expected.getId());

        assertNotNull(result);
        assertEquals(expected, result);

        verify(usersRepositoryMock).findById(any(Long.class));
    }
    @Test
    void getAPatron_unsuccessful() {
        when(usersRepositoryMock.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows( TodoAppException.class, ()-> usersService.getAPatron(1L));

    }

}