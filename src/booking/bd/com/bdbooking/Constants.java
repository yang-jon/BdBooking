package booking.bd.com.bdbooking;

public class Constants {
	
	public static final int DEFAULT_COMPANYS_PER_PAGE = 10;
	public static final String DEFAULT_WORKER_SORT = "xm asc";
	
	public static final String BD_BASE_IP_PORT = "owen0018.6655.la:20000";
	public static final String BD_SERVER_BASE_URI = "http://"+BD_BASE_IP_PORT+"/ZHSQ/service/bmfw";
	public static final String BD_BOX_LOGIN_URI = "http://"+BD_BASE_IP_PORT+"/ZHSQ/service/zhwh/loginForBox.action";
	
	public static final String GETJZWFPROJECT = "getJzfwProject.action";
	public static final String GETJZFWGSALL = "getJzfwGSAll.action?currentPageNum=&pageSize=";
	public static final String GETJZFWGSBYFWLB = "getJzfwGSByFwlb.action?fwlb=&currentPageNum=&pageSize=";
	public static final String GETFWPROJECTBYGSXXID = "getFwProjectByGsxxid.action?gsxxid=";
	public static final String GETJZFWRYXXBYGSID = "getJzfwryxxByGsID.action?gsxxid=&px=&currentPageNum=&pageSize=";
	public static final String GETJZFWRYXXBYFWLBID = "getJzfwryxxByFwlbid.action";

	public static final String GETWXFWPROJECT = "getWxfwProject.action";
	public static final String GETWXFWGSALL = "getWxfwGSAll.action?currentPageNum=&pageSize=";
	public static final String GETWXFWGSBYFWLB = "getWxfwGSByFwlb.action?fwlb=&currentPageNum=&pageSize=";
	public static final String GETWXFWRYXXBYGSID = "getWxfwryxxByGsID.action?gsxxid=&px=&currentPageNum=&pageSize=";
	public static final String GETJGMESSBYGSID="getJGMessByGsid.do";
	
	public static final String GETSSFWPROJECT = "getSsfwProject.action";
	public static final String GETSSFWGSALL = "getSsfwGSAll.action?currentPageNum=&pageSize=";
	public static final String GETSSFWGSBYFWLB = "getSsfwGsByFwlb.action?fwlb=&currentPageNum=&pageSize=";
	public static final String GETSSFWRYXXBYGSID = "getSsfwryxxByGsID.action?gsxxid=&px=&currentPageNum=&pageSize=";
	public static final String GETSCPBYGSID = "getSppByGsID.action";
	public static final String GETCPXHBYPPID = "getCpxhByPpid.action";
	
	public static final String GETRYYYSJ = "getRyyysj.action?ryid=";
	public static final String GETBMFWPJBYRYID = "getBmfwpjByRyid.action";
	public static final String CANCELORDER = "cancelOrder.action";
	
	public static final String CREATEJZDDXX="createJzDdxx.action?";
	public static final String CREATEWXDDXX="createWxDdxx.action?";
	public static final String CREATESSDDXX="createSsDdxx.action";
	
	public static final String GETBMFWDDALLBYRYID="getBmfwddAllByRyID.action";
	public static final String GETWWCDDBYRYID="getWwcddByRyId.action";
	public static final String GETYWCDDBYRYID="getYwcddByRyId.action";
	
	public static final String URL_GETNOTICEBYXQID = "http://"+BD_BASE_IP_PORT+"/ZHSQ/service/sqxxgl/getNoticeByXqID.action";
	public static final String URL_GETNEWSBYXQID = "http://"+BD_BASE_IP_PORT+"/ZHSQ/service/sqxxgl/getNewsByXqID.action";
	
	public static final int REQUEST_CODE_GETJZFWPROJECT = 1001;
	public static final int REQUEST_CODE_GETJZFWGSALL = 1002;
	public static final int REQUEST_CODE_GETJZFWGSBYFWLB = 1003;
	public static final int REQUEST_CODE_GETFWPROJECTBYGSXXID = 1004;
	public static final int REQUEST_CODE_GETJZFWRYXXBYGSID = 1005;
	public static final int REQUEST_CODE_GETJZFWRYXXBYFWLBID= 1006;
	
	public static final int REQUEST_CODE_GETWXFWPROJECT = 2001;
	public static final int REQUEST_CODE_GETWXFWGSALL = 2002;
	public static final int REQUEST_CODE_GETWXFWGSBYFWLB = 2003;
	public static final int REQUEST_CODE_GETWXFWRYXXBYGSID = 2004;
	public static final int REQUEST_CODE_GETJGMESSBYGSID = 2005;
	
	public static final int REQUEST_CODE_GETSSFWPROJECT = 3001;
	public static final int REQUEST_CODE_GETSSFWGSALL = 3002;
	public static final int REQUEST_CODE_GETSSFWGSBYFWLB = 3003;
	public static final int REQUEST_CODE_GETSSFWRYXXBYGSID = 3004;
	public static final int REQUEST_CODE_GETSCPBYGSID = 3005;
	public static final int REQUEST_CODE_GETCPXHBYPPID = 3006;
	
	public static final int REQUEST_CODE_GETBMFWPJBYRYID = 4001;
	public static final int REQUEST_CODE_GETRYYYSJ = 4002;
	
	public static final int REQUEST_CODE_BOXLOGIN = 5001; 
	public static final int REQUEST_CODE_CANCELORDER = 5002;
	
	public static final int REQUEST_CODE_CREATEJZDDXX = 6001; 
	public static final int REQUEST_CODE_CREATEWXDDXX = 6002;
	public static final int REQUEST_CODE_CREATESSDDXX = 6003;
	
	public static final int REQUEST_CODE_GETBMFWDDALLBYRYID = 7001;
	public static final int REQUEST_CODE_GETWWCDDBYRYID = 7002;
	public static final int REQUEST_CODE_GETYWCDDBYRYID = 7003;
	
	public static final int REQUEST_CODE_GETNOTICEBYXQID = 8001;
	public static final int REQUEST_CODE_GETNEWSBYXQID = 8002;
}
