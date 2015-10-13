package booking.bd.com.bdbooking.activitys;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import booking.bd.com.bdbooking.Constants;
import booking.bd.com.bdbooking.XutilImageLoader;
import booking.bd.com.bdbooking.Fragment.BookingFragment;
import booking.bd.com.bdbooking.activitys.BaseActivity.loginListener;
import booking.bd.com.bdbooking.adapter.CompanyListAdapter;
import booking.bd.com.bdbooking.adapter.CompanyListAdapter.SelectedItemChangeListener;
import booking.bd.com.bdbooking.adapter.EvaluateListAdapter;
import booking.bd.com.bdbooking.adapter.ProMenuAdapter;
import booking.bd.com.bdbooking.adapter.WorkerListAdapter;
import booking.bd.com.bdbooking.bean.CompanyInfo;
import booking.bd.com.bdbooking.bean.Evaluation;
import booking.bd.com.bdbooking.bean.EvaluationData;
import booking.bd.com.bdbooking.bean.JzProjectInfo;
import booking.bd.com.bdbooking.bean.User;
import booking.bd.com.bdbooking.bean.Worker;
import booking.bd.com.bdbooking.item.WorkerInfoView;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.PublicInterfaceFactory;
import booking.bd.com.bdbooking.utils.ViewHolder;
import booking.bd.com.bdbooking.utils.XutilHttpPack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class AuthActivity extends BaseActivity implements OnClickListener, SelectedItemChangeListener, loginListener {

	private static final String TAG = "AuthActivity";
	private Button mSubmitBookBtn;
	private TextView mDefSortBtn;
	private TextView mStarSortBtn;
	private TextView mPriceSortBtn;
	private TextView mTitle;
	private ListView mCompanyList;
	private ListView mProMenu;
	private View mSortDivider;
	private int mDividerMoveW;
	
	private View mPreWorker;
	private View mShowWorker;
	private View mNextWorker;
	private ImageView mToPre;
	private ImageView mToNext;
	private WorkerInfoView mWorkerView;
	private Worker mCurrentWorker;
	
	public static int sSortedColor = Color.parseColor("#F61313"); 
	public static int sUnSortedColor;
	private TextView mCurSortView;
	private XutilImageLoader mImgLoader;
	
	private BookingFragment mBookingFragment = new BookingFragment();
	private CompanyListAdapter mComAdapter;
	private ArrayList<CompanyInfo> mCompanyInfos;
	private ArrayList<JzProjectInfo> mJzPros;
	private ArrayList<Worker> mWokers = new ArrayList<Worker>();
	private WorkerListAdapter mWorkerAdapter;
	private ProMenuAdapter mProAdapter;
	
	private String mCurrentProId;
	private String mCurrentComId;
	private int mCurrentWorkerPosition;
	private ArrayList<TextView> mSortViewList = new ArrayList<TextView>();
	
	private int mCurrentListLx = 1;
	private static final int  LIST_LX_COMPANY =1;
	private static final int  LIST_LX_PERSON =2;
	
	private boolean isInSecendaryMenu;
	private boolean isDisplayAll;
	private int mCurrentPage =1;
	private int mCurrentProPosition = 0;
	private int mCurrentComPosition = 0;
	private int mCurrentParentProPosition = 0;
	
	private ArrayList<EvaluationData> mEvaluation;
	private ListView mEvaList;
	private EvaluateListAdapter mEvaAdapter;
	
	private Button mPrePage;
	private Button mNextPage;
	
	private ImageView mCompany;
	private ImageView mPerson;
	private ListView mPersonList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bd_activity_auth);
		initViews();
		setClick();
		mImgLoader = new XutilImageLoader(this);
		isInSecendaryMenu = false;
		mCompanyInfos = new ArrayList<>();
		isDisplayAll = true;
		initKey();
	}

	private void initKey() {
		mPriceSortBtn.setNextFocusRightId(R.id.iv_pre_worker);
		mPreWorker.setNextFocusRightId(R.id.btn_sort_price);
		mProMenu.setNextFocusRightId(R.id.btn_sort_def);
		mPriceSortBtn.setNextFocusDownId(mCompanyList.getId());
	}

	private void setClick() {
		mSubmitBookBtn.setOnClickListener(this);
		mDefSortBtn.setOnClickListener(this);
		mStarSortBtn.setOnClickListener(this);
		mPriceSortBtn.setOnClickListener(this);
		mToNext.setOnClickListener(this);
		mToPre.setOnClickListener(this);
		mPrePage.setOnClickListener(this);
		mNextPage.setOnClickListener(this);
		mCompany.setOnClickListener(this);
		mPerson.setOnClickListener(this);
		mCompanyList.setOnItemClickListener(mComAdapter);
		mProMenu.setOnItemClickListener(new MenuItemClickListener());
		
		mPersonList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(mWorkerAdapter.getSelectPos() != position){
					if(mWorkerAdapter.getSelectPos() > -1){
//						View oldView = mPersonList.getChildAt(index)
//						((ImageView)oldView.findViewById(R.id.iv_appoint)).setImageResource(R.drawable.bd_def_appoint);
					}
//					((ImageView)ViewHolder.get(view,R.id.iv_appoint)).setImageResource(R.drawable.bd_appoint);
					setWorker(position, position, position);
					mWorkerAdapter.setSelectPos(position);
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		if(isInSecendaryMenu){
			mProAdapter.setData(mJzPros);
			isInSecendaryMenu = false;
			mProMenu.setSelection(mCurrentParentProPosition);
			mCurrentProPosition = mCurrentParentProPosition;
			return;
		}
		super.onBackPressed();
	}
	
	private class MenuItemClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			isDisplayAll = false;
			JzProjectInfo menu = (JzProjectInfo)mProAdapter.getItem(position);
			if(menu.hasChildren()){
				mCurrentParentProPosition =position;
				mProAdapter.setData(menu.configChildren());
				isInSecendaryMenu = true;
			}
			mCurrentProPosition = position;
			if(mCurrentListLx == LIST_LX_COMPANY){
				loadCompanyListByJzProId(menu.getId());
			}else{
				loadJzWorkerByFwlbid(menu.getId());
			}
		}
	}
	
	private void initViews() {
		mSubmitBookBtn = (Button) findViewById(R.id.btn_submit);
		mDefSortBtn = (TextView) findViewById(R.id.btn_sort_def);
		mStarSortBtn = (TextView) findViewById(R.id.btn_sort_star);
		mPriceSortBtn = (TextView) findViewById(R.id.btn_sort_price);
		sUnSortedColor = mStarSortBtn.getCurrentTextColor();
		mSortDivider = findViewById(R.id.select_divider);
		
		mPreWorker = findViewById(R.id.ll_pre_worker);
		mShowWorker = findViewById(R.id.ll_focus_worker);
		mNextWorker = findViewById(R.id.ll_next_worker);
		mToPre = (ImageView) findViewById(R.id.iv_pre_worker);
		mToNext = (ImageView) findViewById(R.id.iv_next_worker);
		mWorkerView = (WorkerInfoView) findViewById(R.id.wiv_worker);
		
		mTitle = (TextView) findViewById(R.id.title);
		mWorkerAdapter = new WorkerListAdapter(this);
		mCompanyList = (ListView) findViewById(R.id.lv_company) ;
		mCompanyList.setAdapter(mComAdapter = new CompanyListAdapter(this));
		mComAdapter.setSelectedItemChangeListener(this);
		
		mProMenu = (ListView) findViewById(R.id.lv_jz_menu);
		mProMenu.setAdapter(mProAdapter = new ProMenuAdapter(this));
		
		mEvaList = (ListView) findViewById(R.id.ll_evaluation_list);
		mEvaList.setAdapter(mEvaAdapter = new EvaluateListAdapter(this));
		
		mPrePage = (Button) findViewById(R.id.btn_pre_page);
		mNextPage = (Button) findViewById(R.id.btn_next_page);
		
		mCurSortView = mDefSortBtn;
		mSortViewList.add(mDefSortBtn);
		mSortViewList.add(mStarSortBtn);
		mSortViewList.add(mPriceSortBtn);
		
		findViewById(R.id.ll_com_per_selector).setVisibility(View.VISIBLE);
		mPersonList = (ListView) findViewById(R.id.lv_person);
		mPersonList.setAdapter(mWorkerAdapter);
		mCompany = (ImageView) findViewById(R.id.iv_company);
		mPerson = (ImageView) findViewById(R.id.iv_person);
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_company:
				if(mCurrentListLx != LIST_LX_COMPANY){
					mCurrentListLx = LIST_LX_COMPANY;
					loadCompanyListByJzProId(mCurrentProId);
					mCompany.setImageResource(R.drawable.company_selected);
					mPerson.setImageResource(R.drawable.person);
				}
				break;
			case R.id.iv_person:
				if(mCurrentListLx != LIST_LX_PERSON){
					mCurrentListLx = LIST_LX_PERSON;
					loadJzWorkerByFwlbid(mCurrentProId);
					mCompany.setImageResource(R.drawable.company);
					mPerson.setImageResource(R.drawable.person_selected);
				}
				break;
			case R.id.btn_submit:
				if(mWokers.size()==0 || mWokers.get(mCurrentWorkerPosition) == null) 
					return;
				loginBox(this);
//				showOrder();
				break;
			case R.id.btn_sort_def:
			case R.id.btn_sort_price:
			case R.id.btn_sort_star:
				updateSort(v);
				if(mCurrentListLx == LIST_LX_COMPANY){
					loadCompanyListBySort(getCurrentSortString());
				}else{
					loadJzWorkerBySort(getCurrentSortString());
				}
				break;
			case R.id.iv_pre_worker:
				if(null != mWokers && mWokers.size()>0 && mCurrentWorkerPosition>0){
					mCurrentWorkerPosition--;
					int begin = mCurrentWorkerPosition>0?mCurrentWorkerPosition-1:0;
					int end = mCurrentWorkerPosition+1;
					setWorker(begin, end, mCurrentWorkerPosition);
				}
				break;
			case R.id.iv_next_worker:
				if(null != mWokers && mWokers.size()>0 && mCurrentWorkerPosition<mWokers.size()-1){
					mCurrentWorkerPosition++;
					int begin = mCurrentWorkerPosition-1;
					int end = mCurrentWorkerPosition<mWokers.size()-1?mCurrentWorkerPosition+1:mCurrentWorkerPosition;
					setWorker(begin, end, mCurrentWorkerPosition);
				}
				break;
			case R.id.btn_pre_page:
				if(mCurrentPage == 1){
					showToastShort("已经是第一页了");
				}else{
					mCurrentPage--;
					if(mCurrentListLx == LIST_LX_COMPANY){
						loadCompanyListByJzPageNumber(mCurrentPage);
					}else{
						loadJzWorkerByPagenumber(mCurrentPage);
					}
				}
				break;
			case R.id.btn_next_page:
				if(mCompanyInfos.size() <10){
					showToastShort("已经是最后一页了");
				}else{
					mCurrentPage++;
					if(mCurrentListLx == LIST_LX_COMPANY){
						loadCompanyListByJzPageNumber(mCurrentPage);
					}else{
						loadJzWorkerByPagenumber(mCurrentPage);
					}
				}
				break;
				default:
					break;
		}
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
//		mSortDivider.offsetLeftAndRight(offset);
//		mSortDivider.setPaddingRelative(offset, 0, -offset, 0);
//		mSortDivider.invalidate();
	}

	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		ListView visible = mCurrentListLx == LIST_LX_COMPANY ?mCompanyList:mPersonList;
		if(event.getAction() == KeyEvent.ACTION_DOWN){
			if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP){
				if(mSubmitBookBtn.hasFocus()){
					mToNext.requestFocus();
					return true;
				}
			}
			if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN){
				if(sortBtnHasFocus()){
					if(visible.getCount()>0){
						visible.requestFocus();
						visible.setSelection(0);
					}
					return true;
				}
				if(visible.hasFocus() && visible.getSelectedItemPosition()==visible.getCount()-1){
					mPrePage.requestFocus();
					return true;
				}
				if(mPreWorker.hasFocus()){
					if(mEvaList.getCount()>0){
						mEvaList.requestFocus();
					}
					mSubmitBookBtn.requestFocus();
					return true;
				}
				if(mToNext.hasFocus()){
					mSubmitBookBtn.requestFocus();
					return true;
				}
			}
			if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT){
				if(visible.hasFocus() || mDefSortBtn.hasFocus()){
					mProMenu.requestFocus();
					mProMenu.setSelection(mCurrentProPosition);
					return true;
				}
				if(mEvaList.hasFocus()){
					visible.requestFocus();
					visible.setSelection(mCurrentComPosition);
					return true;
				}
				if(mSubmitBookBtn.hasFocus()){
					if(mEvaList.getCount()>0){
						mEvaList.requestFocus();
						mEvaList.setSelection(0);
					}else if(visible.getCount()>0){
						visible.requestFocus();
						visible.setSelection(mCurrentComPosition);
					}else{
						mProMenu.requestFocus();
						mProMenu.setSelection(mCurrentProPosition);
					}
					return true;
				}
				if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT){
					if(mProMenu.hasFocus()){
						mDefSortBtn.requestFocus();
						return true;
					}
					if(visible.hasFocus()){
						if(mEvaList.getCount()>0){
							mEvaList.requestFocus();
							mEvaList.setSelection(0);
						}else{
							mSubmitBookBtn.requestFocus();
						}
						return true;
					}
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
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onHttpError(Exception e, String msg, int requestCode) {
		showToastShort("http error :: " + msg);
	}

	@Override
	public void onDecoded(String reason, boolean isSuccess,
			JsonObject mJsonResult, JsonArray mLists, int resultCode) {
		
		if(!isSuccess){
			errorMsg(reason);
			return;
		}
		
		if(Constants.REQUEST_CODE_CREATEJZDDXX == resultCode){
			errorMsg(PublicInterfaceFactory.getSuccessMsgFronJson(mJsonResult));
		}
		
		if(Constants.REQUEST_CODE_GETJZFWRYXXBYFWLBID == resultCode){
			mWokers = PublicInterfaceFactory.getJzWorkerFromJson(mLists);
			if(mWokers.size()>0){
				setWorker(0, 0, 0);
			}
			mWorkerAdapter.setData(mWokers);
			mCompanyList.setVisibility(View.GONE);
			mPersonList.setVisibility(View.VISIBLE);
		}
		
		if(Constants.REQUEST_CODE_GETJZFWGSALL == resultCode){
			mCurrentPage = 1;
			mCompanyInfos = PublicInterfaceFactory.getJzCompanyInfoFromJson(mLists);
			mCompanyList.setVisibility(View.VISIBLE);
			mPersonList.setVisibility(View.GONE);
			mComAdapter.setData(mCompanyInfos);
			if(mCompanyInfos.size() > 0){
				loadDefWorkerList();
			}
		}
		
		if (Constants.REQUEST_CODE_GETJZFWPROJECT == resultCode) {
			mJzPros = PublicInterfaceFactory.getJzProjectFromJson(mLists);
			if (mJzPros.size() > 0) {
				mProAdapter.setData(mJzPros);
				loadDefCompanyList();
			}else if(mCurrentPage>1){
				mCurrentPage--;
				showToastShort("已经是最后一页了");
			}
		}
		if(Constants.REQUEST_CODE_GETJZFWGSBYFWLB == resultCode){
			mCurrentPage = 1;
			mCompanyInfos = PublicInterfaceFactory.getJzCompanyInfoFromJson(mLists);
			mCompanyList.setVisibility(View.VISIBLE);
			mPersonList.setVisibility(View.GONE);
			if(mCompanyInfos.size() > 0){
				mComAdapter.setData(mCompanyInfos);
				loadDefWorkerList();
			}else if(mCurrentPage>1){
				mCurrentPage--;
				showToastShort("已经是最后一页了");
			}
		}
		if(Constants.REQUEST_CODE_GETJZFWRYXXBYGSID == resultCode){
			mWokers = PublicInterfaceFactory.getJzWorkerFromJson(mLists);
//			mWorkerAdapter.setData(mWokers);
			if(mWokers.size() > 0){
				setDefWorker();
			}
		}
		if(Constants.REQUEST_CODE_GETBMFWPJBYRYID == resultCode){
			Evaluation eva = PublicInterfaceFactory.getEvaFromJson(mLists);
			mEvaluation = eva.getData();
			if(null != mEvaluation && mEvaluation.size()>0){
				mEvaAdapter.setData(mEvaluation);
			}
		}
	}

	private void setDefWorker() {
		mCurrentWorkerPosition = 0;
		setWorker(0,mWokers.size()>1?1:0,0);
	}

	private void setWorker(int begin,int end,int show){
		mImgLoader.display((ImageView)mPreWorker.findViewById(R.id.iv_pre_icon),mWokers.get(begin).getZp());
		((TextView)mPreWorker.findViewById(R.id.tv_pre_name)).setText(mWokers.get(begin).getXm());
		((RatingBar)mPreWorker.findViewById(R.id.rb_pre_star)).setRating(mWokers.get(begin).getStar());
		
		mImgLoader.display((ImageView)mShowWorker.findViewById(R.id.iv_focus_icon),mWokers.get(show).getZp());
		((TextView)mShowWorker.findViewById(R.id.tv_focus_name)).setText(mWokers.get(show).getXm());
		((RatingBar)mShowWorker.findViewById(R.id.rb_focus_star)).setRating(mWokers.get(show).getStar());
		
		mImgLoader.display((ImageView)mNextWorker.findViewById(R.id.iv_next_icon),mWokers.get(end).getZp());
		((TextView)mNextWorker.findViewById(R.id.tv_next_name)).setText(mWokers.get(end).getXm());
		((RatingBar)mNextWorker.findViewById(R.id.rb_next_star)).setRating(mWokers.get(end).getStar());
		
		mPreWorker.setVisibility(begin==show?View.GONE:View.VISIBLE);
		mNextWorker.setVisibility(end==show?View.GONE:View.VISIBLE);
		
		mCurrentWorker = mWokers.get(show);
		configWorker(mWokers.get(show));
	}
	
	private void configWorker(Worker worker) {
		mWorkerView.configWorkerInfo(worker);
		loadEvaluateByWorkerId(worker.getId());
	}

	private void loadEvaluateByWorkerId(String id){
		loadEvaluate(id,1,Constants.DEFAULT_COMPANYS_PER_PAGE);
	}
	
	private void loadEvaluateByPageNumber(int pageNumber){
		loadEvaluate(mCurrentWorker.getId(),pageNumber,Constants.DEFAULT_COMPANYS_PER_PAGE);
	}
	
	private void loadEvaluate(String id,int curPage,int pageSize){
		String workerEvaluateUri = Constants.BD_SERVER_BASE_URI + "/"
				+ Constants.GETBMFWPJBYRYID;
		sendData(buildWorkerEvaluateParam(id,curPage,pageSize), workerEvaluateUri, this, Constants.REQUEST_CODE_GETBMFWPJBYRYID);
	}
	
	private ArrayList<NameValuePair> buildWorkerEvaluateParam(String id,int curPage,int pageSize) {
		ArrayList<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("ryid", id));
		params.add(new BasicNameValuePair("currentPageNum",curPage+""));
		params.add(new BasicNameValuePair("pageSize", pageSize+""));
		return params;
	}

	private void loadDefWorkerList() {
		String defId = mCompanyInfos.get(0).getGsid();
		loadWorkerListByComId(defId);
	}
	
	private void loadWorkerListByComId(String id) {
		mCurrentComId = id;
		loadWorkerList(id,Constants.DEFAULT_WORKER_SORT,1,Constants.DEFAULT_COMPANYS_PER_PAGE);
	}
	
	private void loadWorkerListByPageNumber(int pageNumber) {
		loadWorkerList(mCurrentComId,Constants.DEFAULT_WORKER_SORT,pageNumber,Constants.DEFAULT_COMPANYS_PER_PAGE);
	}

	private void loadWorkerList(String id,String px,int curPage,int pageSize){
		String JzWorkerUri = Constants.BD_SERVER_BASE_URI + "/"
				+ Constants.GETJZFWRYXXBYGSID;
		sendData(buildWorkerParam(id, px,curPage, pageSize), JzWorkerUri, this, Constants.REQUEST_CODE_GETJZFWRYXXBYGSID);
	}
	
	private ArrayList<NameValuePair> buildWorkerParam(String id,String px,int curPage,int pageSize) {
		ArrayList<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("gsid", id));
		params.add(new BasicNameValuePair("px",px));
		params.add(new BasicNameValuePair("currentPageNum",curPage+""));
		params.add(new BasicNameValuePair("pageSize", pageSize+""));
		return params;
	}
	
	private void loadDefCompanyList() {
		String defId = mJzPros.get(0).getId();
		loadCompanyListByJzProId(defId);
	}

	private void loadJzWorkerByFwlbid(String fwlbid){
		mCurrentProId = fwlbid;
		loadJzWorker(fwlbid,mCurrentPage,Constants.DEFAULT_COMPANYS_PER_PAGE,getCurrentSortString());
	}
	
	private void loadJzWorkerByPagenumber(int currentPageNum){
		loadJzWorker(mCurrentProId,currentPageNum,Constants.DEFAULT_COMPANYS_PER_PAGE,getCurrentSortString());
	}
	
	private void loadJzWorkerBySort(String px){
		loadJzWorker(mCurrentProId,mCurrentPage,Constants.DEFAULT_COMPANYS_PER_PAGE,px);
	}
	
	private void loadJzWorker(String fwlbid,int currentPageNum,int pageSize,String px){
		String url = Constants.BD_SERVER_BASE_URI+"/"+Constants.GETJZFWRYXXBYFWLBID;
		sendData(buildJzWorkerParams(fwlbid,currentPageNum,pageSize,px),url,this,Constants.REQUEST_CODE_GETJZFWRYXXBYFWLBID);
	}
	
	private ArrayList<NameValuePair> buildJzWorkerParams(String fwlbid,int currentPageNum,int pageSize,String px){
		ArrayList<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("fwlbid",fwlbid));
		params.add(new BasicNameValuePair("currentPageNum",currentPageNum+""));
		params.add(new BasicNameValuePair("pageSize", pageSize+""));
		params.add(new BasicNameValuePair("px", px));
		return params;
	}
	
	@Override
	public void loadData() {
//		loadCompanyListByJzProId("8a8a83024e29a851014e29aedc760002");
//		loadDefWorkerListByComId("8a8a83024efbd8c2014efc054e950001");
		// for test
//		loadAllJzCompanyDef();
		loadJzPro();
	}


	private void loadJzPro(){
		String JzProUri = Constants.BD_SERVER_BASE_URI + "/"
				+ Constants.GETJZWFPROJECT;
		sendData(XutilHttpPack.DEFAULT_EMPTY_NAME_VALUE, JzProUri, this, Constants.REQUEST_CODE_GETJZFWPROJECT);
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
	
	@Override
	public void onSelectedItemChanged(int position) {
		if(!(mComAdapter.getItem(position) instanceof CompanyInfo)){
			return;
		}
		mCurrentComPosition = position;
		CompanyInfo info = (CompanyInfo) mComAdapter.getItem(position);
		String gsid = info.getGsid();
		loadWorkerListByComId(gsid);
	}
	
	private void loadCompanyListByJzProId(String id){
		mCurrentProId = id;
		loadCompanyList(id,1,Constants.DEFAULT_COMPANYS_PER_PAGE,getCurrentSortString());
	}
	
	private void loadCompanyListByJzPageNumber(int curPage){
		loadCompanyList(mCurrentProId,curPage,Constants.DEFAULT_COMPANYS_PER_PAGE,getCurrentSortString());
	}
	
	private void loadCompanyListBySort(String px){
		loadCompanyList(mCurrentProId,1,Constants.DEFAULT_COMPANYS_PER_PAGE,px);
	}

	private void loadCompanyList(String id,int curPage,int pageSize,String sort){
		String JzCompanyUri = Constants.BD_SERVER_BASE_URI + "/"
				+ Constants.GETJZFWGSBYFWLB;
		sendData(buildCompanyParam(id, curPage, pageSize,sort), JzCompanyUri, this, Constants.REQUEST_CODE_GETJZFWGSBYFWLB);
	}
	
	private void loadAllJzCompanyDef() {
		loadAllJzCompany(0,Constants.DEFAULT_COMPANYS_PER_PAGE,"");
	}
	
	private void loadAllJzCompanyByPageNumber(int curPage){
		loadAllJzCompany(curPage,Constants.DEFAULT_COMPANYS_PER_PAGE,getCurrentSortString());
	}
	
	private void loadAllJzCompanyBySort(String px){
		loadAllJzCompany(mCurrentPage,Constants.DEFAULT_COMPANYS_PER_PAGE,px);
	}
	
	private void loadAllJzCompany(int curPage,int pageSize,String px){
		String allJzCompanyUri = Constants.BD_SERVER_BASE_URI + "/"
				+ Constants.GETJZFWGSALL;
		sendData(buildAllJzCompanyParam(curPage, pageSize,px), allJzCompanyUri, this, Constants.REQUEST_CODE_GETJZFWGSALL);
	}
	
	private ArrayList<NameValuePair> buildAllJzCompanyParam(int curPage,int pageSize,String px) {
		ArrayList<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("currentPageNum",curPage+""));
		params.add(new BasicNameValuePair("pageSize", pageSize+""));
		params.add(new BasicNameValuePair("px", px));
		return params;
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

	@Override
	public void loginSuccess() {
		String ryid = mWokers.get(mCurrentWorkerPosition).getId();
		loadTimeByWorkerId(ryid);
	}

	@Override
	public void loginFailed(String msg) {
	}

	/*	1	sqrid	申请人ID（当前登录用户）
		2	sqrxm	申请人姓名
		3	sqgsid	申请公司ID（申请家政服务的家政公司ID）
		4	sqgsmc	申请公司名称
		5	sqfwlx	申请服务类型（申请的服务类型，比如：保姆等）
		6	sqfwlxmc申请服务类型名称(支持多个类型提交每一条需用#分割)
		7	yyrid	预约人（提供家政服务的人）
		8	yysjid	预约时间ID（当前预约人的预约时间）
		9	remark	订单说明（可有用户填写也可可不填）
		10	fwdz	服务地址
		11	yldh	预留电话
		12	ddje	订单金额
		3~8 支持多条
		注：如果一个订单包括多个服务类型，请在拼装数据时用#分开。如：sqfwlx=id#id。如果有多条类型需要已sqfwlx为基准。否则不予解析.
		注：传输多个数据是#在url中为“%23”直接输入#有可能会出问题。
		*/
	@Override
	public void submitOrder() {
		User user = getUser();
		CompanyInfo company = mCompanyInfos.get(mCurrentComPosition);
		JzProjectInfo project = mJzPros.get(mCurrentProPosition);
		String url = Constants.BD_SERVER_BASE_URI + "/" + Constants.CREATEJZDDXX;
		try {
			sendData(buildOrderParams(user.getId(), user.getYhm(), mCurrentComId, company.getGsmc(),
					project.getId(),project.getPro(),mWokers.get(mCurrentWorkerPosition).getId(),
					getSelectedTimeId(), user.getSqmc()+user.getFdmc()+user.getDymc()+user.getMph(), 
					mPhoneNumber, mWokers.get(mCurrentWorkerPosition).getQbjg()),
					url, this, Constants.REQUEST_CODE_CREATEJZDDXX);
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		
	}
	public ArrayList<NameValuePair> buildOrderParams(String sqrid,String sqrxm,String sqgsid,
			String sqgsmc,String sqfwlx,String sqfwlxmc,String yyrid,String yysjid,String fwdz,String yldh,String ddje) throws UnsupportedEncodingException{
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sqrid", URLEncoder.encode(sqrid, "GBK")));
		params.add(new BasicNameValuePair("sqrxm", URLEncoder.encode(sqrxm, "GBK")));
		params.add(new BasicNameValuePair("sqgsid", URLEncoder.encode(sqgsid, "GBK")));
		params.add(new BasicNameValuePair("sqgsmc", URLEncoder.encode(sqgsmc, "GBK")));
		params.add(new BasicNameValuePair("sqfwlx", URLEncoder.encode(sqfwlx, "GBK")));
		params.add(new BasicNameValuePair("sqfwlxmc", URLEncoder.encode(sqfwlxmc, "GBK")));
		params.add(new BasicNameValuePair("yyrid", URLEncoder.encode(yyrid, "GBK")));
		params.add(new BasicNameValuePair("yysjid", URLEncoder.encode(yysjid, "GBK")));
		params.add(new BasicNameValuePair("fwdz", URLEncoder.encode(fwdz, "GBK")));
		params.add(new BasicNameValuePair("yldh", URLEncoder.encode(yldh, "GBK")));
		params.add(new BasicNameValuePair("ddje", URLEncoder.encode(ddje, "GBK")));
		return params;
	}
}
