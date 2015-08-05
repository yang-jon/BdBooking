package booking.bd.com.bdbooking.bean;


public class Worker {
	private String id;		//人员ID
	private String xm;		//姓名
	private String nl;		//年龄
	private String gznx;	//工作年限
	private String xb;		//性别
	private String jg;		//籍贯
	private String sjhm;	//手机号码
	private String xl;		//学历
	private String grms;	//个人描述
	private String gsxxid;	//公司信息ID
	private String gsxxmc;	//公司信息名称
	public Worker(String id, String xm, String nl, String gznx, String xb,
			String jg, String sjhm, String xl, String grms, String gsxxid,
			String gsxxmc) {
		super();
		this.id = id;
		this.xm = xm;
		this.nl = nl;
		this.gznx = gznx;
		this.xb = xb;
		this.jg = jg;
		this.sjhm = sjhm;
		this.xl = xl;
		this.grms = grms;
		this.gsxxid = gsxxid;
		this.gsxxmc = gsxxmc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getNl() {
		return nl;
	}
	public void setNl(String nl) {
		this.nl = nl;
	}
	public String getGznx() {
		return gznx;
	}
	public void setGznx(String gznx) {
		this.gznx = gznx;
	}
	public String getXb() {
		return xb;
	}
	public void setXb(String xb) {
		this.xb = xb;
	}
	public String getJg() {
		return jg;
	}
	public void setJg(String jg) {
		this.jg = jg;
	}
	public String getSjhm() {
		return sjhm;
	}
	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	public String getXl() {
		return xl;
	}
	public void setXl(String xl) {
		this.xl = xl;
	}
	public String getGrms() {
		return grms;
	}
	public void setGrms(String grms) {
		this.grms = grms;
	}
	public String getGsxxid() {
		return gsxxid;
	}
	public void setGsxxid(String gsxxid) {
		this.gsxxid = gsxxid;
	}
	public String getGsxxmc() {
		return gsxxmc;
	}
	public void setGsxxmc(String gsxxmc) {
		this.gsxxmc = gsxxmc;
	}
}
