package com.Client.pay.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Client.pay.R;
import com.Client.pay.adapter.CustomeSpinnerAdaptor;
import com.Client.pay.model.Customspinnerbin;
import com.Client.pay.utils.SharedPreferenceKeys;
import com.Client.pay.utils.Utilities;

import java.util.ArrayList;

public class MyWalletActivity extends AppCompatActivity {

    private View toolbar;
    private Context context;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;
    private RelativeLayout rel_main;

    private RelativeLayout rel_spinner;
    private Spinner spinnerstatus;

    private TextView txtmainamt;
    private TextView txtapesamt;
    private TextView txtcreditamt;

    private Customspinnerbin customspinnerbin;
    private ArrayList<Customspinnerbin> customlist = new ArrayList<>();

    CustomeSpinnerAdaptor customeSpinnerAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        initUI();
        setClickListener();

    }

    private void initUI() {
        context = this;
        toolbar = findViewById(R.id.toolbar);

        imgmenu = findViewById(R.id.imgmenu);
        imgmenu.setVisibility(View.INVISIBLE);

        imgback = findViewById(R.id.imgback);
        imgback.setVisibility(View.VISIBLE);

        txt_home = findViewById(R.id.txt_home);
        txt_home.setText(R.string.recharge_to_all);

        imgsearch = findViewById(R.id.imgsearch);
        imgsearch.setVisibility(View.GONE);

        txtmainamt = findViewById(R.id.txtmainamt);
        txtapesamt = findViewById(R.id.txtapesamt);
        txtcreditamt = findViewById(R.id.txtcreditamt);

        spinnerstatus=findViewById(R.id.spinnerstatus);

        if (!Utilities.getInstance().getPreference(context, SharedPreferenceKeys.main_balance).isEmpty())
            txtmainamt.setText(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.main_balance));
        else
            txtmainamt.setText("0.00");

        if (!Utilities.getInstance().getPreference(context, SharedPreferenceKeys.aeps_balnce).isEmpty())
            txtapesamt.setText(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.aeps_balnce));
        else
            txtapesamt.setText("0.00");

        if (!Utilities.getInstance().getPreference(context, SharedPreferenceKeys.stock_balance).isEmpty())
            txtcreditamt.setText(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.stock_balance));
        else
            txtcreditamt.setText("0.00");

        customspinnerbin = new Customspinnerbin();
        customspinnerbin.setName("Select Payment Method");
        customlist.add(customspinnerbin);

        customeSpinnerAdaptor = new CustomeSpinnerAdaptor(context, R.layout.customspinnerorw, customlist);
        spinnerstatus.setAdapter(customeSpinnerAdaptor);
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