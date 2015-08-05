package booking.bd.com.bdbooking.json;

import booking.bd.com.bdbooking.utils.JsonUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class DecodeResult {
	
	public static void decoResult(String result,IDecodeJson mDecoded){
		JsonObject mJsonObject = JsonUtil.parse(result);
		
		if(mJsonObject ==null){
			return;
			
		}
		boolean isSuccess = JsonUtil.getAsBoolean(mJsonObject, "result");
		String reason = JsonUtil.getAsString(mJsonObject, "reason");
		JsonObject mJsonData = JsonUtil.getAsJsonObject(mJsonObject, "data");
		JsonArray mJsonArray = null;
		if(mJsonData!=null){
			if(mJsonData.has("list"))
			mJsonArray=JsonUtil.getAsJsonArray(mJsonData, "list");
		}
		mDecoded.onDecoded(reason, isSuccess, mJsonData, mJsonArray);
	}
}
