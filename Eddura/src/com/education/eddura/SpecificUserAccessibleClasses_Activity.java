package com.education.eddura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eddura.pojo.SpecifiUser_Dept;
import com.eddura.pojo.SpecificUser_Classes;
import com.eddura.utilities.Constants;
import com.education.adapter.SpecificUserDeptClasses_Adapter;
import com.education.adapter.SpecificUserDept_Adapter;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class SpecificUserAccessibleClasses_Activity extends ActionBarActivity {

	// Outlet Declaration
    ListView listView_departmentSpecificUserClass;
    TextView textviewError_departmentSpecificUserClass;
    
    
	// Other variable declaration
 	public static final String MyPREFERENCES = "MyPrefs" ;
 	SharedPreferences sharedpreferences;
	 	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_user_accessible_classes_);
        
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        listView_departmentSpecificUserClass = (ListView) findViewById(R.id.listView_departmentSpecificUserClass);
        textviewError_departmentSpecificUserClass = (TextView) findViewById(R.id.textviewError_departmentSpecificUserClass);
        
        
        // Values in Intent
        Bundle bundle = getIntent().getExtras();
		//Extract the data…
		String dept_id = bundle.getString("DEPT_ID");		
		String dept_name = bundle.getString("DEPT_NAME");
		
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(dept_name);
        
        getValuesFromWebservice(dept_id);
    }


    /**
     * Webservice Method
     */
    private void getValuesFromWebservice(final String dept_id) {
    	final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("Please wait...");
		dialog.setCancelable(false);
		dialog.show();
		
		final String user_id = sharedpreferences.getString(Constants.RESPONSE_ID, "");
		final String college_Domain = sharedpreferences.getString(Constants.KEY_COLLEGE_DOMAIN, "");
 
        String REGISTER_URL = "" + Constants.BASE_URL + "" + Constants.URL_ACCESSIBLE_CLASSES_SPECIFIC_USER;
        
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        if (dialog.isShowing()) {
            				dialog.dismiss();
            			}
                        parseDeptResponse(response);
                        setDataonList();
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
                        setDataonList();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                
                params.put(Constants.KEY_COLLEGE_DOMAIN, college_Domain);
                params.put(Constants.KEY_USER_ID, user_id);
                params.put(Constants.KEY_DEPT_ID, dept_id);
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
	    Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();
	}

	// Parse the data from response we are getting from webservice
	ArrayList<SpecificUser_Classes> classes_Array = new ArrayList<SpecificUser_Classes>();
	protected void parseDeptResponse(String response) {
		try {
			//JSONObject reader = new JSONObject(response);
			JSONArray jArray = new JSONArray(response);
			if (jArray.length() > 0) {
				for(int i=0;i<jArray.length();i++) {
					JSONObject jObj = jArray.getJSONObject(i);
					
					String response_id = jObj.getString("id");					
					String response_name  = jObj.getString("name");
					String response_department_id  = jObj.getString("department_id");
					String response_department_name  = jObj.getString("department_name");
					String response_class_teacher_name  = jObj.getString("class_teacher_name");
					String response_subjects_count  = jObj.getString("subjects_count");
					String response_students_count  = jObj.getString("students_count");
					String response_is_published  = jObj.getString("is_published");

					SpecificUser_Classes d_obj = new SpecificUser_Classes();
					d_obj.setId(response_id);
					d_obj.setName(response_name);
					d_obj.setDepartment_id(response_department_id);
					d_obj.setDepartment_name(response_department_name);
					d_obj.setClass_teacher_name(response_class_teacher_name);
					d_obj.setSubjects_count(response_subjects_count);
					d_obj.setStudents_count(response_students_count);
					d_obj.setIs_published(response_is_published);

					classes_Array.add(d_obj);
				}
			}
			else {
				// No data founds
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Set data on list view as per response getting from webservice
	protected void setDataonList() {
		if (classes_Array.size() > 0) {
			textviewError_departmentSpecificUserClass.setVisibility(View.GONE);
			listView_departmentSpecificUserClass.setVisibility(View.VISIBLE);
			
			SpecificUserDeptClasses_Adapter artificial_adapter = new SpecificUserDeptClasses_Adapter(getApplicationContext(), classes_Array);
			listView_departmentSpecificUserClass.setAdapter(artificial_adapter);
		}
		else {
			textviewError_departmentSpecificUserClass.setVisibility(View.VISIBLE);
			listView_departmentSpecificUserClass.setVisibility(View.GONE);
		}
	}
	
	
	/**
	 * ACTION MENU METHOD
	 */
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.specific_user_accessible_classes_, menu);
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
        else if (id == android.R.id.home) {
        	finish();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
