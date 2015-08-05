package booking.bd.com.bdbooking.bean;


public class CompanyInfo {
	private String gsid; 	//公司id
	private String gsmc;	//公司名称
	private String Icon;	//公司的logo
	private String gsdh;	//公司电话
	private double minjg;	//价格区间的最小值
	private double maxjg;	//价格区间的最大值
	private String	ms;		//公司描述
	private float fwpj;		//服务评级
	private String gsxz;	//公司性质
	public CompanyInfo(String gsid, String gsmc, String icon, String gsdh,
			double minjg, double maxjg, String ms, float fwpj, String gsxz) {
		super();
		this.gsid = gsid;
		this.gsmc = gsmc;
		Icon = icon;
		this.gsdh = gsdh;
		this.minjg = minjg;
		this.maxjg = maxjg;
		this.ms = ms;
		this.fwpj = fwpj;
		this.gsxz = gsxz;
	}
	
	public CompanyInfo(String gsmc, float fwpj) {
		super();
		this.gsmc = gsmc;
		this.fwpj = fwpj;
	}

	public String getGsid() {
		return gsid;
	}
	public void setGsid(String gsid) {
		this.gsid = gsid;
	}
	public String getGsmc() {
		return gsmc;
	}
	public void setGsmc(String gsmc) {
		this.gsmc = gsmc;
	}
	public String getIcon() {
		return Icon;
	}
	public void setIcon(String icon) {
		Icon = icon;
	}
	public String getGsdh() {
		return gsdh;
	}
	public void setGsdh(String gsdh) {
		this.gsdh = gsdh;
	}
	public double getMinjg() {
		return minjg;
	}
	public void setMinjg(double minjg) {
		this.minjg = minjg;
	}
	public double getMaxjg() {
		return maxjg;
	}
	public void setMaxjg(double maxjg) {
		this.maxjg = maxjg;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public float getFwpj() {
		return fwpj;
	}
	public void setFwpj(float fwpj) {
		this.fwpj = fwpj;
	}
	public String getGsxz() {
		return gsxz;
	}
	public void setGsxz(String gsxz) {
		this.gsxz = gsxz;
	}
	

}
