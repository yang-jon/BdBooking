package booking.bd.com.bdbooking.activitys;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import booking.bd.com.bdbooking.Constants;
import booking.bd.com.bdbooking.activitys.BaseActivity.loginListener;
import booking.bd.com.bdbooking.adapter.OrderAdapter;
import booking.bd.com.bdbooking.adapter.OrderAdapter.CancelButtonClickListener;
import booking.bd.com.bdbooking.adapter.OrderAdapter.OrderType;
import booking.bd.com.bdbooking.bean.OrderV2;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.ui.HorizontalListView;
import booking.bd.com.bdbooking.utils.PublicInterfaceFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class OrderFormActivity extends BaseActivity implements loginListener, 
							CancelButtonClickListener, android.view.View.OnClickListener {
	
	private HorizontalListView mCompletedList;
	private HorizontalListView mUnCompletedList;
	private OrderAdapter mCompletedAdapter;
	private OrderAdapter mUnCompletedAdapter;
	
	private ArrayList<OrderV2> mWwcOrder = new ArrayList<>();
	private ArrayList<OrderV2> mYwcOrder = new ArrayList<>();
	
	private int mCurWwcPage = 1;
	private int mCurYwcPage = 1;
	
	private ImageView mWwcPre;
	private ImageView mWwcNext;
	private ImageView mYwcPre;
	private ImageView mYwcNext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bd_activity_order_form);
		mCompletedList  = (HorizontalListView) findViewById(R.id.hl_completed_book_list);
		mUnCompletedList = (HorizontalListView) findViewById(R.id.hl_uncompleted_book_list);
		
		mCompletedList.setAdapter(mCompletedAdapter = new OrderAdapter(this,OrderType.TYPE_COMPLETED));
		mUnCompletedList.setAdapter(mUnCompletedAdapter = new OrderAdapter(this,OrderType.TYPE_UNCOMPLETED));
		
		mUnCompletedAdapter.setCancelButtonClickListener(this);
		
		mWwcPre = (ImageView) findViewById(R.id.iv_pre_wwc_page);
		mWwcNext = (ImageView) findViewById(R.id.iv_next_wwc_page);
		mYwcPre = (ImageView) findViewById(R.id.iv_pre_ywc_page);
		mYwcNext = (ImageView) findViewById(R.id.iv_next_ywc_page);
		
		mWwcPre.setOnClickListener(this);
		mWwcNext.setOnClickListener(this);
		mYwcPre.setOnClickListener(this);
		mYwcNext.setOnClickListener(this);
		
	}

	@Override
	public void onHttpError(Exception e, String msg, int requestCode) {
		
	}

	@Override
	public void onDecoded(String reason, boolean isSuccess,
			JsonObject mJsonResult, JsonArray mLists, int resultCode) {
		if(!isSuccess){
			errorMsg(reason);
		}
		
		if(Constants.REQUEST_CODE_CANCELORDER == resultCode){
			showToastShort("订单取消成功");
			loadWwcOrderDef();
		}
		
		if(Constants.REQUEST_CODE_GETWWCDDBYRYID == resultCode){
			mWwcOrder = PublicInterfaceFactory.getOrderFromJson(mLists);
			/*mWwcOrder.clear();
			mWwcOrder.add(new OrderV2("1","1234","昨天12:00", "1111", "68.0元","13025441231", "加油", 2, null));
			mWwcOrder.add(new OrderV2("1","1234","昨天12:00", "1111", "68.0元","13025441231", "加油", 2, null));
			mWwcOrder.add(new OrderV2("1","1234","昨天12:00", "1111", "68.0元","13025441231", "加油", 2, null));
			mWwcOrder.add(new OrderV2("1","1234","昨天12:00", "1111", "68.0元","13025441231", "加油", 2, null));
			mWwcOrder.add(new OrderV2("1","1234","昨天12:00", "1111", "68.0元","13025441231", "加油", 2, null));*/
			if(mWwcOrder.size()==0){
				if(mCurWwcPage >1){
					mCurWwcPage--;
					showToastShort("已经是最后一页了");
				}
			}else{
				mUnCompletedAdapter.setData(mWwcOrder);
			}
			mWwcNext.setVisibility(mCurWwcPage==1&&mWwcOrder.size()==0?View.GONE:View.VISIBLE);
			mWwcPre.setVisibility(mCurWwcPage>1?View.VISIBLE:View.GONE);
		}
		
		if(Constants.REQUEST_CODE_GETYWCDDBYRYID == resultCode){
			mYwcOrder = PublicInterfaceFactory.getOrderFromJson(mLists);
		/*	mYwcOrder.clear();
			mYwcOrder.add(new OrderV2("1","1234","昨天12:00", "1111", "68.0元","13025441231", "加油", 2, null));
			mYwcOrder.add(new OrderV2("1","1234","昨天12:00", "1111", "68.0元","13025441231", "加油", 2, null));
			mYwcOrder.add(new OrderV2("1","1234","昨天12:00", "1111", "68.0元","13025441231", "加油", 2, null));
			mYwcOrder.add(new OrderV2("1","1234","昨天12:00", "1111", "68.0元","13025441231", "加油", 2, null));
			mYwcOrder.add(new OrderV2("1","1234","昨天12:00", "1111", "68.0元","13025441231", "加油", 2, null));*/
			if(mYwcOrder.size()==0){
				if(mCurYwcPage >1){
					mCurYwcPage--;
					showToastShort("已经是最后一页了");
				}
			}else{
				mCompletedAdapter.setData(mYwcOrder);
			}
			mYwcNext.setVisibility(mCurYwcPage==1&&mYwcOrder.size()==0?View.GONE:View.VISIBLE);
			mYwcPre.setVisibility(mCurYwcPage>1?View.VISIBLE:View.GONE);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		loginBox(this,true);
		super.onNewIntent(intent);
	}
	
	@Override
	public void loadData() {
		loginBox(this,true);
	}

	private void cancelOrder(String yhid,String ddbh){
		String url = Constants.BD_SERVER_BASE_URI + "/" +Constants.CANCELORDER;
		sendData(buildCancelOrderParam(yhid,ddbh), url, this, Constants.REQUEST_CODE_CANCELORDER);
	}
	
	private ArrayList<NameValuePair> buildCancelOrderParam(String yhid,String ddbh){
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("yhid", yhid));
		params.add(new BasicNameValuePair("ddbh", ddbh));
		return params;
	}
	
	private void loadWwcOrderByPageNumber(int currentPageNum){
		loadWwcOrder(getUser().getId(),currentPageNum,5);
	}
	
	private void loadYwcOrderByPageNumber(int currentPageNum){
		loadYwcOrder(getUser().getId(),currentPageNum,5);
	}
	
	private void loadWwcOrderDef(){
		loadWwcOrder(getUser().getId(),1,5);
	}
	
	private void loadYwcOrderDef(){
		loadYwcOrder(getUser().getId(),1,5);
	}
	
	private void loadWwcOrder(String yhid,int currentPageNum,int pageSize){
		String url = Constants.BD_SERVER_BASE_URI + "/" +Constants.GETWWCDDBYRYID;
		sendData(buildOrderParams(yhid, currentPageNum, pageSize), url, this, Constants.REQUEST_CODE_GETWWCDDBYRYID);
	}
	
	private void loadYwcOrder(String yhid,int currentPageNum,int pageSize){
		String url = Constants.BD_SERVER_BASE_URI + "/" +Constants.GETYWCDDBYRYID;
		sendData(buildOrderParams(yhid, currentPageNum, pageSize), url, this, Constants.REQUEST_CODE_GETYWCDDBYRYID);
	}
	
	private ArrayList<NameValuePair> buildOrderParams(String yhid,int currentPageNum,int pageSize){
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("yhid", yhid));
		params.add(new BasicNameValuePair("currentPageNum", currentPageNum+""));
		params.add(new BasicNameValuePair("pageSize", pageSize+""));
		return params;
	}
	
	@Override
	public void loginSuccess() {
		loadWwcOrderDef();
		loadYwcOrderDef();
	}

	@Override
	public void loginFailed(String msg) {
//		showToastShort(msg);
	}

	@Override
	public void cancel(final String ddbh) {
		final String yhid = getUser().getId();
		AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
		.setTitle("提醒")
		.setMessage("确定要取消该订单吗？")
		.setPositiveButton(android.R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				cancelOrder(yhid, ddbh);
			}
		}).setNegativeButton(android.R.string.cancel, null);
		builder.create().show();
	}

	@Override
	public void submitOrder() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_pre_wwc_page:
			if(mCurWwcPage == 1){
				showToastShort("已经是第一页了");
			}else{
				mCurWwcPage--;
				mWwcPre.setVisibility(mCurWwcPage>1?View.VISIBLE:View.GONE);
				loadWwcOrderByPageNumber(mCurWwcPage);
			}
			break;
		case R.id.iv_pre_ywc_page:
			if(mCurYwcPage == 1){
				showToastShort("已经是第一页了");
			}else{
				mCurYwcPage--;
				mYwcPre.setVisibility(mCurYwcPage>1?View.VISIBLE:View.GONE);
				loadYwcOrderByPageNumber(mCurYwcPage);
			}
			break;
		case R.id.iv_next_wwc_page:
			mCurWwcPage++;
//			mWwcPre.setVisibility(mCurWwcPage>1?View.VISIBLE:View.GONE);
			loadWwcOrderByPageNumber(mCurWwcPage);
			break;
		case R.id.iv_next_ywc_page:
			mCurYwcPage++;
//			mYwcPre.setVisibility(mCurYwcPage>1?View.VISIBLE:View.GONE);
			loadYwcOrderByPageNumber(mCurYwcPage);
			break;

		default:
			break;
		}
	}
}
