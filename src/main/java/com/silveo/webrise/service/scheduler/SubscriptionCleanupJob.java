package com.silveo.webrise.service.scheduler;

import com.silveo.webrise.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionCleanupJob {

    private final SubscriptionRepository subscriptionRepository;

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void purgeExpired() {
        LocalDate today = LocalDate.now();
        long deleted = subscriptionRepository.deleteByExpiresAtBefore(today);
        if (deleted > 0) {
            log.info("Removed {} expired subscriptions (before {})", deleted, today);
        }
    }
}
