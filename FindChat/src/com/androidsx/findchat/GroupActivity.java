package com.androidsx.findchat;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class GroupActivity extends ListActivity {
	HashMap<Integer, Contacts> contactsList, grouplist;
	MySelCustomAdapter myselCustomAdapter;
	TextView text;
	Button bottomBtn;
	ImageView add_img;
	String value;
	public static final String MYPREFERENCES = "Myprefer";
	static SharedPreferences sharedpreferences;

	public OnClickListener lisenter;
	static ParseObject parsecontacts = new ParseObject("ParseContacts");
	ParseObject parsetest = new ParseObject("ParseTest");
	String conStr = "contact", grpStr = "group";
	int count = 0, count1 = 0;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_list);
		text = (TextView) findViewById(R.id.text);
		add_img = (ImageView) findViewById(R.id.add_img);
		sharedpreferences = getSharedPreferences(MYPREFERENCES,
				Context.MODE_APPEND);
		value = getIntent().getExtras().getString("name");
		text.setText(value);

		text.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater inflater = getLayoutInflater();
				View convertView = (View) inflater.inflate(
						R.layout.customdialog, null);
				final Dialog alertDialog = new Dialog(GroupActivity.this);
				alertDialog.setContentView(convertView);
				final EditText editText = (EditText) alertDialog
						.findViewById(R.id.editText1);
				Button button = (Button) alertDialog.findViewById(R.id.button1);
				button.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						if (editText.length() == 0) {
							Toast.makeText(GroupActivity.this,
									"please provide subject",
									Toast.LENGTH_SHORT).show();
						} else {
							text.setText(editText.getText().toString());
							value = text.getText().toString();
							alertDialog.dismiss();
						}

					}
				});

				alertDialog.show();
			}
		});

		bottomBtn = (Button) findViewById(R.id.bottomBtn);
		bottomBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				count++;
				count1++;

				// SharedPreferences.Editor editor = sharedpreferences.edit();
				// // for (Entry<Integer, Contacts> entry :
				// // selectedConMap.entrySet()) {
				//
				// System.out
				// .println(" size " + sharedpreferences.getAll().size());

				Contacts contact = new Contacts();

				contact.setName("you");
				contact.setNumber(LoginActivity.user1);
				contact.setId(-1);

				MainActivity.selectedConMap.put(contact.getId(), contact);
				// MainActivity.selectedConMap.put(key, value);

				Gson gson = new Gson();

				String json = gson.toJson(MainActivity.selectedConMap);

				parsetest.put("TEST_CONTACTS", json);
				parsetest.put("TEST_GROUPS", value);

				parsetest.put("TEST_NAME", conStr + count);
				System.out.println("parseregistration" + parsetest);
				parsetest.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							finish();
							startActivity(new Intent(getApplicationContext(),
									GroupNames.class));
						} else {
							Log.d(getClass().getSimpleName(),
									"User update error: " + e);
						}

					}
				});

				//
				// parsecontacts.saveInBackground(new SaveCallback() {
				//
				// @Override
				// public void done(ParseException e) {
				// if (e == null) {
				// finish();
				// startActivity(new Intent(getApplicationContext(),
				// GroupNames.class));
				// } else {
				// Log.d(getClass().getSimpleName(),
				// "User update error: " + e);
				// }
				//
				// }
				// });

			}
		});

		contactsList = (HashMap<Integer, Contacts>) getIntent()
				.getSerializableExtra("MyClass");

		myselCustomAdapter = new MySelCustomAdapter(this, contactsList);
		setListAdapter(myselCustomAdapter);

		add_img.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(GroupActivity.this,
						AddingListContact.class);
				intent.putExtra("MYGROUP", contactsList);
				startActivityForResult(intent, 2);// Activity is started
													// with requestCode 2
			}
		});

	}

	private class MySelCustomAdapter extends BaseAdapter {

		private HashMap<Integer, Contacts> countryList;

		public MySelCustomAdapter(Context context,
				HashMap<Integer, Contacts> contactsList) {
			// TODO Auto-generated constructor stub
			this.countryList = contactsList;
		}

		// public MySelCustomAdapter(Context context, int textViewResourceId,
		// ArrayList<Contacts> countryList) {
		// super(context, textViewResourceId, countryList);
		// this.countryList = new ArrayList<Contacts>();
		// this.countryList.addAll(countryList);
		// }

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return countryList.size();
		}

		@Override
		public Contacts getItem(int position) {
			// TODO Auto-generated method stub
			return countryList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		private class ViewHolder {
			TextView code, tvNumber;
			ImageView removeImg;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder holder = null;
			Log.v("ConvertView", String.valueOf(position));
			Contacts contact = (new ArrayList<Contacts>(countryList.values()))
					.get(position);
			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.group_list, null);

				holder = new ViewHolder();
				holder.code = (TextView) convertView
						.findViewById(R.id.tvNameMain);
				findViewById(R.id.tvNameMain);
				holder.tvNumber = (TextView) convertView
						.findViewById(R.id.tvNumberMain);
				holder.removeImg = (ImageView) convertView
						.findViewById(R.id.removeImg);

				holder.removeImg.setOnClickListener(lisenter);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.removeImg.setTag(contact.getId());

			holder.tvNumber.setText(" (" + contact.getNumber() + ")");
			holder.code.setText(contact.getName());
			// holder.removeImg.setChecked(contact.getIsSelected());

			return convertView;
		}

		OnClickListener lisenter = new OnClickListener() {

			@Override
			public void onClick(View v) {

				MainActivity.selectedConMap.remove((Integer) v.getTag());

				// (new ArrayList<Contacts>(countryList.values()))
				// .get((Integer) v.getTag()).getId();

				// countryList
				// .get((new ArrayList<Contacts>(countryList.values()))
				// .get((Integer) v.getTag()).getId())

				countryList.remove((Integer) v.getTag());

				// System.out.println("Name remove "
				// + countryList.get((Integer) v.getTag()).getName());

				// myselCustomAdapter.remove(contactsList.get((Integer)
				// v.getTag()));

				notifyDataSetChanged();
				System.out.println("Name remove " + countryList.size()
						+ " and " + MainActivity.selectedConMap.size());
			}
		};
	}

	// Call Back method to get the Message form other Activity
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// check if the request code is same as what is passed here it is 2
		
		if (requestCode == 2 && resultCode == 2) {

			Contacts contact = (Contacts) data.getSerializableExtra("MESSAGE");
			contactsList.put(contact.getId(), contact);
			MainActivity.selectedConMap.put(contact.getId(), contact);
			// System.out.println("size " + contactsList.size() + " Details "
			// + contactsList.get(contactsList.size() - 1).getName());

			myselCustomAdapter.notifyDataSetChanged();
		}
	}
}