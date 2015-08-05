package booking.bd.com.bdbooking;

public class Constants {
	
	
	public static final int DEFAULT_COMPANYS_PER_PAGE = 15;
	
	public static final String BD_BASE_IP_PORT = "owen0018.6655.la:20000";
	public static final String BD_SERVER_BASE_URI = "http://"+BD_BASE_IP_PORT+"/bmfw/services/bmfw?wsdl";
	
	public static final String GETJZWFPROJECT = "getJzwfProject";
	public static final String GETJZFWGSALL = "getJzfwGSAll";
	public static final String GETJZFWGSBYFWLB = "getJzfwGSByFwlb";
	public static final String GETFWPROJECTBYGSXXID = "getFwProjectByGsxxid";
	public static final String GETJZFWRYXXBYGSID = "getJzfwryxxByGsID";
	
	public static final String GETWXFWPROJECT = "getWxfwProject";
	public static final String GETWXFWGSALL = "getWxfwGSAll";
	public static final String GETWXFWGSBYFWLB = "getWxfwGSByFwlb";
	public static final String GETWXFWRYXXBYGSID = "getWxfwryxxByGsID";
	
	public static final String GETSSFWPROJECT = "getSsfwProject";
	public static final String GETSSFWGSALL = "getSsfwGsAll.action?currentPageNum=&pageSize=";
	public static final String GETSSFWGSBYFWLB = "getSsfwGSByFwlb";
	public static final String GETSSFWRYXXBYGSID = "getSsfwryxxByGsID";
	
	public static final int REQUEST_CODE_GETJZWFPROJECT = 1001;
	public static final int REQUEST_CODE_GETJZFWGSALL = 1002;
	public static final int REQUEST_CODE_GETJZFWGSBYFWLB = 1003;
	public static final int REQUEST_CODE_GETFWPROJECTBYGSXXID = 1004;
	public static final int REQUEST_CODE_GETJZFWRYXXBYGSID = 1005;
	
	public static final int REQUEST_CODE_GETWXFWPROJECT = 2001;
	public static final int REQUEST_CODE_GETWXFWGSALL = 2002;
	public static final int REQUEST_CODE_GETWXFWGSBYFWLB = 2003;
	public static final int REQUEST_CODE_GETWXFWRYXXBYGSID = 2004;
	
	public static final int REQUEST_CODE_GETSSFWPROJECT = 3001;
	public static final int REQUEST_CODE_GETSSFWGSALL = 3002;
	public static final int REQUEST_CODE_GETSSFWGSBYFWLB = 3003;
	public static final int REQUEST_CODE_GETSSFWRYXXBYGSID = 3004;
}
