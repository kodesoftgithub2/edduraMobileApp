package com.app.framework;

import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

public interface VollyManager extends ErrorListener, Listener<JSONObject> {
	public abstract void setResponse(JSONObject response);

	public abstract void setErrorResponse(VolleyError error);
}
