package booking.bd.com.bdbooking;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.util.DisplayMetrics;
import booking.bd.com.bdbooking.utils.LogUtils;
import booking.bd.com.bdbooking.utils.XutilHttpPack;

import java.util.Iterator;
import java.util.List;

import com.baidu.mapapi.SDKInitializer;


/**
 * Created by youyou on 2015/6/8.
 */
public class BdApplication extends Application{
    public  AppActivityStack mActivityStack;
    public static final String TAG = "BdApplication";

    public XutilHttpPack mHttpPack;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mActivityStack = new AppActivityStack();
        //添加友盟的 5566cb6067e58ea209004115
       // public static void reportError(Context context, String error)
       // MobclickAgent
        SDKInitializer.initialize(this);
        LogUtils.i(TAG, "displayMetrics " + getResources().getDisplayMetrics());
        mHttpPack = new XutilHttpPack();
        CrashHandler.getInstance().init(this);

    }
    public static BdApplication mApplication=null;

    public static BdApplication getInstance(){
        return mApplication;
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

}
