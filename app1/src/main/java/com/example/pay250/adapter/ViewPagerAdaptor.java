package com.Client.pay.adapter;

import android.content.Context;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.Client.pay.R;
import com.Client.pay.model.SliderBean;

import java.util.ArrayList;

public class ViewPagerAdaptor extends PagerAdapter {

    private Context mcontext;
    private ArrayList<SliderBean> adslidelist = new ArrayList<>();
    LayoutInflater layoutInflater;


    int imageURL[] = {
            R.drawable.billpayment,
            R.drawable.allrecharge,
            R.drawable.mobilerecharge,
            R.drawable.moneytransfer
    };
    int headings[] = {
            R.string.bill_payment,
            R.string.all_recharge,
            R.string.mobile_recharge,
            R.string.money_transfer
    };

    public ViewPagerAdaptor(Context mcontext, ArrayList<SliderBean> slidelist) {
//        this.adslidelist = slidelist;
        this.mcontext = mcontext;
        adslidelist.addAll(slidelist);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.sliderlayout, container, false);
        ImageView imageView = view.findViewById(R.id.siderimg);
        TextView heading = view.findViewById(R.id.heading);

//        for (int i = 0; i < adslidelist.size(); i++) {
        Log.d("image", "url" + adslidelist.get(position).getImgaeid());
        imageView.setImageResource(imageURL[position]);
//        }
        heading.setText(headings[position]);
        container.addView(view);

        return view;
    }


    @Override
    public int getCount() {
        return adslidelist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }

    public void destroyItem(ViewGroup container,
                            int position,
                            Object object) {
        container.removeView((View) object);
    }
}
