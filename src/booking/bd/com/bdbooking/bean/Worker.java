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
	private String gsid;	//公司信息ID
	private String gsmc;	//公司信息名称
	private String fwpj;	//服务评级
	private String 	qbjg;	//起步价格
	private String	zp;		//照片
	private String	ygbh;	//员工编号
	private String	pxjl;	//培训记录
	private String	grtc;	//个人特长
	private String	xjzdz;	//现居住地址
	private String 	Grnl;	//个人能力
	public String getFwpj() {
		return fwpj;
	}
	public Worker(String id, String xm, String nl, String gznx, String xb,
			String jg, String sjhm, String xl, String grms, String gsid,
			String gsmc, String fwpj, String qbjg, String zp, String ygbh,
			String pxjl, String grtc, String xjzdz, String grnl) {
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
		this.gsid = gsid;
		this.gsmc = gsmc;
		this.fwpj = fwpj;
		this.qbjg = qbjg;
		this.zp = zp;
		this.ygbh = ygbh;
		this.pxjl = pxjl;
		this.grtc = grtc;
		this.xjzdz = xjzdz;
		Grnl = grnl;
	}
	public void setFwpj(String fwpj) {
		this.fwpj = fwpj;
	}
	public String getQbjg() {
		return qbjg;
	}
	public void setQbjg(String qbjg) {
		this.qbjg = qbjg;
	}
	public String getZp() {
		return zp;
	}
	public void setZp(String zp) {
		this.zp = zp;
	}
	public String getYgbh() {
		return ygbh;
	}
	public void setYgbh(String ygbh) {
		this.ygbh = ygbh;
	}
	public String getPxjl() {
		return pxjl;
	}
	public void setPxjl(String pxjl) {
		this.pxjl = pxjl;
	}
	public String getGrtc() {
		return grtc;
	}
	public void setGrtc(String grtc) {
		this.grtc = grtc;
	}
	public String getXjzdz() {
		return xjzdz;
	}
	public void setXjzdz(String xjzdz) {
		this.xjzdz = xjzdz;
	}
	public String getGrnl() {
		return Grnl;
	}
	public void setGrnl(String grnl) {
		Grnl = grnl;
	}
	public void setGsid(String gsid) {
		this.gsid = gsid;
	}
	public Worker(String id, String xm, String nl, String gznx, String xb,
			String jg, String sjhm, String xl, String grms, String gsid,
			String gsmc) {
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
		this.gsid = gsid;
		this.gsmc = gsmc;
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
	public String getGsid() {
		return gsid;
	}
	public void setGsxxid(String gsid) {
		this.gsid = gsid;
	}
	public String getGsmc() {
		return gsmc;
	}
	public void setGsmc(String gsmc) {
		this.gsmc = gsmc;
	}
	public float getStar(){
		try {
			return Float.parseFloat(fwpj);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
