package booking.bd.com.bdbooking.bean;

public class Order {
	
	private String	ddbh;		//订单编号
	private String	sqgsid;		//服务公司ID
	private String	sqgsmc;		//服务公司名称
	private String	sqfwlx;		//服务类型
	private String	sqfwlxmc;	//服务类型名称
	private String	sqsj;		//申请时间
	private String	yyrid;		//预约人ID
	private String	yyrmc;		//预约人名称
	
	public Order(String ddbh, String sqgsid, String sqgsmc, String sqfwlx,
			String sqfwlxmc, String sqsj, String yyrid, String yyrmc) {
		super();
		this.ddbh = ddbh;
		this.sqgsid = sqgsid;
		this.sqgsmc = sqgsmc;
		this.sqfwlx = sqfwlx;
		this.sqfwlxmc = sqfwlxmc;
		this.sqsj = sqsj;
		this.yyrid = yyrid;
		this.yyrmc = yyrmc;
	}
	public String getDdbh() {
		return ddbh;
	}
	public void setDdbh(String ddbh) {
		this.ddbh = ddbh;
	}
	public String getSqgsid() {
		return sqgsid;
	}
	public void setSqgsid(String sqgsid) {
		this.sqgsid = sqgsid;
	}
	public String getSqgsmc() {
		return sqgsmc;
	}
	public void setSqgsmc(String sqgsmc) {
		this.sqgsmc = sqgsmc;
	}
	public String getSqfwlx() {
		return sqfwlx;
	}
	public void setSqfwlx(String sqfwlx) {
		this.sqfwlx = sqfwlx;
	}
	public String getSqfwlxmc() {
		return sqfwlxmc;
	}
	public void setSqfwlxmc(String sqfwlxmc) {
		this.sqfwlxmc = sqfwlxmc;
	}
	public String getSqsj() {
		return sqsj;
	}
	public void setSqsj(String sqsj) {
		this.sqsj = sqsj;
	}
	public String getYyrid() {
		return yyrid;
	}
	public void setYyrid(String yyrid) {
		this.yyrid = yyrid;
	}
	public String getYyrmc() {
		return yyrmc;
	}
	public void setYyrmc(String yyrmc) {
		this.yyrmc = yyrmc;
	}
	
}
