package com.Client.pay.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Client.pay.R;

public class AboutActivity extends AppCompatActivity {
    private View toolbar;
    private Context context;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        overridePendingTransition(R.anim.enter, R.anim.exit);

        initUI();
        setVisibility();
        setClickListener();
    }


    private void initUI() {
        context = this;
        toolbar = findViewById(R.id.toolbar);
        imgmenu = findViewById(R.id.imgmenu);
        imgback = findViewById(R.id.imgback);
        txt_home = findViewById(R.id.txt_home);
        imgsearch = findViewById(R.id.imgsearch);
    }

    private void setVisibility() {
        imgmenu.setVisibility(View.INVISIBLE);
        imgback.setVisibility(View.VISIBLE);
        txt_home.setText(getString(R.string.about_us));
        imgsearch.setVisibility(View.GONE);
    }

    private void setClickListener() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }
}