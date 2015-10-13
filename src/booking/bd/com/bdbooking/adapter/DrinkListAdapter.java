package booking.bd.com.bdbooking.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import booking.bd.com.bdbooking.XutilImageLoader;
import booking.bd.com.bdbooking.bean.DrinkInfo;
import booking.bd.com.bdbooking.bean.DrinkInfoData;
import booking.bd.com.bdbooking.item.BdDrinkInfoItemView;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.ViewHolder;

public class DrinkListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<DrinkInfo> mData;
	private ArrayList<DrinkInfoData> mChildData;
	private XutilImageLoader mLoader;
	private NumberSelectListener mListener;
	private boolean isChild;
	
	public DrinkListAdapter(Context context,boolean isChild) {
		this.mContext = context;
		mData = new ArrayList<DrinkInfo>();
		mChildData = new ArrayList<DrinkInfoData>();
		mLoader = new XutilImageLoader(mContext);
		this.isChild = isChild;
	}
	
	public void setData(ArrayList<DrinkInfo> data){
		this.mData = data;
		notifyDataSetChanged();
	}
	
	public void setChildData(ArrayList<DrinkInfoData> data){
		this.mChildData = data;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if(isChild){
			return mChildData.size();
		}
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		if(isChild){
			return mChildData.get(position);
		}
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setNumberSelectListener(NumberSelectListener l){
		this.mListener = l;
	}
	
	public interface NumberSelectListener{
		void addNumber(int pos,TextView jia);
		void appNumber(int pos,TextView jia);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = BdDrinkInfoItemView.createBdDrinkInfoItemView(mContext);
		}
		final BdDrinkInfoItemView itemView = (BdDrinkInfoItemView)convertView;
		
		itemView.setChild(isChild);
		
		if(isChild){
			DrinkInfoData child = mChildData.get(position);
			itemView.setName(child.getFwlbmc());
			itemView.setPrice("¥"+child.getJg());
			itemView.setSoldNum("5");
			itemView.setImg(mLoader, "uri");
			if(null != mListener){
				final TextView number = ViewHolder.get(itemView, R.id.tv_number);
				final TextView jia = ViewHolder.get(itemView, R.id.tv_number_jia);
				jia.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mListener.addNumber(position,number);
					}
				});
				final TextView jian = ViewHolder.get(itemView, R.id.tv_number_jian);
				jian.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mListener.appNumber(position,number);
					}
				});
			}
			
		}else{
			DrinkInfo info = mData.get(position);
			itemView.setName(info.getFwlbmc());
			itemView.setPrice("¥"+info.getMinjg()+"-¥"+info.getMaxjg());
			itemView.setSoldNum("5");
			itemView.setImg(mLoader, "uri");
		}
		return itemView;
	}

}
