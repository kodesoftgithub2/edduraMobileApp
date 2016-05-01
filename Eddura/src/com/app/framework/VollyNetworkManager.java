package com.app.framework;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;

public class VollyNetworkManager {

	JSONObject jsonparam;
	VollyManager vollymangerListner;
	AppFragment appFragment;
	String actionname;
	
	
	// These tags will be used to cancel the requests
	private String tag_json_obj = "jobj_req";
	
	public VollyNetworkManager(VollyManager vollymangerListner,
			JSONObject jsonparam, String actionname) {
		this.vollymangerListner = vollymangerListner;
		this.jsonparam = (JSONObject) jsonparam;
		this.actionname = actionname;
	}



	public void makeJsonObjReq() throws JSONException {
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,actionname, jsonparam, vollymangerListner,
				vollymangerListner) {
			/**
			 * Passing some request headers
			 * */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				headers.put("charset", "utf-8");
				return headers;
			}

		};
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);	
	}
	

}
