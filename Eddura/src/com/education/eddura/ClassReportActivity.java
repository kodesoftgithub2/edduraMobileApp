package com.education.eddura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eddura.pojo.SpecifiUser_Dept;
import com.eddura.utilities.Constants;
import com.education.adapter.SpecificUserDept_Adapter;

public class ClassReportActivity extends Fragment {
	// Outlet Declaration
    ListView listView_departmentSpecificUser;
    TextView textviewError_departmentSpecificUser;
    
    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    
    // Other variable declaration
 	public static final String MyPREFERENCES = "MyPrefs" ;
 	SharedPreferences sharedpreferences;
 	
 	
 	// Default Constructor
    public ClassReportActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

          View view=inflater.inflate(R.layout.fragment_layout_two,container, false);          
          sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

          listView_departmentSpecificUser = (ListView) view.findViewById(R.id.listView_departmentSpecificUser);
          textviewError_departmentSpecificUser = (TextView) view.findViewById(R.id.textviewError_departmentSpecificUser);
          
          get_accessible_department_for_the_specific_user();
          
          return view;
    }

	/**
	 * Web-service Method implementation
	 */
	private void get_accessible_department_for_the_specific_user() {
		final ProgressDialog dialog = new ProgressDialog(this.getActivity());
		dialog.setMessage("Please wait...");
		dialog.setCancelable(false);
		dialog.show();
		
		final String user_id = sharedpreferences.getString(Constants.RESPONSE_ID, "");
		final String college_Domain = sharedpreferences.getString(Constants.KEY_COLLEGE_DOMAIN, "");
 
        String REGISTER_URL = "" + Constants.BASE_URL + "" + Constants.URL_ACCESSIBLE_DEPT_SPECIFIC_USER;
        
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        if (dialog.isShowing()) {
            				dialog.dismiss();
            			}
                        parseDeptResponse(response);
                        settab();
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
                        settab();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                
                params.put(Constants.KEY_COLLEGE_DOMAIN, college_Domain);
                params.put(Constants.KEY_USER_ID, user_id);
                return params;
            }
 
        };
 
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
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
	    Toast.makeText(getActivity().getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
	}
	
	
	// Parse the data from response we are getting from webservice
	ArrayList<SpecifiUser_Dept> dept_Array = new ArrayList<SpecifiUser_Dept>();
	protected void parseDeptResponse(String response) {
		try {
			//JSONObject reader = new JSONObject(response);
			JSONArray jArray = new JSONArray(response);
			if (jArray.length() > 0) {
				for(int i=0;i<jArray.length();i++) {
					JSONObject jObj = jArray.getJSONObject(i);
					
					String response_id = jObj.getString("id");					
					String response_name  = jObj.getString("name");
					
					SpecifiUser_Dept d_obj = new SpecifiUser_Dept();
					d_obj.setId(response_id);
					d_obj.setName(response_name);
					
					dept_Array.add(d_obj);
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
	
	
	protected void settab () {
		/*for (int index = 0; index < dept_Array.size(); index++) {
			final ActionBar bar = getActivity().getActionBar();
	        final int tabCount = bar.getTabCount();
	        final String text = dept_Array.get(index).getName();
	        bar.addTab(bar.newTab()
	                .setText(text)
	                .setTabListener(new TabListener(new TabContentFragment(text))));
		}*/
		
		if (dept_Array.size() > 0) {
			textviewError_departmentSpecificUser.setVisibility(View.GONE);
			listView_departmentSpecificUser.setVisibility(View.VISIBLE);
			
			SpecificUserDept_Adapter artificial_adapter = new SpecificUserDept_Adapter(getActivity().getApplicationContext(), dept_Array);
			listView_departmentSpecificUser.setAdapter(artificial_adapter);
		}
		else {
			textviewError_departmentSpecificUser.setVisibility(View.VISIBLE);
			listView_departmentSpecificUser.setVisibility(View.GONE);
		}
	}
	
	
	/**
     * A TabListener receives event callbacks from the action bar as tabs
     * are deselected, selected, and reselected. 
     **/
    /*private class TabListener implements ActionBar.TabListener {
        private TabContentFragment mFragment;

        public TabListener(TabContentFragment fragment) {
            mFragment = fragment;
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            ft.add(R.id.fragment_content, mFragment, mFragment.getText());
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            ft.remove(mFragment);
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            Toast.makeText(getActivity().getApplicationContext(), "Reselected!", Toast.LENGTH_SHORT).show();
        }
    }
	private class TabContentFragment extends Fragment {
        private String mText;

        public TabContentFragment(String text) {
            mText = text;
        }

        public String getText() {
            return mText;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View fragView = inflater.inflate(R.layout.action_bar_tab_content, container, false);

            TextView text = (TextView) fragView.findViewById(R.id.text);
            text.setText(mText);

            return fragView;
        }
    }*/
	
}
