package com.education.eddura;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eddura.utilities.ConnectionDetector;
import com.eddura.utilities.Constants;

public class LoginActivity extends Activity {

	// Outlet declarations
	Button btn_login;
	EditText editText_CollegeID, editText_Username, editText_Password;

	// Other variable declaration
	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedpreferences;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Remove the Title Bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(null);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		
		editText_CollegeID = (EditText) findViewById(R.id.login_collegeId);
		editText_Username = (EditText) findViewById(R.id.login_username);
		editText_Password = (EditText) findViewById(R.id.login_password);

		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					loginValidation();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("error", e.getMessage(), e);
				}
			}
		});
	}

	/**
	 * LOGIN VALIDATION
	 */

	private void loginValidation() throws JSONException {
		String college_ID = editText_CollegeID.getText().toString();
		String username = editText_Username.getText().toString();
		String password = editText_Password.getText().toString();

		editText_CollegeID.setError(null);
		editText_Username.setError(null);
		editText_Password.setError(null);

		if (college_ID.equalsIgnoreCase("")) {

			editText_CollegeID.requestFocus();
			editText_CollegeID.setError("Please enter College ID");

		} else if (username.equalsIgnoreCase("")) {

			editText_Username.requestFocus();
			editText_Username.setError("Please enter Username");

		} else if (password.equalsIgnoreCase("")) {

			editText_Password.requestFocus();
			editText_Password.setError("Please enter Password");

		} else {
			// hide the soft keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editText_Password.getWindowToken(), 0);

			// Checking the connection
			if (!ConnectionDetector.networkStatus(getApplicationContext())) {
				ConnectionDetector.displayNoNetworkDialog(LoginActivity.this);
				
			} else {
				// calling the async task class...
				registerUser();
			}
		}
	}
	
	
	/**
	 * WEBSERVICE CALLING AND STOARING SHARED PREFERENCES METHODS
	 */
	private void registerUser(){
		
		final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
		dialog.setMessage("Signing in");
		dialog.setCancelable(false);
		dialog.show();
		
		
        final String college_ID = editText_CollegeID.getText().toString().trim();
        final String username = editText_Username.getText().toString().trim();
        final String password = editText_Password.getText().toString().trim();
 
        String REGISTER_URL = "" + Constants.BASE_URL + "" + Constants.URL_LOGIN;
        
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                        if (dialog.isShowing()) {
            				dialog.dismiss();
            			}
                        saveThePreferences(response);
                        
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    	if (dialog.isShowing()) {
            				dialog.dismiss();
            			}
                    	NetworkResponse response = error.networkResponse;
                        if(response != null && response.data != null){
                            switch(response.statusCode){
                                case 400:
                                     String json = new String(response.data);
                                     json = trimMessage(json, "message");
                                     if(json != null) displayMessage(json);
                                     break;
                                }
                               //Additional cases
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                
                params.put(Constants.KEY_COLLEGE_DOMAIN, college_ID);
                params.put(Constants.KEY_USERNAME, username);
                params.put(Constants.KEY_PASSWORD, password);
                return params;
            }
 
        };
 
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
	
	public String trimMessage(String json, String key){
	    String trimmedString = null;

	    try{
	        JSONObject obj = new JSONObject(json);
	        trimmedString = obj.getString(key);
	    } catch(JSONException e){
	        e.printStackTrace();
	        return null;
	    }

	    return trimmedString;
	}

	//Somewhere that has access to a context
	public void displayMessage(String toastString){
	    Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
	}
	
	// Save values into shared preferences for future use
	protected void saveThePreferences(String response) {
		try {
			JSONObject reader = new JSONObject(response);
			String response_id  = reader.getString(Constants.RESPONSE_ID);
			String response_username  = reader.getString(Constants.RESPONSE_USERNAME);
			String response_display_name  = reader.getString(Constants.RESPONSE_DISPLAY_NAME);
			
			final String college_ID = editText_CollegeID.getText().toString().trim();
			
			SharedPreferences.Editor editor = sharedpreferences.edit();
            
            editor.putString(Constants.RESPONSE_ID, response_id);
            editor.putString(Constants.KEY_COLLEGE_DOMAIN, college_ID);
            editor.commit();
			startActivity(new Intent(LoginActivity.this, MainActivity.class));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
	/**
	 * EXTRA METHODS
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
