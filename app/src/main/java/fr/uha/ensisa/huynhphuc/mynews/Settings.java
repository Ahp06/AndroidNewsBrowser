package fr.uha.ensisa.huynhphuc.mynews;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Settings {

    private final static String BASE_URL = "https://newsapi.org/v2/everything?";
    private final static String API_KEY = BuildConfig.ApiKey;

    private String language;
    private String pageSize;
    private String sortBy;
    private String from;
    private String to;


    public Settings(String language, String pageSize, String sortBy, String from, String to) {
        this.language = language;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.from = from;
        this.to = to;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String applySettings(String query){

        StringBuilder queryWithSettings = new StringBuilder();

        queryWithSettings.append(BASE_URL);

        queryWithSettings.append("language=" + language);
        queryWithSettings.append("&");

        queryWithSettings.append("pageSize=" + pageSize);
        queryWithSettings.append("&");

        queryWithSettings.append("sortBy=" + sortBy);
        queryWithSettings.append("&");

        if(from != ""){
            queryWithSettings.append("from=" + from);
            queryWithSettings.append("&");
        }
        if(to != ""){
            queryWithSettings.append("to=" + to);
            queryWithSettings.append("&");
        }

        queryWithSettings.append("q=" + query);
        queryWithSettings.append("&");
        queryWithSettings.append("apiKey=" + API_KEY);

        return queryWithSettings.toString();
    }

}
