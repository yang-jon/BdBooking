package booking.bd.com.bdbooking.bean;

public class NewsV2 {
	private String id;		//新闻ID
	private String	title;		//新闻标题
	private String	cjsj;		//发布时间，格式为HH:mm:ss 字符串
	private String	cjrid;		//发布人
	private String	ggr;		//供稿人
	private String	titlePicUrl;		//标题图片路径
	private String	contentUrl;		//新闻详情URL地址,格式为http://ip:port/ZHSQ/+ contentUrl
	public NewsV2(String id, String title, String cjsj, String cjrid,
			String ggr, String titlePicUrl, String contentUrl) {
		super();
		this.id = id;
		this.title = title;
		this.cjsj = cjsj;
		this.cjrid = cjrid;
		this.ggr = ggr;
		this.titlePicUrl = titlePicUrl;
		this.contentUrl = contentUrl;
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
	public String getGgr() {
		return ggr;
	}
	public void setGgr(String ggr) {
		this.ggr = ggr;
	}
	public String getTitlePicUrl() {
		return titlePicUrl;
	}
	public void setTitlePicUrl(String titlePicUrl) {
		this.titlePicUrl = titlePicUrl;
	}
	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
}
