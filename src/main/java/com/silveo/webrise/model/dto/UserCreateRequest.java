package com.silveo.webrise.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    @NotBlank
    private String requestId;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String fullName;
}
