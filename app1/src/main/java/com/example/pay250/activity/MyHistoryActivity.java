package com.Client.pay.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.Client.pay.R;
import com.Client.pay.fragment.AllFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MyHistoryActivity extends AppCompatActivity {

    private View toolbar;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;
    private Context context;

    private TabLayout tabLayout;
    private ViewPager viewpager;
    ViewGroup.MarginLayoutParams layoutParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        intiUI();
        setClickListener();

    }


    private void intiUI() {
        context = this;
        toolbar = findViewById(R.id.toolbar);

        imgmenu = findViewById(R.id.imgmenu);
        imgmenu.setVisibility(View.INVISIBLE);

        imgback = findViewById(R.id.imgback);
        imgback.setVisibility(View.VISIBLE);

        txt_home = findViewById(R.id.txt_home);
        txt_home.setText(R.string.history);

        imgsearch = findViewById(R.id.imgsearch);
        imgsearch.setVisibility(View.GONE);


        //find ids of the tab layout

        tabLayout = findViewById(R.id.tabLayout);

        viewpager = findViewById(R.id.viewpager);

        setupViewPager(viewpager);
        tabLayout.setupWithViewPager(viewpager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null);
            tabLayout.getTabAt(i).setCustomView(tv);
            setTabWidthASWrapContent(i);
        }

    }

    private void setTabWidthASWrapContent(int i) {
        LinearLayout layout = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(i));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.weight = 0.5f; // e.g. 0.5f
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout.setLayoutParams(layoutParams);
    }


    private void setupViewPager(ViewPager viewpager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllFragment(), "ALL");
        adapter.addFragment(new AllFragment(), "MOBILE");
        adapter.addFragment(new AllFragment(), "DTH");
        adapter.addFragment(new AllFragment(), "POSTPAID");
        adapter.addFragment(new AllFragment(), "ELECTRICITY");


        viewpager.setAdapter(adapter);

    }

    private void setClickListener() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }
}