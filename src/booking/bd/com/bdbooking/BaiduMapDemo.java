package booking.bd.com.bdbooking;

import java.util.List;
import java.util.Formatter.BigDecimalLayoutForm;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

public class BaiduMapDemo {
	public static void search(){
		Log.i("yangzheng", "search :: ");
		PoiSearch psearch = PoiSearch.newInstance();
		
		psearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
			
			@Override
			public void onGetPoiResult(PoiResult result) {
				Log.i("yangzheng", "onGetPoiResult :: "+result);
				List<PoiInfo> allPoi = result.getAllPoi();
				for (PoiInfo poiInfo : allPoi) {
					Log.i("yangzheng", poiInfo.name);
				}
			}
			
			@Override
			public void onGetPoiDetailResult(PoiDetailResult result) {
				Log.i("yangzheng", "onGetPoiDetailResult :: "+result);
			}
		});
//		psearch.searchNearby(new PoiNearbySearchOption().location().keyword("小区").pageNum(10));
//		psearch.destroy();
	}
}
