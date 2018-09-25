package fr.uha.ensisa.huynhphuc.mynews;

public class ArticleQuery {

    private String query;
    private Settings settings;

    public ArticleQuery(String query){
        this.query = query;
        this.settings = new Settings();
    }

    public ArticleQuery(String query,Settings settings){
        this.query = query;
        this.settings = settings;
    }

    public void browse(String query){
        String queryWithSettings = this.applySettings(query);
        //this.settings.


    }

    public String applySettings(String query) {

        return null;
    }


}
