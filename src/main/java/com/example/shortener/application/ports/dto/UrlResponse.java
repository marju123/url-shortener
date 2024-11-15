package com.example.shortener.application.ports.dto;

import java.time.LocalDateTime;

public record UrlResponse(String url, LocalDateTime createdAt, LocalDateTime expiresAt, String alias,
                          boolean isActive) {

}