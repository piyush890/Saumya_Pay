package com.Client.pay.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.Client.pay.R;
import com.Client.pay.utils.SharedPreferenceKeys;
import com.Client.pay.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GasActivity extends AppCompatActivity {
    private Spinner Spinner_optypeGas;
    private EditText edt_mobilenumberGas,edtrecharamtGas;
    public static final String SHARED_PREFS ="SharePrefs";
    public static final String TEXT ="Text";
    private String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas);
        Spinner_optypeGas = findViewById(R.id.Spinner_optypeGas);
        edt_mobilenumberGas = findViewById(R.id.edt_mobilenumberGas);
        edtrecharamtGas = findViewById(R.id.edtrecharamtGas);
       // SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        text = Utilities.getInstance().getPreference(GasActivity.this, SharedPreferenceKeys.user_id);

        GasOperator();
    }
    private void GasOperator(){
        class PostPOperator extends AsyncTask<Void,Void,String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(GasActivity.this, "Loading..", "Plz Wait..");
            }

            @Override
            protected String doInBackground(Void... voids) {
                String result = "";

                String json = "{\"s_id\":\"8\",\n" +
                        "\"s_type\":\"2\"}";

                try {
                    URL url = new URL(BaseURL.Operators);


                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "Application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(json);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null)
                        builder.append(line + "\n");

                    result = builder.toString();
                    reader.close();
                    conn.disconnect();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return result;

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    //
                    //  Toast.makeText(PostPaidActivity.this, ""+result, Toast.LENGTH_SHORT).show();
                    JSONArray arrays = new JSONArray(result);
                    List<ListItem> item = new ArrayList<>();
                    item.add(new ListItem("Select your Operator", "0"));
                    for (int i = 0; i < arrays.length(); i++) {
                        JSONObject object = arrays.getJSONObject(i);
                        // Toast.makeText(PostPaidActivity.this, ""+object, Toast.LENGTH_SHORT).show();
                        String id = object.getString("provider_id");
                        String pro = object.getString("provider_name");
                        item.add(new ListItem(pro,id));
                    }
                    ArrayAdapter<ListItem> arrayAdapters = new ArrayAdapter<>(GasActivity.this, android.R.layout.simple_spinner_dropdown_item, item);
                    Spinner_optypeGas.setAdapter(arrayAdapters);


                } catch (Exception ex) {
                    Toast.makeText(GasActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
                } finally {
                    loadingDialog.dismiss();
                }

            }

        }
        PostPOperator PostPaid = new PostPOperator();
        PostPaid.execute();
    }

    private void GasRecharge(String id){
        class PostPaidRe extends AsyncTask<Void,Void,String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(GasActivity.this, "Loading..", "Plz Wait..");
            }

            @Override
            protected String doInBackground(Void... voids) {
                String result = "";

                String json = "{" +
                        "\"username\":\""+ text + "\",\n" +
                        "\"number\":\""+edt_mobilenumberGas.getText().toString()+ "\",\n" +
                        "\"oprator\":\""+id+"\",\n" +
                        "\"amt\":\""+edtrecharamtGas.getText().toString()+"\"}";

                try {
                    URL url = new URL(BaseURL.Recharge);


                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "Application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(json);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null)
                        builder.append(line + "\n");

                    result = builder.toString();
                    reader.close();
                    conn.disconnect();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return result;

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    if (result.length()!=0){
                        Toast.makeText(GasActivity.this, ""+result, Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception ex) {
                    Toast.makeText(GasActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
                } finally {
                    loadingDialog.dismiss();
                }

            }

        }
        PostPaidRe Post = new PostPaidRe();
        Post.execute();
    }

    public void Gas(View view) {
        if (edt_mobilenumberGas.getText().toString().length()==0){
            Toast.makeText(this, "Enter Your Mobile Number", Toast.LENGTH_SHORT).show();
            return;
        }if (edtrecharamtGas.getText().toString().length()==0){
            Toast.makeText(this, "Enter Your Amount", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            ListItem Item = (ListItem) Spinner_optypeGas.getSelectedItem();
            String cid = Item.getValueMember();
            GasRecharge(cid);
            //Toast.makeText(this, ""+cid, Toast.LENGTH_SHORT).show();

        }
    }

    private void BillFetch(String opt){


        String json = "{" +
                "\"user_id\":\""+ text + "\",\n" +
                "\"number\":\""+edt_mobilenumberGas.getText().toString()+ "\",\n" +
                "\"oprator\":\""+opt+"\",\n" +
                "\"Optional1\":\""+1+"\"}";


        class RechargeNow extends AsyncTask<Void,Void,String> {

            private Dialog loadingDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog= ProgressDialog.show(GasActivity.this,"Loading..","Plz Wait..");
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
                    Toast.makeText(GasActivity.this, "Error: "+ex.toString(), Toast.LENGTH_SHORT).show();
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
                        edtrecharamtGas.setText(j);

                        //finish();
                    }
                    else {
                        Toast.makeText(GasActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
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

    public void FetchGasBill(View view) {
        if (edt_mobilenumberGas.getText().toString().length()==0){
            Toast.makeText(this, "Enter Number", Toast.LENGTH_SHORT).show();
            edt_mobilenumberGas.setFocusable(true);

        }
        else {
            ListItem Item = (ListItem) Spinner_optypeGas.getSelectedItem();
            String cid = Item.getValueMember();
            BillFetch(cid);
        }
    }

    public class ListItem {

        private String DisplayMember;
        private String ValueMember;

        public ListItem(String DisplaMember,String ValueMember){
            this.DisplayMember=DisplaMember;
            this.ValueMember=ValueMember;
        }

        @Override
        public String toString() {
            return DisplayMember;
        }

        public String getValueMember(){
            return ValueMember;
        }

    }
}