package booking.bd.com.bdbooking.bean;

public class JzProjectInfo {
	public String id;
	public String pro;
	public String IconUri;
	public String remark;
	public JzProjectInfo(String id, String pro, String iconUri, String remark) {
		super();
		this.id = id;
		this.pro = pro;
		IconUri = iconUri;
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPro() {
		return pro;
	}
	public void setPro(String pro) {
		this.pro = pro;
	}
	public String getIconUri() {
		return IconUri;
	}
	public void setIconUri(String iconUri) {
		IconUri = iconUri;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
