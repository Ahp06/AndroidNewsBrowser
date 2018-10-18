package fr.uha.ensisa.huynhphuc.mynews.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONObject;

@Entity(tableName = "saved_article")
//,foreignKeys = @ForeignKey(entity = Comment.class, parentColumns = "id", childColumns = "user_id"))
public class Article implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "saved_ID")
    private int saved_ID;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "urlToImage")
    private String urlToImage;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "publishedAt")
    private String publishedAt;

    @ColumnInfo(name = "url")
    private String url;

    public Article(String title, String author, String urlToImage, String description, String publishedAt, String url) {
        this.title = title;
        this.author = author;
        this.urlToImage = urlToImage;
        this.description = description;
        this.publishedAt = publishedAt;
        this.url = url;
    }

    /***
     * Constructor by reading JSON object fields
     * @param articleJSON
     */
    public Article(JSONObject articleJSON) {
        this.title = articleJSON.optString("title");
        this.author = articleJSON.optString("author");
        this.urlToImage = articleJSON.optString("urlToImage");
        this.description = articleJSON.optString("description");
        this.publishedAt = articleJSON.optString("publishedAt");
        this.url = articleJSON.optString("url");
    }

    protected Article(Parcel in) {
        title = in.readString();
        author = in.readString();
        urlToImage = in.readString();
        description = in.readString();
        publishedAt = in.readString();
        url = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);

        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getUrl() {
        return url;
    }

    public int getSaved_ID() {
        return saved_ID;
    }

    public void setSaved_ID(int saved_ID) {
        this.saved_ID = saved_ID;
    }

    @Override
    public String toString() {
        return "Article{" +
                "saved_ID=" + saved_ID +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", description='" + description + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(urlToImage);
        dest.writeString(description);
        dest.writeString(publishedAt);
        dest.writeString(url);
    }
}