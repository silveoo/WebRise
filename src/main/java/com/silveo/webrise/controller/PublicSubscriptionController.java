package com.silveo.webrise.controller;

import com.silveo.webrise.model.dto.Top3Dto;
import com.silveo.webrise.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublicSubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping("/subscriptions/top")
    public List<Top3Dto> top() { return subscriptionService.top3(); }
}