package com.zeero.zeero.controller;

import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.dto.response.UnifiedResponse;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UsersController {
    private final UsersService usersService;

    @PutMapping("/update")
    public UnifiedResponse<Users> updatePatron(@RequestBody UserDetailRequest request) {
        return new UnifiedResponse<>(usersService.updatePatronDetail(request));
    }

    @GetMapping("/{id}")
    public UnifiedResponse<Users> getAPatron(@PathVariable Long id) {
        return new UnifiedResponse<>(usersService.getAPatron(id));
    }

    @GetMapping("/all-patrons")
    public UnifiedResponse<Page<Users>> allPatrons(Pageable pageable) {
        return new UnifiedResponse<>(usersService.getAllPatron(pageable));
    }
}
