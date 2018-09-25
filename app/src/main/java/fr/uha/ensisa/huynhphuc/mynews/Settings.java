package fr.uha.ensisa.huynhphuc.mynews;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Settings implements Parcelable {

    private final static String BASE_URL = "https://newsapi.org/v2/everything?";
    private final static String API_KEY = "18b73b4602ee45b0a0d206ff0c619d23";

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

    public static final Creator<Settings> CREATOR = new Creator<Settings>() {
        @Override
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };

    public Settings(Parcel in) {
        this.language = in.readString();
        this.pageSize = in.readString();
        this.sortBy = in.readString();
        this.from = in.readString();
        this.to = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(language);
        dest.writeString(pageSize);
        dest.writeString(sortBy);
        dest.writeString(from);
        dest.writeString(to);
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

        if(from != null){
            queryWithSettings.append("from=" + from);
            queryWithSettings.append("&");
        }
        if(to != null){
            queryWithSettings.append("to=" + to);
            queryWithSettings.append("&");
        }

        queryWithSettings.append("q=" + query);
        queryWithSettings.append("&");
        queryWithSettings.append("apiKey=" + API_KEY);

        return queryWithSettings.toString();
    }

}
