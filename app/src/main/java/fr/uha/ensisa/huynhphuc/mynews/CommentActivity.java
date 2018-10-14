package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CommentActivity extends Activity {

    private Article article;
    private Comment comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        final TextView comment_text = (TextView) this.findViewById(R.id.comment);
        final Button cancel_button = (Button) this.findViewById(R.id.cancel_comment);
        final Button valid_button = (Button) this.findViewById(R.id.valid_comment);

        if (this.getIntent().hasExtra("article")) {
            this.article = (Article) this.getIntent().getParcelableExtra("article");
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

    }

}
