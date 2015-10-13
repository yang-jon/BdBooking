package booking.bd.com.bdbooking;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import booking.bd.com.bdbooking.bean.User;
import booking.bd.com.bdbooking.utils.JsonUtil;
import booking.bd.com.bdbooking.utils.LogUtils;
import booking.bd.com.bdbooking.utils.XutilHttpPack;

import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


/**
 * Created by youyou on 2015/6/8.
 */
public class BdApplication extends Application{
    public  AppActivityStack mActivityStack;
    public static final String TAG = "BdApplication";

    public XutilHttpPack mHttpPack;
    
    public User mUser;
    
    public User getUser() {
		return mUser;
	}

	public void setUser(User user) {
		this.mUser = user;
	}

	@Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mActivityStack = new AppActivityStack();
        SDKInitializer.initialize(this);
        mHttpPack = new XutilHttpPack();
        CrashHandler.getInstance().init(this);
    }
    public static BdApplication mApplication=null;

    public static BdApplication getInstance(){
        return mApplication;
    }

    public String getLocalMacAddress() {
		String Mac=null;
		try{
			
			String path="sys/class/net/wlan0/address";
			if((new File(path)).exists())
			{
	        	FileInputStream fis = new FileInputStream(path);
				byte[] buffer = new byte[8192];
		        int byteCount = fis.read(buffer);
		        if(byteCount>0)
		        {
		        	Mac = new String(buffer, 0, byteCount, "utf-8");
		        }
			}
	        if(Mac==null||Mac.length()==0)
	        {
	        	path="sys/class/net/eth0/address";
				FileInputStream fis_name = new FileInputStream(path);
				byte[] buffer_name = new byte[8192];
		        int byteCount_name = fis_name.read(buffer_name);
		        if(byteCount_name>0)
		        {
		        	Mac = new String(buffer_name, 0, byteCount_name, "utf-8");
		        }
	        }
	        if(Mac.length()==0||Mac==null){
	        	return "";
	        }
		}catch(Exception io){
			}
		if(Mac.length()==0||Mac==null){
        	return "";
        }
		return Mac.trim();
	}
    
    public String getBoxId(){
    	 TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
         return tm.getDeviceId();
    }
    
    public void destroySystem() {
        try {
            isBaiduServiceRunningKill();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Activity mActivity : mActivityStack) {
            if (mActivity != null && !mActivity.isFinishing()) {
                mActivity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    // 检查服务运行状态
    private void isBaiduServiceRunningKill() throws Exception {
        android.os.Process.killProcess(getProcessPid(getPackageName()
                + ":remote"));
    }
    public int getProcessPid(String processName) {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procList = null;
        int result = -1;
        procList = activityManager.getRunningAppProcesses();
        for (Iterator<ActivityManager.RunningAppProcessInfo> iterator = procList.iterator(); iterator
                .hasNext();) {
            ActivityManager.RunningAppProcessInfo procInfo = iterator.next();
            if (procInfo.processName.equals(processName)) {
                result = procInfo.pid;
                break;
            }
        }
        return result;
    }
    
    public SharedPreferences getOrderSharedPreferences(){
    	if(!getPackageName().startsWith("booking.bd.com.bdbooking")){
    		return null;
    	}
    	if("booking.bd.com.bdbooking.order".equals(getPackageName())){
    		return getSharedPreferences("share_order_info", Context.MODE_WORLD_WRITEABLE);
    	}
    	try {
			Context orderContext = createPackageContext( "booking.bd.com.bdbooking.order" , Context.CONTEXT_IGNORE_SECURITY);
			return orderContext.getSharedPreferences("share_order_info", Context.MODE_WORLD_WRITEABLE);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		} 
    }
}
