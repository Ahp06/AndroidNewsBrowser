package fr.uha.ensisa.huynhphuc.mynews;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Settings extends ArticleSetting implements Parcelable {

    private final static String BASE_URL = "https://newsapi.org/v2/everything?";
    private final static String API_KEY = "18b73b4602ee45b0a0d206ff0c619d23";
    private ArrayList<ArticleSetting> settings;


    public Settings() {
        super("NONE","NONE");
        this.settings = new ArrayList<ArticleSetting>();
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
        super("","");
        this.getSetting("language").setValue(in.readString());
        this.getSetting("pageSize").setValue(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getSetting("language").getValue());
        dest.writeString(this.getSetting("pageSize").getValue());
    }

    public void addSetting(ArticleSetting setting){
        this.settings.add(setting);
    }

    public void updateSetting(ArticleSetting setting, String new_value){
        ((ArticleSetting) this.settings.get(this.settings.indexOf(setting))).setValue(new_value);
    }

    public String setSettings(String query){

        StringBuilder queryWithSettings = new StringBuilder();

        queryWithSettings.append(BASE_URL);
        for(int i = 0 ; i < settings.size() ; i++){
            queryWithSettings.append(settings.get(i).toString());
            queryWithSettings.append("&");
        }
        queryWithSettings.append("q=" + query);
        queryWithSettings.append("&");
        queryWithSettings.append("apiKey=" + API_KEY);

        return queryWithSettings.toString();
    }

    public ArrayList<ArticleSetting> getSettingsList() {
        return settings;
    }

    public boolean haveSetting(String setting_type){
        for(ArticleSetting setting: this.getSettingsList()) {
            return setting.getTag().equals(setting_type);
        }
        return false;
    }

    public ArticleSetting getSetting(String setting_type){
        for(ArticleSetting setting: this.getSettingsList()) {
            if(setting.getTag().equals(setting_type)){
                    return setting;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "settings=" + settings.toString() +
                '}';
    }
}
