package com.Client.pay.Rechargemodel;

public class Models {
    private String Provider_id;
    private String Provider_name;

    @Override
    public String toString() {
        return "Models{" +
                "Provider_id='" + Provider_id + '\'' +
                ", Provider_name='" + Provider_name + '\'' +
                '}';
    }

    public String getProvider_id() {
        return Provider_id;
    }

    public void setProvider_id(String provider_id) {
        Provider_id = provider_id;
    }

    public String getProvider_name() {
        return Provider_name;
    }

    public void setProvider_name(String provider_name) {
        Provider_name = provider_name;
    }
}
