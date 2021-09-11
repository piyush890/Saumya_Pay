package com.Client.pay.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.adapter.NavMenuAdapter;
import com.Client.pay.fragment.AEPS_Fragment;
import com.Client.pay.fragment.HomeFragment;
import com.Client.pay.handleclick.FragmentClick;
import com.Client.pay.handleclick.NavClickListenere;
import com.Client.pay.model.Menu_bean;
import com.Client.pay.utils.Constants;
import com.Client.pay.utils.SharedPreferenceKeys;
import com.Client.pay.utils.Utilities;

import org.json.JSONObject;

import java.util.ArrayList;

import payworld.com.aeps_lib.AepsHome;
import payworld.com.aeps_lib.Utility;

public class DashBoardActivity extends AppCompatActivity implements NavClickListenere, FragmentClick {

    DrawerLayout drawerLayout;

    private View toolbar;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;
    private ImageView img_userprofile;
    private TextView txt_name;
    private String userId;
    private String aepe;
    boolean doublebackpresstoExitApp = false;
    private Long mBackPressed = 0l;
    private RecyclerView recyclemenu;
    private Menu_bean menu_bean;
    private ArrayList<Menu_bean> menulist = new ArrayList<>();
    private NavMenuAdapter navMenuAdapter;
    private Context mcontext;
    private NavClickListenere navClickListenere = this;
    private LinearLayoutManager linearLayoutManager;
    private FrameLayout frmcontainer;

    private HomeFragment homeFragment;
    private FragmentTransaction fragmentTransaction;
    private Intent intent;
    private AEPS_Fragment aeps_fragment;

