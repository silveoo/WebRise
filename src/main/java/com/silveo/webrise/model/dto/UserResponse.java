package com.silveo.webrise.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
    private final Long id;
    private final String email;
    private final String fullName;
    private final LocalDateTime createdAt;
}
