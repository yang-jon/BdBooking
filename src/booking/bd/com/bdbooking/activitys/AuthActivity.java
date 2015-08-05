package booking.bd.com.bdbooking.activitys;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import booking.bd.com.bdbooking.BaiduMapDemo;
import booking.bd.com.bdbooking.Constants;
import booking.bd.com.bdbooking.R;
import booking.bd.com.bdbooking.Fragment.BookingFragment;
import booking.bd.com.bdbooking.adapter.CompanyListAdapter;
import booking.bd.com.bdbooking.bean.CompanyInfo;
import booking.bd.com.bdbooking.utils.PublicInterfaceFactory;

import com.baidu.mapapi.SDKInitializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class AuthActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = "AuthActivity";
	private Button mSubmitBookBtn;
	private Button mDefSortBtn;
	private Button mStarSortBtn;
	private Button mPriceSortBtn;
	private TextView mNurse;
	private TextView mHouseKeeping;
	private TextView mProcte;
	private TextView mHourly;
	private TextView mTitle;
	private ListView mCompanyList;

	private BDService mCurSer;
	private BookingFragment mBookingFragment = new BookingFragment();
	private CompanyListAdapter mAdapter;
	private ArrayList<CompanyInfo> mCompanyInfos;
	private SDKReceiver mReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bd_activity_auth);
		initViews();
		setClick();
		mReceiver = new SDKReceiver();
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		registerReceiver(mReceiver, iFilter);
	}

	public class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				showToastShort("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				showToastShort("网络出错");
			}
		}
	}

	private void setClick() {
		mSubmitBookBtn.setOnClickListener(this);
		mDefSortBtn.setOnClickListener(this);
		mStarSortBtn.setOnClickListener(this);
		mPriceSortBtn.setOnClickListener(this);
		mNurse.setOnClickListener(this);
		mHouseKeeping.setOnClickListener(this);
		mProcte.setOnClickListener(this);
		mHourly.setOnClickListener(this);
		mCompanyList.setOnItemClickListener(mAdapter);
	}

	private void initViews() {
		mSubmitBookBtn = (Button) findViewById(R.id.btn_submit);
		mDefSortBtn = (Button) findViewById(R.id.btn_sort_def);
		mStarSortBtn = (Button) findViewById(R.id.btn_sort_star);
		mPriceSortBtn = (Button) findViewById(R.id.btn_sort_price);
		mNurse = (TextView) findViewById(R.id.tv_nurse_container);
		mHouseKeeping = (TextView) findViewById(R.id.tv_housekeeping_container);
		mProcte = (TextView) findViewById(R.id.tv_procte_container);
		mHourly = (TextView) findViewById(R.id.tv_hourly_container);
		mTitle = (TextView) findViewById(R.id.title);
		mCompanyList = (ListView) findViewById(R.id.lv_company);
		mCompanyList.setAdapter(mAdapter = new CompanyListAdapter(this));
	}

	enum BDService {
		NURSE, HOUSEKEEPING, PROCTE, HOURLY;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			mBookingFragment.show(getFragmentManager(),
					BookingFragment.FRAGMENT_TAG);
			break;
		case R.id.tv_nurse_container:
			if (mCurSer == BDService.NURSE) {
				return;
			}
			updateMenu(v.getId());
			mCurSer = BDService.NURSE;
			break;
		case R.id.tv_housekeeping_container:
			if (mCurSer == BDService.HOUSEKEEPING) {
				return;
			}
			updateMenu(v.getId());
			mCurSer = BDService.HOUSEKEEPING;
			break;
		case R.id.tv_procte_container:
			if (mCurSer == BDService.PROCTE) {
				return;
			}
			updateMenu(v.getId());
			mCurSer = BDService.PROCTE;
			break;
		case R.id.tv_hourly_container:
			if (mCurSer == BDService.HOURLY) {
				return;
			}
			updateMenu(v.getId());
			mCurSer = BDService.HOURLY;
			break;
		case R.id.btn_sort_def:
			break;
		}
	}

	private void updateMenu(int resId) {
		TextView selectedView = (TextView) findViewById(resId);
		int selectedDrawResId;
		int defId;
		int defDrawResId;
		switch (getCurService()) {
			case NURSE:
				defId = R.id.tv_nurse_container;
				break;
			case HOUSEKEEPING:
				defId = R.id.tv_housekeeping_container;
				break;
			case PROCTE:
				defId = R.id.tv_procte_container;
				break;
			case HOURLY:
				defId = R.id.tv_hourly_container;
				break;
			default:
				throw new IllegalStateException("service is unsupport");
		}
		TextView defView = (TextView) findViewById(defId);
		switch (resId) {
			case R.id.tv_nurse_container:
				selectedDrawResId = R.drawable.bd_bmfw_selected;
				defDrawResId = R.drawable.bd_bmfw_default;
				break;
			case R.id.tv_housekeeping_container:
				selectedDrawResId = R.drawable.bd_jzfw_selected;
				defDrawResId = R.drawable.bd_jzfw_default;
				break;
			case R.id.tv_procte_container:
				selectedDrawResId = R.drawable.bd_bjfw_selected;
				defDrawResId = R.drawable.bd_bjfw_default;
				break;
			case R.id.tv_hourly_container:
				selectedDrawResId = R.drawable.bd_xsgfw_selected;
				defDrawResId = R.drawable.bd_xsgfw_default;
				break;
			default:
				throw new IllegalStateException("menu is unsupport");
		}
		selectedView.setCompoundDrawablesWithIntrinsicBounds(null,
				getResources().getDrawable(selectedDrawResId), null, null);
		defView.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(defDrawResId), null, null);
		mDefSortBtn.setNextFocusLeftId(resId);
		mCompanyList.setNextFocusLeftId(resId);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return super.dispatchKeyEvent(event);
	}

	private BDService getCurService() {
		return mCurSer;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isFirstLunch) {
			 setDefSelectedMemu();
			isFirstLunch = false;
		}
	}

	private void setDefSelectedMemu() {
		((TextView) findViewById(R.id.tv_nurse_container))
				.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
						.getDrawable(R.drawable.bd_bmfw_selected), null, null);
		updateMenu(R.id.tv_nurse_container);
		mCurSer = BDService.NURSE;
	}


	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	@Override
	public void onHttpError(Exception e, String msg, int requestCode) {
		showToastShort("http error");
	}

	@Override
	public void onDecoded(String reason, boolean isSuccess,
			JsonObject mJsonResult, JsonArray mLists, int resultCode) {
		mCompanyInfos = PublicInterfaceFactory
				.getJzCompanyInfoFromJson(mJsonResult);
		mAdapter.setData(mCompanyInfos);
	}

	@Override
	public void loadData() {
		String JzCompanyUri = Constants.BD_SERVER_BASE_URI + "/"
				+ Constants.GETJZFWGSBYFWLB;
		sendData("", JzCompanyUri, this, Constants.REQUEST_CODE_GETJZFWGSBYFWLB);
	}
}
