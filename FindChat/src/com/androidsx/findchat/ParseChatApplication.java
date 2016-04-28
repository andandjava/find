package com.androidsx.findchat;

import com.parse.Parse;

import android.app.Application;

public class ParseChatApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(this, Constants.APP_ID,Constants.CLIENT_KEY);
	}

}
