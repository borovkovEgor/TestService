package com.borovkov.egor.testservice.controller;

import com.borovkov.egor.testservice.dto.request.UserRequestDto;
import com.borovkov.egor.testservice.dto.response.SubscriptionResponseDto;
import com.borovkov.egor.testservice.dto.response.UserResponseDto;
import com.borovkov.egor.testservice.mapper.CommonMapper;
import com.borovkov.egor.testservice.model.SubName;
import com.borovkov.egor.testservice.model.User;
import com.borovkov.egor.testservice.service.SubscriptionService;
import com.borovkov.egor.testservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final CommonMapper commonMapper;

    @PostMapping()
    @Operation(summary = "Создать пользователя")
    public User createUser(@RequestBody @Valid UserRequestDto dto) {
        log.info("Creating user with email={}", dto.email());
        return userService.saveUser(commonMapper.toUser(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные пользователя")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDto user) {
        log.info("Updating user with id={}", id);
        return commonMapper.toUserResponseDto(userService.updateUser(id, commonMapper.toUser(user)));
    }

    @GetMapping("{id}")
    @Operation(summary = "Получить информацию о пользователе")
    public UserResponseDto getUserInfo(@PathVariable Long id) {
        log.info("Getting user info with id={}", id);
        return commonMapper.toUserResponseDto(userService.getUserById(id));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удалить пользователя")
    public void deleteUser(@PathVariable Long id) {
        log.info("Deleting user with id={}", id);
        userService.deleteUser(id);
    }

    @PostMapping("/{id}/subscriptions")
    @Operation(summary = "Добавить подписку пользователю")
    public UserResponseDto addSubscription(@PathVariable Long id, @RequestParam SubName subName) {
        log.info("Adding subscription '{}' to user with id={}", subName, id);
        return commonMapper.toUserResponseDto(subscriptionService.addSubscription(id, subName));
    }

    @GetMapping("/{id}/subscriptions")
    @Operation(summary = "Получить подписки пользователя")
    public List<SubscriptionResponseDto> getUserSubscriptions(@PathVariable Long id) {
        log.info("Getting user subscriptions for user with id={}", id);
        return commonMapper.toSubscriptionResponseDto(subscriptionService.getUserSubscriptions(id));
    }

    @DeleteMapping("/{id}/subscriptions/{subId}")
    @Operation(summary = "Удалить подписку пользователя")
    public void deleteSubscription(@PathVariable Long id, @PathVariable Long subId) {
        log.info("Deleting subscription with subId={} for user with id={}", subId, id);
        subscriptionService.deleteSubscription(id, subId);
    }
}
