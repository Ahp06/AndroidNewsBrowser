package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ArticlesAdapter extends ArrayAdapter<Article> {

    public ArticlesAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    static class ViewHolder {
        private TextView contentView;
        private ImageView imageView;
        private Button save_button;
        private Button comment_button;
        int position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final Article article = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder();
            holder.contentView = (TextView) convertView.findViewById(R.id.articleContent);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.save_button = (Button) convertView.findViewById(R.id.save_button);
            holder.comment_button = (Button) convertView.findViewById(R.id.comment_button);
            holder.position = position;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DataHolder.isSaved(article, DataHolder.LIST_ACTIVITY)) {
                    DataHolder.getSavedArticles().add(article);
                } else {
                    DataHolder.delete(article);
                }
                notifyDataSetChanged();
            }
        });

        holder.comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("article", article);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });

        //Image downloading
        ArticleImageDownload downloader = new ArticleImageDownload(holder.imageView);
        downloader.download(article.getUrlToImage());


        //Date parsing
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;
        try {
            date = df.parse(article.getPublishedAt().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormatted = outputFormatter.format(date);

        //Set content into article item
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

        holder.contentView.setText(Html.fromHtml(content));

        if (DataHolder.isSaved(article,DataHolder.LIST_ACTIVITY)) {
            holder.save_button.setText(R.string.saved_text);
        } else {
            holder.save_button.setText(R.string.save_text);
        }

        if (DataHolder.isCommented(article)) {
            holder.comment_button.setText(R.string.see_comment);
        } else {
            holder.comment_button.setText(R.string.comment_text);
        }

        return convertView;
    }

}
