package booking.bd.com.bdbooking.activitys;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import booking.bd.com.bdbooking.Constants;
import booking.bd.com.bdbooking.Fragment.BookingFragment;
import booking.bd.com.bdbooking.activitys.BaseActivity.loginListener;
import booking.bd.com.bdbooking.adapter.CompanyListAdapter;
import booking.bd.com.bdbooking.adapter.CompanyListAdapter.SelectedItemChangeListener;
import booking.bd.com.bdbooking.adapter.ProMenuAdapter;
import booking.bd.com.bdbooking.adapter.WorkerListAdapter;
import booking.bd.com.bdbooking.bean.CompanyInfo;
import booking.bd.com.bdbooking.bean.JzProjectInfo;
import booking.bd.com.bdbooking.bean.User;
import booking.bd.com.bdbooking.bean.Worker;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.PublicInterfaceFactory;
import booking.bd.com.bdbooking.utils.XutilHttpPack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class RepairActivity extends BaseActivity implements OnClickListener, SelectedItemChangeListener, loginListener {
	
	private ListView mProMenu;
	private ListView mComList;
	private ArrayList<JzProjectInfo> mProInfos;
	private ProMenuAdapter mProAdapter;
	private JzProjectInfo mCurerntParentPro;
	private String mCurrentProId;
	private boolean isInSecendaryMenu = false;
	
	private ArrayList<CompanyInfo> mCompanys;
	
	private CompanyListAdapter mComAdapter;
	private BookingFragment mBookingFragment;
	private ArrayList<TextView> mSortViewList = new ArrayList<TextView>();
	
	private ListView mWorkerList;
	private ArrayList<Worker> mWorkers;
	private WorkerListAdapter mWorkerAdapter;
	
	private TextView mCurSortView;
	private Button mSubmitBookBtn;
	private TextView mDefSortBtn;
	private TextView mStarSortBtn;
	private TextView mPriceSortBtn;
	private View mSortDivider;
	private int mDividerMoveW;
	public static int sSortedColor = Color.parseColor("#F61313"); 
	public static int sUnSortedColor;
	private int mCurrentPage;
	private String mCurrentComId;
	private CompanyInfo mCurrentCom;
	private JzProjectInfo mCurerntPro;
	
	
	private Button mPrePage;
	private Button mNextPage;
	
	private TextView mJgmc;
	private WebView mWebView;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.bd_activity_repair);
		findViews();
		mBookingFragment = new BookingFragment();
		mCompanys = new ArrayList<>();
	}

	private void findViews() {
		
		mSubmitBookBtn = (Button) findViewById(R.id.btn_submit);
		mDefSortBtn = (TextView) findViewById(R.id.btn_sort_def);
		mStarSortBtn = (TextView) findViewById(R.id.btn_sort_star);
		mPriceSortBtn = (TextView) findViewById(R.id.btn_sort_price);
		sUnSortedColor = mStarSortBtn.getCurrentTextColor();
		mSortDivider = findViewById(R.id.select_divider);
		mJgmc = (TextView) findViewById(R.id.tv_jgmc);
		mWebView = (WebView) findViewById(R.id.wv_jgmc);
		
		mPrePage = (Button) findViewById(R.id.btn_pre_page);
		mNextPage = (Button) findViewById(R.id.btn_next_page);
		
		mProMenu = (ListView) findViewById(R.id.lv_wx_menu);
		mComList = (ListView) findViewById(R.id.lv_company);
		mWorkerList = (ListView) findViewById(R.id.lv_worker_selector);
		mWorkerAdapter = new WorkerListAdapter(this);
		mWorkerList.setAdapter(mWorkerAdapter);
		mWorkerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(mWorkerAdapter.getSelectPos() == position){
					mWorkerAdapter.setSelectPos(-1);
				}else{
					mWorkerAdapter.setSelectPos(position);
				}
			}
		});
		
		mProMenu.setAdapter(mProAdapter = new ProMenuAdapter(this));
		mProAdapter.setMenuForWx();
		mProMenu.setOnItemClickListener(new MenuItemClickListener());
		
		mComList.setAdapter(mComAdapter = new CompanyListAdapter(this));
		mComList.setOnItemClickListener(mComAdapter);
		mComAdapter.setSelectedItemChangeListener(this);
		
		mCurSortView = mDefSortBtn;
		mSortViewList.add(mDefSortBtn);
		mSortViewList.add(mStarSortBtn);
		mSortViewList.add(mPriceSortBtn);
		
		setClick();
	}
	private void setClick() {
		mPrePage.setOnClickListener(this);
		mNextPage.setOnClickListener(this);
		mSubmitBookBtn.setOnClickListener(this);
		mDefSortBtn.setOnClickListener(this);
		mStarSortBtn.setOnClickListener(this);
		mPriceSortBtn.setOnClickListener(this);
	}
	@Override
	public void onBackPressed() {
		if(isInSecendaryMenu){
			mProAdapter.setData(mProInfos);
			mProMenu.setSelection(mProInfos.indexOf(mCurerntParentPro));
			mCurerntPro = mCurerntParentPro;
			loadCompanyListByWxProId(mCurerntParentPro.getId());
			isInSecendaryMenu = false;
			return;
		}
		super.onBackPressed();
	}
	
	private void loadJgmcByGsid(String gsid){
		mCurrentComId = gsid;
		loadJgmc(mCurrentProId,gsid);
	}
	
	private void loadJgmc(String fwlbid,String gsid){
		String url = Constants.BD_SERVER_BASE_URI + "/" + Constants.GETJGMESSBYGSID;
		sendData(buildJgmcParams(fwlbid, gsid), url, this, Constants.REQUEST_CODE_GETJGMESSBYGSID);
	}
	
	private ArrayList<NameValuePair> buildJgmcParams(String fwlbid,String gsid){
		ArrayList<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("gsid", gsid));
		params.add(new BasicNameValuePair("fwlbid", fwlbid));
		return params;
	}
	
	private class MenuItemClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			JzProjectInfo menu = (JzProjectInfo)mProAdapter.getItem(position);
			if(menu.hasChildren()){
				mCurerntParentPro = menu;
				mProAdapter.setData(menu.configChildren());
				isInSecendaryMenu = true;
			}else{
			}
			mCurerntParentPro = menu;
			loadCompanyListByWxProId(menu.getId());
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_DOWN){
			if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN){
				if(sortBtnHasFocus()){
					if(mComList.getCount()>0){
						mComList.requestFocus();
						mComList.setSelection(0);
					}else{
						mPrePage.requestFocus();
					}
					return true;
				}
				if(mComList.hasFocus() && mComList.getSelectedItemPosition() == mComList.getCount()-1){
					mPrePage.requestFocus();
					return true;
				}
			}
		}
		return super.dispatchKeyEvent(event);
	}
	
	private boolean sortBtnHasFocus() {
		return mDefSortBtn.hasFocus() 
				|| mPriceSortBtn.hasFocus() || mStarSortBtn.hasFocus();
	}
	
	@Override
	public void onHttpError(Exception e, String msg, int requestCode) {
		
	}

	@Override
	public void onDecoded(String reason, boolean isSuccess,
			JsonObject mJsonResult, JsonArray mLists, int resultCode) {
		
		if(!isSuccess){
			errorMsg(reason);
			return;
		}
		
		if(Constants.REQUEST_CODE_CREATEWXDDXX == resultCode){
			errorMsg(PublicInterfaceFactory.getSuccessMsgFronJson(mJsonResult));
		}
		
		if(Constants.REQUEST_CODE_GETJGMESSBYGSID == resultCode){
			Log.i("yangzheng", Html.fromHtml(reason).toString());
//			mJgmc.setText(Html.fromHtml(reason));
//			mJgmc.setVisibility(View.VISIBLE);
//			mWebView.setVisibility(View.GONE);
			mWebView.getSettings().setJavaScriptEnabled(true);
//			mWebView.loadData(reason, "text/html","GBK");
			mWebView.loadDataWithBaseURL(null, reason, "text/html", "utf-8", null);
		}
		
		if(Constants.REQUEST_CODE_GETWXFWPROJECT == resultCode){
			mProInfos = PublicInterfaceFactory.getJzProjectFromJson(mLists);
			mProAdapter.setData(mProInfos);
			if(mProInfos.size()>0){
				loadDefCompanyList();
			}
		}
		if(Constants.REQUEST_CODE_GETWXFWGSBYFWLB == resultCode){
			mCompanys = PublicInterfaceFactory.getJzCompanyInfoFromJson(mLists);
			mComAdapter.setData(mCompanys);
			mCurrentPage =1;
			if(mCompanys.size() > 0){
//				loadWorkerListDef();
				mCurrentCom = mCompanys.get(0);
				mCurrentComId = mCurrentCom.getGsid();
				loadJgmc(mCurrentProId, mCompanys.get(0).getGsid());
			}
		}
		if(Constants.REQUEST_CODE_GETWXFWRYXXBYGSID == resultCode){
			mWorkers = PublicInterfaceFactory.getJzWorkerFromJson(mLists);
			mWorkerAdapter.setData(mWorkers);
		}
	}

	private void loadWorkerListDef(){
		String gsid = mCompanys.get(0).getGsid();
		loadWorkerListByGsid(gsid);
	}
	
	private void loadWorkerListByGsid(String gsid){
		mCurrentComId = gsid;
		loadWorkerList(gsid,"qbjg asc",1,Constants.DEFAULT_COMPANYS_PER_PAGE);
	}
	
	private void loadWorkerListByPageNum(int currentPageNum){
		loadWorkerList(mCurrentComId, "qbjg asc",currentPageNum, Constants.DEFAULT_COMPANYS_PER_PAGE);
	}
	
	private void loadWorkerList(String gsid,String px,int currentPageNum,int pageSize){
		String url = Constants.BD_SERVER_BASE_URI + "/" + Constants.GETWXFWGSBYFWLB;
		sendData(buildWorkerParam(gsid, px, currentPageNum, pageSize), url, this, Constants.REQUEST_CODE_GETWXFWRYXXBYGSID);
	}
	
	private ArrayList<NameValuePair> buildWorkerParams(String gsid,String px,int currentPageNum,int pageSize){
		ArrayList<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("gsid", gsid));
		params.add(new BasicNameValuePair("currentPageNum",currentPageNum+""));
		params.add(new BasicNameValuePair("pageSize", pageSize+""));
		params.add(new BasicNameValuePair("px", px));
		return params;
	}
	
	private void loadDefCompanyList() {
		mCurerntPro = mProInfos.get(0);
		loadCompanyListByWxProId(mCurerntPro.getId());
	}
	
	private void loadCompanyListSort(String px) {
		loadCompanyList(mCurrentProId,1,Constants.DEFAULT_COMPANYS_PER_PAGE,px);
	}
	
	private void loadCompanyListByWxProId(String id){
		mCurrentProId = id;
		loadCompanyList(id,1,Constants.DEFAULT_COMPANYS_PER_PAGE,"");
	}
	
	private void loadCompanyListByWxPageNumber(int curPage){
		loadCompanyList(mCurrentProId,curPage,Constants.DEFAULT_COMPANYS_PER_PAGE,"");
	}

	private void loadCompanyList(String id,int curPage,int pageSize,String px){
		String wxCompanyUri = Constants.BD_SERVER_BASE_URI + "/"
				+ Constants.GETWXFWGSBYFWLB;
		sendData(buildCompanyParam(id, curPage, pageSize,px), wxCompanyUri, this, Constants.REQUEST_CODE_GETWXFWGSBYFWLB);
	}
	
	@Override
	public void loadData() {
		String wxProUri = Constants.BD_SERVER_BASE_URI + "/"
				+ Constants.GETWXFWPROJECT;
		sendData(XutilHttpPack.DEFAULT_EMPTY_NAME_VALUE, wxProUri, this, Constants.REQUEST_CODE_GETWXFWPROJECT);
	}
	
	private ArrayList<NameValuePair> buildCompanyParam(String id,int curPage,int pageSize,String px) {
		ArrayList<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("fwlbid", id));
		params.add(new BasicNameValuePair("currentPageNum",curPage+""));
		params.add(new BasicNameValuePair("pageSize", pageSize+""));
		if(!TextUtils.isEmpty(px)){
			params.add(new BasicNameValuePair("px", px));
		}
		return params;
	}
	
	private ArrayList<NameValuePair> buildWorkerParam(String id,String px,int curPage,int pageSize) {
		ArrayList<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("gsxxid", id));
		params.add(new BasicNameValuePair("px",px));
		params.add(new BasicNameValuePair("currentPageNum",curPage+""));
		params.add(new BasicNameValuePair("pageSize", pageSize+""));
		return params;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			loginBox(this);
		/*	if(mWorkerAdapter.getSelectPos() == -1){
				AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
				.setTitle("提示")
				.setMessage("请选择预约人员")
				.setPositiveButton(android.R.string.ok, null);
				builder.create().show();
			}else{
				showOrder();
			}*/
			break;
		case R.id.btn_sort_def:
		case R.id.btn_sort_price:
		case R.id.btn_sort_star:
			updateSort(v);
			loadCompanyListSort(getCurrentSortString());
			break;
		case R.id.btn_pre_page:
			if(mCurrentPage == 1){
				showToastShort("已经是第一页了");
			}else{
				mCurrentPage--;
				loadCompanyListByWxPageNumber(mCurrentPage);
			}
			break;
		case R.id.btn_next_page:
			if(mCompanys.size() <10){
				showToastShort("已经是最后一页了");
			}else{
				mCurrentPage++;
				loadCompanyListByWxPageNumber(mCurrentPage);
			}
			break;
			default:
				break;
		}
	}
	
	private String getCurrentSortString(){
		String sort = "";
		switch (mCurSortView.getId()) {
		case R.id.btn_sort_def:
			break;
		case R.id.btn_sort_price:
			sort = "qbjg asc";
			break;
		case R.id.btn_sort_star:
			sort = "fwpj desc";
			break;
		default:
			break;
		}
		return sort;
	}
	
	private void updateSort(View v){
		if(!(mCurSortView instanceof TextView) || mCurSortView == v) return;
		int fOffsetStep = mSortViewList.indexOf(mCurSortView) - mSortViewList.indexOf(mDefSortBtn);
		int tOffsetStep = mSortViewList.indexOf(v) - mSortViewList.indexOf(mDefSortBtn);
		float fOffset = mDefSortBtn.getMeasuredWidth() * fOffsetStep;
		float tOffset = mDefSortBtn.getMeasuredWidth() * tOffsetStep;
		mCurSortView = (TextView) v;
		mDefSortBtn.setTextColor(sUnSortedColor);
		mStarSortBtn.setTextColor(sUnSortedColor);
		mPriceSortBtn.setTextColor(sUnSortedColor);
		mCurSortView.setTextColor(sSortedColor);
		TranslateAnimation ta = new TranslateAnimation(fOffset, tOffset, 0, 0);
		ta.setFillAfter(true);
		ta.setDuration(100);
		mSortDivider.startAnimation(ta);
	}

	/*	
 	1	sqrid	申请人ID
	2	sqrxm	申请人姓名
	3	sqgsid	申请公司ID
	4	sqgsmc	申请公司名称
	5	sqfwlx	申请服务类型
	6	sqfwlxmc申请服务类型名称（支持多个类型提交每一条需用#分割）
	7	remark	订单说明（可有用户填写也可可不填）
	8	yykssj	预约的开始时间，格式为HH:mm:ss 字符串
	9	yyjssj	预约的结束时间，格式为HH:mm:ss 字符串
	10	fwdz	服务地址
	11	yldh	预留电话
	12	ddje	订单金额
	*/
	@Override
	public void submitOrder() {
		User user = getUser();
		String url = Constants.BD_SERVER_BASE_URI + "/" + Constants.CREATEWXDDXX;
		try {
			sendData(buildOrderParams(user.getId(), user.getYhm(), mCurrentComId, mCurrentCom.getGsmc(), 
					mCurerntPro.getId(),mCurerntPro.getPro(), getOrderBeginTime(), getOrderEndTime(),
					user.getSqmc()+user.getFdmc()+user.getDymc()+user.getMph(), mPhoneNumber, mCurrentCom.getMinjg()+""),
					url, this, Constants.REQUEST_CODE_CREATEWXDDXX);
		} catch (UnsupportedEncodingException e) {
			errorMsg(e.getMessage());
			e.printStackTrace();
		}
		
	}
	public ArrayList<NameValuePair> buildOrderParams(String sqrid,String sqrxm,String sqgsid,
			String sqgsmc,String sqfwlx,String sqfwlxmc,String yykssj,String yyjssj,String fwdz,String yldh,String ddje) throws UnsupportedEncodingException{
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sqrid", URLEncoder.encode(sqrid, "GBK")));
		params.add(new BasicNameValuePair("sqrxm", URLEncoder.encode(sqrxm, "GBK")));
		params.add(new BasicNameValuePair("sqgsid", URLEncoder.encode(sqgsid, "GBK")));
		params.add(new BasicNameValuePair("sqgsmc", URLEncoder.encode(sqgsmc, "GBK")));
		params.add(new BasicNameValuePair("sqfwlx", URLEncoder.encode(sqfwlx, "GBK")));
		params.add(new BasicNameValuePair("sqfwlxmc", URLEncoder.encode(sqfwlxmc, "GBK")));
		params.add(new BasicNameValuePair("yykssj", URLEncoder.encode(yykssj, "GBK")));
		params.add(new BasicNameValuePair("yyjssj", URLEncoder.encode(yyjssj, "GBK")));
		params.add(new BasicNameValuePair("fwdz", URLEncoder.encode(fwdz, "GBK")));
		params.add(new BasicNameValuePair("yldh", URLEncoder.encode(yldh, "GBK")));
		params.add(new BasicNameValuePair("ddje", URLEncoder.encode(ddje, "GBK")));
		return params;
	}

	@Override
	public void onSelectedItemChanged(int position) {
		mCurrentCom = mCompanys.get(position);
		loadJgmcByGsid(mCompanys.get(position).getGsid());
//		loadWorkerListByGsid(mCompanys.get(position).getGsid());
	}

	@Override
	public void loginSuccess() {
		showOrder();
	}

	@Override
	public void loginFailed(String msg) {
		
	}
}
