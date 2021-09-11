package com.Client.pay.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.Client.pay.R;
import com.Client.pay.application.ApplicationDetails;
import com.google.android.material.snackbar.Snackbar;

import static android.content.Context.MODE_PRIVATE;

public class Utilities {

    private static Utilities utility = null;
    private Snackbar snackbar;
    private ProgressDialog progressDialog;

    private Utilities() {
    }

    public static Utilities getInstance() {
        return utility == null ? utility = new Utilities() : utility;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean isNetworkAvailable(Context context) {
        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE,
                ConnectivityManager.TYPE_WIFI,
                ConnectivityManager.TYPE_ETHERNET};
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null &&
                        activeNetworkInfo.getType() == networkType)
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public void hideKeyBoard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();

        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private String MY_PREFS_NAME = "Pay205";

    public void setPreference(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    }

    public void removePreference(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key);
        editor.apply();
        editor.commit();
    }

    public String getPreference(Context act, String key) {

        SharedPreferences prefs = act.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(key, "");

    }

    @SuppressWarnings("deprecation")
    public Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml("<font color=\"#FFFFFF\">" + source + "</font>", Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml("<font color=\"#FFFFFF\">" + source + "</font>");
        }
    }

    public void showLog(String msg) {
        Log.e("", msg);
    }

    public void showSnackBar(View layout, String msg) {
        snackbar = Snackbar.make(layout, fromHtml(msg), Constants.getInstance().SNACKBAR_LENGTH);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(layout.getContext(), R.color.grey));
        TextView textView = (snackbar.getView()).findViewById(com.google.android.material.R.id.snackbar_text);
        snackbar.show();
        vibrate(ApplicationDetails.getInstance().getContext());
    }

    private void vibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(150);
    }

    public boolean isBlankString(String text) {
        return TextUtils.isEmpty(text) || text.trim().length() == 0;

    }

    public boolean isValidPass(String text) {
        return TextUtils.isEmpty(text) || text.trim().length() <= 4;
    }

    public void showProgressDialog(Context mcontext) {
        progressDialog = new ProgressDialog(mcontext, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

    }

    public boolean checkPermissions(Activity act, String permissionName, int request_code) {
        int hasPermission = PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasPermission = act.checkSelfPermission(permissionName);
        }
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                act.requestPermissions(new String[]{permissionName}, request_code);
            }
        } else {
            return true;
        }
        return false;
    }

    public void hideProgressdialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    public void grantPermission(final Context context, View view, String msg) {

        Snackbar snackbar = Snackbar.make(view, fromHtml(msg), Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.getView().setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.colorPrimary, null));
        snackbar.setAction(context.getString(R.string.settings), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
        snackbar.show();

        vibrate(context);
    }


}
