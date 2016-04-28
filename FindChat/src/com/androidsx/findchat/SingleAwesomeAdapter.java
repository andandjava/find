package com.androidsx.findchat;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SingleAwesomeAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> mMessages;
	public static final String MYPREFERENCES = "Myprefer";
	static SharedPreferences sharedpreferences;
	public static final String USER_NAME_ID = "userid";
	String userid;
	ArrayList<String> groupusers = new ArrayList<String>();

	public SingleAwesomeAdapter(Context context, ArrayList<String> messagesList) {
		super();
		this.mContext = context;
		this.mMessages = messagesList;
//		sharedpreferences = mContext.getSharedPreferences(MYPREFERENCES,Context.MODE_APPEND);
//		userid = sharedpreferences.getString("USER_NAME", " ").toString();
//		System.out.println("userid" + userid);
	}

	@Override
	public int getCount() {
		return mMessages.size();
	}

	@Override
	public Object getItem(int position) {
		return mMessages.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String message = (String) this.getItem(position);

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.sms_row, parent, false);
			holder.message = (TextView) convertView.findViewById(R.id.message_text);
			holder.message1 = (TextView) convertView.findViewById(R.id.message_text1);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
			holder.message1.setVisibility(View.GONE);

		// LayoutParams lp = (LayoutParams) holder.message.getLayoutParams();
		// check if it is a status message then remove background, and change
		// text color.

		System.out.println(" message string " + message.toString());

		// if (userid.equalsIgnoreCase(message.toString().trim().split(":")[0]))
		// {
		// holder.message1
		// .setBackgroundResource(R.drawable.orange);
		// // lp.gravity = Gravity.RIGHT;
		// // holder.message1.setTextColor(R.color.white);
		// holder.message1.setText(message.toString());
		// holder.message.setVisibility(View.GONE);
		// holder.message1.setVisibility(View.VISIBLE);
		// }
		// else {
		// Check whether message is mine to show green background and align

		if (LoginActivity.user1.equalsIgnoreCase(message.toString().trim().split(":")[0])) {
			holder.message1.setBackgroundResource(R.drawable.speech_bubble_green);
			holder.message1.setText(message.toString().trim().split(":")[1]);
			// lp.gravity = Gravity.RIGHT;
			holder.message.setVisibility(View.GONE);
			holder.message1.setVisibility(View.VISIBLE);
		}
		// If not mine then it is from sender to show orange background and
		// align to left
		else {
			if (!SingleChatActivity.tvTitulo1.equals(message.toString().trim().split(":")[0])) {
				holder.message.setBackgroundResource(R.drawable.speech_bubble_orange);
				holder.message.setText(message.toString().trim().split(":")[1]);
				// lp.gravity = Gravity.RIGHT;
				holder.message.setVisibility(View.VISIBLE);
				holder.message1.setVisibility(View.GONE);

			} 
//				else {

				// lp.gravity = Gravity.RIGHT;\
			
			
			
			
				
//				holder.message.setVisibility(View.GONE);
//				holder.message1.setVisibility(View.GONE);
//			}

//		}
		// holder.message.setLayoutParams(lp);
		// holder.message.setTextColor(R.color.white);
		 }
		
//		Toast.makeText(mContext, "name", 1).show();
//		Toast.makeText(mContext, "name", 1).show();
		
		
//		for(int n=0;n<mMessages.size();n++)
//		{
//			String s=message.toString().trim().split(":")[0];
//			Toast.makeText(mContext, "name"+s, 1).show();
//			System.out.println("");
//		}
		return convertView;
	}

	private static class ViewHolder {
		TextView message, message1;
	}

	@Override
	public long getItemId(int position) {
		// Unimplemented, because we aren't using Sqlite.
		return position;
	}

}
