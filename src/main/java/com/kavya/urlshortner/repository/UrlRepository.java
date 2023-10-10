package com.kavya.urlshortner.repository;

import java.util.Date;

public interface UrlRepository {

    public void writeToCSV(String longUrl);
    public void readFromCSV(String shortUrl);
    public void updateFromCSV(String shortUrl);
    public void deleteFromCSV(Date expiryDate);

}
