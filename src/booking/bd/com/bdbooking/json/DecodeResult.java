package booking.bd.com.bdbooking.json;

import android.util.Log;
import booking.bd.com.bdbooking.utils.JsonUtil;
import booking.bd.com.bdbooking.utils.LogUtils;
import booking.bd.com.bdbooking.utils.XutilHttpPack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class DecodeResult {
	
	public static void decoResult(String result,IDecodeJson mDecoded){
		Log.i("DecodeResult", "result :: "+ result);
		JsonObject mJsonObject = JsonUtil.parseObject(result);
		JsonArray mJsonArray = JsonUtil.parseArray(result);
		Log.i("DecodeResult", "mJsonObject :: "+ mJsonObject);
		Log.i("DecodeResult", "mJsonArray :: "+ mJsonArray);
		if(null != mJsonObject && null != JsonUtil.getAsString(mJsonObject, "errorMessage")){
			mDecoded.onDecoded(JsonUtil.getAsString(mJsonObject, "errorMessage"), false, mJsonObject, mJsonArray);
		}else{
			mDecoded.onDecoded(result, true, mJsonObject, mJsonArray);
		}
	}
}
