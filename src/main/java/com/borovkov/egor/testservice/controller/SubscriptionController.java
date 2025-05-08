package com.borovkov.egor.testservice.controller;

import com.borovkov.egor.testservice.dto.response.SubscriptionResponseDto;
import com.borovkov.egor.testservice.mapper.CommonMapper;
import com.borovkov.egor.testservice.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final CommonMapper commonMapper;

    @GetMapping("/top")
    @Operation(summary = "Получить топ популярных подписок")
    public List<SubscriptionResponseDto> getTopSubscriptions(
            @Parameter(description = "Количество подписок")
            @RequestParam(value = "quantity")
            Long quantity
    ) {
        log.info("Getting top {} subscriptions", quantity);
        return commonMapper.toSubscriptionResponseDto(subscriptionService.getTopSubscriptions(quantity));
    }
}
