package booking.bd.com.bdbooking.bean;

public class DrinkInfoV2 {
	private String	id; 	//品牌ID、水产品ID
	private String	cpmc;	//产品名称
	private int		xssl;	//销售数量
	private String	ico;	//品牌图片
	
	public DrinkInfoV2(String id, String cpmc, int xssl, String ico) {
		super();
		this.id = id;
		this.cpmc = cpmc;
		this.xssl = xssl;
		this.ico = ico;
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
	
}
