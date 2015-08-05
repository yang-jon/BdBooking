package booking.bd.com.bdbooking.activitys;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import booking.bd.com.bdbooking.Constants;
import booking.bd.com.bdbooking.R;
import booking.bd.com.bdbooking.bean.CompanyInfo;
import booking.bd.com.bdbooking.utils.PublicInterfaceFactory;
import android.os.Bundle;

public class BottledWaterActivity extends BaseActivity {
	
	private final String ALLSSGS_URI = Constants.BD_SERVER_BASE_URI + "/" + Constants.GETSSFWGSALL;
	private static final String PARAM_NAME_CURPAGE = "currentPageNum";
	private static final String PARAM_NAME_PAGESIZE = "pageSize";
	private ArrayList<CompanyInfo> mCompanys = new ArrayList<CompanyInfo>();
	
	private int mCurPage = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.bd_activity_bottled_water);
	}

	@Override
	public void onHttpError(Exception e, String msg, int requestCode) {
		showToastShort(msg);
	}

	@Override
	public void onDecoded(String reason, boolean isSuccess,
			JsonObject mJsonResult, JsonArray mLists, int resultCode) {
		mCompanys = PublicInterfaceFactory.getJzCompanyInfoFromJson(mJsonResult);
		
	}

	@Override
	public void loadData() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		configParams(params,mCurPage);
		sendData(params, ALLSSGS_URI, this, Constants.REQUEST_CODE_GETSSFWGSALL);
	}
	
	private void configParams(ArrayList<NameValuePair> params, int CurPage){
		NameValuePair pm1 = new BasicNameValuePair(PARAM_NAME_CURPAGE, CurPage+"");
		NameValuePair pm2 = new BasicNameValuePair(PARAM_NAME_PAGESIZE, Constants.DEFAULT_COMPANYS_PER_PAGE+"");
		params.add(pm1);
		params.add(pm2);
	}
}
