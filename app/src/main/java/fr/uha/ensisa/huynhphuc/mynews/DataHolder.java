package fr.uha.ensisa.huynhphuc.mynews;

import java.util.ArrayList;

public class DataHolder {

    private static ArrayList<Article> articlesList = new ArrayList<Article>();
    private static ArrayList<Article> savedArticles = new ArrayList<Article>();
    private static ArrayList<Article> toDelete = new ArrayList<>();
    private static Settings settings;

    public static ArrayList<Article> getArticlesList() {
        return articlesList;
    }

    public static void setArticlesList(ArrayList<Article> list) {
        DataHolder.articlesList = list;
    }

    public static ArrayList<Article> getSavedArticles(){
        return savedArticles;
    }

    public static void save(Article article){
        DataHolder.savedArticles.add(article);
    }

    public static boolean isSaved(Article article){
        return savedArticles.contains(article) && !toDelete.contains(article);
    }

    public static int getIndex(Article article, ArrayList<Article> list){
        int index = 0;
        for(Article art : list){
            if(art.equals(article)){
                return index;
            }
            index ++;
        }
        return -1;
    }

    public static void delete(Article article){
        DataHolder.savedArticles.remove(getIndex(article,savedArticles));
    }

    public static ArrayList<Article> getToDelete() {
        return toDelete;
    }

    public static void addToDelete(Article article){
        toDelete.add(article);
    }

    public static void removeToDelete(Article article){
        toDelete.remove(getIndex(article,toDelete));
    }

    public static Settings getSettings(){
        return settings;
    }

    public static void updateSettings(Settings settings){
        DataHolder.settings = settings;
    }


}
