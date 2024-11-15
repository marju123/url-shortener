package com.example.shortener.application.ports.out;


import com.example.shortener.domain.model.Url;

import java.util.Optional;

public interface UrlRepositoryPort {

    Optional<Url> findByUrl(String url);

    Optional<Url> findByAlias(String alias);

    Url save(Url url);

    void deleteByAlias(String alias);
}