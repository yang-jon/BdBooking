package booking.bd.com.bdbooking.bean;

public class DrinkInfoDataV2 {
	private String	id; 	//品牌ID、水产品ID
	private String	cpmc;	//产品名称
	private int		xssl;	//销售数量
	private String	ico;	//品牌图片
	private float ckjg;		//参考价格
	private float hdjg;		//活动价格
	private CompanyInfo gsxx; //产品所属公司信息
	public CompanyInfo getGsxx() {
		return gsxx;
	}
	public void setGsxx(CompanyInfo gsxx) {
		this.gsxx = gsxx;
	}
	public DrinkInfoDataV2(String id, String cpmc, int xssl, String ico,
			float ckjg, float hdjg) {
		super();
		this.id = id;
		this.cpmc = cpmc;
		this.xssl = xssl;
		this.ico = ico;
		this.ckjg = ckjg;
		this.hdjg = hdjg;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCpmc() {
		return cpmc;
	}
	public void setCpmc(String cpmc) {
		this.cpmc = cpmc;
	}
	public int getXssl() {
		return xssl;
	}
	public void setXssl(int xssl) {
		this.xssl = xssl;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public float getCkjg() {
		return ckjg;
	}
	public void setCkjg(float ckjg) {
		this.ckjg = ckjg;
	}
	public float getHdjg() {
		return hdjg;
	}
	public void setHdjg(float hdjg) {
		this.hdjg = hdjg;
	}
	
}
