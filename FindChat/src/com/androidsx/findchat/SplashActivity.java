package com.androidsx.findchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

public class SplashActivity extends Activity {

	Intent i;
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		activity = SplashActivity.this;

		// if (PreferenceUtilities.getSavedUserTypeData(activity).size() == 0) {

		// Bitmap icon = BitmapFactory.decodeResource(
		// this.getResources(), R.drawable.ic_launcher);
		//
		// ByteArrayOutputStream stream = new ByteArrayOutputStream();
		// icon.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		//
		// byte[] byteArray = stream.toByteArray();
		//
		//
		//
		// File myFile = new File("/sdcard/serverErrorFile.txt");
		// try {
		// myFile.createNewFile();
		// FileOutputStream fOut = new FileOutputStream(myFile);
		// OutputStreamWriter myOutWriter = new OutputStreamWriter(
		// fOut);
		//
		// myOutWriter.append(""+byteArray);
		// myOutWriter.close();
		// fOut.close();
		//
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// if (NetworkUtility.checkInternetConnection(activity)) {
		// // startActivity(new Intent(activity, MainActivity.class));
		//
		// new UserTypeAsyncTask().execute();
		// } else {
		// DialogUtility.ShowFinishDialoguMessage(Constants.newtWorkMsg,
		// activity);
		// }
		// } else {

		Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (waited < 2000) {
						sleep(100);
						waited += 100;
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {

					finish();
					i = new Intent(getApplicationContext(), LoginActivity.class);
					startActivity(i);
				}
			}
		};
		splashThread.start();
		// }

		// Thread splashThread = new Thread() {
		// @Override
		// public void run() {
		// try {
		// int waited = 0;
		// while (waited < 2000) {
		// sleep(100);
		// waited += 100;
		// }
		// } catch (InterruptedException e) {
		// // do nothing
		// } finally {
		// // finish();
		// // if (LoginScreen.getSavedUserData(context) != null) {
		// //
		// // if (!LoginScreen.getSavedUserData(context)[2]
		// // .equalsIgnoreCase("")) {
		// //
		// // LoginScreen.user_country = LoginScreen
		// // .getSavedUserData(context)[2].toString();
		// // LoginScreen.user_city = LoginScreen
		// // .getSavedUserData(context)[3].toString();
		// // LoginScreen.user_location = LoginScreen
		// // .getSavedUserData(context)[4].toString();
		// //
		// // // if
		// // // (LoginScreen.user_country.equalsIgnoreCase(value))
		// // // {
		// // // LoginScreen.user_country_code =
		// // // Integer.parseInt(key);
		// // // Log.e(" user country code", ""
		// // // + user_country_code);
		// // // }
		// //
		// // System.out
		// // .println(" City " + LoginScreen.user_city);
		// //
		// // finish();
		// // i = new Intent(getApplicationContext(),
		// // MainScreenView.class);
		// // startActivity(i);
		// // } else {
		// // finish();
		// // i = new Intent(getApplicationContext(),
		// // LoginScreen.class);
		// // startActivity(i);
		// // }
		// //
		// // } else {
		//
		// finish();
		// i = new Intent(getApplicationContext(), LoginActivity.class);
		// startActivity(i);
		// // }
		// }
		// }
		// };
		// splashThread.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
