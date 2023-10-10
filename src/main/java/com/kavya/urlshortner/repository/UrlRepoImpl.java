package com.kavya.urlshortner.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@Service
public class UrlRepoImpl implements UrlRepository {





    public UrlRepoImpl() throws IOException {
    }


    @Override
    public void writeToCSV(String longUrl) {

    }

    @Override
    public void readFromCSV(String shortUrl) {

    }

    @Override
    public void updateFromCSV(String shortUrl) {

    }

    @Override
    public void deleteFromCSV(Date expiryDate) {

    }



}
