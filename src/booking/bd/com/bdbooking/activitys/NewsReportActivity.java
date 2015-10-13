package booking.bd.com.bdbooking.activitys;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import booking.bd.com.bdbooking.Constants;
import booking.bd.com.bdbooking.activitys.BaseActivity.loginListener;
import booking.bd.com.bdbooking.bean.NewsV1;
import booking.bd.com.bdbooking.bean.User;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.PublicInterfaceFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class NewsReportActivity extends BaseActivity implements loginListener {
	
	private TextView mNewTitle;
	private TextView mNewSubTitle;
	private WebView mJjtzView;
	private ArrayList<NewsV1> mNewsV1;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.bd_activity_news);
		mJjtzView = (WebView) findViewById(R.id.wv_news_content);
		mJjtzView.getSettings().setJavaScriptEnabled(true);
		mNewTitle = (TextView) findViewById(R.id.tv_new_title);
		mNewSubTitle = (TextView) findViewById(R.id.tv_name_and_time);
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
		if(Constants.REQUEST_CODE_GETNOTICEBYXQID == resultCode){
			mNewsV1 = PublicInterfaceFactory.getNewsV1FromJson(mLists);
			if(mNewsV1.size()>0){
				mNewTitle.setText(mNewsV1.get(0).getTitle());
				mNewSubTitle.setText(mNewsV1.get(0).getCjsj());
				mJjtzView.loadUrl("http://owen0018.6655.la:20000/ZHSQ"+mNewsV1.get(0).getContentUrl());
			}
		}
	}

	public void enterSqxw(View v){
		startActivity(PublicInterfaceFactory.createNewsDetailsIntent(this, Xxlx.SQXW));
	}
	
	public void enterBmxx(View v){
		startActivity(PublicInterfaceFactory.createNewsDetailsIntent(this, Xxlx.BMXX));	
	}
	
	public void enterWytz(View v){
		startActivity(PublicInterfaceFactory.createNewsDetailsIntent(this, Xxlx.WYTZ));
	}
	
	public void enterJjtzls(View v){
		startActivity(PublicInterfaceFactory.createNewsDetailsIntent(this, Xxlx.JJTZ));
	}
	
	@Override
	public void submitOrder() {
		
	}


	@Override
	public void loadData() {
		loginBox(this,true);
	}

	private ArrayList<NameValuePair> buildParams(int xxlx,String xqid,int currentPageNum,int pageSize,String px){
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("xxlx", xxlx+""));
		params.add(new BasicNameValuePair("xqid", xqid));
		params.add(new BasicNameValuePair("currentPageNum", currentPageNum+""));
		params.add(new BasicNameValuePair("pageSize", pageSize+""));
		params.add(new BasicNameValuePair("px", px));
		return params;
	}

	public enum Xxlx {
		WYTZ(1,"物业通知"),BMXX(2,"便民信息"),JJTZ(3,"紧急通知历史"),SQXW(4,"社区新闻");
		private int value;
		private String lxmc;
		Xxlx(int value,String lxmc){
			this.value = value;
			this.lxmc = lxmc;
		}
		public int getValue() {
			return value;
		}
		public String getLxmc() {
			return lxmc;
		}
	}
	
	@Override
	public void loginSuccess() {
		User user = getUser();
		sendData(buildParams(Xxlx.JJTZ.getValue(), user.getSqid(), 1,1, "cjsj desc"),
				Constants.URL_GETNOTICEBYXQID, this, Constants.REQUEST_CODE_GETNOTICEBYXQID);
//		sendData(/*XutilHttpPack.DEFAULT_EMPTY_NAME_VALUE*/new BasicNameValuePair("px","cjsj desc"), 
//				buildUrlByParams(Xxlx.JJTZ.getValue(), user.getSqid(), 1,1, "cjsj desc")
////				"http://owen0018.6655.la:20000/ZHSQ/service/sqxxgl/getNoticeByXqID.action?xxlx="+Xxlx.JJTZ.getValue()+"&xqid="+user.getSqid()+"&currentPageNum=1&pageSize=10"
//				, this, Constants.REQUEST_CODE_GETNOTICEBYXQID);
	}

	public String buildUrlByParams(int xxlx,String xqid,int currentPageNum,int pageSize,String px){
		return "http://owen0018.6655.la:20000/ZHSQ/service/sqxxgl/getNoticeByXqID.action?xxlx="
				+xxlx+"&xqid="+xqid+"&currentPageNum="+currentPageNum+"&pageSize="+pageSize/*+"&px="+px*/;
	}
	
	@Override
	public void loginFailed(String msg) {
		
	}
	
}
