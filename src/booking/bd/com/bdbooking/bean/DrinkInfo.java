package booking.bd.com.bdbooking.bean;

import java.util.ArrayList;

public class DrinkInfo {
	private String	id;		//	字符串
	private String	fwlbid;	//服务类别id	字符串
	private String	fwlbmc;	//服务类别名称	字符串
	private float	minjg;	//最低价格	数字
	private float	maxjg;	//最大价格	数字
	private String	ms;		//描述	字符串
	
	private ArrayList<DrinkInfoData>	children;	//子节点	
	
	public DrinkInfo(String id, String fwlbid, String fwlbmc, float minjg,
			float maxjg, String ms, ArrayList<DrinkInfoData> children) {
		super();
		this.id = id;
		this.fwlbid = fwlbid;
		this.fwlbmc = fwlbmc;
		this.minjg = minjg;
		this.maxjg = maxjg;
		this.ms = ms;
		this.children = children;
	}
	
	public DrinkInfo(String id, String fwlbid, String fwlbmc, float minjg,
			float maxjg, String ms) {
		super();
		this.id = id;
		this.fwlbid = fwlbid;
		this.fwlbmc = fwlbmc;
		this.minjg = minjg;
		this.maxjg = maxjg;
		this.ms = ms;
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
	public float getMinjg() {
		return minjg;
	}
	public void setMinjg(float minjg) {
		this.minjg = minjg;
	}
	public float getMaxjg() {
		return maxjg;
	}
	public void setMaxjg(float maxjg) {
		this.maxjg = maxjg;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public ArrayList<DrinkInfoData> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<DrinkInfoData> children) {
		this.children = children;
	}
	
	public boolean hasChild(){
		return children != null && children.size()>0;
	}
}
