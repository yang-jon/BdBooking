package booking.bd.com.bdbooking;

import android.content.Context;
import android.util.Log;
import android.view.View;
import booking.bd.com.bdbooking.jz.R;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;

/**
 * Created by youyou on 2015/6/10.
 */
public class XutilImageLoader {
	/**
	 * 用于 imageloader 的异步加载 刚开始我们先配置好默认的显示数据
	 * */

	private final int DRAWABLE_LOADFILE = R.drawable.movie;
	private final int DRAWABLE_LOADING = R.drawable.movie;
	BitmapUtils mBitmapUtils;
	BitmapDisplayConfig mConfig;
	Context mContext;

	public XutilImageLoader(Context mContext) {
		this.mContext = mContext;
		init();
	}

	private void init() {
		mBitmapUtils = new BitmapUtils(mContext);
		mBitmapUtils.configDefaultBitmapMaxSize(
				mContext.getResources().getDimensionPixelSize(
						R.dimen.bd_default_bitmap_size_w),
				mContext.getResources().getDimensionPixelSize(
						R.dimen.bd_default_bitmap_size_h));
		mBitmapUtils.configDefaultLoadFailedImage(DRAWABLE_LOADFILE);
		mBitmapUtils.configDefaultLoadingImage(DRAWABLE_LOADING);
		mBitmapUtils.configDefaultShowOriginal(true);
	}

	public void setDefLoadFile(int resId){
		if(mBitmapUtils != null){
			mBitmapUtils.configDefaultLoadFailedImage(resId);
			mBitmapUtils.configDefaultLoadingImage(resId);
		}
	}
	
	public <T extends View> void display(T container, String uri) {
		Log.i("yangzheng", "http://"+Constants.BD_BASE_IP_PORT+"/ZHSQ/"+uri);
		mBitmapUtils.display(container, "http://"+Constants.BD_BASE_IP_PORT+"/ZHSQ/"+uri);
	}

	public <T extends View> void display(T container, String uri,
			BitmapDisplayConfig displayConfig) {
		mBitmapUtils.display(container, "http://"+Constants.BD_BASE_IP_PORT+"/ZHSQ/"+uri, displayConfig);
	}

	public <T extends View> void display(T container, String uri,
			BitmapLoadCallBack<T> callBack) {
		mBitmapUtils.display(container, "http://"+Constants.BD_BASE_IP_PORT+"/ZHSQ/"+uri, callBack);
	}

	public <T extends View> void display(T container, String uri,
			BitmapDisplayConfig displayConfig, BitmapLoadCallBack<T> callBack) {
		mBitmapUtils.display(container, "http://"+Constants.BD_BASE_IP_PORT+"/ZHSQ/"+uri, displayConfig, callBack);
	}
}
