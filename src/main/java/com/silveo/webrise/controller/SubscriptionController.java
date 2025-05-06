package com.silveo.webrise.controller;

import com.silveo.webrise.model.dto.SubscriptionRequest;
import com.silveo.webrise.model.dto.SubscriptionResponse;
import com.silveo.webrise.model.dto.Top3Dto;
import com.silveo.webrise.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/{userId}/subscriptions")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public SubscriptionResponse add(@PathVariable Long userId,
                                    @Valid @RequestBody SubscriptionRequest body) {
        log.info("Request to add subscription to user {}. RequestId: {}", userId, body.getRequestId());
        return subscriptionService.add(userId, body);
    }

    @GetMapping
    public List<SubscriptionResponse> list(@PathVariable Long userId) {
        return subscriptionService.list(userId);
    }

    @DeleteMapping("/{subId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId,
                       @PathVariable Long subId) {
        log.info("Request to delete subscription from user {}", userId);
        subscriptionService.delete(userId, subId);
    }
}
