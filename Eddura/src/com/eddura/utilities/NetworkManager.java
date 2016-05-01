package com.eddura.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.eddura.pojo.Login_GS;

import android.util.Log;

/**
 * This code is for Network related operation Which deals with json connection
 * and json reponce from Web-Service
 * 
 * */

public class NetworkManager {

	static String jsonResponceFromServer = "";

	public static final String MSG_RESPONSE = "response";
	public static final String STATUS = "status";
	public static final String DATA = "data";
	public static final String USER_ID = "user_id";
	public static final String USERNAME = "username";
	public static final String KEY_MSG = "msg";
	public static final String NAME = "Name";
	public static String successInfo;
	public static String loginValue;

	// Converting JSON as String.
	public static String getJsonAsString(String url) throws Exception {

		Log.e("TAG", "URL Called : " + url);

		StringBuilder sb = new StringBuilder();
		HttpGet requst = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(requst);

		BufferedReader br = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		String s = "";
		while ((s = br.readLine()) != null) {
			sb.append(s);
			jsonResponceFromServer = sb.toString();
		}
		return jsonResponceFromServer;
	}

	// Using post method
	public static String SendHttpPost(String URL, JSONObject jsonObjSend) {

		String resultString = null;
		try {

			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPostRequest = new HttpPost(URL);

			StringEntity se;
			se = new StringEntity(jsonObjSend.toString());

			/*if (Utilities.sessionCookie != null) {
				Log.e("TAG", "Setting Cookie: " + Utilities.sessionCookie);
				httpPostRequest.setHeader("Cookie", Utilities.sessionCookie);
			} else {
				Log.e("TAG", "Null session request get()");
			}*/

			// Set HTTP parameters
			httpPostRequest.setEntity(se);
			httpPostRequest.setHeader("Accept", "application/json");
			httpPostRequest.setHeader("Content-type", "application/json");
			// only set this parameter if you would like to use gzip compression
			httpPostRequest.setHeader("Accept-Encoding", "gzip");
			long t = System.currentTimeMillis();
			HttpResponse response = (HttpResponse) httpclient
					.execute(httpPostRequest);
			Log.i("TAG",
					"HTTPResponse received in ["
							+ (System.currentTimeMillis() - t) + "ms]");

			// Get hold of the response entity (-> the data):
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// Read the content stream
				InputStream instream = entity.getContent();
				Header contentEncoding = response
						.getFirstHeader("Content-Encoding");
				if (contentEncoding != null
						&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
					instream = new GZIPInputStream(instream);
				}
				// convert content stream to a String
				resultString = convertStreamToString(instream);
				instream.close();
			}

		} catch (Exception e) {
			// More about HTTP exception handling in another tutorial.
			// For now we just print the stack trace.
			e.printStackTrace();
		}
		return resultString;
	}

	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");

				Utilities.data_converted_tostring = sb.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	// This function is used for login
	// This function returns succes or failure message
	public static ArrayList<Login_GS> getLoginFromJson(String logindata) {
		String failureMessage;
		boolean isSuccessful = false;
		ArrayList<Login_GS> arrayListLogingettersetter = new ArrayList<Login_GS>();
		try {
			JSONObject jObject;
			JSONArray jsonArray = new JSONArray();

			Object json = new JSONTokener(logindata).nextValue();
			if (json instanceof JSONObject) {
				{
					jObject = (JSONObject) json;

					if (jObject.has(MSG_RESPONSE)) {

						loginValue = jObject.getString(MSG_RESPONSE).toString();

						if (loginValue.equals("success")) {
							System.out.println("Login =Success");

							Login_GS loginGetterSetter = new Login_GS();

							if (jObject.has(DATA)) {

								jsonArray = jObject.getJSONArray(DATA);

								for (int i = 0; i < jsonArray.length(); i++) {
									
									//loginGetterSetter.setFailure(loginValue);

									loginGetterSetter.setId(jsonArray
											.getJSONObject(i).getString("id")
											.toString());

									loginGetterSetter.setUserName(jsonArray
											.getJSONObject(i)
											.getString("username").toString());
									
									loginGetterSetter.setDisplay_name(jsonArray
											.getJSONObject(i)
											.getString("display_name").toString());

									arrayListLogingettersetter
											.add(loginGetterSetter);

								}
								if (arrayListLogingettersetter != null
										&& arrayListLogingettersetter.size() > 0) {
									isSuccessful = true;
								}
							} // if ends data obj not available
						}// if End Response unsuccessful

						else {

							JSONObject jObjectFailure = new JSONObject(
									logindata);

							failureMessage = jObjectFailure.getString(KEY_MSG)
									.toString();
							// This is to check the failure
							Login_GS loginGetterSetter = new Login_GS();
							loginGetterSetter.setFailure(failureMessage);
							arrayListLogingettersetter.add(loginGetterSetter);

							isSuccessful = false;
						}

					} // If End No Response tag

				}// if end for JsonObject or not
			}
		} catch (JSONException e) {
			isSuccessful = false;
			e.printStackTrace();
		}
		if (isSuccessful) {
			return arrayListLogingettersetter;
		}
		return arrayListLogingettersetter;
	}

	/*public static ArrayList<Login_GS> getRegistrationResponse(
			String registerData) {
		String failureMessage;
		boolean isSuccessful = false;
		ArrayList<Login_GS> arrayListLogingettersetter = new ArrayList<Login_GS>();
		try {
			JSONObject jObject;
			JSONArray jsonArray = new JSONArray();

			Object json = new JSONTokener(registerData).nextValue();
			if (json instanceof JSONObject) {
				{
					jObject = (JSONObject) json;

					if (jObject.has(MSG_RESPONSE)) {

						loginValue = jObject.getString(MSG_RESPONSE).toString();

						if (loginValue.equals("success")) {
							System.out.println("Login =Success");

							Login_GS loginGetterSetter = new Login_GS();

							if (jObject.has(DATA)) {

								jsonArray = jObject.getJSONArray(DATA);

								for (int i = 0; i < jsonArray.length(); i++) {

									loginGetterSetter.setFailure(loginValue);

									loginGetterSetter
											.setUserId(Integer
													.parseInt(jsonArray
															.getJSONObject(i)
															.getString("id")
															.toString()));
									loginGetterSetter.setUserName(jsonArray
											.getJSONObject(i).getString("name")
											.toString());

									loginGetterSetter.setUserEmail(jsonArray
											.getJSONObject(i)
											.getString("email").toString());

									arrayListLogingettersetter
											.add(loginGetterSetter);

								}
								if (arrayListLogingettersetter != null
										&& arrayListLogingettersetter.size() > 0) {
									isSuccessful = true;
								}
							} // if ends data obj not available
						}// if End Response unsuccessful

						else {

							JSONObject jObjectFailure = new JSONObject(
									registerData);

							failureMessage = jObjectFailure.getString(KEY_MSG)
									.toString();
							// This is to check the failure
							Login_GS loginGetterSetter = new Login_GS();
							loginGetterSetter.setFailure(failureMessage);
							arrayListLogingettersetter.add(loginGetterSetter);

							isSuccessful = false;
						}
					} // If End No Response tag

				}// if end for JsonObject or not
			}
		} catch (JSONException e) {
			isSuccessful = false;
			e.printStackTrace();
		}
		if (isSuccessful) {
			return arrayListLogingettersetter;
		}
		return arrayListLogingettersetter;

	}*/
}
