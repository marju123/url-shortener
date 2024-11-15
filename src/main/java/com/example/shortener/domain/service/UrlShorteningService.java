package com.example.shortener.domain.service;

import com.example.shortener.application.ports.in.UrlShortenerUseCase;
import com.example.shortener.application.ports.out.UrlRepositoryPort;
import com.example.shortener.domain.exception.BusinessException;
import com.example.shortener.domain.model.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.nonNull;

@Service
public class UrlShorteningService implements UrlShortenerUseCase {

    private static final Logger logger = LoggerFactory.getLogger(UrlShorteningService.class);

    private final UrlRepositoryPort urlRepository;
    private final UrlGeneratorService urlGenerator;

    public UrlShorteningService(UrlRepositoryPort urlRepository, UrlGeneratorService urlGenerator) {
        this.urlRepository = urlRepository;
        this.urlGenerator = urlGenerator;
    }

    @Override
    public String createAlias(String url) {
        return urlGenerator.generateAlias(url);
    }

    @Override
    public Optional<Url> getAlias(String alias) {
        return urlRepository.findByAlias(alias);
    }

    @Override
    public void deleteAlias(String alias) {
        urlRepository.deleteByAlias(alias);
    }

    @Override
    public Optional<Url> getUrl(String url) {
        return urlRepository.findByUrl(url);
    }

    @Override
    public Url saveUrl(String url, String customAlias, Integer expirationDays) {
        LocalDateTime now = LocalDateTime.now();
        if (isBlank(customAlias)) {
            String alias = createAlias(url);
            verifyIfAliasAlreadyExists(alias);
            return urlRepository.save(new Url(alias, url, now, calculateExpirationDays(now, expirationDays)));
        } else {
            verifyIfAliasAlreadyExists(customAlias);
            return urlRepository.save(new Url(customAlias, url, now, calculateExpirationDays(now, expirationDays)));
        }
    }

    private LocalDateTime calculateExpirationDays(LocalDateTime now, Integer expirationDays) {
        if (nonNull(expirationDays)) {
            return now.plusDays(expirationDays);
        } else {
            return null;
        }
    }

    private void verifyIfAliasAlreadyExists(String alias) {
        if (urlRepository.findByAlias(alias).isPresent()) {
            logger.error("Alias: {} is already in use", alias);
            throw new BusinessException(String.format("Alias: %s already in use", alias));
        }
    }
}