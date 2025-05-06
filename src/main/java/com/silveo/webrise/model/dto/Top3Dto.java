package com.silveo.webrise.model.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class Top3Dto {
    private final String serviceName;
    private final long count;
}
