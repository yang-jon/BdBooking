package booking.bd.com.bdbooking.activitys;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import booking.bd.com.bdbooking.Constants;
import booking.bd.com.bdbooking.activitys.BaseActivity.loginListener;
import booking.bd.com.bdbooking.adapter.CompanyListAdapter;
import booking.bd.com.bdbooking.adapter.CompanyListAdapter.SelectedItemChangeListener;
import booking.bd.com.bdbooking.adapter.DrinkListAdapterV2;
import booking.bd.com.bdbooking.adapter.DrinkListAdapterV2.NumberSelectListener;
import booking.bd.com.bdbooking.bean.CompanyInfo;
import booking.bd.com.bdbooking.bean.DrinkInfoDataV2;
import booking.bd.com.bdbooking.bean.DrinkInfoV2;
import booking.bd.com.bdbooking.bean.User;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.PublicInterfaceFactory;
import booking.bd.com.bdbooking.utils.URLEncoderUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BottledWaterActivity extends BaseActivity implements SelectedItemChangeListener
, OnItemClickListener, OnClickListener, NumberSelectListener, loginListener {
	
	private final String ALLSSGS_URI = Constants.BD_SERVER_BASE_URI + "/" + Constants.GETSSFWGSALL;
	
	private ArrayList<CompanyInfo> mCompanys = new ArrayList<CompanyInfo>();
	private CompanyListAdapter mCompanyAdapter;
	private DrinkListAdapterV2 mDrinkAdapter;
	private DrinkListAdapterV2 mDrinkChildAdapter;
	
	private ListView mCompanyListView;
	private GridView mDrinkGridView;
	private Button mBtnSubmit;
	private boolean isInChild; 
	
	private Button mBtnPrePage;
	private Button mBtnNextPage;
	private int mCurrentComPage = 1;
	private CompanyInfo mCurCompany;
	
	private String mCurrentComId;
	private DrinkInfoV2 mCurrentDrink;
	private ArrayList<DrinkInfoDataV2> mData = new ArrayList<DrinkInfoDataV2>();
	
	private ArrayList<DrinkInfoV2> mDrinkInfos = new ArrayList<DrinkInfoV2>();
	private Map<DrinkInfoDataV2,Integer> mProduct = new HashMap<>();
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.bd_activity_bottled_water);
		findViews();
	}

	private void findViews() {
		mCompanyListView = (ListView) findViewById(R.id.ll_company_list);
		mDrinkGridView = (GridView) findViewById(R.id.gv_drinking_list);
		mBtnSubmit = (Button) findViewById(R.id.btn_submit);
		mBtnSubmit.setOnClickListener(this);
		mCompanyListView.setAdapter(mCompanyAdapter = new CompanyListAdapter(this));
		mDrinkGridView.setAdapter(mDrinkAdapter = new DrinkListAdapterV2(this,false));
		mDrinkChildAdapter = new DrinkListAdapterV2(this, true);
		mDrinkChildAdapter.setNumberSelectListener(this);
		
		mCompanyListView.setOnItemClickListener(mCompanyAdapter);
		mCompanyAdapter.setSelectedItemChangeListener(this);
		mDrinkGridView.setOnItemClickListener(this);
		
		mBtnPrePage = (Button) findViewById(R.id.btn_pre_page);
		mBtnNextPage = (Button) findViewById(R.id.btn_next_page);
		mBtnPrePage.setOnClickListener(this);
		mBtnNextPage.setOnClickListener(this);
	}

	@Override
	public void onHttpError(Exception e, String msg, int requestCode) {
		showToastShort(msg);
	}

	@Override
	public void onBackPressed() {
		if(isInChild){
			mDrinkGridView.setAdapter(mDrinkAdapter);
			mDrinkAdapter.setData(mDrinkInfos);
			isInChild = false;
			return;
		}
		super.onBackPressed();
	}
	
	@Override
	public void onDecoded(String reason, boolean isSuccess,
			JsonObject mJsonResult, JsonArray mLists, int resultCode) {
		
		if(!isSuccess){
			errorMsg(reason);
			return;
		}
		
		if(Constants.REQUEST_CODE_CREATESSDDXX == resultCode){
			errorMsg(PublicInterfaceFactory.getSuccessMsgFronJson(mJsonResult));
			mProduct.clear();
			if(isInChild){
				mDrinkChildAdapter.notifyDataSetChanged();
			}
		}
		
		if(Constants.REQUEST_CODE_GETSSFWGSALL == resultCode){
			mCompanys = PublicInterfaceFactory.getJzCompanyInfoFromJson(mLists);
			if(mCompanys.size() == 0){
				if(mCurrentComPage > 1){
					mCurrentComPage--;
					showToastShort("已经是最后一页了");
				}
			}else{
				mCompanyAdapter.setData(mCompanys);
				loadDrinkInfoDef();
			}
		}
		if(Constants.REQUEST_CODE_GETSCPBYGSID == resultCode){
			mDrinkInfos = PublicInterfaceFactory.getDrinkInfoV2FromJson(mLists);
			isInChild = false;
			mDrinkGridView.setAdapter(mDrinkAdapter);
			mDrinkAdapter.setData(mDrinkInfos);
			if(mDrinkInfos.size() > 0){
				mDrinkGridView.requestFocus();
				mCurrentDrink = mDrinkInfos.get(0);
			}
		}
		if(Constants.REQUEST_CODE_GETCPXHBYPPID == resultCode){
			mData = PublicInterfaceFactory.getDrinkInfoDataV2FromJson(mLists);
			isInChild = true;
			mDrinkGridView.setSelected(false);
			mDrinkGridView.setAdapter(mDrinkChildAdapter);
			mDrinkChildAdapter.setChildData(mData);
		}
	}
	
	private void loadDrinkInfoDef(){
		mCurCompany = mCompanys.get(0);
		loadDrinkInfo(mCompanys.get(0).getGsid());
	}
	
	private void loadDrinkInfo(String gsid){
		mCurrentComId = gsid;
		String url = Constants.BD_SERVER_BASE_URI + "/" + Constants.GETSCPBYGSID;
		sendData(new BasicNameValuePair("gsid", gsid), url, this, Constants.REQUEST_CODE_GETSCPBYGSID);
	}
	
	@Override
	public void loadData() {
		loadCompanyDef();
	}
	
	private void loadCompanyDef(){
		loadCompanyByPageNumber(1);
	}
	
	private void loadCompanyByPageNumber(int currentPageNum){
		mCurrentComPage = currentPageNum;
		loadCompany(currentPageNum,10,"");
	}
	
	private void loadCompany(int currentPageNum,int pageSize,String px){
		sendData(buildCompanyParams(currentPageNum,pageSize,px), ALLSSGS_URI, this, Constants.REQUEST_CODE_GETSSFWGSALL);
	}
	
	private ArrayList<NameValuePair> buildCompanyParams(int currentPageNum,int pageSize,String px){
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("currentPageNum", currentPageNum+""));
		params.add(new BasicNameValuePair("pageSize", pageSize+""));
		if(!TextUtils.isEmpty(px)){
			params.add(new BasicNameValuePair("px", px));
		}
		return params;
	}

	@Override
	public void onSelectedItemChanged(int position) {
		mCurCompany = mCompanys.get(position);
		loadDrinkInfo(mCurCompany.getGsid());
	}

