package com.example.urlshortener.dto;

public class UrlRequest {
    private String longUrl;
    private String customAlias; // ✅ NEW

    public UrlRequest() {}

    public UrlRequest(String longUrl, String customAlias) {
        this.longUrl = longUrl;
        this.customAlias = customAlias;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getCustomAlias() {
        return customAlias;
    }

    public void setCustomAlias(String customAlias) {
        this.customAlias = customAlias;
    }
}
