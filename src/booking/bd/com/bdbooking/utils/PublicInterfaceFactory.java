package booking.bd.com.bdbooking.utils;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import booking.bd.com.bdbooking.BdApplication;
import booking.bd.com.bdbooking.OnHttpActionListener;
import booking.bd.com.bdbooking.activitys.OrderFormActivity;
import booking.bd.com.bdbooking.bean.CompanyInfo;
import booking.bd.com.bdbooking.bean.JzProjectInfo;
import booking.bd.com.bdbooking.bean.Worker;
import booking.bd.com.bdbooking.json.DecodeResult;
import booking.bd.com.bdbooking.json.IDecodeJson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lidroid.xutils.exception.HttpException;

public class PublicInterfaceFactory {

	private static final String TAG = "PublicInterfaceFactory";
	
	public static Intent createOrderFormIntent(Context context) {
		return new Intent(context, OrderFormActivity.class);
	}

	
	public static ArrayList<JzProjectInfo> getJzProjectFromJson(JsonObject json){
		Log.i(TAG, json.toString());
		Gson gson = new Gson();
		JsonArray jsons = json.getAsJsonArray();
		ArrayList<JzProjectInfo> infos = new ArrayList<JzProjectInfo>();
		for (int i = 0; i < jsons.size(); i++) {
			JsonElement jsonElement = jsons.get(i);
			infos.add(gson.fromJson(jsonElement, JzProjectInfo.class));
		}
		return infos;
	}
	
	public static ArrayList<CompanyInfo> getJzCompanyInfoFromJson(JsonObject json){
		Log.i(TAG, json.toString());
		Gson gson = new Gson();
		JsonArray jsons = json.getAsJsonArray();
		ArrayList<CompanyInfo> infos = new ArrayList<CompanyInfo>();
		for (int i = 0; i < jsons.size(); i++) {
			JsonElement jsonElement = jsons.get(i);
			infos.add(gson.fromJson(jsonElement, CompanyInfo.class));
		}
		return infos;
	}
	
	public static ArrayList<Worker> getJzWorkerFromJson(JsonObject json){
		Log.i(TAG, json.toString());
		Gson gson = new Gson();
		JsonArray jsons = json.getAsJsonArray();
		ArrayList<Worker> infos = new ArrayList<Worker>();
		for (int i = 0; i < jsons.size(); i++) {
			JsonElement jsonElement = jsons.get(i);
			infos.add(gson.fromJson(jsonElement, Worker.class));
		}
		return infos;
	}
	
	public static JsonObject getJzwfProject() {

		return null;
	}
	public static JsonObject getJzfwryxxByGsID(){
		
		return null;
	}
	public void sendData(String data, String url,
			final OnHttpActionListener mTatget, final int code) {

		BdApplication.getInstance().mHttpPack.sendData(data, url,
				new XutilHttpPack.OnHttpActionCallBack() {

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
}
