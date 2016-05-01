package com.app.framework;

import org.json.JSONObject;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

	public static final String TAG = AppController.class.getSimpleName();
	public static Listener<JSONObject> jsonlistener;
	private static AppController mInstance;
	public static RequestQueue mRequestQueue;
	public static final String NOLOGIN = "no_login";
	public static String ISLOGINFIRSTTIME = NOLOGIN;
	public static final String FIRSTTIME = "first_login";
	public static final String SECONDTIME = "second_login";
	public static final String CHECKLOGIN = "login_check";
	public static final String USER_ID = "u_id";

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public AppController() {
		// TODO Auto-generated constructor stub
		mInstance = this;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.e("welcome", "application start");
		super.onCreate();
		mInstance = this;
		
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getInstance());
		}
		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}
