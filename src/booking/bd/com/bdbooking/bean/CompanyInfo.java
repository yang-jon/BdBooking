package booking.bd.com.bdbooking.bean;

import android.util.Log;


public class CompanyInfo {
	private String gsid; 	//公司id
	private String gsmc;	//公司名称
	private String Icon;	//公司的logo
	private String gsdh;	//公司电话
	private float minjg;	//价格区间的最小值
	private float maxjg;	//价格区间的最大值
	private String	ms;		//公司描述
	private String fwpj;	//服务评级
	private String gsxz;	//公司性质
	public CompanyInfo(String gsid, String gsmc, String icon, String gsdh,
			float minjg, float maxjg, String ms, String fwpj, String gsxz) {
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
	
	public CompanyInfo(String gsmc, String fwpj) {
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
	public String getFwpj() {
		return fwpj;
	}
	public void setFwpj(String fwpj) {
		this.fwpj = fwpj;
	}
	public String getGsxz() {
		return gsxz;
	}
	public void setGsxz(String gsxz) {
		this.gsxz = gsxz;
	}
	public float getPj(){
		float pj = 1;
		try {
			pj = Float.parseFloat(getFwpj());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return pj;
	}

}
