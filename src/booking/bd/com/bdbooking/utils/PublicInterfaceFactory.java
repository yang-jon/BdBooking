package booking.bd.com.bdbooking.utils;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import booking.bd.com.bdbooking.activitys.NewsDetailsActivity;
import booking.bd.com.bdbooking.activitys.NewsReportActivity.Xxlx;
import booking.bd.com.bdbooking.activitys.OrderFormActivity;
import booking.bd.com.bdbooking.bean.CompanyInfo;
import booking.bd.com.bdbooking.bean.DrinkInfoDataV2;
import booking.bd.com.bdbooking.bean.DrinkInfoV2;
import booking.bd.com.bdbooking.bean.Evaluation;
import booking.bd.com.bdbooking.bean.JzProjectInfo;
import booking.bd.com.bdbooking.bean.NewsV1;
import booking.bd.com.bdbooking.bean.NewsV2;
import booking.bd.com.bdbooking.bean.OrderTime;
import booking.bd.com.bdbooking.bean.OrderV2;
import booking.bd.com.bdbooking.bean.User;
import booking.bd.com.bdbooking.bean.Worker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PublicInterfaceFactory {

	private static final String TAG = "PublicInterfaceFactory";
	
	public static Intent createOrderFormIntent(Context context) {
		return new Intent(context, OrderFormActivity.class);
	}

	public static Evaluation getEvaDataFromJson(JsonArray json){
//		String result = "{\"datasum\":\"1\",\"pagesum\":\"0\",\"data\":[{\"pjsj\":\"2015-08-27 03:19:28\",\"fwzlpj\":6.0,\"pjms\":\"测试\",\"pjryxm\":\"13621110024\"}]}";
//		json = JsonUtil.parseObject(result);
		Log.i(TAG, json.toString());
		JsonObject jObj = JsonUtil.getAsJsonObject(json.get(0).getAsJsonObject(), "data");
		Gson gson = new Gson();
		return gson.fromJson(jObj, Evaluation.class);
	}
	
	public static Evaluation getEvaFromJson(JsonArray json){
//		String result = "{\"datasum\":\"1\",\"pagesum\":\"0\",\"data\":[{\"pjsj\":\"2015-08-27 03:19:28\",\"fwzlpj\":6.0,\"pjms\":\"测试\",\"pjryxm\":\"13621110024\"}]}";
//		json = JsonUtil.parseObject(result);
		Log.i(TAG, json.toString());
		JsonObject jObj = json.get(0).getAsJsonObject();
		Gson gson = new Gson();
		return gson.fromJson(jObj, Evaluation.class);
	}
	
	public static ArrayList<JzProjectInfo> getJzProjectFromJson(JsonArray json){
		Log.i(TAG, json.toString());
		Gson gson = new Gson();
		ArrayList<JzProjectInfo> infos = new ArrayList<JzProjectInfo>();
		for (int i = 0; i < json.size(); i++) {
			JsonElement jsonElement = json.get(i);
			infos.add(gson.fromJson(jsonElement, JzProjectInfo.class));
		}
		return infos;
	}
	
	public static ArrayList<DrinkInfoV2> getDrinkInfoV2FromJson(JsonArray json){
		Log.i(TAG, json.toString());
		Gson gson = new Gson();
		ArrayList<DrinkInfoV2> infos = new ArrayList<DrinkInfoV2>();
		for (int i = 0; i < json.size(); i++) {
			JsonElement jsonElement = json.get(i);
			infos.add(gson.fromJson(jsonElement, DrinkInfoV2.class));
		}
		return infos;
	}
	
	public static ArrayList<CompanyInfo> getJzCompanyInfoFromJson(JsonArray json){
		Log.i(TAG, json.toString());
		Gson gson = new Gson();
		ArrayList<CompanyInfo> infos = new ArrayList<CompanyInfo>();
		for (int i = 0; i < json.size(); i++) {
			JsonElement jsonElement = json.get(i);
			infos.add(gson.fromJson(jsonElement, CompanyInfo.class));
		}
		return infos;
	}
	
	public static ArrayList<Worker> getJzWorkerFromJson(JsonArray json){
		Log.i(TAG, json.toString());
		Gson gson = new Gson();
		ArrayList<Worker> infos = new ArrayList<Worker>();
		for (int i = 0; i < json.size(); i++) {
			JsonElement jsonElement = json.get(i);
			infos.add(gson.fromJson(jsonElement, Worker.class));
		}
		return infos;
	}
	
	public static ArrayList<DrinkInfoDataV2> getDrinkInfoDataV2FromJson(JsonArray json){
		Log.i(TAG, json.toString());
		Gson gson = new Gson();
		ArrayList<DrinkInfoDataV2> infos = new ArrayList<DrinkInfoDataV2>();
		for (int i = 0; i < json.size(); i++) {
			JsonElement jsonElement = json.get(i);
			infos.add(gson.fromJson(jsonElement, DrinkInfoDataV2.class));
		}
		return infos;
	}
	
	public static ArrayList<OrderV2> getOrderFromJson(JsonArray json){
		Log.i(TAG, json.toString());
		Gson gson = new Gson();
		ArrayList<OrderV2> infos = new ArrayList<OrderV2>();
		for (int i = 0; i < json.size(); i++) {
			JsonElement jsonElement = json.get(i);
			infos.add(gson.fromJson(jsonElement, OrderV2.class));
		}
		return infos;
	}
	
	public static ArrayList<OrderTime> getTimesFromJson(JsonArray json){
		Log.i(TAG, json.toString());
		ArrayList<OrderTime> times = new ArrayList<OrderTime>();
		Gson gson = new Gson();
		for (int i = 0; i < json.size(); i++) {
			JsonElement jsonElement = json.get(i);
			times.add(gson.fromJson(jsonElement, OrderTime.class));
		}
		return times;
	}
	
	public static String getSuccessMsgFronJson(JsonObject json){
		return JsonUtil.getAsString(json, "successMessage");
	}
	
	public static User getUserInfoFromJson(JsonObject json){
		Log.i(TAG, json.toString());
		Gson gson = new Gson();
		return gson.fromJson(json, User.class);
	}
	
	public static ArrayList<NewsV1> getNewsV1FromJson(JsonArray json){
		Log.i(TAG, json.toString());
		ArrayList<NewsV1> news = new ArrayList<NewsV1>();
		Gson gson = new Gson();
		for (int i = 0; i < json.size(); i++) {
			JsonElement jsonElement = json.get(i);
			news.add(gson.fromJson(jsonElement, NewsV1.class));
		}
		return news;
	}
	
	public static ArrayList<NewsV2> getNewsV2FromJson(JsonArray json){
		Log.i(TAG, json.toString());
		ArrayList<NewsV2> news = new ArrayList<NewsV2>();
		Gson gson = new Gson();
		for (int i = 0; i < json.size(); i++) {
			JsonElement jsonElement = json.get(i);
			news.add(gson.fromJson(jsonElement, NewsV2.class));
		}
		return news;
	}
	
	public static Intent createNewsDetailsIntent(Context context,Xxlx xxlx){
		Intent in = new Intent(context,NewsDetailsActivity.class);
		in.putExtra("xxlx", xxlx);
		return in;
	}
}
