package com.borovkov.egor.testservice.init;

import com.borovkov.egor.testservice.model.SubName;
import com.borovkov.egor.testservice.model.Subscription;
import com.borovkov.egor.testservice.model.User;
import com.borovkov.egor.testservice.repository.SubscriptionRepository;
import com.borovkov.egor.testservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.random.RandomGenerator;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0 || subscriptionRepository.count() > 0) return;

        List<Subscription> subscriptions = new ArrayList<>(List.of(
                new Subscription(SubName.NETFLIX),
                new Subscription(SubName.YOUTUBE),
                new Subscription(SubName.SPOTIFY),
                new Subscription(SubName.YANDEX),
                new Subscription(SubName.VK),
                new Subscription(SubName.APPLE)
        ));
        subscriptionRepository.saveAll(subscriptions);

        Faker faker = new Faker();
        RandomGenerator random = RandomGenerator.getDefault();

        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            user.setAge(random.nextInt(18, 100));

            Collections.shuffle(subscriptions);
            Set<Subscription> userSubs = new HashSet<>(subscriptions.subList(0, random.nextInt(1, 6)));
            user.getSubscriptions().addAll(userSubs);

            userRepository.save(user);
        }

        log.info("Test data seeded");
    }
}