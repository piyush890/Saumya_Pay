package com.Client.pay.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Client.pay.R;
import com.Client.pay.utils.Utilities;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private View toolbar;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;


    private Context context;
    private WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        initUI();
        loadURL();
        setClickListner();

    }

    private void setClickListner() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initUI() {
        context = this;
        toolbar = findViewById(R.id.toolbar);

        imgmenu = findViewById(R.id.imgmenu);
        imgmenu.setVisibility(View.INVISIBLE);

        imgback = findViewById(R.id.imgback);
        imgback.setVisibility(View.VISIBLE);

        txt_home = findViewById(R.id.txt_home);
        txt_home.setText(R.string.privacy_policy);

        imgsearch = findViewById(R.id.imgsearch);
        imgsearch.setVisibility(View.GONE);

        webview = findViewById(R.id.webview);

    }

    private void loadURL() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Utilities.getInstance().showProgressDialog(context);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Utilities.getInstance().hideProgressdialog();
            }
        });
        webview.loadUrl("http://www.example.com");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }
}