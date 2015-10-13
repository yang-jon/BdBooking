package booking.bd.com.bdbooking.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import booking.bd.com.bdbooking.XutilImageLoader;
import booking.bd.com.bdbooking.activitys.BottledWaterActivity;
import booking.bd.com.bdbooking.bean.DrinkInfoDataV2;
import booking.bd.com.bdbooking.bean.DrinkInfoV2;
import booking.bd.com.bdbooking.item.BdDrinkInfoItemView;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.ViewHolder;

public class DrinkListAdapterV2 extends BaseAdapter {

	private Context mContext;
	private ArrayList<DrinkInfoV2> mData;
	private ArrayList<DrinkInfoDataV2> mChildData;
	private XutilImageLoader mLoader;
	private NumberSelectListener mListener;
	private boolean isChild;
	
	public DrinkListAdapterV2(Context context,boolean isChild) {
		this.mContext = context;
		mData = new ArrayList<DrinkInfoV2>();
		mChildData = new ArrayList<DrinkInfoDataV2>();
		mLoader = new XutilImageLoader(mContext);
		this.isChild = isChild;
	}
	
	public void setData(ArrayList<DrinkInfoV2> data){
		this.mData = data;
		notifyDataSetChanged();
	}
	
	public void setChildData(ArrayList<DrinkInfoDataV2> data){
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
	public View getView(final int position, View convertView, final ViewGroup parent) {
		if (null == convertView) {
			convertView = BdDrinkInfoItemView.createBdDrinkInfoItemView(mContext);
		}
		final BdDrinkInfoItemView itemView = (BdDrinkInfoItemView)convertView;
		
		itemView.setChild(isChild);
		
		if(isChild){
			DrinkInfoDataV2 child = mChildData.get(position);
			itemView.setName(child.getCpmc());
			itemView.setPrice("¥"+child.getHdjg());
			itemView.setImg(mLoader, child.getIco());
			
			final TextView jia = (TextView) itemView.findViewById(R.id.tv_number_jia);
			final TextView jian = (TextView) itemView.findViewById(R.id.tv_number_jian);
			
/*			jia.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					Toast.makeText(mContext, "jia :: "+hasFocus + "………………jian "+jian.hasFocus(), 0).show();
					parent.requestFocus();
					if(hasFocus||jian.hasFocus()){
						((GridView) parent).setSelection(position);
					}
				}
			});
			
			jian.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					Toast.makeText(mContext, "jian :: "+hasFocus + "………………jia "+jia.hasFocus(), 0).show();
					parent.requestFocus();
					if(hasFocus||jia.hasFocus()){
						((GridView) parent).setSelection(position);
					}
				}
			});*/
			final TextView number = ViewHolder.get(itemView, R.id.tv_number);
			if(mContext instanceof BottledWaterActivity){
				number.setText(((BottledWaterActivity)(mContext)).getNumberByid(child.getId())+"");
			}
			if(null != mListener){
				jia.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mListener.addNumber(position,number);
					}
				});
				jian.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mListener.appNumber(position,number);
					}
				});
			}
			
		}else{
			DrinkInfoV2 info = mData.get(position);
			itemView.setName(info.getCpmc());
			itemView.setSoldNum(info.getXssl()+"");
			itemView.setImg(mLoader, info.getIco());
		}
		return itemView;
	}

}
