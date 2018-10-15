package fr.uha.ensisa.huynhphuc.mynews;

import java.util.ArrayList;

public class DataHolder {

    public final static int LIST_ACTIVITY = 0;
    public final static int SAVED_ACTIVITY = 1;

    private static ArrayList<Article> articlesList = new ArrayList<Article>();
    private static ArrayList<Article> savedArticles = new ArrayList<Article>();
    private static ArrayList<Article> toDelete = new ArrayList<Article>();
    private static ArrayList<Article> commentedArticles = new ArrayList<Article>();
    private static ArrayList<Comment> comments = new ArrayList<Comment>();
    private static Settings settings;

    public static ArrayList<Article> getArticlesList() {
        return articlesList;
    }

    public static void setArticlesList(ArrayList<Article> list) {
        DataHolder.articlesList = list;
    }

    public static ArrayList<Article> getSavedArticles() {
        return savedArticles;
    }

    public static void save(Article article) {
        DataHolder.savedArticles.add(article);
    }

    public static boolean isSaved(Article article, int activity) {
        boolean isSaved = false;
        for (Article a : savedArticles) {
            if (compare(article, a)) {
                isSaved = true;
            }
        }

        boolean inToDeleteList = false;
        if (activity == DataHolder.SAVED_ACTIVITY) {
            for (Article a : toDelete) {
                if (compare(article, a)) {
                    inToDeleteList = true;
                }
            }
        }

        //Log.d("DataHolder", savedArticles.toString());

        return activity == DataHolder.SAVED_ACTIVITY ? (isSaved && !inToDeleteList) : isSaved;
    }

    public static boolean compare(Article a1, Article a2) {
        boolean condition =
                a1.getTitle().equals(a2.getTitle())
                        && a1.getAuthor().equals(a2.getAuthor())
                        && a1.getDescription().equals(a2.getDescription())
                        && a1.getUrl().equals(a2.getUrl())
                        && a1.getUrlToImage().equals(a2.getUrlToImage());

        return condition;
    }


    public static int getIndex(Article article, ArrayList<Article> list) {
        int index = 0;
        for (Article art : list) {
            if (compare(art, article)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static void delete(Article article) {
        DataHolder.savedArticles.remove(getIndex(article, savedArticles));
    }

    public static ArrayList<Article> getToDelete() {
        return toDelete;
    }

    public static void addToDelete(Article article) {
        toDelete.add(article);
    }

    public static void removeToDelete(Article article) {
        toDelete.remove(getIndex(article, toDelete));
    }

    public static Settings getSettings() {
        return settings;
    }

    public static void updateSettings(Settings settings) {
        DataHolder.settings = settings;
    }

    public static void addComment(Comment comment) {
        comments.add(comment);
    }

    public static void deleteComment(Comment comment) {
        comments.remove(comment);
    }

    public static Comment getCommentOf(Article article) {

        for (Comment comment : comments) {
            if (compare(article, comment.getArticle())) {
                return comment;
            }
        }

        return null;
    }

    public static boolean isCommented(Article article){
        boolean isCommented = false;
        for(Comment comment : comments){
            if(compare(article,comment.getArticle())){
                isCommented = true;
            }
        }

        return isCommented;
    }

    //Doublon si sauvegardé puis commenté à traiter
    //+ ajouter la suppresion des commentaires (bouton)
    //Si on enleve un articles des sauvegardés, les commentaires persistent ?
    //Pas de menu bar dans comment activity

}
