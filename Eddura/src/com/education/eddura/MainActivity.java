package com.education.eddura;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.education.adapter.CustomDrawerAdapter;
import com.education.adapter.DrawerItem;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;

	List<DrawerItem> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		/*
		 * mDrawerLayout.setDrawerShadow(R.drawable.ic_launcher,
		 * GravityCompat.START);
		 */

		// Add Drawer Item to dataList
		dataList.add(new DrawerItem("Dashboard", R.drawable.dashboard));
		dataList.add(new DrawerItem("My Dashboard", R.drawable.my_dashboard));
		dataList.add(new DrawerItem("Message", R.drawable.message));
		dataList.add(new DrawerItem("Subject", R.drawable.subject));
		dataList.add(new DrawerItem("All Subjects", R.drawable.all_subject));
		dataList.add(new DrawerItem("Attendence", R.drawable.attendance));
		dataList.add(new DrawerItem("Students", R.drawable.students));
		dataList.add(new DrawerItem("Reports", R.drawable.report));
		dataList.add(new DrawerItem("Students", R.drawable.students));
		dataList.add(new DrawerItem("Classes", R.drawable.classes));

		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.drawer, R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			SelectItem(0);
		}
	}

	public void SelectItem(int possition) {

		Fragment fragment = null;
		Bundle args = new Bundle();
		switch (possition) {
		case 0:
			Toast.makeText(getApplicationContext(), "DASHBOARD", Toast.LENGTH_SHORT).show();
			fragment = new DashboardActivity();
			args.putString(DashboardActivity.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(DashboardActivity.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 1:
			Toast.makeText(getApplicationContext(), "MY DASHBOARD", Toast.LENGTH_SHORT).show();
			fragment = new DashboardActivity();
			args.putString(DashboardActivity.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(DashboardActivity.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 2:
			Toast.makeText(getApplicationContext(), "MESSAGE", Toast.LENGTH_SHORT).show();
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
					.get(possition).getImgResID());
			break;
		case 3:
			Toast.makeText(getApplicationContext(), "SUBJECT", Toast.LENGTH_SHORT).show();
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 4:
			Toast.makeText(getApplicationContext(), "ALL SUBJECT", Toast.LENGTH_SHORT).show();
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 5:
			Toast.makeText(getApplicationContext(), "ATTENDENCE", Toast.LENGTH_SHORT).show();
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
					.get(possition).getImgResID());
			break;
		case 6:
			Toast.makeText(getApplicationContext(), "STUDENTS", Toast.LENGTH_SHORT).show();
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 7:
			Toast.makeText(getApplicationContext(), "REPORT", Toast.LENGTH_SHORT).show();
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 8:
			Toast.makeText(getApplicationContext(), "MY STUDENTS", Toast.LENGTH_SHORT).show();
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
					.get(possition).getImgResID());
			break;
		case 9:
			Toast.makeText(getApplicationContext(), "CLASSES", Toast.LENGTH_SHORT).show();
			fragment = new ClassReportActivity();
			args.putString(ClassReportActivity.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(ClassReportActivity.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		default:
			break;
		}

		fragment.setArguments(args);
		FragmentManager frgManager = getFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment)
				.commit();

		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SelectItem(position);

		}
	}

	/**
	 * ACTION BAR MENU
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		else {
			switch (item.getItemId()) {
	        case R.id.action_profile:
	            // Profile action
	        	Toast.makeText(getApplicationContext(), "Profile Clicked", Toast.LENGTH_SHORT).show();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
		}
	}
}
