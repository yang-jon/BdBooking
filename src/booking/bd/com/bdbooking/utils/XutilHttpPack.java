package booking.bd.com.bdbooking.utils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.apache.http.NameValuePair;

import java.io.File;
import java.util.List;

public class XutilHttpPack {
	
	private final String HTTP_TAG = "BD-HTTP";
	
	private HttpUtils mHttPUtils;
	
	public interface OnHttpActionCallBack{
		public void onHttpSuccess(String result);
		public void onHttpError(HttpException e, String messge);
	}
	
	public XutilHttpPack(){
		mHttPUtils =  new HttpUtils(); 
		mHttPUtils.configTimeout(10*1000);
	}
	public HttpUtils getHttpUtils(){
		return mHttPUtils;
	}

	//用于使用body多个params的用法
	public HttpHandler<String> sendData(List<NameValuePair> nameValuePairs,String url,final OnHttpActionCallBack httpCallBack ){
		LogUtils.d(HTTP_TAG, nameValuePairs.toString());
		RequestParams mRequest = getSendDataRequestParams(nameValuePairs,null);
		return sendData(mRequest,url,httpCallBack);
	}

	//用于使用单个param 然后用body封装
	public HttpHandler<String> sendData(String nameValuePairs,String url,final OnHttpActionCallBack httpCallBack ){
		LogUtils.d(HTTP_TAG, nameValuePairs.toString());
		RequestParams mRequest = getSendDataRequestParams(nameValuePairs,null);
		return sendData(mRequest,url,httpCallBack);
	}
	private HttpHandler<String> sendData(RequestParams mRequest,String url,final OnHttpActionCallBack httpCallBack ){
		HttpHandler<String> mHandler = mHttPUtils.send(HttpMethod.POST, url, mRequest, new  RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				httpCallBack.onHttpSuccess(responseInfo.result);
				LogUtils.d(HTTP_TAG, responseInfo.result);
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
			}
			@Override
			public void onFailure(HttpException error, String msg) {
				httpCallBack.onHttpError(error, msg);
				
			}
		});
		return mHandler;
	}
	
	
	public HttpHandler<String> sendDataAndImgs(List<NameValuePair> nameValuePairs,String url,List<File> mListFiles ,RequestCallBack<String> mRequestCallBack ){
		RequestParams mRequest = getSendDataRequestParams(nameValuePairs,mListFiles);
		return mHttPUtils.send(HttpMethod.POST, url,mRequest, mRequestCallBack);
	}
	
	public HttpHandler<String> sendDataAndImgs(String mData,String url,List<File> mListFiles ,RequestCallBack<String> mRequestCallBack ){
		RequestParams mRequest = getSendDataRequestParams(mData,mListFiles);
		return mHttPUtils.send(HttpMethod.POST, url,mRequest, mRequestCallBack);
	}
	
	public RequestParams getSendDataRequestParams(String data,List<File> mListFiles){
		RequestParams mRequest = new RequestParams();
		mRequest.addBodyParameter("body", data);
		if(mListFiles!=null){
			for(int i=0;i<mListFiles.size();i++){
				mRequest.addBodyParameter("img"+i, mListFiles.get(i));
			}
		}
		return mRequest;
	}
	
	public RequestParams getSendDataRequestParams(List<NameValuePair> nameValuePairs,List<File> mListFiles){
		RequestParams mRequest = new RequestParams();
		mRequest.addBodyParameter(nameValuePairs);
		if(mListFiles!=null){
			for(int i=0;i<mListFiles.size();i++){
				mRequest.addBodyParameter("img"+i, mListFiles.get(i));
			}
		}
		return mRequest;
	}
	
	public void destroy(){
	}

}
