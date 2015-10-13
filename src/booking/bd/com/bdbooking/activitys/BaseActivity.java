package booking.bd.com.bdbooking.activitys;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import booking.bd.com.bdbooking.BdApplication;
import booking.bd.com.bdbooking.Constants;
import booking.bd.com.bdbooking.OnHttpActionListener;
import booking.bd.com.bdbooking.bean.OrderTime;
import booking.bd.com.bdbooking.bean.User;
import booking.bd.com.bdbooking.json.DecodeResult;
import booking.bd.com.bdbooking.json.IDecodeJson;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.LogUtils;
import booking.bd.com.bdbooking.utils.NetWorkUtils;
import booking.bd.com.bdbooking.utils.PublicInterfaceFactory;
import booking.bd.com.bdbooking.utils.XutilHttpPack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends Activity implements OnHttpActionListener{
	
	protected BitmapUtils mBitmapUtils;

	protected Activity mContext;
	protected boolean isFirstLunch;
//	private SDKReceiver mReceiver;
	
	private View mOrderView;
	private ArrayList<OrderTime> mTimes;
	private ArrayList<String> mDrinks;
	
	protected String mPhoneNumber;
	
	private String timeBuilder = "";
	private CheckBox mChecked;
	private TextView time;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.mContext = this;
		ViewUtils.inject(this);
		BdApplication.getInstance().mActivityStack.push(this);
		isFirstLunch = true;
/*		mReceiver = new SDKReceiver();
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		registerReceiver(mReceiver, iFilter);*/
	}
