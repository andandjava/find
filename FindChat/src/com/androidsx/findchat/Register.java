package com.androidsx.findchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class Register extends Activity {

	EditText etuser_name, et_mobileno;
	String logusername;
	String regexStr = "^[0-9]{10}$";

	public static final String MYPREFERENCES = "Myprefer";
	static SharedPreferences sharedpreferences;
	 ParseObject parseregistration = new ParseObject("ParseRegistration");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);

		TextView tv_signup = (TextView) findViewById(R.id.tv_signup);
		etuser_name = (EditText) findViewById(R.id.etuser_name);
		et_mobileno = (EditText) findViewById(R.id.et_mobileno);

		tv_signup.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
//				sharedpreferences = getSharedPreferences(MYPREFERENCES,
//						Context.MODE_APPEND);
//				SharedPreferences.Editor editor = sharedpreferences.edit();
				logusername = et_mobileno.getText().toString();
				if(logusername!=null)
				{
				System.out.println("editor" + logusername);

//				editor.putString("USER_NAME", logusername);
				parseregistration.put("REGISTRATION_NAME",logusername );
				System.out.println("parseregistration"+parseregistration);
				parseregistration.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							
//							startActivity(new Intent(getApplicationContext(),
//									GroupNames.class));
						} else {
							Log.d(getClass().getSimpleName(),
									"User update error: " + e);
						}

					}
				});
				}
				else
				{
					Toast.makeText(Register.this,"enter valid username",0).show();
				}

				String s= et_mobileno.getText().toString();
				int k=s.length();
				if ((k == 10)&&(s.matches(regexStr))) {
//				if(s.matches(regexStr)){
					
					
//					Pattern pattern=Pattern.compile("\\d{3}-\\d{7}");
//					Matcher matcher=pattern.matcher(s);
//					editor.commit();

					startActivity(new Intent(Register.this, LoginActivity.class));
				}
				else
				{
					Toast.makeText(Register.this,"enter valid phone number",0).show();
				}
			}
		});
	}

}
