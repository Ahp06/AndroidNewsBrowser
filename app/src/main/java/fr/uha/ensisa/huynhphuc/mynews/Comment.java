package fr.uha.ensisa.huynhphuc.mynews;

public class Comment {

    private Article article;
    private String comment;

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

    public Comment(Article article, String comment){
        this.article = article;
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
