package com.borovkov.egor.testservice.service;

import com.borovkov.egor.testservice.exception.SubscriptionNotFoundException;
import com.borovkov.egor.testservice.model.SubName;
import com.borovkov.egor.testservice.model.Subscription;
import com.borovkov.egor.testservice.model.User;
import com.borovkov.egor.testservice.repository.SubscriptionRepository;
import com.borovkov.egor.testservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public User addSubscription(Long userId, SubName subName) {
        log.info("Adding subscription '{}' to user id={}", subName, userId);

        // Проверяем, существует ли подписка с таким названием
        SubName.existDisplayName(subName.getDisplayName());
        Subscription subscription = getSubscriptionByName(subName);

        User user = userService.getUserById(userId);
        user.getSubscriptions().add(subscription);
        return userRepository.save(user);
    }

    public Set<Subscription> getUserSubscriptions(Long userId) {
        return userService.getUserById(userId).getSubscriptions();
    }

    public void deleteSubscription(Long userId, Long subId) {
        log.info("Deleting subscription '{}' from user id={}", subId, userId);
        User user = userService.getUserById(userId);
        user.getSubscriptions().removeIf(s -> s.getId().equals(subId));
        userRepository.save(user);
    }

    public List<Subscription> getTopSubscriptions(Long quantity) {
        return subscriptionRepository.findTopSubscriptions(quantity);
    }

    public Subscription getSubscriptionByName(SubName name) {
        return subscriptionRepository.findByName(name)
                .orElseThrow(() -> new SubscriptionNotFoundException(String.format("Subscription with name={%s} not found", name)));
    }
}
