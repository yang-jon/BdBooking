package booking.bd.com.bdbooking.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface IDecodeJson {
	
	public void onDecoded(String reason, boolean isSuccess, JsonObject mJsonResult, JsonArray mLists);
}
