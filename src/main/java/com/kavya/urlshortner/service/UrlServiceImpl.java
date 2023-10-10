package com.kavya.urlshortner.service;

import com.kavya.urlshortner.data.UrlData;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UrlServiceImpl implements UrlService{

    private static final String CSV_FILE = "C:/Users/kavya/urls.csv";
    @Override
    public String longToShortUrl(String longUrl) throws IOException {
        String shortUrl = generateShortUrl();
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime expirationDate = creationDate.plusDays(30);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            writer.append("shortUrl").append(",").append("longUrl").
                    append(",").append("creationDate").append(",").append("expirationDate");
            writer.newLine();
            String line = shortUrl + "," + longUrl + "," + creationDate + "," + expirationDate;
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shortUrl;

    }

    @Override
    public Optional<String> getLongUrlGivenShortUrl(String shortUrl) {

        List<UrlData> mappings = getAllUrlMappings();

        for (UrlData mapping : mappings) {
            if (mapping.getShortUrl().equals(shortUrl)) {
                if (mapping.isExpired()) {
                    return Optional.empty();
                }
                return Optional.of(mapping.getLongUrl());
            }
        }

        return Optional.empty();

    }

    @Override
    public boolean updateShortUrl(String shortUrl, String updateUrl) {

        List<UrlData> mappings = getAllUrlMappings();
        boolean updated = false;

        for (UrlData mapping : mappings) {
            if (mapping.getShortUrl().equals(shortUrl)) {
                mapping.setLongUrl(updateUrl);
                updated = true;
                break;
            }
        }

        if (updated) {
            saveMappingsToFile(mappings);
        }

        return updated;

    }
    @Override
    public List<UrlData> getAllUrlMappings() {
        List<UrlData> mappings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                UrlData mapping = new UrlData();
                mapping.setShortUrl(parts[0]);
                mapping.setLongUrl(parts[1]);
                mapping.setCreationDate(LocalDateTime.parse(parts[2]));
                mapping.setExpirationDate(LocalDateTime.parse(parts[3]));
                mappings.add(mapping);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mappings;
    }

    private void saveMappingsToFile(List<UrlData> mappings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (UrlData mapping : mappings) {
                String line = mapping.getShortUrl() + "," + mapping.getLongUrl() + ","
                        + mapping.getCreationDate() + "," + mapping.getExpirationDate();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean updateExpiry(String shortUrl, int daysToAdd) {
        List<UrlData> mappings = getAllUrlMappings();
        boolean updated = false;

        for (UrlData mapping : mappings) {
            if (mapping.getShortUrl().equals(shortUrl)) {
                mapping.extendExpiration(daysToAdd);
                updated = true;
                break;
            }
        }

        if (updated) {
            saveMappingsToFile(mappings);
        }

        return updated;
    }
    @Override
    public void deleteUrlWithEndOfExpiry() {

            List<UrlData> mappings = getAllUrlMappings();
            LocalDateTime now = LocalDateTime.now();

            Iterator<UrlData> iterator = mappings.iterator();
            while (iterator.hasNext()) {
                UrlData mapping = iterator.next();
                if (mapping.getExpirationDate().isBefore(now)) {
                    iterator.remove();
                }
            }

            saveMappingsToFile(mappings);
    }

    private String generateShortUrl() {
        return "http://localhost:8080/" + UUID.randomUUID().toString().substring(0, 6);
    }
}
