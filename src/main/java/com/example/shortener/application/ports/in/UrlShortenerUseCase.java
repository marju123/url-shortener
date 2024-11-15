package com.example.shortener.application.ports.in;


import com.example.shortener.domain.model.Url;

import java.util.Optional;

public interface UrlShortenerUseCase {
    String createAlias(String url);

    Optional<Url> getAlias(String alias);

    void deleteAlias(String alias);

    Optional<Url> getUrl(String url);
    Url saveUrl(String url, String customAlias, Integer expirationDays);
}
