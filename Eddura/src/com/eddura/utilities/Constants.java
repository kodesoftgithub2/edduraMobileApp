package com.eddura.utilities;

public class Constants {
	
	public static final String NEW_RELIC_API = "AA6a3bec9e7676541372b8dce982cb31f43508aad7";
	
	/* 
	 1. Login
	 http://api.eddura.com/index.php/user/authenticate?college[domain]=demo&user[username]=mithun&user[password]=kodesoft
	
	 2. Dashboard Infographics (Department wise number of lectures conducted)
	 http://api.eddura.com/index.php/lectures/countByDepartments?college[domain]=demo&user[id]=1110

	 3. List of accessible department for the specific user
	 http://api.eddura.com/index.php/departments?college[domain]=demo&user[id]=1110
 
	 4. List of accessible classes for the specific user
	 http://api.eddura.com/index.php/classes?college[domain]=demo&user[id]=1110&department[id]=5

	 5. List of accessible subjects class id/ids
	 http://api.eddura.com/index.php/subjects/byClassIds?college[domain]=demo&user[id]=1110&class[id]=13
	*/
	
	public static final String BASE_URL = "http://api.eddura.com/index.php/";
	public static final String URL_LOGIN = "user/authenticate?";
	public static final String URL_DASHBOARD_INFOGRAPHICS = "lectures/countByDepartments?";
	public static final String URL_ACCESSIBLE_DEPT_SPECIFIC_USER = "departments?";
	public static final String URL_ACCESSIBLE_CLASSES_SPECIFIC_USER = "classes?";
	public static final String URL_ACCESSIBLE_SUBJECT_CLASS = "subjects/byClassIds?";

	public static final String KEY_COLLEGE_DOMAIN = "college[domain]";
    public static final String KEY_PASSWORD = "user[password]";
    public static final String KEY_USERNAME = "user[username]";
    public static final String KEY_DEVICE_ID = "device_id";
    public static final String KEY_USER_ID = "user[id]";
    public static final String KEY_DEPT_ID = "department[id]";
    public static final String KEY_CLASS_ID = "class[id]";

    public static final String RESPONSE_ID = "id";
    public static final String RESPONSE_DISPLAY_NAME = "display_name";
    public static final String RESPONSE_USERNAME = "username";
}
