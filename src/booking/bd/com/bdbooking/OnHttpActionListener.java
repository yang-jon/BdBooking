package booking.bd.com.bdbooking;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface OnHttpActionListener {

	public void onHttpError(Exception e, String msg, int requestCode);
	public void onDecoded(String reason, boolean isSuccess, JsonObject mJsonResult, JsonArray mLists, int resultCode);
}
