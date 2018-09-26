package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ArticlesAdapter extends ArrayAdapter<Article> {

    private View convertView;

    public ArticlesAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Article article = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }

        TextView contentView = (TextView) convertView.findViewById(R.id.articleContent);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        ArticleImageDownload imgDownloader = new ArticleImageDownload(imageView);
        if(!article.getUrlToImage().equals(null)){
            imgDownloader.execute(article.getUrlToImage());
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;
        try {
            date = df.parse(article.getPublishedAt().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormatted = outputFormatter.format(date);

        String author = "Non renseigné";
        if(!article.getAuthor().equals(null)){
            author = article.getAuthor();
        }

        String content =
                "<h2>" + article.getTitle() + "</h2>" +
                        "<p> Auteur : " + author + "</p>" +
                        "</br>" +
                        "<p>" + article.getDescription() + "</p>" +
                        "</br>" +
                        "<p> écrit le : " + dateFormatted + "</p>";

        contentView.setText(Html.fromHtml(content));

        return convertView;
    }
}
