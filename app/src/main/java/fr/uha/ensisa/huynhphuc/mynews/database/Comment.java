package fr.uha.ensisa.huynhphuc.mynews.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import fr.uha.ensisa.huynhphuc.mynews.database.Article;

@Entity(tableName = "comment")
public class Comment {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "comment_ID")
    private int comment_ID;

    @ColumnInfo(name = "saved_ID")
    private int saved_ID;

    @ColumnInfo(name = "comment")
    private String comment;



    private Article article;

    public Comment(Article article, String comment){
        this.article = article;
        this.comment = comment;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



    @Override
    public String toString() {
        return "Comment{" +
                "article=" + article +
                ", comment='" + comment + '\'' +
                '}';
    }

}
