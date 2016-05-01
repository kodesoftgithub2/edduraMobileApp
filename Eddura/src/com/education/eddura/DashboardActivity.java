package com.education.eddura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.framework.MyValueFormatter;
import com.eddura.pojo.Dashboard_Infogrraphics;
import com.eddura.utilities.ConnectionDetector;
import com.eddura.utilities.Constants;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends Fragment {
	// Outlet declaration
	Button btn_logout;
	View view;
	protected BarChart mChart;
	
	
	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";

	
	// Other variable declaration
	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedpreferences;
	ArrayList<Dashboard_Infogrraphics> infographics_arrayList = new ArrayList<Dashboard_Infogrraphics>();
	
	
	// Default Constructor
	public DashboardActivity() {

	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_layout_one, container,
                      false);
        init();  
        return view;
    }

	
	
	private void init() {
		sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		mChart = (BarChart) view.findViewById(R.id.chart);
		chartInitialization();
		btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callToLogout();
			}
		});

        
        // Checking the connection
		if (!ConnectionDetector.networkStatus(getActivity().getApplicationContext())) {
			ConnectionDetector.displayNoNetworkDialog(this.getActivity());
			
		} else {
			// calling the async task class...
			getDashboardInfographics();
		}		
	}

	private void chartInitialization() {
		mChart = (BarChart) view.findViewById(R.id.chart);
		//mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
     // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mChart.setDrawGridBackground(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setTextSize(13f);
        //ValueFormatter custom = new MyValueFormatter();

        mChart.getAxisLeft().setDrawLabels(true);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        //leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setTextSize(13f);
        
        mChart.getAxisRight().setDrawLabels(false);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        //rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        leftAxis.setTextSize(13f);

        mChart.getLegend().setEnabled(true);
        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.BELOW_CHART_LEFT);
        l.setForm(LegendForm.SQUARE);
        l.setFormSize(15f);
        l.setTextSize(15f);
        l.setXEntrySpace(4f);	
	}

	
	
	
	/**
	 * Web-service Method implementation
	 */
	private void getDashboardInfographics() {
		final ProgressDialog dialog = new ProgressDialog(this.getActivity());
		dialog.setMessage("Please wait...");
		dialog.setCancelable(false);
		dialog.show();
		
		final String user_id = sharedpreferences.getString(Constants.RESPONSE_ID, "");
		final String college_Domain = sharedpreferences.getString(Constants.KEY_COLLEGE_DOMAIN, "");
 
        String REGISTER_URL = "" + Constants.BASE_URL + "" + Constants.URL_DASHBOARD_INFOGRAPHICS;
        
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        if (dialog.isShowing()) {
            				dialog.dismiss();
            			}
                        
                        parseResponse(response);
                        
                        getValues();
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
                
                params.put(Constants.KEY_COLLEGE_DOMAIN, college_Domain);
                params.put(Constants.KEY_USER_ID, user_id);
                return params;
            }
 
        };
 
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        requestQueue.add(stringRequest);
	}

	protected void getValues() {
		// System.out.println("Size : " + infographics_arrayList.size());
		for (int i = 0; i < infographics_arrayList.size(); i++) {
			//System.out.println("Key : " + infographics_arrayList.get(i).getResponse_key() + "\nValue : " + infographics_arrayList.get(i).getResponse_value());
			
		}
		
		 BarData data = new BarData(getXAxisValues(), getDataSet());
	        mChart.setData(data);
	        mChart.animateXY(2000, 2000);
	        mChart.invalidate();
	}

	private List<BarDataSet> getDataSet() {
		ArrayList<BarDataSet> dataSets = null;
		 
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();        
        for (int i = 0; i < infographics_arrayList.size(); i++) {
			//xAxis.add(infographics_arrayList.get(i).getResponse_key());
        	
        	float y_Val = Float.parseFloat(infographics_arrayList.get(i).getResponse_value());
			BarEntry v1e = new BarEntry(y_Val, i);
			valueSet1.add(v1e);
		}
  
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Number of Lectures");
        barDataSet1.setColor(getResources().getColor(R.color.bar_color));
        barDataSet1.setBarSpacePercent(60f);
        
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
	}

	private List<String> getXAxisValues() {
		ArrayList<String> xAxis = new ArrayList<>();
		
		for (int i = 0; i < infographics_arrayList.size(); i++) {
			xAxis.add(infographics_arrayList.get(i).getResponse_key());
		}
		
		return xAxis;
	}

	protected void parseResponse(String response) {
		try {
			JSONObject reader = new JSONObject(response);
			if (reader.length() > 0) {
				
				Iterator iterator = reader.keys();
				while(iterator.hasNext()){
					String key = (String)iterator.next();					
					String value  = reader.getString(key);
					
					Dashboard_Infogrraphics d_obj = new Dashboard_Infogrraphics();
					d_obj.setResponse_key(key);
					d_obj.setResponse_value(value);
					
					infographics_arrayList.add(d_obj);
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
	
	
	
	
	// Logout
	protected void callToLogout() {
		
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.clear(); 
		editor.commit();
		
		Intent i = new Intent(getActivity(), LoginActivity.class);
		startActivity(i);
		((Activity) getActivity()).overridePendingTransition(0, 0);

	}
}
