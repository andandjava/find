package com.androidsx.findchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;
import com.parse.SaveCallback;

public class SingleChatActivity extends Activity {
	public static final String MYPREFERENCES = "Myprefer";
	static SharedPreferences sharedpreferences;
	String singleName = "";
	String userName;
	static TextView tvTitulo, tvTitulo1;
	public static final String USER_NAME_KEY = "username";
	private static final String TAG = SingleChatActivity.class.getName();
	private static final int MAX_CHAT_MESSAGES_TO_SHOW = 25;
	ImageView imgSend, iv_map;
	private Spinner spinnerUsers;
	private EditText txtMessage;
	private Button btnSend;
	private static ListView chatListView;
	private SingleAwesomeAdapter adapter;
	ArrayList values1;
	ParseObject parsenewchats = new ParseObject("ParseNewChats");
	ArrayList<String> messagesList = new ArrayList<String>();
	public static String conNumber = "", groupName = "", newusers = "";
	ArrayList<String> parcts = new ArrayList<String>();
	HashMap<Integer, Contacts> groupContactsList = new HashMap<Integer, Contacts>();
	private BroadcastReceiver pushReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "Just received a push. Let's update the messages");
			receiveMessage();
		}
	};

	String sender = "";

	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startchat);
		tvTitulo = (TextView) findViewById(R.id.tvTitulo);
		tvTitulo1 = (TextView) findViewById(R.id.tvTitulo1);

		// sharedpreferences =
		// getSharedPreferences(MYPREFERENCES,Context.MODE_APPEND);
		imgSend = (ImageView) findViewById(R.id.imgSend);
		iv_map = (ImageView) findViewById(R.id.iv_map);
		if (getIntent() != null) {

			groupName = getIntent().getExtras().getString("name");

			newusers = getIntent().getExtras().getString("MyClass");

			singleName = getIntent().getExtras().getString("MYOTHRNAM");
			// groupContactsList = (HashMap<Integer, Contacts>) getIntent()
			// .getSerializableExtra("MyClass");
			conNumber = getIntent().getExtras().getString("MYOTHRNUM");

		}

		if (groupName != null) {

			tvTitulo.setText(groupName);

		} else {

			tvTitulo.setText(singleName);
		}
		if (newusers != null) {

			tvTitulo1.setText(newusers);

		} else {

			tvTitulo1.setText(conNumber);
		}

		System.out.println("grouplist" + conNumber);
		// receiveMessage();
		// printMap(groupContactsList);
		// userName = sharedpreferences.getString("USER_NAME", " ").toString();
		setupUI();

		PushService.subscribe(this, "Prueba", SingleChatActivity.class);
		PushService.setDefaultPushCallback(this, SingleChatActivity.class);

		receiveMessage();
		registerReceiver(pushReceiver, new IntentFilter("MyAction"));

		ImageView img = (ImageView) findViewById(R.id.backarraow);

		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		iv_map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SingleChatActivity.this,
						GetMapActivity.class);
				startActivity(i);
			}
		});
	}

	private void setupUI() {
		// configureUserNameInParse(LoginActivity.user1);
		txtMessage = (EditText) findViewById(R.id.etMensaje);
		btnSend = (Button) findViewById(R.id.btnSend);
		chatListView = (ListView) findViewById(R.id.chatList);
		receiveMessage();
		adapter = new SingleAwesomeAdapter(getApplicationContext(),
				messagesList);
		chatListView.setAdapter(adapter);
		imgSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String data = txtMessage.getText().toString();
				ParseObject messages = new ParseObject("S_"
						+ tvTitulo.getText().toString().replace(" ", "_")
								.trim());
				sender = LoginActivity.user1;
				messages.put(USER_NAME_KEY, LoginActivity.user1);
				messages.put("message", data);

				messages.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						receiveMessage();
					}
				});

				createPushNotifications(data);
				txtMessage.setText("");
				// if(!conNumber.equalsIgnoreCase("null") &&
				// !singleName.equalsIgnoreCase("null"))
				// {
				parsenewchats.put("NEW_NUMOTHER", conNumber + ","
						+ LoginActivity.user1);
				parsenewchats.put("NEW_NAMOTHER", singleName + "," + "you");

				System.out.println("parseregistration" + parsenewchats);

				parsenewchats.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {

						if (e == null) {

						} else {
							Log.d(getClass().getSimpleName(),
									"User update error: " + e);
						}
					}
				});
			}
			// else{
			//
			// }
			//
			// }
			// updateList();

		});
	}

	public void updateList() {
		adapter = new SingleAwesomeAdapter(getApplicationContext(),
				messagesList);
		chatListView.setAdapter(adapter);
	}

	private void configureUserNameInParse(String userName) {
		ParseInstallation installation = ParseInstallation
				.getCurrentInstallation();
		installation.put(SingleChatActivity.USER_NAME_KEY, userName);
		installation.saveInBackground(); // TODO: We should actually wait until
											// this is done...
	}

	public void showToast(String text) {
		Toast toast = Toast.makeText(this, text, 3000);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.parse_hello_world, menu);
		return true;
	}

	public void createPushNotifications(String message) {
		JSONObject object = new JSONObject();
		try {
			object.put("alert", message);
			object.put("title", "Chat");
			object.put("action", "MyAction");

			ParseQuery query = ParseInstallation.getQuery();
			query.whereNotEqualTo(USER_NAME_KEY, LoginActivity.user1);

			ParsePush pushNotification = new ParsePush();
			pushNotification.setQuery(query);
			pushNotification.setData(object);
			pushNotification.sendInBackground();
		} catch (JSONException e) {
			Log.e(TAG, "Could not parse the push notification", e);
		}
	}

	// JSONObject obj;
	// try {
	// obj = new JSONObject();
	// obj.put("alert", message + " From "+LoginActivity.user1 );
	// obj.put("action", "com.androidsx.parsechat.UPDATE_STATUS");
	// obj.put("customdata", "MyAction");
	//
	// ParsePush push = new ParsePush();
	// ParseQuery<ParseInstallation> query = ParseInstallation.getQuery();
	// // query.whereNotEqualTo(USER_NAME_KEY, LoginActivity.user1);
	// // Notification for Android users
	// query.whereEqualTo("deviceType", "android");
	// push.setQuery(query);
	// push.setData(obj);
	// push.sendInBackground();
	//
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// Log.e(TAG, "Could not parse the push notification", e);
	// }
	// }

	private void receiveMessage() {
		// ParseQuery<ParseObject> query = ParseQuery.getQuery("Messages");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("S_"
				+ tvTitulo.getText().toString().replace(" ", "_").trim());

		query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
		query.orderByDescending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> messages, ParseException e) {
				if (e == null) {
					messagesList.clear();

					StringBuilder builder = new StringBuilder();
					for (int i = messages.size() - 1; i >= 0; i--) {

						builder.append(
						// messages.get(i).getString(USER_NAME_KEY)
						// + ": " +
						messages.get(i).getString("message") + "\n");

						messagesList.add(messages.get(i).getString(USER_NAME_KEY)
								 + ": "+messages.get(i).getString("message")
								+ "\n");

						// Toast.makeText(getApplicationContext(),
						// ""+messages.get(i).getString("message"), 1).show();
						// Toast.makeText(getApplicationContext(),
						// ""+messages.get(i).getString(USER_NAME_KEY),
						// 1).show();

						// System.out.println("usernamezzz"+messages.get(i).getString(USER_NAME_KEY));
						// System.out.println("messagezzz"+
						// messages.get(i).getString("message"));

						// messagesList.add(messages.get(i).getString(newusers.get(0).toString())+
						// ": "
						// + messages.get(i).getString("message")+ "\n");
						// messagesList.add(messages.get(i).getString("message"));

					}
					// Toast.makeText(getApplicationContext(),
					// "size of messagelist"+messagesList.size(), 1).show();
					// for(int n=0;n<messages.size();n++)
					// {
					// System.out.println("usernamezzz"+messages.get(n).getString(USER_NAME_KEY));
					// System.out.println("messagezzz"+
					// messages.get(n).getString("message"));
					// }
					addItemstoListView(builder.toString());
				} else {
					Log.d("message", "Error: " + e.getMessage());
				}
			}
		});
	}

	public void addItemstoListView(String message) {
		// messagesList.add(message);
		adapter.notifyDataSetChanged();
		chatListView.invalidate();
	}
}
