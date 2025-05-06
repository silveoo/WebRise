package com.silveo.webrise.service;

import com.silveo.webrise.exception.AlreadySubscribedException;
import com.silveo.webrise.exception.NotFoundException;
import com.silveo.webrise.model.Subscription;
import com.silveo.webrise.model.User;
import com.silveo.webrise.model.dto.SubscriptionRequest;
import com.silveo.webrise.model.dto.SubscriptionResponse;
import com.silveo.webrise.model.dto.Top3Dto;
import com.silveo.webrise.repository.SubscriptionRepository;
import com.silveo.webrise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private static final int DAYS = 30;
    private static final int TOP_SIZE = 3;

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionResponse add(Long userId, SubscriptionRequest dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User", userId));

        LocalDate newExpiry = LocalDate.now().plusDays(DAYS);

        Subscription sub = subscriptionRepository.findByUserIdAndServiceNameIgnoreCase(userId, dto.getServiceName())
                .map(existing -> {
                    existing.setExpiresAt(newExpiry);
                    return existing;
                })
                .orElseGet(() ->
                        Subscription.builder()
                                .serviceName(dto.getServiceName())
                                .user(user)
                                .createdAt(LocalDateTime.now())
                                .expiresAt(newExpiry)
                                .build()
                );

        subscriptionRepository.save(sub);

        log.info("User {} subscribed/extended {} till {}. RequestId: {}", userId,
                dto.getServiceName(), newExpiry, dto.getRequestId());

        return map(sub);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionResponse> list(Long userId) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("User", userId);
        return subscriptionRepository.findByUserId(userId).stream()
                .filter(s -> !s.getExpiresAt().isBefore(LocalDate.now()))
                .map(this::map)
                .toList();
    }

    public void delete(Long userId, Long subId) {
        Subscription sub = subscriptionRepository.findById(subId)
                .filter(s -> s.getUser().getId().equals(userId))
                .orElseThrow(() -> new NotFoundException("Subscription", subId));

        subscriptionRepository.delete(sub);
        log.info("Subscription {} removed for user {}", subId, userId);
    }

    @Transactional(readOnly = true)
    public List<Top3Dto> top3() {
        return subscriptionRepository.findTop(PageRequest.of(0, TOP_SIZE)).stream()
                .map(t -> Top3Dto.builder()
                                 .serviceName(t.getName())
                                 .count(t.getCnt())
                                 .build())
                .toList();
    }


    private SubscriptionResponse map(Subscription s) {
        return SubscriptionResponse.builder()
                .id(s.getId())
                .serviceName(s.getServiceName())
                .build();
    }
}
