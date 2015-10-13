package booking.bd.com.bdbooking.bean;

public class NewsV1 {
	private String	id;		//信息ID
	private String 	title;	//信息标题
	private String	contentUrl;	//信息内容
	private String	cjsj;	//发布时间，格式为HH:mm:ss 字符串
	private String	cjrid;	//发布人
	private String	fbjg;	//布机构
	private String	zzrq;	//终止日期，格式为HH:mm:ss 字符串
	public NewsV1(String id, String title, String contentUrl, String cjsj,
			String cjrid, String fbjg, String zzrq) {
		super();
		this.id = id;
		this.title = title;
		this.contentUrl = contentUrl;
		this.cjsj = cjsj;
		this.cjrid = cjrid;
		this.fbjg = fbjg;
		this.zzrq = zzrq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	public String getCjsj() {
		return cjsj;
	}
	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}
	public String getCjrid() {
		return cjrid;
	}
	public void setCjrid(String cjrid) {
		this.cjrid = cjrid;
	}
	public String getFbjg() {
		return fbjg;
	}
	public void setFbjg(String fbjg) {
		this.fbjg = fbjg;
	}
	public String getZzrq() {
		return zzrq;
	}
	public void setZzrq(String zzrq) {
		this.zzrq = zzrq;
	}
}
