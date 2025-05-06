package com.silveo.webrise.model.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class SubscriptionResponse {
    private final Long id;
    private final String serviceName;
}
