package com.androidsx.findchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	// public static ArrayList<Contacts> conNumbers, selectedCon;
	public static HashMap<Integer, Contacts> conNumbersMap, selectedConMap;
	private Cursor crContacts;
	ListView listview;
	// CheckBox eventname;
	MyContactsAdapter myContactsAdapter;
	CheckBox cb;
	int count = 0;
	TextView contxt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listview = (ListView) findViewById(android.R.id.list);
		

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Contacts contact = (Contacts) parent
						.getItemAtPosition(position);
				cb = (CheckBox) view.findViewById(R.id.checkbox);

				if (cb.isChecked()) {
					cb.setChecked(false);
					// selectedCon.remove(contact);
					selectedConMap.remove(contact);
					contact.setIsSelected(cb.isChecked());

				} else {
					cb.setChecked(true);
					contact.setIsSelected(cb.isChecked());
					// selectedCon.add(contact);
					selectedConMap.put(contact.getId(), contact);

				}

			}
		});

		// conNumbers = new ArrayList<Contacts>();
		// selectedCon = new ArrayList<Contacts>();
		conNumbersMap = new HashMap<Integer, Contacts>();
		selectedConMap = new HashMap<Integer, Contacts>();

		if (selectedConMap.size() > 0) {

			findViewById(R.id.bottomBtn).setVisibility(View.GONE);
			findViewById(R.id.bottomBtn1).setVisibility(View.VISIBLE);

		} else {
			findViewById(R.id.bottomBtn).setVisibility(View.VISIBLE);
			findViewById(R.id.bottomBtn1).setVisibility(View.GONE);
		}

		crContacts = ContactHelper.getContactCursor(getContentResolver(), "");

		if (crContacts.getCount() > 0) {

			crContacts.moveToFirst();

			while (!crContacts.isAfterLast()) {

				Contacts contact = new Contacts();

				contact.setName(crContacts.getString(1));
				contact.setNumber(crContacts.getString(2));
				contact.setId(count);
				// conNumbers.add(contact);
				conNumbersMap.put(count, contact);
				count++;
				crContacts.moveToNext();
			}

			myContactsAdapter = new MyContactsAdapter(this, R.layout.liststyle,
					conNumbersMap);

			// System.out.println();

			setListAdapter(myContactsAdapter);
		}
		else
		{
			if(crContacts.getCount() >=0)
			{
				
				contxt = (TextView)findViewById(R.id.contxt);  

			    // this is the text we'll be operating on  
			    SpannableString text = new SpannableString("No Existing Contacts");  

			    // make "hello" to (characters 0 to 5) red color 
			    text.setSpan(new ForegroundColorSpan(Color.CYAN), 5, 5, 0);  

			    contxt.setText(text, BufferType.SPANNABLE);  
				Toast.makeText(MainActivity.this,text, Toast.LENGTH_SHORT).show();
			}
		}

		findViewById(R.id.bottomBtn1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, GroupActivity.class);
				i.putExtra("MyClass", selectedConMap);
				// i.putExtra("name", editText.getText()
				// .toString());

			}

		});

		findViewById(R.id.bottomBtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// for (Contacts d : conNumbersMap) {
				//
				// System.out.println(d.getName());
				// System.out.println(d.getIsSelected());
				// if (d.getIsSelected() == true) {
				// // selectedCon.add(d);
				//
				// System.out.println("selected contact "
				// + selectedConMap.containsKey(d.getId()));
				//
				// if (selectedConMap.containsKey(d.getId())) {
				// System.out.println("selected contact "
				// + d.getName());
				//
				// }
				//
				// }
				// }

				for (Entry<Integer, Contacts> entry : conNumbersMap.entrySet()) {
					Integer key = entry.getKey();
					Contacts value = entry.getValue();

					if (value.getIsSelected() == true) {
						// selectedCon.add(d);

						System.out.println("selected contact "
								+ selectedConMap.containsKey(key));

						if (selectedConMap.containsKey(key)) {
							System.out.println("selected contact "
									+ value.getName());

						}

					}
					// do stuff
				}

				// SharedPreferences preferences = MainActivity.this
				// .getSharedPreferences(
				// "SharePreference_messages_history_name",
				// Context.MODE_PRIVATE);
				// SharedPreferences.Editor editor = preferences.edit();
				// // for (Entry<Integer, Contacts> entry :
				// selectedConMap.entrySet()) {
				// Gson gson = new Gson();
				// String json = gson.toJson(selectedConMap);
				// editor.putString("mondAy", json);
				// // editor.putString(entry.getKey(), entry.getValue());
				// // }
				// editor.commit();
				//
				// // startActivity(i);
				//
				// Gson gson1 = new Gson();
				//
				//
				// HashMap<Integer, Contacts> listDayItems = gson1.fromJson(
				// preferences.getString("mondAy", null),
				// new TypeToken<HashMap<Integer, Contacts>>() {
				// }.getType());
				//
				// Iterator<Entry<Integer, Contacts>> it =
				// listDayItems.entrySet().iterator();
				// while (it.hasNext()) {
				// Entry<Integer, Contacts> pair = it.next();
				// System.out.println(pair.getKey() + " = "
				// + ((Contacts) pair.getValue()).getName().toString());
				// it.remove(); // avoids a ConcurrentModificationException
				// }
				// Intent i = new Intent(MainActivity.this,
				// GroupActivity.class);
				// i.putExtra("MyClass", selectedConMap);
				// startActivity(i);

				// prints [Tommy, tiger]

				if (selectedConMap.size() > 0) {

					LayoutInflater inflater = getLayoutInflater();
					View convertView = (View) inflater.inflate(
							R.layout.customdialog, null);

					// displaying alert dialog with list of
					// numbers
					final Dialog alertDialog = new Dialog(MainActivity.this);
					alertDialog.setContentView(convertView);
					final EditText editText = (EditText) alertDialog
							.findViewById(R.id.editText1);
					Button button = (Button) alertDialog
							.findViewById(R.id.button1);
					button.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							if (editText.length() == 0) {
								Toast.makeText(MainActivity.this,
										"please provide subject",
										Toast.LENGTH_SHORT).show();
							} else {
								// text.setText(editText.getText().toString());

								Intent i = new Intent(MainActivity.this,
										GroupActivity.class);
								i.putExtra("MyClass", selectedConMap);
								i.putExtra("name", editText.getText()
										.toString());

								startActivity(i);

								alertDialog.dismiss();

							}

						}
					});
					alertDialog.show();

				} else {
					final AlertDialog.Builder builder = new AlertDialog.Builder(
							MainActivity.this);
					builder.setMessage("Select Contacts for Grouping");
					builder.setCancelable(false);
					builder.setNegativeButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// finish the application
									dialog.cancel();

								}
							});

					AlertDialog alert = builder.create();
					// Show Alert Dialog
					alert.show();
				}
				// myCustomAdapter.countryList.
			}
		});
	}

	private class MyContactsAdapter extends BaseAdapter {

		private HashMap<Integer, Contacts> countryList;

		public MyContactsAdapter(Context context, int textViewResourceId,
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
				convertView = vi.inflate(R.layout.liststyle, null);

				holder = new ViewHolder();
				holder.code = (TextView) convertView
						.findViewById(R.id.tvNameMain);
				findViewById(R.id.tvNameMain);
				holder.tvNumber = (TextView) convertView
						.findViewById(R.id.tvNumberMain);
				holder.name = (CheckBox) convertView
						.findViewById(R.id.checkbox);
				convertView.setTag(holder);

				holder.name.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						Contacts contact = (Contacts) cb.getTag();
						Toast.makeText(
								getApplicationContext(),
								"Clicked on Checkbox: " + cb.getText() + " is "
										+ cb.isChecked(), Toast.LENGTH_LONG)
								.show();
						contact.setIsSelected(cb.isChecked());

						if (cb.isChecked()) {
							// selectedCon.add(contact);

							selectedConMap.put(contact.getId(), contact);
						} else {
							// selectedConMap.remove(contact);
							selectedConMap.remove(contact.getId());
						}
					}
				});
			} else { 
				holder = (ViewHolder) convertView.getTag();
			}

			// System.out.println(" values "+countryList.get);

			Contacts contact = (new ArrayList<Contacts>(countryList.values()))
					.get(position);
			holder.tvNumber.setText(" (" + contact.getNumber() + ")");
			holder.code.setText(contact.getName());
			holder.name.setChecked(contact.getIsSelected());
			holder.name.setTag(contact);

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
			Intent intent = new Intent(MainActivity.this, AddContact.class);
			startActivity(intent);
		} else if (item.getItemId() == R.id.item2) {
			Intent intent = new Intent(MainActivity.this, DeleteContacts.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
