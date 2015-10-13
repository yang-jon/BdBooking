package booking.bd.com.bdbooking.bean;

import java.util.ArrayList;

import com.google.gson.JsonArray;

import booking.bd.com.bdbooking.utils.JsonUtil;
import booking.bd.com.bdbooking.utils.PublicInterfaceFactory;
import android.text.TextUtils;

public class JzProjectInfo {
	public String id;
	public String pro;
	public String IconUri;
	public String remark;
	public JsonArray children;
	public JzProjectInfo(String id, String pro, String iconUri, String remark, JsonArray children) {
		super();
		this.id = id;
		this.pro = pro;
		IconUri = iconUri;
		this.remark = remark;
		this.children = children;
	}
	
	public JzProjectInfo(String id, String pro, String iconUri, String remark) {
		super();
		this.id = id;
		this.pro = pro;
		IconUri = iconUri;
		this.remark = remark;
	}
	
	
	@Override
	public String toString() {
		return "JzProjectInfo [id=" + id + ", pro=" + pro + ", IconUri="
				+ IconUri + ", remark=" + remark + ", children=" + children
				+ "]";
	}

	public JsonArray getChildren() {
		return children;
	}
	public void setChildren(JsonArray children) {
		this.children = children;
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
	
	public ArrayList<JzProjectInfo> configChildren(){
		if(!hasChildren()){
			return null;
		}
		return PublicInterfaceFactory.getJzProjectFromJson(children);
	}
	
	public boolean hasChildren(){
		return !children.isJsonNull() && children.size()>0;
	}
}
