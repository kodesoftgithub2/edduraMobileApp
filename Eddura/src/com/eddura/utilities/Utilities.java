package com.eddura.utilities;


public final class Utilities {

	

	public static boolean isLoginSession;

	public static String sessionCookie = "123456";

	public static String KEYWORD = "";

	public static String loginState = "firstTime";

	public static String data_converted_tostring = "";

	public static String KEY_USERNAME = "username";

	public static String KEY_USERID = "userId";

	public static String KEY_EMAIL = "email";

	public static String KEY_REGISTER_ID = "regno";
	
	/*public static GoogleApiClient mGoogleApiClient;*/
	
	
	// Google project id
	/*static final public String SENDER_ID = "405567498189";*/
	

	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}

}
