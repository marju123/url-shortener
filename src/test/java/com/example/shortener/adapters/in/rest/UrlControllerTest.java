package com.example.shortener.adapters.in.rest;

import com.example.shortener.application.ports.dto.UrlResponse;
import com.example.shortener.application.ports.in.UrlShortenerUseCase;
import com.example.shortener.domain.model.Url;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

class UrlControllerTest {

    @Mock
    private UrlShortenerUseCase urlShortenerService;

    @InjectMocks
    private UrlController urlController;

    UrlControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCreatedResponse() {
        // given
        String originalUrl = "https://example.com";
        String alias = "short123";
        Url mockUrl = new Url(alias, originalUrl, LocalDateTime.now(), LocalDateTime.now().plusDays(30));
        when(urlShortenerService.saveUrl(originalUrl, null, 30)).thenReturn(mockUrl);

        // when
        ResponseEntity<UrlResponse> response = urlController.createShortUrl(originalUrl, null, 30);

        // then
        Assertions.assertEquals(201, response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(alias, response.getBody().alias());
    }

    @Test
    void shouldReturnFoundResponse() {
        // given
        String alias = "short123";
        String originalUrl = "https://example.com";
        Url mockUrl = new Url(alias, originalUrl, LocalDateTime.now(), null);
        when(urlShortenerService.getAlias(alias)).thenReturn(Optional.of(mockUrl));
        // when
        ResponseEntity<Void> response = urlController.redirectToUrl(alias);
        // then
        Assertions.assertEquals(302, response.getStatusCodeValue());
        Assertions.assertEquals(originalUrl, response.getHeaders().getLocation().toString());
    }
}