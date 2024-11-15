package com.example.shortener.domain.service;

import com.example.shortener.application.ports.out.UrlRepositoryPort;
import com.example.shortener.domain.exception.BusinessException;
import com.example.shortener.domain.model.Url;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UrlShorteningServiceTest {

    @Mock
    private UrlRepositoryPort urlRepository;

    @Mock
    private UrlGeneratorService urlGenerator;

    @InjectMocks
    private UrlShorteningService urlShorteningService;

    UrlShorteningServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGenerateAlias() {
        // given
        String url = "https://example.com";
        String generatedAlias = "abc123";
        when(urlGenerator.generateAlias(url)).thenReturn(generatedAlias);
        // when
        String alias = urlShorteningService.createAlias(url);
        // then
        assertEquals(generatedAlias, alias);
        verify(urlGenerator, times(1)).generateAlias(url);
    }

    @Test
    void shouldSaveUrlWithGeneratedAlias() {
        // given
        String url = "https://example.com";
        String alias = "short123";
        LocalDateTime now = LocalDateTime.now();
        Url urlEntity = new Url(alias, url, now, now.plusDays(30));
        when(urlRepository.findByAlias(alias)).thenReturn(Optional.empty());
        when(urlRepository.save(any(Url.class))).thenReturn(urlEntity);
        // when
        Url savedUrl = urlShorteningService.saveUrl(url, null, 30);
        // then
        assertNotNull(savedUrl);
        assertEquals(alias, savedUrl.getAlias());
        verify(urlRepository, times(1)).save(any(Url.class));
    }

    @Test
    void shouldThrowsIfAliasExists() {
        // given
        String alias = "abc123";
        when(urlRepository.findByAlias(alias)).thenReturn(Optional.of(mock(Url.class)));
        // when then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            urlShorteningService.saveUrl("https://example.com", alias, null);
        });
        assertEquals("Alias: abc123 already in use", exception.getMessage());
    }
}