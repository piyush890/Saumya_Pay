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

public class FastTagActivity extends AppCompatActivity {
    private Spinner FastTagSpinner_optype;
    private EditText edt_mobilenumberFastTag, edtrecharamtFastTag;
    public static final String SHARED_PREFS = "SharePrefs";
    public static final String TEXT = "Text";
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_tag);
        FastTagSpinner_optype = findViewById(R.id.fasttagSpinner_optype);
        edt_mobilenumberFastTag = findViewById(R.id.edt_mobilenumberfasttag);
        edtrecharamtFastTag = findViewById(R.id.edtrecharamtfasttag);
        //SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = Utilities.getInstance().getPreference(FastTagActivity.this, SharedPreferenceKeys.user_id);
        FastTagOperator();

    }

    private void FastTagOperator() {
        class PPOperator extends AsyncTask<Void, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(FastTagActivity.this, "Loading..", "Plz Wait..");
            }

            @Override
            protected String doInBackground(Void... voids) {
                String result = "";

                String json = "{\"s_id\":\"7\",\n" +
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
                    //  Toast.makeText(datcard.this, ""+result, Toast.LENGTH_SHORT).show();
                    JSONArray array = new JSONArray(result);
                    List<ListItem> items = new ArrayList<>();
                    items.add(new ListItem("Select your State", "up"));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        // Toast.makeText(this, ""+object, Toast.LENGTH_SHORT).show();
                        String dis = object.getString("provider_id");
                        String val = object.getString("provider_name");
                        items.add(new ListItem(val, dis));
                    }

                    ArrayAdapter<ListItem> itemArrayAdapter = new ArrayAdapter<>(FastTagActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
                    FastTagSpinner_optype.setAdapter(itemArrayAdapter);


                } catch (Exception ex) {
                    Toast.makeText(FastTagActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
                } finally {
                    loadingDialog.dismiss();
                }

            }

        }
        PPOperator PP = new PPOperator();
        PP.execute();
    }

    private void FastTagRecharge(String id){
        class DthRecharge extends AsyncTask<Void,Void,String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(FastTagActivity.this, "Loading..", "Plz Wait..");
            }

            @Override
            protected String doInBackground(Void... voids) {
                String result = "";

                String json = "{" +
                        "\"username\":\""+ text + "\",\n" +
                        "\"number\":\""+edt_mobilenumberFastTag.getText().toString()+ "\",\n" +
                        "\"oprator\":\""+id+"\",\n" +
                        "\"amt\":\""+edtrecharamtFastTag.getText().toString()+"\"}";

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
                        Toast.makeText(FastTagActivity.this, ""+result, Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception ex) {
                    Toast.makeText(FastTagActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
                } finally {
                    loadingDialog.dismiss();
                }

            }

        }
        DthRecharge DTH = new DthRecharge();
        DTH.execute();
    }

    public void Datacard(View view) {
        if (edt_mobilenumberFastTag.getText().toString().length()==0){
            Toast.makeText(this, "Enter Your Number", Toast.LENGTH_SHORT).show();
            return;
        }if (edtrecharamtFastTag.getText().toString().length()==0){
            Toast.makeText(this, "Enter Your Amount", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            ListItem Item = (ListItem)FastTagSpinner_optype  .getSelectedItem();
            String cid = Item.getValueMember();
            FastTagRecharge(cid);
            //Toast.makeText(this, ""+cid, Toast.LENGTH_SHORT).show();

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