    static String headerKey = "1Vx1IGJMp/8Y7oMQtJcr0gj3gMsIEUy0SyDMkousZ0c=";
    static String bodyKey = "UaapiKilzh9/R5Un92mDSQRoPfhCaiwdtzp9s6ZJM+k=";
    static int REQ_CODE_AEPS = 1001;
    static String marchantKey = "LnQP0F6aOpIb4IFH5wgkn3+yc+HBD3JBaSAHt3B6m40=";
    static String marchantId = "63456929";
    static String AgentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        //getSupportActionBar().hide();
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setArraylsit();
        initUI();
        setAdaptor();
        setClickListener();
    }

    private void setClickListener() {
        imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(mcontext, MyWalletActivity.class);
                startActivity(intent);
            }
        });
    }




    private void initUI() {
        mcontext = this;
        aeps_fragment = new AEPS_Fragment();
        recyclemenu = findViewById(R.id.recyclemenu);
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        imgmenu = findViewById(R.id.imgmenu);
        frmcontainer = findViewById(R.id.frmcontainer);

        imgback = findViewById(R.id.imgback);
        txt_home = findViewById(R.id.txt_home);
        imgsearch = findViewById(R.id.imgsearch);
        img_userprofile = findViewById(R.id.img_userprofile);

        txt_name = findViewById(R.id.txt_name);

        txt_name.setText(Utilities.getInstance().getPreference(mcontext, SharedPreferenceKeys.user_name));


        imgmenu.setVisibility(View.VISIBLE);
        imgback.setVisibility(View.GONE);
        txt_home.setVisibility(View.VISIBLE);
        imgsearch.setVisibility(View.VISIBLE);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setHomePage();
    }

    private String getHeaderJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("merchantId", marchantId);
            jsonObject.put("Timestamp", Utility.getCurrentTimeStamp());
            jsonObject.put("merchantKey", marchantKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonObject.toString();

    }

    private String getBodyJson() {

        Log.d("Agent id is", "agent" + Utilities.getInstance().getPreference(mcontext, SharedPreferenceKeys.agen_id));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("AgentId", Utilities.getInstance().getPreference(mcontext, SharedPreferenceKeys.agen_id));
            jsonObject.put("merchantService", "AEPS");
            jsonObject.put("Version", "1.0");
            jsonObject.put("Mobile", "8657117675");
            jsonObject.put("Email", "abhijeet.gavade24@gmail.com");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonObject.toString();
    }

    private void setHomePage() {
        homeFragment = new HomeFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frmcontainer, homeFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(mcontext, "onResume is called", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
       // Toast.makeText(mcontext, "onStart is called", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
//        Toast.makeText(mcontext, "onPause is called", Toast.LENGTH_SHORT).show();
//        fragmentTransaction.attach(homeFragment);
//        fragmentTransaction.detach(homeFragment);
//        try {
//            fragmentTransaction.commit();
//        }catch (IllegalStateException ignored){
//
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Toast.makeText(mcontext, "onPause is called", Toast.LENGTH_SHORT).show();

    }

    private void setArraylsit() {
        Menu_bean menu_bean1 = new Menu_bean();
        menu_bean1.setImageID(R.drawable.profile);
        menu_bean1.setMenuImg("Profile");
        menulist.add(menu_bean1);

        Menu_bean menu_bean2 = new Menu_bean();
        menu_bean2.setImageID(R.drawable.wallet);
        menu_bean2.setMenuImg("My Wallet");
        menulist.add(menu_bean2);

        Menu_bean menu_bean3 = new Menu_bean();
        menu_bean3.setImageID(R.drawable.hisotry);
        menu_bean3.setMenuImg("My History");
        menulist.add(menu_bean3);

        Menu_bean menu_bean4 = new Menu_bean();
        menu_bean4.setImageID(R.drawable.privacy_policy);
        menu_bean4.setMenuImg("Privacy Policy");
        menulist.add(menu_bean4);

        Menu_bean menu_bean5 = new Menu_bean();
        menu_bean5.setImageID(R.drawable.profile);
        menu_bean5.setMenuImg("Communication");
        menulist.add(menu_bean5);

        Menu_bean menu_bean6 = new Menu_bean();
        menu_bean6.setImageID(R.drawable.help);
        menu_bean6.setMenuImg("Help");
        menulist.add(menu_bean6);

        Menu_bean menu_bean7 = new Menu_bean();
        menu_bean7.setImageID(R.drawable.ic_business_report);
        menu_bean7.setMenuImg("Reports");
        menulist.add(menu_bean7);

        Menu_bean menu_bean8 = new Menu_bean();
        menu_bean8.setImageID(R.drawable.logout);
        menu_bean8.setMenuImg("Logout");
        menulist.add(menu_bean8);


    }

    private void setAdaptor() {
        navMenuAdapter = new NavMenuAdapter(mcontext, menulist, navClickListenere);
        recyclemenu.setAdapter(navMenuAdapter);
        linearLayoutManager = new LinearLayoutManager(mcontext);
        recyclemenu.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onBackPressed() {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (mBackPressed + 2000 > System.currentTimeMillis()) {
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            finishAffinity();
        } else {
            Utilities.getInstance().showSnackBar(drawerLayout, getString(R.string.press_once_again_exit));
        }

        mBackPressed = System.currentTimeMillis();
    }

    @Override
    public void onClick(int position, String where) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (position == 7) {
            showLogoutAler();
        } else if (position == 0) {
            intent = new Intent(mcontext, ProfileActivity.class);
            startActivity(intent);
        } else if (position == 1) {
            intent = new Intent(mcontext, MyWalletActivity.class);
            startActivity(intent);
        } else if (position == 2) {
            intent = new Intent(mcontext, MyHistoryActivity.class);
            startActivity(intent);
        } else if (position == 3) {
            intent = new Intent(mcontext, PrivacyPolicyActivity.class);
            startActivity(intent);
        } else if (position == 5) {
            intent = new Intent(mcontext, HelpActivity.class);
            startActivity(intent);
        } else if (position == 6) {
            intent = new Intent(mcontext, ReportActivity.class);
            startActivity(intent);
        }
    }

    private void showLogoutAler() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(DashBoardActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Logout");
        dialog.setMessage("Are you sure you want to logout?");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finishAffinity();
            }
        })
                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        clearPreferences();
                    }
                });

        final AlertDialog alert = dialog.create();
        alert.show();

    }

    private void clearPreferences() {
        Utilities.getInstance().removePreference(mcontext, SharedPreferenceKeys.agen_id);
        Utilities.getInstance().removePreference(mcontext, SharedPreferenceKeys.user_name);
        Utilities.getInstance().removePreference(mcontext, SharedPreferenceKeys.user_id);
        Utilities.getInstance().removePreference(mcontext, SharedPreferenceKeys.stock_balance);
        Utilities.getInstance().removePreference(mcontext, SharedPreferenceKeys.aeps_balnce);
        Utilities.getInstance().removePreference(mcontext, SharedPreferenceKeys.main_balance);
        Utilities.getInstance().removePreference(mcontext, SharedPreferenceKeys.stock_balance);


    }


    @Override
    public void onClickFrag(String where) {
        if (where.equalsIgnoreCase(Constants.mobileclick)) {
            intent = new Intent(mcontext, MobileRechargeActivity.class);
            startActivity(intent);
        } else if (where.equalsIgnoreCase(Constants.apes_click)) {

        } else if (where.equalsIgnoreCase(Constants.DTH_CLICK)) {
            intent = new Intent(mcontext, DTHRechargeActivity.class);
            startActivity(intent);
        } else if (where.equalsIgnoreCase(Constants.Broad_band)) {

            intent = new Intent(mcontext, BroadBandActivity.class);
            startActivity(intent);
        } else if (where.equalsIgnoreCase(Constants.AEPS_TAB_CLICK))
            aeps_fragment.show(getSupportFragmentManager(), DashBoardActivity.class.getSimpleName());
        else if (where.equalsIgnoreCase(Constants.AEPS_TAB_SELECTED)) {
            try {
                Intent intent = new Intent(mcontext, AepsHome.class);
                Utility utility = Utility.getInstance();
                intent.putExtra("header", utility.encryptHeader(getHeaderJson(), headerKey));
                intent.putExtra("body", utility.encryptBody(getBodyJson(), bodyKey));
                intent.putExtra("receipt", true);
                startActivityForResult(intent, REQ_CODE_AEPS);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (where.equalsIgnoreCase(Constants.Electricity)) {
            intent = new Intent(mcontext, ElectricityBillActivity.class);
            startActivity(intent);
        } else if (where.equalsIgnoreCase(Constants.DMT)) {


        } else if (where.equalsIgnoreCase(Constants.MoneyTransfer)) {
            intent = new Intent(mcontext, SatelmentActivity.class);
            startActivity(intent);
        }else if (where.equalsIgnoreCase(Constants.Data_card)) {
            intent = new Intent(mcontext, datcard.class);
            startActivity(intent);
        }else if (where.equalsIgnoreCase(Constants.PostPaid)) {
            intent = new Intent(mcontext, PostPaidActivity.class);
            startActivity(intent);
        }else if (where.equalsIgnoreCase(Constants.gas)) {
            intent = new Intent(mcontext, GasActivity.class);
            startActivity(intent);
        }else if (where.equalsIgnoreCase(Constants.water)) {
            intent = new Intent(mcontext, WaterActivity.class);
            startActivity(intent);
        }else if (where.equalsIgnoreCase(Constants.fastPay)) {
            intent = new Intent(mcontext, FastTagActivity.class);
            startActivity(intent);
        }else if (where.equalsIgnoreCase(Constants.Insurance)) {
            intent = new Intent(mcontext, InsuranceActivity.class);
            startActivity(intent);
        }else if (where.equalsIgnoreCase(Constants.Emi)) {
            intent = new Intent(mcontext, EmiActivity.class);
            startActivity(intent);
        }else if (where.equalsIgnoreCase(Constants.Landline)) {
            intent = new Intent(mcontext, LandLineActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_AEPS) {
            if (resultCode == RESULT_OK) {
            } else {
            }
        }
    }
}