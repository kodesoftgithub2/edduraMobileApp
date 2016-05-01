package com.education.eddura;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.eddura.utilities.Constants;
import com.newrelic.agent.android.NewRelic;


public class SplashActivity extends Activity {

	// Other variable declaration
	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedpreferences;
		
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Remove the Title Bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(null);
		
		super.onCreate(savedInstanceState);			
		setContentView(R.layout.activity_splash);
	
		// Shared Preferences 
		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		final String user_id = sharedpreferences.getString(Constants.RESPONSE_ID, "");
		
		// New Relic Declaration Part
		NewRelic.withApplicationToken(
				"AAca7e8dff6240c720a325c64a9bc1cf12b4d08916"
				).start(this.getApplication());
		
		
		// Display Splash screen for 5 second and redirect to next screen
		Thread logoTimer = new Thread() {
			public void run() {
				try {
					int logoTimer = 0;
					while (logoTimer < 5000) {
						sleep(100);
						logoTimer = logoTimer + 100;
					};
					
					if (user_id == null || user_id.length() == 0) {
						startActivity(new Intent(SplashActivity.this,
								LoginActivity.class));
					} else {
						startActivity(new Intent(SplashActivity.this,
								MainActivity.class));
					}					
				}

				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				finally {
					finish();
				}
			}
		};

		logoTimer.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
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
