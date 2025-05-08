package com.borovkov.egor.testservice.model;

import com.borovkov.egor.testservice.exception.InvalidSubscriptionException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubName {
    NETFLIX("Netflix"),
    YOUTUBE("YouTube Premium"),
    SPOTIFY("Spotify"),
    YANDEX("Яндекс.Плюс"),
    VK("VK Музыка"),
    APPLE("Apple Music");

    private final String displayName;

    public static void existDisplayName(String displayName) {
        for (SubName value : SubName.values()) {
            if (value.displayName.equalsIgnoreCase(displayName)) {
                return;
            }
        }
        throw new InvalidSubscriptionException("Unknown subscription name: " + displayName);
    }
}
