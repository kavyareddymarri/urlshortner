package com.kavya.urlshortner.service;

import com.kavya.urlshortner.data.UrlData;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UrlService {

    public String longToShortUrl(String url) throws IOException;
    public Optional<String> getLongUrlGivenShortUrl(String url);


    public boolean updateShortUrl(String shortUrl, String updateUrl);
    public List<UrlData> getAllUrlMappings();
    public boolean updateExpiry(String shortUrl, int daysToAdd);
    public void deleteUrlWithEndOfExpiry();
}
