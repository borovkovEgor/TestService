package com.borovkov.egor.testservice.dto.response;

import java.util.List;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        List<String> subscriptions
) {
}
