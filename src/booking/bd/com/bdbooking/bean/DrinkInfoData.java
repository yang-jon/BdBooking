package booking.bd.com.bdbooking.bean;

public class DrinkInfoData {
	private String	id;		//字符串
	private String	fwlbid;	//服务类别id	字符串
	private String	fwlbmc;	//服务类别名称	字符串
	private String	ms;		//描述	字符串
	private float	jg;		//价格	数字
	private	float hdjg;		//活动价格	数字
	
	public DrinkInfoData(String id, String fwlbid, String fwlbmc, String ms,
			float jg, float hdjg) {
		super();
		this.id = id;
		this.fwlbid = fwlbid;
		this.fwlbmc = fwlbmc;
		this.ms = ms;
		this.jg = jg;
		this.hdjg = hdjg;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFwlbid() {
		return fwlbid;
	}
	public void setFwlbid(String fwlbid) {
		this.fwlbid = fwlbid;
	}
	public String getFwlbmc() {
		return fwlbmc;
	}
	public void setFwlbmc(String fwlbmc) {
		this.fwlbmc = fwlbmc;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public float getJg() {
		return jg;
	}
	public void setJg(float jg) {
		this.jg = jg;
	}
	public float getHdjg() {
		return hdjg;
	}
	public void setHdjg(float hdjg) {
		this.hdjg = hdjg;
	}
	
}
