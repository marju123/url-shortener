package com.example.shortener.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @Column(nullable = false, unique = true)
    private String alias;

    @Column(nullable = false, unique = true)
    private String url;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public Url(String alias, String url, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.alias = alias;
        this.url = url;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.uuid = UUID.randomUUID();
    }

    protected Url() {
    }

    public boolean isActive() {
        return Objects.isNull(expiresAt) || createdAt.isBefore(expiresAt);
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", alias='" + alias + '\'' +
                ", url='" + url + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                '}';
    }
}

