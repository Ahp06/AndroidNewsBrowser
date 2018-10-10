package fr.uha.ensisa.huynhphuc.mynews;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CommentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        final TextView comment_text = (TextView) this.findViewById(R.id.comment);
        final Button valid_button = (Button) this.findViewById(R.id.valid_button);

        valid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment_text.getText().equals("")){
                    String comment = (String) comment_text.getText();


                }
            }
        });


    }

}
