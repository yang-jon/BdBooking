package booking.bd.com.bdbooking.activitys;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import booking.bd.com.bdbooking.R;
import android.os.Bundle;

public class RepairActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.bd_activity_repair);
	}

	@Override
	public void onHttpError(Exception e, String msg, int requestCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDecoded(String reason, boolean isSuccess,
			JsonObject mJsonResult, JsonArray mLists, int resultCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}


}
