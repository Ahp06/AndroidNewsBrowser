package fr.uha.ensisa.huynhphuc.mynews;

public abstract class ArticleSetting {

    private String tag;
    private String value;

    public ArticleSetting(String tag, String value) {
        this.tag = tag;
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return this.tag + "=" + this.value;
    }
}
