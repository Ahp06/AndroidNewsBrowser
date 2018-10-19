package fr.uha.ensisa.huynhphuc.mynews.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import fr.uha.ensisa.huynhphuc.mynews.model.Article;
import fr.uha.ensisa.huynhphuc.mynews.model.Comment;
import fr.uha.ensisa.huynhphuc.mynews.model.Settings;

public class DataHolder {

    public final static int LIST_ACTIVITY = 0;
    public final static int SAVED_ACTIVITY = 1;
    public static final int COMMENT_DELETED = 2;

    private static ArrayList<Article> articlesList = new ArrayList<Article>();
    private static ArrayList<Article> savedArticles = new ArrayList<Article>();
    private static ArrayList<Article> toDelete = new ArrayList<Article>();
    private static ArrayList<Comment> comments = new ArrayList<Comment>();
    private static ArrayList<String> history = new ArrayList<String>();
    private static Settings settings;
    private static boolean dataLoaded = false;

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

    public static boolean isCommented(Article article) {
        boolean isCommented = false;
        for (Comment comment : comments) {
            if (compare(article, comment.getArticle())) {
                isCommented = true;
            }
        }

        return isCommented;
    }

    public static ArrayList<Comment> getComments() {
        return comments;
    }

    public static void addIntoHistory(String query) {
        history.add(query);
    }

    public static ArrayList<String> getHistory() {
        return history;
    }

    public static void setSavedArticles(ArrayList<Article> savedArticles) {
        DataHolder.savedArticles = savedArticles;
    }

    public static void setComments(ArrayList<Comment> comments) {
        DataHolder.comments = comments;
    }

    public static void setHistory(ArrayList<String> history) {
        DataHolder.history = history;
    }

    public static void setSettings(Settings settings) {
        DataHolder.settings = settings;
    }

    public static boolean isDataLoaded() {
        return dataLoaded;
    }

    public static void setDataLoaded(boolean dataLoaded) {
        dataLoaded = dataLoaded;
    }

    public static void writeSaved(Context context) {
        String filename = "saved";
        FileOutputStream outputStream;

        Gson gson = new Gson();
        Log.d("DataHolder","saved articles in holder = " + DataHolder.getSavedArticles());
        String savedJson = gson.toJson(DataHolder.getSavedArticles());

        File fileDir = new File(context.getFilesDir(), filename);
        fileDir.delete();

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(savedJson.getBytes());
            outputStream.flush();
            fileDir = new File(context.getFilesDir(), filename);
            Toast.makeText(context, "File saved at : " + fileDir, Toast.LENGTH_LONG).show();
            outputStream.close();

            Log.d("DataHolder", "Write done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Article> readSaved(Context context) {
        String filename = "saved";
        FileInputStream fis;

        Gson gson = new Gson();
        try {
            fis = context.getApplicationContext().openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String savedJson;
            while ((savedJson = bufferedReader.readLine()) != null) {
                sb.append(savedJson);
            }
            fis.close();

            Type articlesType = new TypeToken<ArrayList<Article>>() {
            }.getType();

            return  gson.fromJson(sb.toString(), articlesType);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
