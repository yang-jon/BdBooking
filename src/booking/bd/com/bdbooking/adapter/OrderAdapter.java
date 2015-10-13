package booking.bd.com.bdbooking.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;
import booking.bd.com.bdbooking.bean.Order;
import booking.bd.com.bdbooking.bean.OrderV2;
import booking.bd.com.bdbooking.item.BdDrinkInfoItemView;
import booking.bd.com.bdbooking.item.BdOderFormInfoView;

public class OrderAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<OrderV2> mData;
	public OrderType mType;
	private CancelButtonClickListener mListener;
	
	public OrderAdapter(Context context,OrderType type) {
		this.mContext = context;
		this.mData = new ArrayList<OrderV2>();
		this.mType = type;
	}
	
	public interface CancelButtonClickListener{
		void cancel(String ddbh);
	}
	
	public void setCancelButtonClickListener(CancelButtonClickListener l){
		this.mListener = l;
	}
	
	public enum OrderType{
		TYPE_COMPLETED,TYPE_UNCOMPLETED
	}
	
	public void setData(ArrayList<OrderV2> data){
		mData = data;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final OrderV2 info = mData.get(position);
		if(null == convertView){
			convertView = BdOderFormInfoView.createBdOderFormInfoView(mContext);
		}
		final BdOderFormInfoView itemView = (BdOderFormInfoView)convertView;
		if(OrderType.TYPE_COMPLETED == mType){
			itemView.setTag(BdOderFormInfoView.TAG_UNEVALUATE);
		}else{
			itemView.setTag(BdOderFormInfoView.TAG_UNFINISHED);
		}
		itemView.setPersonName(info.getFwlxStr());
		itemView.setDate(info.getSqsj());
		itemView.setCompany("¥"+info.getDdje());
		itemView.setStar(3);
		if(info.getDdzt() == 2){
			itemView.getCancelButton().setText("已取消");
			itemView.getCancelButton().setEnabled(false);
		}
		itemView.getCancelButton().setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				itemView.setSelected(hasFocus);
			}
		});
		itemView.getCancelButton().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.cancel(info.getDdbh());
			}
		});
		return itemView;
	}
}
