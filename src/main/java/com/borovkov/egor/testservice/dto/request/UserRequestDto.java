package com.borovkov.egor.testservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(
        String firstName,
        String lastName,

        @Min(value = 18, message = "Age must be at least 18")
        @Max(value = 120, message = "Age must be less than or equal to 120")
        Integer age,

        @Email(message = "Email must be valid")
        @NotBlank(message = "Email is required")
        String email
) {
}
