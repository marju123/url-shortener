package com.example.shortener.adapters.in.rest.mapper;

import com.example.shortener.application.ports.dto.UrlResponse;
import com.example.shortener.domain.model.Url;

public class UrlResponseMapper {

    public static UrlResponse toUrlResponse(Url url) {
        return new UrlResponse(url.getUrl(),
                url.getCreatedAt(),
                url.getExpiresAt(),
                url.getAlias(),
                url.isActive());
    }
}
