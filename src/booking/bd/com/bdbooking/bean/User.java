package booking.bd.com.bdbooking.bean;

import java.util.ArrayList;

public class User {
	private String	id;		//用户ID
	private String	yhm;	//登录系统的用户名
	private String	nc;		//昵称
	private String	xb;		//性别
	private String	sjhm;	//手机号码
	private String	tx;		//头像，此返回的登录用户的头像的访问地址
	private String	sqmc;	//小区名称
	private String	fdmc;	//分栋名称
	private String	dymc;	//单元名称
	private String	mph;	//门牌号
//	private ArrayList<Order>	wcdd;	//完成订单(具体信息参见订单详情)
//	private ArrayList<Order>	wwcdd;	//未完成订单(具体信息参见订单详情)
	private String sqid;
	
	public User(String id, String yhm, String nc, String xb, String sjhm,
			String tx, String sqmc, String fdmc, String dymc, String mph,
			/*ArrayList<Order> wcdd, ArrayList<Order> wwcdd,*/String sqid) {
		super();
		this.id = id;
		this.yhm = yhm;
		this.nc = nc;
		this.xb = xb;
		this.sjhm = sjhm;
		this.tx = tx;
		this.sqmc = sqmc;
		this.fdmc = fdmc;
		this.dymc = dymc;
		this.mph = mph;
//		this.wcdd = wcdd;
//		this.wwcdd = wwcdd;
		this.sqid = sqid;
	}
	public String getSqid() {
		return sqid;
	}
	public void setSqid(String sqid) {
		this.sqid = sqid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYhm() {
		return yhm;
	}
	public void setYhm(String yhm) {
		this.yhm = yhm;
	}
	public String getNc() {
		return nc;
	}
	public void setNc(String nc) {
		this.nc = nc;
	}
	public String getXb() {
		return xb;
	}
	public void setXb(String xb) {
		this.xb = xb;
	}
	public String getSjhm() {
		return sjhm;
	}
	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	public String getTx() {
		return tx;
	}
	public void setTx(String tx) {
		this.tx = tx;
	}
	public String getSqmc() {
		return sqmc;
	}
	public void setSqmc(String sqmc) {
		this.sqmc = sqmc;
	}
	public String getFdmc() {
		return fdmc;
	}
	public void setFdmc(String fdmc) {
		this.fdmc = fdmc;
	}
	public String getDymc() {
		return dymc;
	}
	public void setDymc(String dymc) {
		this.dymc = dymc;
	}
	public String getMph() {
		return mph;
	}
	public void setMph(String mph) {
		this.mph = mph;
	}
/*	public ArrayList<Order> getWcdd() {
		return wcdd;
	}
	public void setWcdd(ArrayList<Order> wcdd) {
		this.wcdd = wcdd;
	}
	public ArrayList<Order> getWwcdd() {
		return wwcdd;
	}
	public void setWwcdd(ArrayList<Order> wwcdd) {
		this.wwcdd = wwcdd;
	}*/
	
}
