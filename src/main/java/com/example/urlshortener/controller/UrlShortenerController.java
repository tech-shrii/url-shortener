package com.example.urlshortener.controller;
import io.swagger.v3.oas.annotations.Operation;

import com.example.urlshortener.entity.UrlMapping;
import com.example.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import com.example.urlshortener.dto.UrlRequest;
import com.example.urlshortener.dto.UrlResponse;

@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService service;



    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shortenUrl(@RequestBody UrlRequest request) {
        String shortUrl = service.shortenUrl(request.getLongUrl(), request.getCustomAlias());
        return ResponseEntity.ok(new UrlResponse(shortUrl));
    }

//    @PostMapping("/shorten")
//    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody Map<String, String> body) {
//        String longUrl = body.get("longUrl");
//        String shortUrl = service.shortenUrl(longUrl);
//        Map<String, String> response = new HashMap<>();
//        response.put("shortUrl", shortUrl);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {
        return service.getOriginalUrl(shortCode)
                .map(mapping -> {
                    service.incrementAccessCount(mapping);
                    return ResponseEntity.status(302).location(URI.create(mapping.getLongUrl())).build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/stats/{shortCode}")
    public ResponseEntity<?> stats(@PathVariable String shortCode) {
        return service.getStats(shortCode)
                .map(mapping -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("originalUrl", mapping.getLongUrl());
                    map.put("createdAt", mapping.getCreatedAt());
                    map.put("accessCount", mapping.getAccessCount());
                    return ResponseEntity.ok(map);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
