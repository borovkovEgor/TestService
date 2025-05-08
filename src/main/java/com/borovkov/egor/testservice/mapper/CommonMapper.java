package com.borovkov.egor.testservice.mapper;

import com.borovkov.egor.testservice.dto.request.UserRequestDto;
import com.borovkov.egor.testservice.dto.response.SubscriptionResponseDto;
import com.borovkov.egor.testservice.dto.response.UserResponseDto;
import com.borovkov.egor.testservice.model.SubName;
import com.borovkov.egor.testservice.model.Subscription;
import com.borovkov.egor.testservice.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommonMapper {

    public UserResponseDto toUserResponseDto(User user) {
        List<String> subs = user.getSubscriptions().stream()
                .map(Subscription::getSubName)
                .map(SubName::getDisplayName)
                .collect(Collectors.toList());

        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                subs
        );
    }

    public List<SubscriptionResponseDto> toSubscriptionResponseDto(List<Subscription> subscription) {
        List<SubscriptionResponseDto> subs = new ArrayList<>();
        subscription.forEach(sub -> {
            SubscriptionResponseDto dto = new SubscriptionResponseDto(sub.getId(), sub.getSubName().getDisplayName());
            subs.add(dto);
        });
        return subs;
    }

    public List<SubscriptionResponseDto> toSubscriptionResponseDto(Set<Subscription> subscription) {
        List<SubscriptionResponseDto> subs = new ArrayList<>();
        subscription.forEach(sub -> {
            SubscriptionResponseDto dto = new SubscriptionResponseDto(sub.getId(), sub.getSubName().getDisplayName());
            subs.add(dto);
        });
        return subs;
    }

    public User toUser(UserRequestDto dto) {
        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setAge(dto.age());
        user.setEmail(dto.email());
        return user;
    }
}
