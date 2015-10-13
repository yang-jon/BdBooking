package booking.bd.com.bdbooking.bean;

import booking.bd.com.bdbooking.utils.BdJsonTimeFormater;
import android.text.TextUtils;

public class OrderTime {
	private String	id;		//数据ID
	private String 	yysjd;	//预约的时间段
	public OrderTime(String id, String yysjd) {
		super();
		this.id = id;
		this.yysjd = yysjd;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYysjd() {
		return yysjd;
	}
	public void setYysjd(String yysjd) {
		this.yysjd = yysjd;
	}
	public String getOrderTimeBegin(){
		if(!TextUtils.isEmpty(yysjd)){
			String[] split = yysjd.split("~");
			if(null != split && split.length >1){
				return split[0];
			}
		}
		return null;
	}
	public String getOrderTimeEnd(){
		if(!TextUtils.isEmpty(yysjd)){
			String[] split = yysjd.split("~");
			if(null != split && split.length >2){
				return split[1];
			}
		}
		return null;
	}
	public String getShowTime(){
		/*if(!TextUtils.isEmpty(yysjd)){
			return BdJsonTimeFormater.format(yysjd);
		}*/
		return yysjd;
	}
	
}
