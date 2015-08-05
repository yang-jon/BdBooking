package booking.bd.com.bdbooking.activitys;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import booking.bd.com.bdbooking.R;
import android.os.Bundle;

public class OrderFormActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bd_activity_order_form);
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
