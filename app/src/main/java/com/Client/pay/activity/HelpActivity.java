package com.Client.pay.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Client.pay.R;

public class HelpActivity extends AppCompatActivity {

    private View toolbar;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        initUI();
        setOnClickListener();

    }


    private void initUI() {
        context = this;
        toolbar = findViewById(R.id.toolbar);

        imgmenu = findViewById(R.id.imgmenu);
        imgmenu.setVisibility(View.INVISIBLE);

        imgback = findViewById(R.id.imgback);
        imgback.setVisibility(View.VISIBLE);

        txt_home = findViewById(R.id.txt_home);
        txt_home.setText("Help");

        imgsearch = findViewById(R.id.imgsearch);
        imgsearch.setVisibility(View.GONE);
    }


    private void setOnClickListener() {
        imgback.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           onBackPressed();
                                       }
                                   }
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }
}