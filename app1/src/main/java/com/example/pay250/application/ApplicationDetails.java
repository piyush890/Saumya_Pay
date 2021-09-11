package com.Client.pay.application;

import android.app.Application;
import android.content.Context;

public class ApplicationDetails extends Application {
    private static ApplicationDetails instance;
   public boolean isAppOpen = false;

  public static ApplicationDetails getInstance() {
       return instance;
   }

   @Override
    public void onCreate() {
      super.onCreate();
     instance = this;
       isAppOpen = true;
    }

   @Override
   public void onTerminate() {
      super.onTerminate();
       isAppOpen = false;
  }

   public Context getContext() {
        return getApplicationContext();
    }
}
