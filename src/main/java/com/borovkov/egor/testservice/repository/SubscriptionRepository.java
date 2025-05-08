package com.borovkov.egor.testservice.repository;

import com.borovkov.egor.testservice.model.SubName;
import com.borovkov.egor.testservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("select s from Subscription s where s.id=:id")
    Optional<Subscription> findById(@Param("id") Long id);

    @Query(value = """
                SELECT s.*
                FROM test_service.subscriptions s
                JOIN test_service.user_subscriptions us ON s.id = us.subscription_id
                GROUP BY s.id
                ORDER BY COUNT(us.user_id) DESC
                LIMIT :limit
            """, nativeQuery = true)
    List<Subscription> findTopSubscriptions(@Param("limit") Long limit);

    @Query("select s from Subscription s where s.subName=:name")
    Optional<Subscription> findByName(@Param("name") SubName name);
}
