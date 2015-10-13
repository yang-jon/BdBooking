package booking.bd.com.bdbooking.activitys;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import booking.bd.com.bdbooking.Constants;
import booking.bd.com.bdbooking.activitys.NewsReportActivity.Xxlx;
import booking.bd.com.bdbooking.adapter.NewsAdapter;
import booking.bd.com.bdbooking.bean.NewsV1;
import booking.bd.com.bdbooking.bean.NewsV2;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.PublicInterfaceFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class NewsDetailsActivity extends BaseActivity implements OnItemClickListener {

	private Xxlx mXxlx;
	private TextView mTitle;
	
	private TextView mNewTitle;
	private TextView mNewSubTitle;
	private WebView mJjtzView;
	
	private ListView mNewList;
	private ArrayList<NewsV1> mNewsV1 = new ArrayList<NewsV1>();
	private ArrayList<NewsV2> mNewsV2 = new ArrayList<NewsV2>();
	private NewsAdapter<NewsV1> mAdapterV1;
	private NewsAdapter<NewsV2> mAdapterV2;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		configIntent(getIntent());
		setContentView(R.layout.bd_activity_news_detail);
		
		mTitle = (TextView) findViewById(R.id.title);
		mTitle.setText(mXxlx.getLxmc());
		mJjtzView = (WebView) findViewById(R.id.wv_news_content);
		mJjtzView.getSettings().setJavaScriptEnabled(true);
		mNewTitle = (TextView) findViewById(R.id.tv_new_title);
		mNewSubTitle = (TextView) findViewById(R.id.tv_name_and_time);
		
		mNewList = (ListView) findViewById(R.id.lv_news_list);
		mNewList.setOnItemClickListener(this);
		configAdapter();
	}
	
	private void configAdapter() {
		if(mXxlx == Xxlx.SQXW){
			mNewList.setAdapter(mAdapterV2 = new NewsAdapter<NewsV2>(this));
		}else{
			mNewList.setAdapter(mAdapterV1 = new NewsAdapter<NewsV1>(this));
		}
	}

	private void configIntent(Intent intent) {
		mXxlx = (Xxlx) intent.getSerializableExtra("xxlx");
		if(mXxlx == null) finish();
	}

	@Override
	public void onHttpError(Exception e, String msg, int requestCode) {

	}

	@Override
	public void onDecoded(String reason, boolean isSuccess,
			JsonObject mJsonResult, JsonArray mLists, int resultCode) {
		if(!isSuccess){
			errorMsg(true,reason);
			return;
		}
		if(Constants.REQUEST_CODE_GETNOTICEBYXQID == resultCode){
			mNewsV1 = PublicInterfaceFactory.getNewsV1FromJson(mLists);
			if(mNewsV1.size() > 0){
				mAdapterV1.setData(mNewsV1);
				configContent(mNewsV1.get(0).getTitle(),mNewsV1.get(0).getCjsj(),mNewsV1.get(0).getContentUrl());
			}
		}
		if(Constants.REQUEST_CODE_GETNEWSBYXQID == resultCode){
			mNewsV2 = PublicInterfaceFactory.getNewsV2FromJson(mLists);
			if(mNewsV2.size() > 0){
				mAdapterV2.setData(mNewsV2);
				configContent(mNewsV2.get(0).getTitle(),mNewsV2.get(0).getCjsj(),mNewsV2.get(0).getContentUrl());
			}
		}
	}

	private void configContent(String title, String subTitle, String contentUrl) {
		mNewSubTitle.setText(subTitle);
		mNewTitle.setText(title);
		mJjtzView.loadUrl("http://"+Constants.BD_BASE_IP_PORT+"/ZHSQ/"+ contentUrl);
	}

	@Override
	public void submitOrder() {

	}

	@Override
	public void loadData() {
		if(mXxlx == Xxlx.SQXW){
			loadSqxwDef();
		}else{
			loadNewsV1Def();
		}
	}
	
	private void loadSqxwDef() {
		loadSqxw(getUser().getSqid(), 1, 10, "cjsj desc");
	}

	private void loadNewsV1Def() {
		loadNewsV1(mXxlx.getValue(),getUser().getSqid(),1,10,"cjsj desc");
	}

	private void loadNewsV1(int xxlx,String sqid,int currentPageNum,int pageSize, String px) {
		sendData(buildParams(xxlx, sqid, currentPageNum,pageSize, px),
				Constants.URL_GETNOTICEBYXQID, this, Constants.REQUEST_CODE_GETNOTICEBYXQID);
	}

	private void loadSqxw(String xqid,int currentPageNum,int pageSize,String px) {
		sendData(buildSqxwParams(xqid, currentPageNum, pageSize, px), Constants.URL_GETNEWSBYXQID, this, Constants.REQUEST_CODE_GETNEWSBYXQID);
	}
	
	private ArrayList<NameValuePair> buildSqxwParams(String xqid,int currentPageNum,int pageSize,String px){
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("xqid", xqid));
		params.add(new BasicNameValuePair("currentPageNum", currentPageNum+""));
		params.add(new BasicNameValuePair("pageSize", pageSize+""));
		params.add(new BasicNameValuePair("px", px));
		return params;
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(mXxlx == Xxlx.SQXW){
			configContent(mNewsV2.get(position).getTitle(),mNewsV2.get(position).getCjrid()+"		"+mNewsV2.get(position).getCjsj()
					,mNewsV2.get(position).getContentUrl());
		}else{
			configContent(mNewsV1.get(position).getTitle(),mNewsV1.get(position).getCjrid()+"		"+mNewsV1.get(position).getCjsj()
					,mNewsV1.get(position).getContentUrl());
		}
	}
}
