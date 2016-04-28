package com.androidsx.findchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class GroupNames extends Activity {
	public static final String MYPREFERENCES = "Myprefer";
	static SharedPreferences sharedpreferences;
	private List<Note> posts;
	ListView listview;
	// CheckBox eventname;
	int j;
	private static final String TAG = GroupNames.class.getName();
	MyGroupAdapter myGroupAdapter;
	public static ArrayList<String> groupnameslist = new ArrayList<String>();
	ArrayList<String> newothrnamelist = new ArrayList<String>();
	ArrayList<String> allgrpconlist = new ArrayList<String>();

	HashMap<String, String> namesList = new HashMap<String, String>();
	ArrayList<ArrayList<String>> allnewnumlist = new ArrayList<ArrayList<String>>();
	ArrayList<String> grpconlist;
	ArrayList<String> newothrnumbrlist = new ArrayList<String>();
	String groupnames, contacts, newothrname, newothrnum;

	Button btn, bottomBtnchts;
	public static HashMap<Integer, Contacts> listDayItems;

	Set<java.util.Map.Entry<Integer, Contacts>> contactset;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chating_main);

		btn = (Button) findViewById(R.id.bottomBtn);

		bottomBtnchts = (Button) findViewById(R.id.bottomBtnchts);
		listview = (ListView) findViewById(android.R.id.list);

		TextView text1 = (TextView) findViewById(R.id.text1);
		// parseTest is a groups list
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParseTest");
		setProgressBarIndeterminateVisibility(true);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> postList, ParseException e) {
				// TODO Auto-generated method stub
				if (e == null) {
					// If there are results, update the list of posts
					// and notify the adapter
					groupnameslist.clear();
					String numbers = "";
					StringBuilder builder = new StringBuilder();
					for (int i = postList.size() - 1; i >= 0; i--) {
						contacts = postList.get(i).getString("TEST_CONTACTS");
						Gson gson = new Gson();
						listDayItems = gson.fromJson(contacts,
								new TypeToken<HashMap<Integer, Contacts>>() {
								}.getType());

						Iterator it = GroupNames.listDayItems.entrySet()
								.iterator();
						grpconlist = new ArrayList<String>();

						while (it.hasNext()) {
							Map.Entry pair = (Map.Entry) it.next();

							// System.out.println("Group Name "+ groupnames+
							// " values "+ pair.getKey()
							// + " = "
							// + ((Contacts)
							// pair.getValue()).getNumber().toString());

							// for(String list:)

							String number = ((Contacts) pair.getValue())
									.getNumber().toString();
							number = number.replace("-", "").replace("(", "")
									.replace(")", "").replace(" ", "");

							System.out.println("number   " + number);
							// number=number.replace(( )-\" \",'');
							numbers = numbers + "," + number;
							grpconlist.add(number);

							// System.out.println("groupnameesssss"+
							// groupnameslist);
							// System.out.println("grpconlist" + listDayItems);
						}

						if (grpconlist.contains(LoginActivity.user1)) {

							groupnames = postList.get(i).getString(
									"TEST_GROUPS");
							// System.out.println("=" + groupnames);
							// builder.append(groupnames);

							// builder.append(contacts);

							allgrpconlist.add(numbers);
							namesList.put("Group_" + i, groupnames);
							// System.out.println("allgrpssssss"+allgrpconlist);
							groupnameslist.add(groupnames);
						} else {

						}

					}

					System.out.println(" group name : " + builder.toString());

					addItemstoListView();
				} else {
					Log.d(getClass().getSimpleName(),
							"Error: " + e.getMessage());
				}

			}
		});
		ParseQuery<ParseObject> query1 = ParseQuery.getQuery("ParseNewChats");
		setProgressBarIndeterminateVisibility(true);
		query1.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> newList, ParseException e) {
				// TODO Auto-generated method stub
				if (e == null) {
					// If there are results, update the list of posts
					// and notify the adapter
					newothrnamelist.clear();
					StringBuilder builder = new StringBuilder();
					for (int i = newList.size() - 1; i >= 0; i--) {

						newothrnum = newList.get(i).getString("NEW_NUMOTHER")
								.toString().split(",")[0];
						System.out.println("new..........=" + newothrnum);
						builder.append(newothrnum);

						newothrname = newList.get(i).getString("NEW_NAMOTHER")
								.toString().split(",")[0];

						builder.append(newothrname);

						if (newList.get(i).getString("NEW_NUMOTHER").toString()
								.contains(LoginActivity.user1)) {
							newothrnumbrlist.add(newothrnum);

							System.out.println("allnewssssss"
									+ newothrnumbrlist);
							
							if (!newothrname.equalsIgnoreCase("null")
									&& !newothrnum.equalsIgnoreCase("null")) {
								
								groupnameslist.add(newothrname);
								
								
								if (newothrnum.equalsIgnoreCase(LoginActivity.user1)) {

									
								}else{
									allgrpconlist.add(newothrnum);
								}

							} else {

							}

							namesList.put("single_" + i, newothrname);

						} else {

						}
						// }

					}

					System.out.println(" group name : " + builder.toString());

					addItemstoListView();
				} else {
					Log.d(getClass().getSimpleName(),
							"Error: " + e.getMessage());
				}

			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				System.out.println("listview clicked");

				String s = groupnameslist.get(position);

				TextView text = (TextView) view.findViewById(R.id.groupstxt);

				System.out.println("ssssssssssssssssss"
						+ text.getText().toString());

				if (((String) getKeyFromValue(namesList, text.getText()
						.toString().trim())).contains("Group")) {

					System.out.println("in    ssssssssssssssssss" + s);

					Intent i = new Intent(GroupNames.this,
							StartChatActivity.class);
					i.putExtra("name", s.toString());
					i.putExtra("isList", true);

					i.putExtra("MyClass", allgrpconlist.get(position));
					startActivity(i);
				} else {

					System.out.println("out    ssssssssssssssssss" + s);
					Intent i = new Intent(GroupNames.this,
							SingleChatActivity.class);
					i.putExtra("name", s.toString());
					i.putExtra("isList", true);

					i.putExtra("MyClass", allgrpconlist.get(position));
					startActivity(i);
				}

			}
		});

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(GroupNames.this, MainActivity.class);
				groupnameslist.clear();
				startActivity(i);
			}
		});

		bottomBtnchts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(GroupNames.this, ContactsActivity.class);
				startActivity(i);
			}
		});

		// groupnameslist.addAll(StartChatingGroupList.groupheadlist);
		myGroupAdapter = new MyGroupAdapter(this, R.layout.text_list,
				groupnameslist);

		listview.setAdapter(myGroupAdapter);

	}

	// public static HashMap<Integer, Contacts> Fetchdata(int i) {
	// // SharedPreferences shared;
	// // SharedPreferences.Editor editor;
	// Gson gson = new Gson();
	// // shared = getSharedPreferences("MONDAY", Context.MODE_PRIVATE);
	// // editor = shared.edit();
	// HashMap<Integer, Contacts> listDayItems = gson.fromJson(
	// sharedpreferences.getString("mondAy_" + i, null),
	// new TypeToken<HashMap<Integer, Contacts>>() {
	// }.getType());
	// printMap(listDayItems);
	//
	// return listDayItems;
	// }
	//
	// public static void printMap(HashMap<Integer, Contacts> mp) {
	//
	// Iterator it = mp.entrySet().iterator();
	// while (it.hasNext()) {
	// Map.Entry pair = (Map.Entry) it.next();
	// System.out.println(pair.getKey() + " = "
	// + ((Contacts) pair.getValue()).getName().toString());
	// // it.remove(); // avoids a ConcurrentModificationException
	// }
	//
	// }
	// private void receiveContacts() {
	//
	//
	// }
	public void addItemstoListView() {
		// messagesList.add(message);
		myGroupAdapter.notifyDataSetChanged();
		listview.invalidate();
	}

	protected ArrayAdapter<com.androidsx.findchat.Note> getListAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

	private class MyGroupAdapter extends BaseAdapter {

		private ArrayList<String> countrylist;

		public MyGroupAdapter(GroupNames context, int addcontactsLists,
				ArrayList<String> list) {
			// TODO Auto-generated constructor stub
			super();
			countrylist = new ArrayList<String>();
			countrylist.addAll(list);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return groupnameslist.size();
		}

		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return (new ArrayList<String>(groupnameslist)).get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		private class ViewHolder {
			TextView grpname, tvNumber;
			CheckBox name;
			ImageView removeImg;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.text_list, null);

				holder = new ViewHolder();
				holder.grpname = (TextView) convertView
						.findViewById(R.id.groupstxt);
				holder.removeImg = (ImageView) convertView
						.findViewById(R.id.removeImg);
				// holder.removeImg.setOnClickListener(lisenter);
				// holder.name = (CheckBox) convertView
				// .findViewById(R.id.checkbox);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			String contact = (new ArrayList<String>(groupnameslist))
					.get(position);
			holder.grpname.setText(contact);
			holder.grpname.setTag(getKeyFromValue(namesList, contact));
			// holder.name.setChecked(contact.getIsSelected());
			// holder.name.setTag(contact);

			return convertView;

		}

		// OnClickListener lisenter = new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// final int position = listview.getPositionForView((View) v
		// .getParent());
		// groupnameslist.remove(position);
		//
		// notifyDataSetChanged();
		//
		// }
		// };
	}

	public Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}

	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// groupnameslist.clear();
	//
	// ParseQuery<ParseObject> query = ParseQuery.getQuery("ParseContacts");
	// setProgressBarIndeterminateVisibility(true);
	// query.findInBackground(new FindCallback<ParseObject>() {
	//
	// @Override
	// public void done(List<ParseObject> postList, ParseException e) {
	// // TODO Auto-generated method stub
	// if (e == null) {
	// // If there are results, update the list of posts
	// // and notify the adapter
	// groupnameslist.clear();
	// StringBuilder builder = new StringBuilder();
	// for (int i = postList.size() - 1; i >= 0; i--) {
	// builder.append(postList.get(i).getString("groupnAM_1"));
	//
	// groupnameslist.add(postList.get(i).getString(
	// "groupnAM_1"));
	// System.out.println("="+postList.get(i).getString(
	// "groupnAM_1"));
	//
	// builder.append(postList.get(i).getString("contact_1"));
	// Gson gson = new Gson();
	// listDayItems = gson.fromJson(
	// postList.get(i).getString("contact_1"),
	// new TypeToken<HashMap<Integer, Contacts>>() {
	// }.getType());
	//
	// Iterator it = GroupNames.listDayItems.entrySet()
	// .iterator();
	// grpconlist=new ArrayList<String>();
	// while (it.hasNext()) {
	// Map.Entry pair = (Map.Entry) it.next();
	//
	// System.out.println( "Group Name "+postList.get(i).getString(
	// "groupnAM_1") +" values "+ pair.getKey() + " = "
	// + ((Contacts) pair.getValue()).getNumber()
	// .toString());
	// grpconlist.add(((Contacts) pair.getValue())
	// .getNumber().toString());
	// System.out.println("connnnnnnnn" + grpconlist);
	//
	//
	// System.out.println("groupnameesssss" + groupnameslist);
	// System.out.println("grpconlist" + listDayItems);
	// }
	//
	// allgrpconlist.add(grpconlist);
	// }
	// addItemstoListView(builder.toString());
	// } else {
	// Log.d(getClass().getSimpleName(),
	// "Error: " + e.getMessage());
	// }
	//
	// }
	// });

	// sharedpreferences = getSharedPreferences(MYPREFERENCES,
	// Context.MODE_WORLD_WRITEABLE);
	// System.out.println("Size:" + sharedpreferences.getAll().size());
	// if (sharedpreferences.getAll().size() != 0) {
	//
	// StringBuilder strbuilder = new StringBuilder();
	//
	// for (int i = 1; i < sharedpreferences.getAll().size(); i++) {
	//
	// strbuilder.append(" name " + i + " :"
	// + sharedpreferences.getString("Grpnam_" + i, ""));
	//
	// groupnameslist.add(sharedpreferences.getString("Grpnam_" + i,
	// ""));
	//
	// // ArrayAdapter<String> listadapter17 = new
	// // ArrayAdapter<String>(this,
	// // R.layout.list_item, data);
	//
	// if (sharedpreferences.getAll().size() / 2 == i) {
	//
	// break;
	// }
}
