package fr.uha.ensisa.huynhphuc.mynews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fr.uha.ensisa.huynhphuc.mynews.database.Comment;
import fr.uha.ensisa.huynhphuc.mynews.DataHolder;
import fr.uha.ensisa.huynhphuc.mynews.R;
import fr.uha.ensisa.huynhphuc.mynews.database.Article;

public class CommentActivity extends Activity {

    private Article article;
    private Comment comment;
    private int previousActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        final TextView comment_text = (TextView) this.findViewById(R.id.comment);
        final Button cancel_button = (Button) this.findViewById(R.id.cancel_comment);
        final Button valid_button = (Button) this.findViewById(R.id.valid_comment);
        final Button delete_comment = (Button) this.findViewById(R.id.delete_comment);

        if (this.getIntent().hasExtra("article")) {
            this.article = (Article) this.getIntent().getParcelableExtra("article");
        }

        if(this.getIntent().hasExtra("activity")){
            this.previousActivity = this.getIntent().getIntExtra("activity",-1);
        }

        this.comment = DataHolder.getCommentOf(article);
        if (comment != null) {
            comment_text.setText(comment.getComment());
        } else {
            comment_text.setText("");
            comment = new Comment(article, "");
        }

        valid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!comment_text.getText().equals("")) {
                    String content = comment_text.getText().toString();
                    comment.setComment(content);
                    DataHolder.addComment(comment);
                    if(!DataHolder.isSaved(article,DataHolder.LIST_ACTIVITY)){
                        DataHolder.save(article);
                    }
                }
                finish(); //Go back to previous activity

            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHolder.deleteComment(comment);
                Toast.makeText(v.getContext(),R.string.delete_comment_text, Toast.LENGTH_SHORT).show();
                Intent intent;
                if(previousActivity == DataHolder.LIST_ACTIVITY){
                    intent = new Intent(v.getContext(),ArticlesListActivity.class);
                } else {
                    intent = new Intent(v.getContext(),SavedArticlesActivity.class);
                }
                startActivityForResult(intent, DataHolder.COMMENT_DELETED);
            }
        });

    }

}
