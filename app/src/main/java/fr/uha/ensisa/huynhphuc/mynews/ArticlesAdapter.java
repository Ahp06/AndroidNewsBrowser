package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ArticlesAdapter extends ArrayAdapter<Article> {

    public ArticlesAdapter(Context context, ArrayList<Article> articles){
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Article article = getItem(position);

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

        String author;
        if(!article.getAuthor().equals(null)){
            author = article.getAuthor();
        } else {
            author = getContext().getString(R.string.unknow_author);
        }

        String content =
                "<h2>" + article.getTitle() + "</h2>" +
                        "<p> Auteur : " + author + "</p>" +
                        "</br>" +
                        "<p>" + article.getDescription() + "</p>" +
                        "</br>" +
                        "<p> écrit le : " + dateFormatted + "</p>";

        contentView.setText(Html.fromHtml(content));


        final Button save_button = (Button) convertView.findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!article.isSaved()) {
                    save_button.setText(R.string.saved_text);
                    article.save();
                } else {
                    save_button.setText(R.string.save_text);
                    article.delete();
                }
            }
        });


        if(article.isSaved()){
            Log.d(this.getClass().getName(), "Article saved : " + article.getTitle());
            save_button.setText(R.string.saved_text);
        }

        return convertView;
    }
}
