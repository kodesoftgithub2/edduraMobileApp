package com.app.framework;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.eddura.pojo.MasterPojo;
import com.google.gson.Gson;

public abstract class AppFragment extends Fragment implements VollyManager {

	protected MasterPojo mp;
	private Class<?> typeClass;

	protected View view;
	protected JSONObject param;
	// public final String PREF_NAME = getStringResources(R.string.pref_name);
	public String webservice_Status = "no status";
	//	public AppActivity appActivity = (AppActivity) getActivity();

	// protected SharedPreferences preferences =
	// appActivity.getPreference(R.string.spName);
	@Override
	public void onErrorResponse(VolleyError error) {
		setErrorResponse(error);

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

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
		return getActivity().getIntent().getExtras().get(key);

	}

	@Override
	public void onResponse(JSONObject response) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		mp = (MasterPojo) gson.fromJson(response.toString(), typeClass);
		// if (webservice_Status.equalsIgnoreCase("no status")) {
		// Toast.makeText(AppActivity.this, "Something went wrong",
		// Toast.LENGTH_SHORT).show();
		// }
		setResponse(response);
	}

	protected AppListAdapter applist;
	protected ListView globallistView;

	public abstract void setCustomView(View view, int position);

	public void setListAdapter(int layoutid, ArrayList<?> itemlist,
			Context context) {
		applist = new AppListAdapter(layoutid, itemlist, context);
		globallistView.setAdapter(applist);
	}

	public class AppListAdapter extends BaseAdapter {

		int layoutid;
		ArrayList<?> itemlist;
		LayoutInflater inflater;

		public AppListAdapter(int layoutid, ArrayList<?> itemlist,
				Context context) {
			// TODO Auto-generated constructor stub
			this.layoutid = layoutid;
			this.itemlist = itemlist;
			inflater = LayoutInflater.from(context);
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
			if (view == null) {
				view = inflater.inflate(layoutid, parent, false);
				setCustomView(view, position);
			}
			return view;
		}

	}
}
