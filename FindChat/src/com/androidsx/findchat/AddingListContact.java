package com.androidsx.findchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class AddingListContact extends ListActivity {

	// public static ArrayList<Contacts> conNumbers, grouplist, data;
	HashMap<Integer, Contacts> contactsMap, groupMap;
	private Cursor crContacts;
	ListView listview;
	// CheckBox eventname;
	MyCustomAdapter myCustomAdapter;
	int count = 0;
	Button btn;
TextView	text_contact;
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addition_contacts);
		btn=(Button)findViewById(R.id.bottomBtn);
		btn.setVisibility(View.INVISIBLE);
		text_contact=(TextView)findViewById(R.id.text_contact);
		text_contact.setVisibility(View.INVISIBLE);
		listview = (ListView) findViewById(android.R.id.list);
		groupMap = (HashMap<Integer, Contacts>) getIntent()
				.getSerializableExtra("MYGROUP");
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Contacts contact = (Contacts) parent
						.getItemAtPosition(position);

				Intent intent = new Intent();
				intent.putExtra("MESSAGE", contact);
				setResult(2, intent);

				finish();// finishing activity
			}
		});

		// conNumbers = new ArrayList<Contacts>();
		contactsMap = new HashMap<Integer, Contacts>();
		crContacts = ContactHelper.getContactCursor(getContentResolver(), "");

		if (crContacts.getCount() > 0) {

			crContacts.moveToFirst();

			while (!crContacts.isAfterLast()) {

				Contacts contact = new Contacts();
				contact.setName(crContacts.getString(1));
				
				
				contact.setNumber(crContacts.getString(2));
				contact.setId(count);
				

				for (Entry<Integer, Contacts> entry : MainActivity.selectedConMap
						.entrySet()) {

					Integer key = entry.getKey();

					System.out.println(" Keys are " + key);
					Contacts value = entry.getValue();
					System.out.println(" Values " + value.getName());
					// do stuff
				}

				if (!MainActivity.selectedConMap.containsKey(count)) {
					System.out.println(" Values " + crContacts.getString(1));
					// conNumbers.add(contact);
					contactsMap.put(count, contact);
				}

				count++;
				crContacts.moveToNext();
			}

			myCustomAdapter = new MyCustomAdapter(this,
					R.layout.addcontacts_lists, contactsMap);
			setListAdapter(myCustomAdapter);
		}
	}

	private class MyCustomAdapter extends BaseAdapter {

		private HashMap<Integer, Contacts> countryList;

		public MyCustomAdapter(Context context, int textViewResourceId,
				HashMap<Integer, Contacts> contactsMap) {
			super();
			countryList = new HashMap<Integer, Contacts>();
			countryList.putAll(contactsMap);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return countryList.size();
		}

		@Override
		public Contacts getItem(int position) {
			// TODO Auto-generated method stub
			return (new ArrayList<Contacts>(countryList.values()))
					.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		private class ViewHolder {
			TextView code, tvNumber;
			CheckBox name;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.addcontacts_lists, null);

				holder = new ViewHolder();
				holder.code = (TextView) convertView
						.findViewById(R.id.tvNameMain);
				findViewById(R.id.tvNameMain);
				holder.tvNumber = (TextView) convertView
						.findViewById(R.id.tvNumberMain);
				// holder.name = (CheckBox) convertView
				// .findViewById(R.id.checkbox);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Contacts contact = (new ArrayList<Contacts>(countryList.values()))
					.get(position);
			holder.tvNumber.setText(" (" + contact.getNumber() + ")");
			holder.code.setText(contact.getName());
			// holder.name.setChecked(contact.getIsSelected());
			// holder.name.setTag(contact);

			return convertView;

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater imf = getMenuInflater();
		imf.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.item1) {
			Intent intent = new Intent(AddingListContact.this, AddContact.class);
			startActivity(intent);
		} else if (item.getItemId() == R.id.item2) {
			Intent intent = new Intent(AddingListContact.this,
					DeleteContacts.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
