package com.app.framework;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.eddura.pojo.MasterPojo;
import com.education.eddura.R;
import com.google.gson.Gson;

public abstract class AppActivity extends Activity implements
		VollyManager {

	protected com.eddura.pojo.MasterPojo mp;
	protected LayoutInflater inflater;
	public Class<?> typeClass;

	public JSONObject param;
	protected SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		preferences = getPreference(getStringResources(R.string.app_name));
	}

	// public final String PREF_NAME = getStringResources(R.string.pref_name);
	public String webservice_Status = "no status";

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	private SharedPreferences getPreference(String spName) {
		// TODO Auto-generated method stub
		return getSharedPreferences(spName, Context.MODE_PRIVATE);
	}

	private String getStringResources(int prefName) {
		return getResources().getString(prefName);
		
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		Log.e("Error", error.getMessage(), error);
		setErrorResponse(error);
	}

	public void makejsonObjRequest(Class<?> typeClass, String actionurl) {
		this.typeClass = typeClass;
		VollyNetworkManager jrnnew = new VollyNetworkManager(this, param,
				actionurl);

		try {
			jrnnew.makeJsonObjReq();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("Error", e.getMessage(), e);
		}
	}

	public Object getExtra(String key) {
		return getIntent().getExtras().get(key);

	}

	@Override
	public void onResponse(JSONObject response) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		mp = (MasterPojo) gson.fromJson(response.toString(), typeClass);
		setResponse(response);
	}

	protected AppListAdapter applist;
	protected ListView globallistView;
	protected int layoutid;

	public abstract View setCustomView(View view, int position, ViewGroup parent);

	public void setListAdapter(int layoutid, ArrayList<?> itemlist) {
		inflater = LayoutInflater.from(this);
		this.layoutid = layoutid;
		applist = new AppListAdapter(layoutid, itemlist);
		globallistView.setAdapter(applist);

	}

	public class AppListAdapter extends BaseAdapter {

		ArrayList<?> itemlist;

		public AppListAdapter(int layoutid, ArrayList<?> itemlist) {
			// TODO Auto-generated constructor stub
		
			this.itemlist = itemlist;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return itemlist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return getItem(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub
			return setCustomView(view, position, parent);

		}

	}

}
