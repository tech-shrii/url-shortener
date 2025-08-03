package com.example.urlshortener.service;

import com.example.urlshortener.entity.UrlMapping;
import com.example.urlshortener.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlMappingRepository repository;

    private static final String BASE_URL = "http://localhost:8080/";

    public String generateShortCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

//    public String shortenUrl(String longUrl) {
//        String code = generateShortCode();
//        UrlMapping urlMapping = new UrlMapping(longUrl, code);
//        repository.save(urlMapping);
//        return BASE_URL + code;
//    }

    public Optional<UrlMapping> getOriginalUrl(String shortCode) {
        return repository.findByShortCode(shortCode);
    }

    public Optional<UrlMapping> getStats(String shortCode) {
        return repository.findByShortCode(shortCode);
    }

    public void incrementAccessCount(UrlMapping mapping) {
        mapping.setAccessCount(mapping.getAccessCount() + 1);
        repository.save(mapping);
    }
    public String shortenUrl(String longUrl, String customAlias) {
        String shortCode;

        if (customAlias != null && !customAlias.isEmpty()) {
            if (repository.existsByShortCode(customAlias)) {
                throw new RuntimeException("Custom alias already exists");
            }
            shortCode = customAlias;
        } else {
            shortCode = generateShortCode();
        }

        UrlMapping mapping = new UrlMapping(longUrl, shortCode);
        repository.save(mapping);
        return BASE_URL + shortCode;
    }

}
