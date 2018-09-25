package fr.uha.ensisa.huynhphuc.mynews;

import java.util.ArrayList;

public class Settings extends ArticleSetting {

    private final static String BASE_URL = "https://newsapi.org/v2/top-headlines?";
    private final static String API_KEY = "18b73b4602ee45b0a0d206ff0c619d23";
    private ArrayList<ArticleSetting> settings;

    public Settings() {
        super("NONE","NONE");
        this.settings = new ArrayList<ArticleSetting>();
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
        }
        queryWithSettings.append("q=" + query);
        queryWithSettings.append("&");
        queryWithSettings.append("apiKey=" + API_KEY);

        return queryWithSettings.toString();
    }
}
