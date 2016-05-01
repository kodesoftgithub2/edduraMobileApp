package com.education.adapter;

import java.util.List;

import com.eddura.pojo.SpecifiUser_Dept;
import com.education.eddura.DashboardActivity;
import com.education.eddura.R;
import com.education.eddura.SpecificUserAccessibleClasses_Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SpecificUserDept_Adapter extends BaseAdapter{

	Context context;
	List<SpecifiUser_Dept> rowItems;

	public SpecificUserDept_Adapter(Context context, List<SpecifiUser_Dept> items) {
		this.context = context;
		this.rowItems = items;

	}

	/* private view holder class */
	private class ViewHolder {
		TextView custom_specificUserDept_title;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.custom_list_specific_user_dept, null);
			holder = new ViewHolder();

			holder.custom_specificUserDept_title = (TextView) convertView
					.findViewById(R.id.custom_specificUserDept_title);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		SpecifiUser_Dept rowItem = (SpecifiUser_Dept) getItem(position);
		holder.custom_specificUserDept_title.setText(rowItem.getName());
		

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
				
				// Create the bundle
				Bundle bundle = new Bundle();
				// Add your data from getFactualResults method to bundle
				bundle.putString("DEPT_ID", rowItems.get(position).getId());
				bundle.putString("DEPT_NAME", rowItems.get(position).getName());
				
				Intent intent = new Intent(context ,SpecificUserAccessibleClasses_Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// Add the bundle to the intent
				intent.putExtras(bundle);
				context.startActivity(intent);

			}
		});

		return convertView;
	}

	@Override
	public int getCount() {
		return rowItems.size();
	}

	@Override
	public Object getItem(int position) {
		return rowItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowItems.indexOf(getItem(position));
	}
}