/*	1	sqrid	申请人ID
	2	sqrxm	申请人姓名
	3	sqgsid	申请公司ID
	4	sqgsmc	申请公司名称
	5	sqfwlx	申请服务类型
	6	sqsl	申请数量
	7	Sqfwlxmc申请服务类型名称(支持多个类型提交每一条需用#分割)
	8	remark	订单说明（可有用户填写也可可不填）
	9	yykssj	预约的开始时间，格式为HH:mm:ss 字符串
	10	yyjssj	预约的结束时间，格式为HH:mm:ss 字符串
	11	fwdz	服务地址
	12	yldh	预留电话
	13	ddje	订单金额*/
	@Override
	public void submitOrder() {
		User user = getUser();
		String sqsl ="";
		String sqfwlx ="" ;
		String sqfwlxmc ="" ;
		String gsid ="";
		String gsmc ="";
		float ddje = 0;
		String yykssj="";
		String yyjssj="";
		for (Map.Entry<DrinkInfoDataV2, Integer> entry : mProduct.entrySet()) {
			if(entry.getValue() >0){
				DrinkInfoDataV2 data =entry.getKey();
				gsid = gsid + data.getGsxx().getGsid()+"#";
				gsmc = gsmc + data.getGsxx().getGsmc()+"#";
				sqsl = sqsl + entry.getValue()+"#";
				sqfwlx = sqfwlx + data.getId()+"#";
				sqfwlxmc = sqfwlxmc + data.getCpmc()+"#";
				ddje += entry.getValue() * data.getHdjg();
				yykssj = yykssj + getOrderBeginTime()+"#";
				yyjssj = yyjssj + getOrderEndTime()+"#";
			}
		}
		Log.i("yangzheng", sqfwlx +"\n"+ sqfwlxmc +"\n"+ sqsl+"\n"+gsid+"\n"+gsmc+"\n"+yykssj+"\n"+yyjssj);
		if(sqsl.endsWith("#")){
			sqsl = sqsl.substring(0,sqsl.lastIndexOf("#"));
		}
		if(sqfwlx.endsWith("#")){
			sqfwlx = sqfwlx.substring(0,sqfwlx.lastIndexOf("#"));
		}
		if(sqfwlxmc.endsWith("#")){
			sqfwlxmc = sqfwlxmc.substring(0,sqfwlxmc.lastIndexOf("#"));
		}
		if(gsid.endsWith("#")){
			gsid = gsid.substring(0,gsid.lastIndexOf("#"));
		}
		if(gsmc.endsWith("#")){
			gsmc = gsmc.substring(0,gsmc.lastIndexOf("#"));
		}
		if(yykssj.endsWith("#")){
			yykssj = yykssj.substring(0,yykssj.lastIndexOf("#"));
		}
		if(yyjssj.endsWith("#")){
			yyjssj = yyjssj.substring(0,yyjssj.lastIndexOf("#"));
		}
		Log.i("yangzheng", sqfwlx +"\n"+ sqfwlxmc +"\n"+ sqsl+"\n"+gsid+"\n"+gsmc+"\n"+yykssj+"\n"+yyjssj);
		String url = Constants.BD_SERVER_BASE_URI + "/" + Constants.CREATESSDDXX;
		try {
			sendData(buildOrderParams(user.getId(), user.getYhm(), gsid, gsmc,
					sqfwlx,sqfwlxmc, sqsl, getOrderBeginTime(),getOrderEndTime(), user.getSqmc()+user.getFdmc()+user.getDymc()+user.getMph(),
					mPhoneNumber, ddje+""),
					url, this, Constants.REQUEST_CODE_CREATESSDDXX);
		} catch (UnsupportedEncodingException e) {
			showToastShort(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<NameValuePair> buildOrderParams(String sqrid,String sqrxm,String sqgsid,
			String sqgsmc,String sqfwlx,String Sqfwlxmc,String sqsl,String yykssj,String yyjssj,String fwdz,String yldh,String ddje) throws UnsupportedEncodingException{
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sqrid", URLEncoderUtils.encode(sqrid)));
		params.add(new BasicNameValuePair("sqrxm", URLEncoderUtils.encode(sqrxm)));
		params.add(new BasicNameValuePair("sqgsid", URLEncoderUtils.encode(sqgsid)));
		params.add(new BasicNameValuePair("sqgsmc", URLEncoderUtils.encode(sqgsmc)));
		params.add(new BasicNameValuePair("sqfwlx", URLEncoderUtils.encode(sqfwlx)));
		params.add(new BasicNameValuePair("sqfwlxmc", URLEncoderUtils.encode(Sqfwlxmc)));
		params.add(new BasicNameValuePair("sqsl", URLEncoderUtils.encode(sqsl)));
		params.add(new BasicNameValuePair("yykssj", URLEncoderUtils.encode(yykssj)));
		params.add(new BasicNameValuePair("yyjssj", URLEncoderUtils.encode(yyjssj)));
		params.add(new BasicNameValuePair("fwdz", URLEncoderUtils.encode(fwdz)));
		params.add(new BasicNameValuePair("yldh", URLEncoderUtils.encode(yldh)));
		params.add(new BasicNameValuePair("ddje", URLEncoderUtils.encode(ddje)));
		return params;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(!isInChild){
			final DrinkInfoV2 info = mDrinkInfos.get(position);
			mCurrentDrink = info;
			loadDrinkByGsidAndDrinkId(mCurrentComId,info.getId());
		}
	}
	
	private void loadDrinkByGsidAndDrinkId(String gsid,String ppid){
		String url = Constants.BD_SERVER_BASE_URI+ "/" + Constants.GETCPXHBYPPID;
		sendData(buildDrinkParams(gsid, ppid), url, this, Constants.REQUEST_CODE_GETCPXHBYPPID);
	}
	
	private ArrayList<NameValuePair> buildDrinkParams(String gsid,String ppid){
		ArrayList<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("gsid", gsid));
		params.add(new BasicNameValuePair("ppid", ppid));
		return params;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			if(mProduct.isEmpty()){
				AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
				.setTitle("提示")
				.setMessage("请选择产品")
				.setPositiveButton(android.R.string.ok, null);
				builder.create().show();
			}else{
				setDrinkProduct(buildProductString());
//				showOrder();
				loginBox(this);
			}
			break;
		case R.id.btn_next_page:
			if(mCompanys.size()<10){
				showToastShort("已经是最后一页了");
			}else{
				mCurrentComPage++;
				loadCompanyByPageNumber(mCurrentComPage);
			}
			break;
		case R.id.btn_pre_page:
			if(mCurrentComPage == 1){
				showToastShort("已经是第一页了");
			}else{
				mCurrentComPage--;
				loadCompanyByPageNumber(mCurrentComPage);
			}
			break;
		default:
			break;
		}

	}

	public ArrayList<String> buildProductString(){
		ArrayList<String> result = new ArrayList<String>();
		for (Map.Entry<DrinkInfoDataV2, Integer> entry : mProduct.entrySet()) {
				if(entry.getValue() >0){
					result.add(entry.getKey().getCpmc() + "  x"+entry.getValue());
				}
			}
		return result;
	}
	@Override
	public void addNumber(int pos,TextView numberText) {
		int number = Integer.parseInt(numberText.getText().toString());
		numberText.setText(number+1+"");
		mData.get(pos).setGsxx(mCurCompany);
		mProduct.put(/*mCurrentDrink.getCpmc()+*/mData.get(pos), number+1);
//		mProduct.put(mCurDrinkInfoV2P.getCpmc()+mDrinkInfosV2C.get(pos).getCpmc(), number+1);
	}

	@Override
	public void appNumber(int pos,TextView numberText) {
		int number = Integer.parseInt(numberText.getText().toString());
		if(number > 0){
			numberText.setText(number-1+"");
		}
		if(number > 2){
			mData.get(pos).setGsxx(mCurCompany);
			mProduct.put(/*mCurrentDrink.getCpmc()+*/mData.get(pos), number-1);
//			mProduct.put(mCurDrinkInfoV2P.getCpmc()+mDrinkInfosV2C.get(pos).getCpmc(), number-1);
		}else if(mProduct.containsKey(/*mCurrentDrink.getCpmc()+*/mData.get(pos))){
			mData.get(pos).setGsxx(null);
			mProduct.remove(/*mCurrentDrink.getCpmc()+*/mData.get(pos));
//			mProduct.remove(mCurDrinkInfoV2P.getCpmc()+mDrinkInfosV2C.get(pos).getCpmc());
		}
	}

	public int getNumberByid(String id){
		for (Map.Entry<DrinkInfoDataV2, Integer> entry : mProduct.entrySet()) {
			if(entry.getValue() >0 && entry.getKey().getGsxx() !=null &&
					mCurrentComId.equals(entry.getKey().getGsxx().getGsid()) && id.equals(entry.getKey().getId())){
				return entry.getValue();
			}
		}
		return 0;
	}
	
	@Override
	public void loginSuccess() {
		showOrder();
	}

	@Override
	public void loginFailed(String msg) {
		
	}
}
