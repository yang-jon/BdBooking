package booking.bd.com.bdbooking.bean;

import com.google.gson.JsonObject;

public class OrderV2 {
	private String id; // 订单id
	private String ddbh; // 订单编号
	private String sqsj; // 申请时间
	private String fwdz; // 服务地址
	private String ddje; // 订单金额
	private String yldh; // 预留电话
	private int ddzt; // 订单状态（）
	private int fwlx; // 服务类型1==家政服务 2==维修服务 3==送水服务
	private JsonObject fwmx; // 服务明细（具体参见对应的明细描述）

	public OrderV2(String id, String ddbh, String sqsj, String fwdz,
			String ddje, String yldh, int ddzt, int fwlx, JsonObject fwmx) {
		super();
		this.id = id;
		this.ddbh = ddbh;
		this.sqsj = sqsj;
		this.fwdz = fwdz;
		this.ddje = ddje;
		this.yldh = yldh;
		this.ddzt = ddzt;
		this.fwlx = fwlx;
		this.fwmx = fwmx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDdbh() {
		return ddbh;
	}

	public void setDdbh(String ddbh) {
		this.ddbh = ddbh;
	}

	public String getSqsj() {
		return sqsj;
	}

	public void setSqsj(String sqsj) {
		this.sqsj = sqsj;
	}

	public String getFwdz() {
		return fwdz;
	}

	public void setFwdz(String fwdz) {
		this.fwdz = fwdz;
	}

	public String getDdje() {
		return ddje;
	}

	public void setDdje(String ddje) {
		this.ddje = ddje;
	}

	public String getYldh() {
		return yldh;
	}

	public void setYldh(String yldh) {
		this.yldh = yldh;
	}

	public int getDdzt() {
		return ddzt;
	}

	public void setDdzt(int ddzt) {
		this.ddzt = ddzt;
	}

	public int getFwlx() {
		return fwlx;
	}

	public void setFwlx(int fwlx) {
		this.fwlx = fwlx;
	}

	public JsonObject getFwmx() {
		return fwmx;
	}

	public void setFwmx(JsonObject fwmx) {
		this.fwmx = fwmx;
	}

	
	public String getFwlxStr() {
		String fwlx = "";
		switch (getFwlx()) {
		case 1:
			fwlx = "家政服务";
			break;
		case 2:
			fwlx = "维修服务";
			break;
		case 3:
			fwlx = "送水服务";
			break;

		default:
			break;
		}
		return fwlx;
	}
}
