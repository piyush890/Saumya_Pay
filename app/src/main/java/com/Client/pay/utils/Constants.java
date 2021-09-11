package com.Client.pay.utils;

public class Constants {
    private static Constants constants = null;
    public int SNACKBAR_LENGTH = 5000;
    public String pref_userid = "user_id";
    public static String mobileclick = "mobileclick";
    public static String apes_click = "APES CLICK";
    public static String DTH_CLICK = "DTH_CLICK";
    public static String AEPS_TAB_CLICK = "AEPS_TAB_CLICK";
    public static String AEPS_TAB_SELECTED = "AEPS_TAB_SELECTED";
    public static String AEPS_SETTLEMENT = "AEPS_SETTLEMENT";
    public static String Broad_band = "Broad_band";
    public static String Bill_payment = "Bill_payment";
    public static String Data_card = "Data_card";
    public static String gas = "Gas";
    public static String water = "Water";
    public static String fastPay = "FastPay";
    public static String Insurance = "Insurance";
    public static String Emi = "EMI";
    public static String Landline = "LandLine";
    public static String Electricity = "Electricity";
    public static String PostPaid = "PostPaid";
    public static String DMT = "DMT";
    public static String MoneyTransfer = "Money Settlemetn";


    public Constants() {

    }

    public static Constants getInstance() {
        return constants == null ? constants = new Constants() : constants;
    }

//    public String getUserID() {
//        return Utilities.getInstance().getPreference(ApplicationDetails.getInstance().getContext(), pref_userid);
//    }
}
