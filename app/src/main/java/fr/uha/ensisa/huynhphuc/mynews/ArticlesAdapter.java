package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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

        String content =
                "<h2>" + article.getTitle() + "</h2>" +
                        "<p> écrit par : " + article.getAuthor() + "</p>" +
                        "</br>" +
                        "<p>" + article.getDescription() + "</p>" +
                        "</br>" +
                        "<p> écrit le : " + article.getPublishedAt() + "</p>";

        contentView.setText(Html.fromHtml(content));

        return convertView;
    }
}
