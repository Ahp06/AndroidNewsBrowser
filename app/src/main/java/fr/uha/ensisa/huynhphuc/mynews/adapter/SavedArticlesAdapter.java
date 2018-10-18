package fr.uha.ensisa.huynhphuc.mynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;

import fr.uha.ensisa.huynhphuc.mynews.DataHolder;
import fr.uha.ensisa.huynhphuc.mynews.R;
import fr.uha.ensisa.huynhphuc.mynews.activity.CommentActivity;
import fr.uha.ensisa.huynhphuc.mynews.database.Article;
import fr.uha.ensisa.huynhphuc.mynews.utils.ArticleImageDownload;

import static android.app.Activity.RESULT_CANCELED;

public class SavedArticlesAdapter extends ArrayAdapter<Article> {

    public SavedArticlesAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Article article = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.saved_item_list, parent, false);
        }

        TextView contentView = (TextView) convertView.findViewById(R.id.articleContent_saved);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView_saved);

        ArticleImageDownload downloader = new ArticleImageDownload(imageView);
        downloader.download(article.getUrlToImage());

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
        if(article.getUrlToImage() != "null"){
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

        final Button delete_button = (Button) convertView.findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataHolder.isSaved(article,DataHolder.SAVED_ACTIVITY)){
                    delete_button.setText(R.string.cancel_delete_text);
                    DataHolder.addToDelete(article);
                    Log.d("Log del","to delete = " + DataHolder.getToDelete());

                } else {
                    delete_button.setText(R.string.delete_text);
                    DataHolder.removeToDelete(article);
                    Log.d(this.getClass().getName(),"to delete = " + DataHolder.getToDelete());
                }
            }
        });

        final Button comment_button = (Button) convertView.findViewById(R.id.saved_comment_button);
        comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("article", article);
                bundle.putInt("activity", DataHolder.SAVED_ACTIVITY);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });

        if (DataHolder.isCommented(article)) {
           comment_button.setText(R.string.see_comment);
        } else {
            comment_button.setText(R.string.comment_text);
        }

        return convertView;
    }
}

