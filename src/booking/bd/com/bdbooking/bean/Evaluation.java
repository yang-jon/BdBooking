package booking.bd.com.bdbooking.bean;

import java.util.ArrayList;

public class Evaluation {
	private int	datasum;	//数据总条数	数字
	private int	pagesum;	//分页数		数字
	private ArrayList<EvaluationData>	data;	//显示评价信息，参见评价信息列表	字符串
	
	public Evaluation(int datasum, int pagesum, ArrayList<EvaluationData> data) {
		super();
		this.datasum = datasum;
		this.pagesum = pagesum;
		this.data = data;
	}
	public int getDatasum() {
		return datasum;
	}
	public void setDatasum(int datasum) {
		this.datasum = datasum;
	}
	public int getPagesum() {
		return pagesum;
	}
	public void setPagesum(int pagesum) {
		this.pagesum = pagesum;
	}
	public ArrayList<EvaluationData> getData() {
		return data;
	}
	public void setData(ArrayList<EvaluationData> data) {
		this.data = data;
	}
	
}
