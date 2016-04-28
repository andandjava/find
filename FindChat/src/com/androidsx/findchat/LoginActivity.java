package com.androidsx.findchat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LoginActivity extends Activity {
	public static final String MYPREFERENCES = "Myprefer";
	static SharedPreferences sharedpreferences;
	EditText etuser_name;
	String logusername;
	TextView enteruser;
	static String user1;
	ArrayList<String> registrationlist = new ArrayList<String>();
	ArrayList<ArrayList<String>> totreglist = new ArrayList<ArrayList<String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		sharedpreferences = getSharedPreferences(MYPREFERENCES,Context.MODE_APPEND);
		TextView tv_signin = (TextView) findViewById(R.id.tv_signin);
		TextView txt_new = (TextView) findViewById(R.id.txt_new);
		enteruser = (TextView) findViewById(R.id.enteruser);
		etuser_name = (EditText) findViewById(R.id.etuser_name);
		// final String user1=etuser_name.getText().toString();

		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParseRegistration");
		setProgressBarIndeterminateVisibility(true);

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> parreglits, ParseException e) {
				// TODO Auto-generated method stub
				if (e == null) {
					// If there are results, update the list of posts
					// and notify the adapter

					StringBuilder builder = new StringBuilder();
					for (int i = parreglits.size() - 1; i >= 0; i--) {

						builder.append(parreglits.get(i).getString("REGISTRATION_NAME"));

						registrationlist.add(parreglits.get(i).getString("REGISTRATION_NAME"));
						System.out.println("="+ parreglits.get(i).getString("REGISTRATION_NAME"));
						// totreglist.add(registrationlist);

					}
					addItemstoListView(builder.toString());
				} else {
					Log.d(getClass().getSimpleName(),"Error: " + e.getMessage());
				}

			}
		});
		tv_signin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				// SharedPreferences.Editor editor = sharedpreferences.edit();
				// logusername=etuser_name.getText()
				// .toString();
				// System.out.println("editor"+logusername);
				//
				//
				// editor.putString("USER_NAME", logusername);
				//
				// editor.commit();
				if (etuser_name.length() == 0) {
					Toast.makeText(LoginActivity.this, "Please provide number",
							Toast.LENGTH_SHORT).show();
					// enteruser.setVisibility(View.VISIBLE);
					// blink();
				} else {
					//

					enteruser.setVisibility(View.INVISIBLE);

					 user1 = etuser_name.getText().toString();
					 
					// String user = sharedpreferences.getString("USER_NAME",
					 
					 
					// "no values exist");

					String user = registrationlist.toString();
					System.out.println("userrrrrrrrrrrrrrrrrrrrrrrrrrr" + user1);
					if (registrationlist.contains(user1)) {
						// if (etuser_name.length() == 0) {
						// enteruser.setVisibility(View.VISIBLE);
						// blink();
						// } else {
						// enteruser.setVisibility(View.INVISIBLE);
						// startActivity(new Intent(LoginActivity.this,
						// GroupNames.class));
						// }
//						Toast.makeText(LoginActivity.this,
//								"user1" + user1 + "user" + user, 0).show();
						startActivity(new Intent(LoginActivity.this,
								GroupNames.class));

					} else {

						Toast.makeText(LoginActivity.this,
								"Enter valid username", 0).show();
					}
				}
			}
		});

		txt_new.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(LoginActivity.this, Register.class));
			}
		});
	}

	protected void addItemstoListView(String string) {
		// TODO Auto-generated method stub

	}

	private void blink() {
		final Handler handler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int timeToBlink = 800; // in milissegunds
				try {
					Thread.sleep(timeToBlink);
				} catch (Exception e) {
				}
				handler.post(new Runnable() {
					@Override
					public void run() {

						if (enteruser.getVisibility() == View.VISIBLE) {
							enteruser.setVisibility(View.INVISIBLE);
						} else {
							enteruser.setVisibility(View.VISIBLE);
						}
						blink();
					}
				});
			}
		}).start();
	}

}
