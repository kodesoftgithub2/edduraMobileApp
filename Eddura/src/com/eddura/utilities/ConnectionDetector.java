package com.eddura.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Function to check either mobile or wifi network is available or not.
 * 
 * @param context
 * @return true if either mobile or wifi network is available else it returns
 *         false.
 */
public class ConnectionDetector {

	public static boolean networkStatus(Context context) {

		return (ConnectionDetector.isWifiAvailable(context) || ConnectionDetector
				.isMobileNetworkAvailable(context));
	}

	public static boolean isMobileNetworkAvailable(Context ctx) {
		ConnectivityManager connecManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo myNetworkInfo = connecManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (myNetworkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isWifiAvailable(Context ctx) {
		ConnectivityManager myConnManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo myNetworkInfo = myConnManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (myNetworkInfo.isConnected())
			return true;
		else
			return false;
	}

	public static boolean isGpsEnabled(Context ctx) {
		LocationManager locationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
			return true;
		else
			return false;
	}

	public static void displayNoNetworkDialog(final Activity activity) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("Network connection failure")
				.setCancelable(false)
				.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								/*Intent intent = activity.getIntent();
								activity.finish();
								activity.startActivity(intent);*/
								dialog.cancel();
							}
						});
				/*.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								activity.finish();
							}
						});*/
				
		// builder.show();
		Dialog d = builder.show();
		/*new AppDialogColor(d);*/
					
	}

}
