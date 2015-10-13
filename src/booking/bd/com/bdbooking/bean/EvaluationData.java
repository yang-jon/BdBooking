package booking.bd.com.bdbooking.bean;

public class EvaluationData {
	private String	pjid;		//评价ID		字符串
	private String	pjryid;		//评价人员ID	字符串
	private String	pjryxm;		//评价人员姓名	字符串
	private float	fwzlpj;		//服务质量评级	数字
	private float 	fwtdpj;		//服务态度评级	数字
	private float 	fwsspj;		//服务守时评级	数字
	private float 	fwxlpj;		//服务效率评级	数字
	private String	pjsj;		//评价时间		字符串
	private String	pjms;		//评价描述		字符串
	
	public EvaluationData(String pjid, String pjryid, String pjryxm,
			float fwzlpj, float fwtdpj, float fwsspj, float fwxlpj,
			String pjsj, String pjms) {
		super();
		this.pjid = pjid;
		this.pjryid = pjryid;
		this.pjryxm = pjryxm;
		this.fwzlpj = fwzlpj;
		this.fwtdpj = fwtdpj;
		this.fwsspj = fwsspj;
		this.fwxlpj = fwxlpj;
		this.pjsj = pjsj;
		this.pjms = pjms;
	}
	public String getPjid() {
		return pjid;
	}
	public void setPjid(String pjid) {
		this.pjid = pjid;
	}
	public String getPjryid() {
		return pjryid;
	}
	public void setPjryid(String pjryid) {
		this.pjryid = pjryid;
	}
	public String getPjryxm() {
		return pjryxm;
	}
	public void setPjryxm(String pjryxm) {
		this.pjryxm = pjryxm;
	}
	public float getFwzlpj() {
		return fwzlpj;
	}
	public void setFwzlpj(float fwzlpj) {
		this.fwzlpj = fwzlpj;
	}
	public float getFwtdpj() {
		return fwtdpj;
	}
	public void setFwtdpj(float fwtdpj) {
		this.fwtdpj = fwtdpj;
	}
	public float getFwsspj() {
		return fwsspj;
	}
	public void setFwsspj(float fwsspj) {
		this.fwsspj = fwsspj;
	}
	public float getFwxlpj() {
		return fwxlpj;
	}
	public void setFwxlpj(float fwxlpj) {
		this.fwxlpj = fwxlpj;
	}
	public String getPjsj() {
		return pjsj;
	}
	public void setPjsj(String pjsj) {
		this.pjsj = pjsj;
	}
	public String getPjms() {
		return pjms;
	}
	public void setPjms(String pjms) {
		this.pjms = pjms;
	}
	
}
