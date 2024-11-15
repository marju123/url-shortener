package com.example.shortener.adapters.out.persistence;

import com.example.shortener.application.ports.out.UrlRepositoryPort;
import com.example.shortener.domain.model.Url;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class UrlRepositoryAdapter implements UrlRepositoryPort {

    private final UrlJpaRepository urlJpaRepository;

    public UrlRepositoryAdapter(UrlJpaRepository urlJpaRepository) {
        this.urlJpaRepository = urlJpaRepository;
    }

    @Override
    public Optional<Url> findByUrl(String url) {
        return urlJpaRepository.findByUrl(url);
    }

    @Override
    public Optional<Url> findByAlias(String alias) {
        return urlJpaRepository.findByAlias(alias);
    }

    @Override
    public Url save(Url url) {
        return urlJpaRepository.save(url);
    }

    @Override
    @Transactional
    public void deleteByAlias(String alias) {
        urlJpaRepository.deleteByAlias(alias);
    }
}
