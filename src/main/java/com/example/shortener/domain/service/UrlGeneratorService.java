package com.example.shortener.domain.service;


import com.example.shortener.adapters.common.Base62Generator;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UrlGeneratorService {

    public String generateAlias(String url) {
        return Base62Generator.encodeFromString(url);
    }
}
