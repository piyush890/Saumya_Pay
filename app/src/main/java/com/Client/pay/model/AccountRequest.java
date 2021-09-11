package com.Client.pay.model;

public class AccountRequest {
    private String  user_id;
    private String aeps;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAeps() {
        return aeps;
    }

    public void setAeps(String aeps) {
        this.aeps = aeps;
    }
}
