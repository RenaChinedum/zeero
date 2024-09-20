package com.zeero.zeero.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    @NotNull
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password field cannot be empty")
    @Length(min = 8, max = 24, message = "Password cannot be less than 8 or more than 24 characters")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%&]).*$",
            message = "Password must contain at least one letter, one digit, and one special character")
    private String password;
}
