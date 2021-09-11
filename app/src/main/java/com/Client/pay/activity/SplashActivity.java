package com.Client.pay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.Client.pay.R;
import com.Client.pay.adapter.ViewPagerAdaptor;
import com.Client.pay.model.SliderBean;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.widget.LinearLayout.OnClickListener;

public class SplashActivity extends AppCompatActivity {
    private ViewPager frmviewPager;
    ViewPagerAdaptor viewPagerAdaptor;
    private ArrayList<SliderBean> slidelist = new ArrayList<>();
    private Context mcontext;
    private Handler handler;
    private int currentPage = 0;
    private int NUM_PAGES = 4;
    Timer timer;
    private Long DELAY_MS = 500l;
    private long PERIOD_MS = 3000l;
    private LinearLayout lldots;
    private TextView textView[];
    private View view[];
    private Intent intent;
    private RelativeLayout relskip;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        hidestatusbar();

        mcontext = this;
        initUI();
        setClickListener();
    }

    private void setClickListener() {
        relskip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(mcontext, LoginActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                finish();
            }
        });

    }

    private void hidestatusbar() {
        View decorView = getWindow().getDecorView();
////Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    private void setArraylist() {
        SliderBean slidebillpayment = new SliderBean();
        slidebillpayment.setImgaeid(R.drawable.billpayment);
        slidebillpayment.setImageheading(R.string.bill_payment);
        slidelist.add(slidebillpayment);

        SliderBean slideallrecharge = new SliderBean();
        slidebillpayment.setImgaeid(R.drawable.allrecharge);
        slidebillpayment.setImageheading(R.string.all_recharge);
        slidelist.add(slideallrecharge);


        SliderBean slidemobilerecharge = new SliderBean();
        slidebillpayment.setImgaeid(R.drawable.mobilerecharge);
        slidebillpayment.setImageheading(R.string.mobile_recharge);
        slidelist.add(slidemobilerecharge);


        SliderBean slidemoneytransfer = new SliderBean();
        slidebillpayment.setImgaeid(R.drawable.moneytransfer);
        slidebillpayment.setImageheading(R.string.money_transfer);
        slidelist.add(slidemoneytransfer);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initUI() {
        frmviewPager = findViewById(R.id.frmviewPager);
        lldots = findViewById(R.id.lldots);
        relskip = findViewById(R.id.relskip);

        setArraylist();
        viewPagerAdaptor = new ViewPagerAdaptor(mcontext, slidelist);
        frmviewPager.setAdapter(viewPagerAdaptor);
        addDots(0);


        frmviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                addDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addDots(int pos) {
        view = new View[4];
        lldots.removeAllViews();

        for (int i = 0; i < 4; i++) {
            view[i] = new View(this);
            view[i].setLayoutParams(new LinearLayout.LayoutParams(35, 10));
            view[i].setBackgroundColor(getResources().getColor(R.color.notselectslide));
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view[i].getLayoutParams();
            marginLayoutParams.setMarginStart(5);
            marginLayoutParams.setMarginEnd(20);
            lldots.addView(view[i]);
        }
        if (pos >= 0) {
            view[pos].setBackgroundColor(getResources().getColor(R.color.slideselect));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void automateslider() {
        /*After setting the adapter use the timer */
        handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
                frmviewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }

}