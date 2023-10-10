package com.kavya.urlshortner.controller;

import com.kavya.urlshortner.data.UrlData;
import com.kavya.urlshortner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody Map<String, String> request) throws IOException {
        String longUrl = request.get("destination_url");
        return urlService.longToShortUrl(longUrl);
    }

    @PostMapping("/update")
    public boolean updateShortUrl(@RequestBody Map<String, String> request) {
        String shortUrl = request.get("short_url");
        String newLongUrl = request.get("destination_url");
        return urlService.updateShortUrl(shortUrl, newLongUrl);
    }

    @GetMapping("/{shortUrl}")
    public String redirectToFullUrl(@PathVariable String shortUrl) {
        Optional<String> destinationUrl = urlService.getLongUrlGivenShortUrl(shortUrl);
        if (destinationUrl.isPresent()) {
            return "Redirecting to: " + destinationUrl.get();
        } else {
            throw new IllegalArgumentException("Short URL not found");
        }
    }

    @PostMapping("/update-expiry")
    public boolean updateExpiry(@RequestBody Map<String, Object> request) {
        String shortUrl = request.get("short_url").toString();
        int daysToAdd = (int) request.get("days_to_add");
        return urlService.updateExpiry(shortUrl, daysToAdd);
    }

    @GetMapping("/urlMappings")
    public List<UrlData> getAllUrlMappings() {
        return urlService.getAllUrlMappings();
    }

    @PostMapping("/delete-expired")
    public void deleteExpiredUrls() {
        urlService.deleteUrlWithEndOfExpiry();
    }


}
