package com.Client.pay.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.adapter.ReportAdaptor;
import com.Client.pay.model.Login_bean;
import com.Client.pay.model.ReportBean;
import com.Client.pay.model.ReportModel;
import com.Client.pay.retrofit.APICallBack;
import com.Client.pay.retrofit.APIService;
import com.Client.pay.utils.SharedPreferenceKeys;
import com.Client.pay.utils.Utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReportActivity extends AppCompatActivity {

    private View toolbar;
    private Context context;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;
    //    private ArrayList<ReportBean.TranstionData> reportlist = new ArrayList<>();
    private Intent intent;
    private ReportModel reportModel;
    private RelativeLayout relfromdate;
    private RelativeLayout relTodate;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Calendar mCalender;
    private Calendar cal;
    private DatePickerDialog datePickerDialog;
    private String fromDate;
    private EditText etfromdate;
    private EditText etToDate;
    private Boolean fromdate = false;
    private int day;
    private int date;
    private int year;
    private int fromday;
    private int today;
    private int frommonth;
    private int tomonth;
    private String frdate;
    private String todate;
    private RelativeLayout relinfo;
    private RecyclerView recyle_history;
    private ReportAdaptor reportAdaptor;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
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
        txt_home.setText(R.string.reports);

        imgsearch = findViewById(R.id.imgsearch);
        imgsearch.setVisibility(View.GONE);

        relfromdate = findViewById(R.id.relfromdate);
        relTodate = findViewById(R.id.relTodate);
        etfromdate = findViewById(R.id.etfromdate);
        etToDate = findViewById(R.id.etToDate);
        relinfo = findViewById(R.id.relinfo);

        recyle_history = findViewById(R.id.recyle_history);

        mCalender = Calendar.getInstance();
        cal = Calendar.getInstance();

    }

    private void callReportAPI() {

        relinfo.setVisibility(View.GONE);
        recyle_history.setVisibility(View.VISIBLE);
        reportModel = new ReportModel();
        reportModel.setUser_id(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.user_id));
        reportModel.setFrom_date(frdate);
        reportModel.setTo_date(todate);

        if (Utilities.getInstance().isNetworkAvailable(context)) {
            Utilities.getInstance().showProgressDialog(context);
            APIService.getInstance().getBaseUrl()
                    .report(reportModel)
                    .enqueue(new APICallBack<ArrayList<ReportBean>>() {
                        @Override
                        protected void success(ArrayList<ReportBean> reportBeans) {
                            Utilities.getInstance().hideProgressdialog();
                            setData(reportBeans);

                        }

                        @Override
                        protected void failure(String errorMsg) {
                            Utilities.getInstance().hideProgressdialog();
                            Log.d("Failure reason", "reson" + errorMsg);
                            Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        protected void onFailure(String failureReason) {
                            Utilities.getInstance().hideProgressdialog();
                            Log.d("Failure reason", "reson" + failureReason);

                            Toast.makeText(context, "" + failureReason, Toast.LENGTH_SHORT).show();


                        }
                    });
        } else {
            Toast.makeText(context, "Please check internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(ArrayList<ReportBean> reportBeans) {

        reportAdaptor = new ReportAdaptor(context, reportBeans);
        recyle_history.setAdapter(reportAdaptor);
        linearLayoutManager = new LinearLayoutManager(context);
        recyle_history.setLayoutManager(linearLayoutManager);

    }

    private void setData(Login_bean model) {
    }

    private void setClickListener() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        etfromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "from date is selected", Toast.LENGTH_SHORT).show();
                etToDate.setText("");
                fromdate = true;
                openCalender();
            }
        });
        etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromdate = false;
                openToDateCalender();
            }
        });

        updateReportDueDate();

    }

    private void openToDateCalender() {

        if (!Utilities.getInstance().isBlankString(etfromdate.getText().toString())) {
            cal.set(year, day - 1, date);
            datePickerDialog = new DatePickerDialog(context, dateSetListener, mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH), mCalender.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();


        } else {
            Toast.makeText(context, "Plese select the from date", Toast.LENGTH_SHORT).show();
        }

    }

    private void openCalender() {
        datePickerDialog = new DatePickerDialog(context, dateSetListener, mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH), mCalender.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
//        updateReportDueDate();
    }

    private void updateReportDueDate() {
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthofyear, int dayofmonth) {

                mCalender.set(Calendar.YEAR, year);
                mCalender.set(Calendar.MONTH, monthofyear);
                mCalender.set(Calendar.DAY_OF_MONTH, dayofmonth);
                if (fromdate) {
                    fromday = dayofmonth;
                    frommonth = monthofyear;
                } else {
                    today = dayofmonth;
                    tomonth = monthofyear;
                }

//                Toast.makeText(context, "day"+dayofmonth, Toast.LENGTH_SHORT).show();

                updateLable();

            }
        };
    }

    private void updateLable() {
        String mydateformat = "yy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mydateformat);

        fromDate = simpleDateFormat.format(mCalender.getTime());
        day = mCalender.getTime().getMonth();
        date = mCalender.getTime().getDate();
        year = mCalender.getTime().getYear();

//        Toast.makeText(context, "day "+day+"date  "+date+"year "+year, Toast.LENGTH_SHORT).show();

        if (fromdate) {
            etfromdate.setText(fromDate);
            frdate = fromDate;
        } else {
            etToDate.setText(fromDate);
            todate = fromDate;
        }

        if (!Utilities.getInstance().isBlankString(etToDate.getText().toString())) {
            if ((fromday < today && frommonth > tomonth) || (fromday < today && frommonth == tomonth)) {
                callReportAPI();
            } else {
                Toast.makeText(context, "The To date must greater than the from ", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }
}