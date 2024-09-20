package com.zeero.zeero.service;

import com.zeero.zeero.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;


@Component
@RequiredArgsConstructor
public class BaseService {


    public Users loggedInUser() {
        return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void updateFieldIfNotNullOrEmpty(String value, Consumer<String> setter) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        }
    }

}
