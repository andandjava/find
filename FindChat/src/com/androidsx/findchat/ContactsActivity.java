package com.androidsx.findchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

public class ContactsActivity extends ListActivity {

	// public static ArrayList<Contacts> conNumbers, selectedCon;
	public static HashMap<Integer, Contacts> connmmap, selectedConMap;
	private Cursor crContacts;
	ListView listview;
	// CheckBox eventname;
	MyNewchatAdapter myNewchatAdapter;
	ArrayList<String> conlist, namelist;
	CheckBox cb;
	int count = 0;
	TextView contxt, text_contact;
	Button btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addition_contacts);
		btn = (Button) findViewById(R.id.bottomBtn);
		btn.setVisibility(View.INVISIBLE);
		listview = (ListView) findViewById(android.R.id.list);
		text_contact = (TextView) findViewById(R.id.text_contact);
		// text_contact.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// System.exit(0);
		// }
		// });

		// conNumbers = new ArrayList<Contacts>();
		// selectedCon = new ArrayList<Contacts>();
		connmmap = new HashMap<Integer, Contacts>();

		crContacts = ContactHelper.getContactCursor(getContentResolver(), "");

		if (crContacts.getCount() > 0) {

			crContacts.moveToFirst();

			while (!crContacts.isAfterLast()) {

				Contacts contact = new Contacts();

				contact.setName(crContacts.getString(1));
				contact.setNumber(crContacts.getString(2));
				contact.setId(count);
				connmmap.put(count, contact);
				
				// conNumbers.add(contact);
				
				
				System.out.println("conNumber..." + connmmap);
				Iterator it = connmmap.entrySet().iterator();
				conlist = new ArrayList<String>();
				namelist = new ArrayList<String>();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();

					String othrnumber = ((Contacts) pair.getValue())
							.getNumber().toString();
					othrnumber = othrnumber.replace("-", "").replace("(", "")
							.replace(")", "").replace(" ", "");
					conlist.add(othrnumber);
					System.out.println("newcon=" + conlist);
					namelist.add(((Contacts) pair.getValue()).getName()
							.toString());
					System.out.println("newname=" + namelist);
				}

				count++;
				crContacts.moveToNext();
			}

			myNewchatAdapter = new MyNewchatAdapter(this, R.layout.liststyle,
					connmmap);

			// System.out.println();

			setListAdapter(myNewchatAdapter);
		}
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String c = conlist.get(position);
				System.out.println("ccc" + c);
				String n = namelist.get(position);
				System.out.println("nnn" + n);
				Contacts contact = (Contacts) parent
						.getItemAtPosition(position);
				System.out.println("contacts...." + contact);

				Intent i = new Intent(ContactsActivity.this,
						SingleChatActivity.class);
				// i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("MYOTHRNUM", c.toString());
				i.putExtra("MYOTHRNAM", n.toString());
				startActivity(i);
				finish();
			}
		});
	}

	private class MyNewchatAdapter extends BaseAdapter {

		private HashMap<Integer, Contacts> countryList;

		public MyNewchatAdapter(Context context, int textViewResourceId,
				HashMap<Integer, Contacts> conNumbersMap) {
			super();
			countryList = new HashMap<Integer, Contacts>();
			countryList.putAll(conNumbersMap);
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

			// System.out.println(" values "+countryList.get);

			Contacts contact = (new ArrayList<Contacts>(countryList.values()))
					.get(position);
			holder.tvNumber.setText(" (" + contact.getNumber() + ")");
			holder.code.setText(contact.getName());
			// // holder.name.setChecked(contact.getIsSelected());
			// holder.name.setTag(contact);

			return convertView;

		}

	}

	/*
	 * private class MyAdapter extends ArrayAdapter<Contacts> {
	 * 
	 * public MyAdapter(Context context, int resource, int textViewResourceId,
	 * ArrayList<String> conNames) { super(context, resource,
	 * textViewResourceId, conNumbers);
	 * 
	 * }
	 * 
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) {
	 * 
	 * View row = setList(position, parent); return row; }
	 * 
	 * private View setList(int position, ViewGroup parent) { LayoutInflater inf
	 * = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 * 
	 * View row = inf.inflate(R.layout.liststyle, parent, false);
	 * 
	 * TextView tvName = (TextView) row.findViewById(R.id.tvNameMain); TextView
	 * tvNumber = (TextView) row.findViewById(R.id.tvNumberMain);
	 * 
	 * tvName.setText(conNames.get(position)); tvNumber.setText("No: " +
	 * conNumbers.get(position)); return row; } }
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater imf = getMenuInflater();
		imf.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.item1) {
			Intent intent = new Intent(ContactsActivity.this, AddContact.class);
			startActivity(intent);
		} else if (item.getItemId() == R.id.item2) {
			Intent intent = new Intent(ContactsActivity.this,
					DeleteContacts.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
