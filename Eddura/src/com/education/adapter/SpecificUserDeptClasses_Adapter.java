package com.education.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eddura.pojo.SpecifiUser_Dept;
import com.eddura.pojo.SpecificUser_Classes;
import com.education.eddura.R;

public class SpecificUserDeptClasses_Adapter extends BaseAdapter {
	Context context;
	List<SpecificUser_Classes> rowItems;

	public SpecificUserDeptClasses_Adapter(Context context,
			ArrayList<SpecificUser_Classes> classes_Array) {
		this.context = context;
		this.rowItems = classes_Array;

	}

	/* private view holder class */
	private class ViewHolder {
		TextView textView_customList_class, textView_customList_classTeacher,
				textView_customList_subjects, textView_customList_students;
		
		ImageView ImageView_customList_status;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.custom_list_specific_user_dept_classes, null);
			holder = new ViewHolder();

			holder.textView_customList_class = (TextView) convertView
					.findViewById(R.id.textView_customList_class);
			holder.textView_customList_classTeacher = (TextView) convertView
					.findViewById(R.id.textView_customList_classTeacher);
			holder.textView_customList_subjects = (TextView) convertView
					.findViewById(R.id.textView_customList_subjects);
			holder.textView_customList_students = (TextView) convertView
					.findViewById(R.id.textView_customList_students);
			holder.ImageView_customList_status = (ImageView) convertView
					.findViewById(R.id.ImageView_customList_status);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		SpecificUser_Classes rowItem = (SpecificUser_Classes) getItem(position);
		holder.textView_customList_class.setText(rowItem.getName());
		holder.textView_customList_classTeacher.setText(rowItem.getClass_teacher_name());
		holder.textView_customList_subjects.setText(rowItem.getStudents_count());
		holder.textView_customList_students.setText(rowItem.getSubjects_count());

		if (rowItem.getIs_published().equalsIgnoreCase("1")) {
			holder.ImageView_customList_status.setImageResource(R.drawable.correct);
		} else {
			holder.ImageView_customList_status.setImageResource(R.drawable.cross);
		}
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