/*
	public class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
//				showToastShort("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
//				showToastShort("网络出错");
			}
		}
	}*/
	
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
		if (!NetWorkUtils.isNetWorkAlive(this)) {
			showToastShort("网络异常请检查网络!");
			finish();
		}
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
//		unregisterReceiver(mReceiver);
		BdApplication.mApplication.mActivityStack.remove(this);
	}

	public void showOrder(){
		timeBuilder = "";
		configOrderView();
		AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
		.setView(mOrderView)
		.setPositiveButton("提交订单", null)
		.setNegativeButton("取消订单",null); 
		final AlertDialog dialog = builder.create();
		dialog.show();
		Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					mPhoneNumber = ((EditText)mOrderView.findViewById(R.id.et_phone)).getText().toString();
					if(TextUtils.isEmpty(mPhoneNumber)){
						mPhoneNumber = ((EditText)mOrderView.findViewById(R.id.et_phone)).getHint().toString();
					}
					String time = ((TextView)mOrderView.findViewById(R.id.tv_selected_time)).getText().toString();
					if(!mPhoneNumber.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")){
						errorMsg("请输入正确的电话号码");
					}else if(TextUtils.isEmpty(mUser.getSqmc())||
							TextUtils.isEmpty(mUser.getFdmc())||
							TextUtils.isEmpty(mUser.getDymc())||
							TextUtils.isEmpty(mUser.getMph())){
						errorMsg("请完善手机账号地址信息");
					}else if(TextUtils.isEmpty(time) || "请选择服务时间".equals(time.trim())){
						errorMsg("请选择预约时间");
					}else{
						dialog.dismiss();
						submitOrder();
					}
				}
		});
	}
	
	public abstract void submitOrder();
	
	public void setDrinkProduct(ArrayList<String> infos){
		mDrinks = infos;
	}
	
	private void configOrderView() {
		mOrderView = View.inflate(this, R.layout.bd_activity_check_booking, null);
		((EditText)mOrderView.findViewById(R.id.et_phone)).setHint(mUser.getSjhm());
		((TextView)mOrderView.findViewById(R.id.tv_address)).setText(mUser.getSqmc());
		((TextView)mOrderView.findViewById(R.id.tv_ldmph)).setText(mUser.getFdmc()+" "+mUser.getDymc()+" "+mUser.getMph());
		
		final TextView time1 = (TextView)mOrderView.findViewById(R.id.tv_time_1);
		final TextView time2 = (TextView)mOrderView.findViewById(R.id.tv_time_2);
		final TextView time3 = (TextView)mOrderView.findViewById(R.id.tv_time_3);
		final TextView time4 = (TextView)mOrderView.findViewById(R.id.tv_time_4);
		final TextView time5 = (TextView)mOrderView.findViewById(R.id.tv_time_5);
		final TextView time6 = (TextView)mOrderView.findViewById(R.id.tv_time_6);
		setText(time1,time2,time3,time4,time5,time6);
		CheckBox mBox1 = (CheckBox) mOrderView.findViewById(R.id.cb_time_1);
		CheckBox mBox2 = (CheckBox) mOrderView.findViewById(R.id.cb_time_2);
		CheckBox mBox3 = (CheckBox) mOrderView.findViewById(R.id.cb_time_3);
		CheckBox mBox4 = (CheckBox) mOrderView.findViewById(R.id.cb_time_4);
		CheckBox mBox5 = (CheckBox) mOrderView.findViewById(R.id.cb_time_5);
		CheckBox mBox6 = (CheckBox) mOrderView.findViewById(R.id.cb_time_6);
		
		time = (TextView) mOrderView.findViewById(R.id.tv_selected_time);
		
		mBox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				configTime(time1.getText(), isChecked,buttonView);
			}
		});
		mBox2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				configTime(time2.getText(), isChecked,buttonView);
			}
		});
		mBox3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				configTime(time3.getText(), isChecked,buttonView);
			}
		});
		mBox4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				configTime(time4.getText(), isChecked,buttonView);
			}
		});
		mBox5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				configTime(time5.getText(), isChecked,buttonView);	
			}
		});
		mBox6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				configTime(time6.getText(), isChecked,buttonView);		
			}
		});
		
		TextView drink = (TextView)mOrderView.findViewById(R.id.tv_services);
		View services = mOrderView.findViewById(R.id.fl_services);
		if(mDrinks != null && mDrinks.size()>0){
			String drinkString = "";
			for (int i = 0; i <mDrinks.size(); i++) {
				if(i==0){
					drinkString = mDrinks.get(i);
				}else{
					drinkString = drinkString+ "\n"+mDrinks.get(i);
				}
			}
			drink.setText(drinkString);
			services.setVisibility(View.VISIBLE);
		}else{
			services.setVisibility(View.GONE);
		}
	}
	
	public void configTime(CharSequence select,boolean isChecked ,CompoundButton buttonView){
	/*	if(isChecked){
			if("请选择服务时间".equals(timeBuilder)){
				timeBuilder = "";
			}
			timeBuilder = timeBuilder + select+"  ";
		}else{
			timeBuilder = timeBuilder.replace(select+"  ", "");
		}
		if(TextUtils.isEmpty(timeBuilder)){
			timeBuilder = "请选择服务时间";
		}*/
		CheckBox check = (CheckBox) buttonView;
		if(isChecked){
			if(mChecked != null && mChecked != check){
				mChecked.setChecked(false);
			}
			timeBuilder = select+"";
			mChecked = check;
		}else{
			timeBuilder = "请选择服务时间";
		}
		time.setText(timeBuilder);
	}
	
	private void setText(TextView time1, TextView time2, TextView time3,
			TextView time4,TextView time5,TextView time6) {
		if(mTimes == null || mTimes.size()==0) 
			return;
		try {
			time1.setText(mTimes.get(0).getShowTime());
		} catch (IndexOutOfBoundsException e) {
			((View) time1.getParent()).setVisibility(View.GONE);
		}
		try {
			time2.setText(mTimes.get(1).getShowTime());
		} catch (IndexOutOfBoundsException e) {
			((View) time2.getParent()).setVisibility(View.GONE);
		}
		try {
			time3.setText(mTimes.get(2).getShowTime());
		} catch (IndexOutOfBoundsException e) {
			((View) time3.getParent()).setVisibility(View.GONE);
		}
		try {
			time4.setText(mTimes.get(3).getShowTime());
		} catch (IndexOutOfBoundsException e) {
			((View) time4.getParent()).setVisibility(View.GONE);
		}
		try {
			time5.setText(mTimes.get(4).getShowTime());
		} catch (IndexOutOfBoundsException e) {
			((View) time5.getParent()).setVisibility(View.GONE);
		}
		try {
			time6.setText(mTimes.get(5).getShowTime());
		} catch (IndexOutOfBoundsException e) {
			((View) time6.getParent()).setVisibility(View.GONE);
		}
		
	}

	public void loadTimeByWorkerId(String ryid){
		String timeUri = Constants.BD_SERVER_BASE_URI + "/" +Constants.GETRYYYSJ + ryid;
		sendData(/*new BasicNameValuePair("ryid", ryid)*/XutilHttpPack.DEFAULT_EMPTY_NAME_VALUE, timeUri, new OnHttpActionListener() {
			
			@Override
			public void onHttpError(Exception e, String msg, int requestCode) {
				showToastShort("服务异常，请检查网络后重试");
			}
			
			@Override
			public void onDecoded(String reason, boolean isSuccess,
					JsonObject mJsonResult, JsonArray mLists, int resultCode) {
				if(isSuccess){
					mTimes = PublicInterfaceFactory.getTimesFromJson(mLists);
					showOrder();
				}else{
					errorMsg(reason);
				}
			}
		}, Constants.REQUEST_CODE_GETRYYYSJ);
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

	protected User mUser;
	
	protected User getUser(){
		if(mUser != null){
			return mUser;
		}
		return BdApplication.getInstance().getUser();
	}
	
/*	protected ArrayList<Order> getFinishedOrder(){
		if(mUser != null){
			return mUser.getWcdd();
		}
		return null;
	}
	
	protected ArrayList<Order> getUnFinishedOrder(){
		if(mUser != null){
			return mUser.getWwcdd();
		}
		return null;
	}*/
	
	public interface loginListener{
		void loginSuccess();
		void loginFailed(String msg);
	} 
	
	protected void errorMsg(String msg) {
		errorMsg(false,msg);
	}
	
	protected void errorMsg(final boolean isOrderForm,String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
		.setTitle("提示")
		.setMessage(msg)
		.setNegativeButton(android.R.string.ok,new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(isOrderForm){
					finish();
				}
			}
		}); 
		builder.create().show();
	}
	
	public void loginBox(final loginListener listener){
		loginBox(listener,false);
	}
	
	public void loginBox(final loginListener listener,final boolean isOrderForm){
		String id = BdApplication.getInstance().getLocalMacAddress();
		if(!TextUtils.isEmpty(id)){
			sendData(new BasicNameValuePair("hzid", id/*"24:82:8b:00:85:2e"*/),Constants.BD_BOX_LOGIN_URI,new OnHttpActionListener(){
				@Override
				public void onHttpError(Exception e, String msg, int requestCode) {
					listener.loginFailed(msg);
					showToastShort("服务异常，请检查网络后重试");
				}

				@Override
				public void onDecoded(String reason, boolean isSuccess,
						JsonObject mJsonResult, JsonArray mLists, int resultCode) {
					if(isSuccess){
						mUser = PublicInterfaceFactory.getUserInfoFromJson(mJsonResult);
						if(null != mUser){
							BdApplication.mApplication.setUser(mUser);
							listener.loginSuccess();
						}
					}else{
						errorMsg(isOrderForm,reason);
					}
				}
			},Constants.REQUEST_CODE_BOXLOGIN);
		}
	}

	
	public abstract void loadData();
   //发送数据的方法
	public void sendData(NameValuePair data, String url,
						 final OnHttpActionListener mTatget, final int code) {

		mHttpHandler = BdApplication.getInstance().mHttpPack.sendData(data,
				url, new XutilHttpPack.OnHttpActionCallBack() {

					@Override
					public void onHttpSuccess(String result) {
						LogUtils.d(XutilHttpPack.HTTP_TAG, "onHttpSuccess :: ");
						DecodeResult.decoResult(result, new IDecodeJson() {

							@Override
							public void onDecoded(String reason,
												  boolean isSuccess, JsonObject mJsonResult,
												  JsonArray mLists) {
								LogUtils.d(XutilHttpPack.HTTP_TAG, "onDecoded :: ");
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
    
    public String getSelectedTimeId(){
    	ArrayList<OrderTime> times = getSelectedTime();
    	StringBuilder sb = new StringBuilder();
    	for (int i=0; i<times.size(); i++) {
    		if(i==0){
    			sb.append(times.get(i).getId());
    		}else{
    			sb.append("#"+times.get(i).getId());
    		}
		}
    	return sb.toString();
    }
    
    public String buildSelectedTime(){
    	ArrayList<OrderTime> times = getSelectedTime();
    	if(null == times || times.size()==0){
    		errorMsg("请选择预约时间");
    	}
    	StringBuilder sb = new StringBuilder();
    	for (OrderTime orderTime : times) {
    		
		}
    	return null;
    }
    
    public String getOrderBeginTime(){
    	String timeStr = time.getText().toString();
    	String[] split = timeStr.split("-");
    	if(null == split || split.length<2){
    		errorMsg("时间格式不正确");
    		return null;
    	}
		return split[0].trim();
    }
    
    public String getOrderEndTime(){
    	String timeStr = time.getText().toString();
    	String[] split = timeStr.split("-");
    	if(null == split || split.length<2){
    		errorMsg("时间格式不正确");
    		return null;
    	}
		return split[1].trim();
      	
      }
    
    public ArrayList<OrderTime> getSelectedTime(){
    	ArrayList<OrderTime> selectedTime = new ArrayList<OrderTime>();
    	if(mTimes == null || mTimes.size() == 0){
    		return null;
    	}
    	CheckBox cb1 = (CheckBox) mOrderView.findViewById(R.id.cb_time_1);
    	if(cb1.isChecked() && mTimes.size()>0){
    		selectedTime.add(mTimes.get(0));
    	}
    	CheckBox cb2 = (CheckBox) mOrderView.findViewById(R.id.cb_time_2);
    	if(cb2.isChecked() && mTimes.size()>1){
    		selectedTime.add(mTimes.get(1));
    	}
    	CheckBox cb3 = (CheckBox) mOrderView.findViewById(R.id.cb_time_3);
    	if(cb3.isChecked() && mTimes.size()>2){
    		selectedTime.add(mTimes.get(2));
    	}
    	CheckBox cb4 = (CheckBox) mOrderView.findViewById(R.id.cb_time_4);
    	if(cb4.isChecked() && mTimes.size()>3){
    		selectedTime.add(mTimes.get(3));
    	}
    	CheckBox cb5 = (CheckBox) mOrderView.findViewById(R.id.cb_time_5);
    	if(cb5.isChecked() && mTimes.size()>4){
    		selectedTime.add(mTimes.get(4));
    	}
    	CheckBox cb6 = (CheckBox) mOrderView.findViewById(R.id.cb_time_6);
    	if(cb6.isChecked() && mTimes.size()>5){
    		selectedTime.add(mTimes.get(5));
    	}
    	return selectedTime;
    }
}
