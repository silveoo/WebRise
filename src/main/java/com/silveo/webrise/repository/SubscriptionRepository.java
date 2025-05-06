package com.silveo.webrise.repository;

import com.silveo.webrise.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUserIdAndServiceNameIgnoreCase(Long userId, String service);

    List<Subscription> findByUserId(Long userId);

    @Query("""
           SELECT s.serviceName AS name, COUNT(s) AS cnt
           FROM Subscription s
           WHERE s.expiresAt >= CURRENT_DATE
           GROUP BY s.serviceName
           ORDER BY cnt DESC
           """)
    Page<TopSubscription> findTop(Pageable pageable);

    long deleteByExpiresAtBefore(LocalDate date);

    interface TopSubscription {
        String getName();
        long   getCnt();
    }
}