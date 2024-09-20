package com.zeero.zeero.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailRequest {
    private String title;
    private String author;
    @NotBlank(message = "Invalid: Date cannot be blank")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date format should be 'yyyy-MM-dd'")
    private String publicationYear;
    private String isbn;
    @Length(min = 9, message = "Must be a 9-digit string")
    @Pattern(regexp = "\\d+", message = "Only digits are allowed")
    private String pages;
    @Length(min = 9, message = "Must be a 9-digit string")
    @Pattern(regexp = "\\d+", message = "Only digits are allowed")
    private String chapters;
}
