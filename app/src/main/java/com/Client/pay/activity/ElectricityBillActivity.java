package com.Client.pay.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Client.pay.R;
import com.Client.pay.utils.Utilities;

public class ElectricityBillActivity extends AppCompatActivity {

    private View toolbar;
    private Context context;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;

    private EditText edt_billnumber;
    private EditText edt_mobilenumber;
    private EditText edtrecharamt;
    private RelativeLayout relenter;

    private String message;

    private Context mcontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_bill);
        overridePendingTransition(R.anim.enter, R.anim.exit);

        initUI();
        setOnClickListener();

    }

    private void initUI() {
        mcontext = this;

        toolbar = findViewById(R.id.toolbar);
        imgmenu = findViewById(R.id.imgmenu);
        imgback = findViewById(R.id.imgback);
        txt_home = findViewById(R.id.txt_home);
        imgsearch = findViewById(R.id.imgsearch);


        edt_billnumber = findViewById(R.id.edt_billnumber);
        edt_mobilenumber = findViewById(R.id.edt_mobilenumber);
        edtrecharamt = findViewById(R.id.edtrecharamt);
        relenter = findViewById(R.id.relenter);

        imgmenu.setVisibility(View.GONE);
        imgback.setVisibility(View.VISIBLE);
        txt_home.setVisibility(View.GONE);
        imgsearch.setVisibility(View.GONE);


    }

    private void setOnClickListener() {
        relenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    onBackPressed();

                } else {
                    Toast.makeText(mcontext, ""+message, Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    private boolean isValidate() {
        if (Utilities.getInstance().isBlankString(edt_billnumber.getText().toString())) {
            message = "Please enter the consumer number";
            return false;
        } else if (Utilities.getInstance().isBlankString(edt_mobilenumber.getText().toString())) {
            message = "Please enter the mobile number";
            return false;

        } else if (Utilities.getInstance().isBlankString(edtrecharamt.getText().toString())) {
            message = "Please enter the recharge amount";
            return false;

        } else {
            return true;
        }
    }
}