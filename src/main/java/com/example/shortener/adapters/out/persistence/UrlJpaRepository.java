package com.example.shortener.adapters.out.persistence;

import com.example.shortener.domain.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlJpaRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByUrl(String url);
    Optional<Url> findByAlias(String alias);
    void deleteByAlias(String alias);
}
