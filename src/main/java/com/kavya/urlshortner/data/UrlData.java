package com.kavya.urlshortner.data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class UrlData {
    private String shortUrl;
    private String longUrl;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

    public UrlData() {

    }



    public UrlData(String shortUrl, String longUrl, LocalDateTime creationDate, LocalDateTime expirationDate) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }
    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
    public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(expirationDate);
    }
    public void extendExpiration(int daysToAdd) {
        expirationDate = expirationDate.plus(daysToAdd, ChronoUnit.DAYS);
    }

    @Override
    public String toString() {
        return "UrlData{" +
                "shortUrl='" + shortUrl + '\'' +
                ", longUrl='" + longUrl + '\'' +
                ", creationDate=" + creationDate +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
