package com.Client.pay.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Client.pay.R;
import com.Client.pay.adapter.CustomeSpinnerAdaptor;
import com.Client.pay.fragment.BottomSheetDialog;
import com.Client.pay.handleclick.FragmentClickListenerBean;
import com.Client.pay.model.AepsSettlementModel;
import com.Client.pay.model.OperaterBean;
import com.Client.pay.model.RecharageModel;
import com.Client.pay.retrofit.APIService;
import com.Client.pay.utils.SharedPreferenceKeys;
import com.Client.pay.utils.Utilities;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BroadBandActivity extends AppCompatActivity implements FragmentClickListenerBean {
    private View toolbar;
    private Context context;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;

    private RelativeLayout relenter;
    private EditText edt_mobilenumber;
    private EditText edtrecharamt;
    private TextView txt_optype;
    private Button FetchBillBroad;


    private String mobilenumber;
    private String rechargeamt;
    private String message;
    private String opertor_type;
    private String operaterCode = "";
    private String text;

    private CustomeSpinnerAdaptor customeSpinnerAdaptor;
    private ArrayList<OperaterBean> operatorlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_band);
        overridePendingTransition(R.anim.enter, R.anim.exit);

        initUI();
        onClickListener();
    }

    private void initUI() {
        context = this;
        toolbar = findViewById(R.id.toolbar);
        imgmenu = findViewById(R.id.imgmenu);
        imgback = findViewById(R.id.imgback);
        txt_home = findViewById(R.id.txt_home);
        imgsearch = findViewById(R.id.imgsearch);
        FetchBillBroad = findViewById(R.id.FetchBillBroad);

        relenter = findViewById(R.id.relenter);
        edt_mobilenumber = findViewById(R.id.edt_mobilenumber);
        edtrecharamt = findViewById(R.id.edtrecharamt);
        txt_optype = findViewById(R.id.txt_optype);
        text = Utilities.getInstance().getPreference(BroadBandActivity.this, SharedPreferenceKeys.user_id);


        mobilenumber = edt_mobilenumber.getText().toString();
        rechargeamt = edtrecharamt.getText().toString();
        opertor_type = txt_optype.getText().toString();


        imgmenu.setVisibility(View.GONE);
        imgback.setVisibility(View.VISIBLE);
        txt_home.setVisibility(View.GONE);
        imgsearch.setVisibility(View.GONE);


    }

    private void onClickListener() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        relenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidate()) {
                    RecharageModel obj = new RecharageModel();
                    obj.setUser_id(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.user_id));
                    obj.setNumber(edt_mobilenumber.getText().toString());
                    obj.setOprator(operaterCode);
                    obj.setAmt(edtrecharamt.getText().toString());

                    recharge(obj);

                } else {
                    Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_optype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(BroadBandActivity.class.getSimpleName());
                bottomSheetDialog.show(getSupportFragmentManager(), "Modal Bottom sheet");
            }
        });


        FetchBillBroad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_mobilenumber.getText().toString().length()==0){
                    Toast.makeText(context, "Enter Number", Toast.LENGTH_SHORT).show();
                return;
                }
                else{
                    BillFetch();
                }
            }
        });
    }

    private void recharge(RecharageModel obj) {
        if (Utilities.getInstance().isNetworkAvailable(context)) {
            Utilities.getInstance().showProgressDialog(context);
            APIService.getInstance().getBaseUrl()
                    .mobileRrechrage(obj)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Utilities.getInstance().hideProgressdialog();
                            try {
                                Toast.makeText(context, "" + response.body().string(), Toast.LENGTH_SHORT).show();
                                clear();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Utilities.getInstance().hideProgressdialog();
                            Toast.makeText(context, "" + call.toString(), Toast.LENGTH_SHORT).show();


                        }
                    });


        } else {
            Toast.makeText(context, "Please check the network connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clear() {
        edt_mobilenumber.setText("");
        edtrecharamt.setText("");
        txt_optype.setText("Select Operater");
    }

    private boolean isValidate() {
        if (Utilities.getInstance().isBlankString(edt_mobilenumber.getText().toString())) {
            message = "Please enter mobile number";
            return false;
        } else if (Utilities.getInstance().isBlankString(edtrecharamt.getText().toString())) {
            message = "Please enter recharge amount";
            return false;
        } else if (txt_optype.getText().toString().equalsIgnoreCase(getString(R.string.select_operater))) {
            message = "Please select operater type";
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void itemOnClick(AepsSettlementModel model,OperaterBean operater, int pos) {
        txt_optype.setText(operater.getOperatorType());
        operaterCode = operater.getCode();
    }
    private void BillFetch(){


        String json = "{" +
                "\"user_id\":\""+ text + "\",\n" +
                "\"number\":\""+edt_mobilenumber.getText().toString()+ "\",\n" +
                "\"oprator\":\""+edtrecharamt+"\",\n" +
                "\"Optional1\":\""+1+"\"}";


        class RechargeNow extends AsyncTask<Void,Void,String> {

            private Dialog loadingDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog= ProgressDialog.show(BroadBandActivity.this,"Loading..","Plz Wait..");
            }

            @Override
            protected String doInBackground(Void... voids) {
                String result="";


                try {
                    URL url = new URL(BaseURL.BillFetch);



                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestProperty("Content-Type", "Application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    OutputStreamWriter wr=new OutputStreamWriter(conn.getOutputStream());
                    wr.write(json);
                    wr.flush();

                    BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null)
                        builder.append(line + "\n");

                    result = builder.toString();
                    reader.close();
                    conn.disconnect();

                }catch (Exception ex){
                    //Toast.makeText(BroadBandActivity.this, "Error: "+ex.toString(), Toast.LENGTH_SHORT).show();
                }
                return result;

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                //   Toast.makeText(PostPaidActivity.this, ""+result, Toast.LENGTH_SHORT).show();
                try {
                    if (result.length() != 0) {
                        JSONObject jsonObject = new JSONObject(result);
                        // Toast.makeText(PostPaidActivity.this, ""+result, Toast.LENGTH_SHORT).show();
                        String j = jsonObject.getString("dueamount");
                        edtrecharamt.setText(j);

                        //finish();
                    }
                    else {
                        Toast.makeText(BroadBandActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    loadingDialog.dismiss();
                }
            }
        }
        RechargeNow user = new RechargeNow();
        user.execute();
    }

}