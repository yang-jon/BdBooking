package booking.bd.com.bdbooking.activitys;


import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.umeng.analytics.MobclickAgent;

import booking.bd.com.bdbooking.BdApplication;
import booking.bd.com.bdbooking.OnHttpActionListener;
import booking.bd.com.bdbooking.json.DecodeResult;
import booking.bd.com.bdbooking.json.IDecodeJson;
import booking.bd.com.bdbooking.utils.XutilHttpPack;

public abstract class BaseActivity extends Activity implements OnHttpActionListener{
	
	protected BitmapUtils mBitmapUtils;

	protected Activity mContext;
	protected boolean isFirstLunch;
	

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.mContext = this;
		ViewUtils.inject(this);
		BdApplication.getInstance().mActivityStack.push(this);
		isFirstLunch = true;
	}

	public String getResourceFromId(int id) {
		return getResources().getString(id);
	}

	public String getStringFormat(String format, String... args) {
		return format.format(format, args);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		if(isFirstLunch){
			loadData();
			isFirstLunch = false;
		}
		MobclickAgent.onResume(this);
	
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		BdApplication.mApplication.mActivityStack.remove(this);
		}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	HttpHandler<String> mHttpHandler;

	

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
	}
	public abstract void loadData();
   //发送数据的方法
	public void sendData(String data, String url,
						 final OnHttpActionListener mTatget, final int code) {

		mHttpHandler = BdApplication.getInstance().mHttpPack.sendData(data,
				url, new XutilHttpPack.OnHttpActionCallBack() {

					@Override
					public void onHttpSuccess(String result) {
						DecodeResult.decoResult(result, new IDecodeJson() {

							@Override
							public void onDecoded(String reason,
												  boolean isSuccess, JsonObject mJsonResult,
												  JsonArray mLists) {
								mTatget.onDecoded(reason, isSuccess,
										mJsonResult, mLists, code);
							}
						});
					}

					@Override
					public void onHttpError(HttpException e, String messge) {
						mTatget.onHttpError(e, messge, code);
					}
				});

	}

	public void sendData(List<NameValuePair> data, String url,
			final OnHttpActionListener mTatget, final int code) {

		mHttpHandler = BdApplication.getInstance().mHttpPack.sendData(data,
				url, new XutilHttpPack.OnHttpActionCallBack() {

					@Override
					public void onHttpSuccess(String result) {
						DecodeResult.decoResult(result, new IDecodeJson() {

							@Override
							public void onDecoded(String reason,
									boolean isSuccess, JsonObject mJsonResult,
									JsonArray mLists) {
								mTatget.onDecoded(reason, isSuccess,
										mJsonResult, mLists, code);
							}
						});
					}

					@Override
					public void onHttpError(HttpException e, String messge) {
						mTatget.onHttpError(e, messge, code);
					}
				});

	}
	
    public void showToastLong(String text){
    	Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
    
    public void showToastShort(String text){
    	Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
