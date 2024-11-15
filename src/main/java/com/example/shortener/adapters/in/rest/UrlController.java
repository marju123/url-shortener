package com.example.shortener.adapters.in.rest;

import com.example.shortener.application.ports.dto.UrlResponse;
import com.example.shortener.application.ports.in.UrlShortenerUseCase;
import com.example.shortener.domain.model.Url;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.example.shortener.adapters.in.rest.mapper.UrlResponseMapper.toUrlResponse;

@RestController
@RequestMapping("/api/v1")
@Validated
public class UrlController {

    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

    private final UrlShortenerUseCase urlShortenerService;

    public UrlController(UrlShortenerUseCase urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/url/create")
    public ResponseEntity<UrlResponse> createShortUrl(
            @RequestParam
            @NotNull(message = "Original URL must not be null")
            @Pattern(regexp = "https://.*", message = "URL must start with https://")
            String url,
            @RequestParam(required = false)
            @Size(max = 6, message = "Custom alias cannot be longer than 6 characters")
            String customAlias,
            @RequestParam(required = false)
            @Min(value = 1, message = "Expiration days must be greater than 1")
            @Max(value = 180, message = "Expiration days must not be greater than 180")
            Integer expirationDays) {

        Optional<Url> existingUrl = urlShortenerService.getUrl(url);

        if (existingUrl.isPresent()) {
            Url urlModel = existingUrl.get();
            logger.info("Given url: {} already exists. Returning existing alias: {}", url, urlModel.getAlias());
            return ResponseEntity.ok(toUrlResponse(urlModel));
        }

        Url newUrl = urlShortenerService.saveUrl(url, customAlias, expirationDays);
        logger.info("New alias has been created: {} for given url:{}", newUrl.getAlias(), url);
        return ResponseEntity.status(HttpStatus.CREATED).body(toUrlResponse(newUrl));
    }

    @GetMapping("urls/{alias}")
    public ResponseEntity<Void> redirectToUrl(
            @PathVariable
            @NotNull(message = "Alias param must not be null")
            String alias) {

        Optional<Url> url = urlShortenerService.getAlias(alias);
        if (url.isEmpty()) {
            logger.warn("Alias: {} not found", alias);
            return ResponseEntity.badRequest().build();
        } else {
            logger.info("Redirecting alias: {} to url: {}", alias, url.get().getUrl());
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .location(java.net.URI.create(url.get().getUrl()))
                    .build();
        }
    }

    @DeleteMapping("urls/{alias}")
    public ResponseEntity<Void> deleteAlias(
            @PathVariable
            @NotNull(message = "Alias param must not be null")
            String alias) {

        Optional<Url> url = urlShortenerService.getAlias(alias);
        if (url.isEmpty()) {
            logger.info("Alias: {} not found", alias);
        } else {
            urlShortenerService.deleteAlias(alias);
            logger.info("Url:{} has been removed", url);
        }
        return ResponseEntity.noContent().build();
    }
}
