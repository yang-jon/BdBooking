package booking.bd.com.bdbooking.adapter;

import java.util.ArrayList;

import booking.bd.com.bdbooking.bean.Worker;
import booking.bd.com.bdbooking.item.BdCompanyListItemView;
import booking.bd.com.bdbooking.item.WorkerInfoView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class WorkerListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Worker> mData = new ArrayList<Worker>();
	public WorkerListAdapter(Context context){
		this.context = context;
	}
	public void setData(ArrayList<Worker> workers){
		this.mData = workers;
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
		final Worker info = mData.get(position);
		WorkerInfoView itemView;
		if(null == convertView){
			itemView = new WorkerInfoView(context, null);
		}
		itemView = (WorkerInfoView)convertView;
		return itemView;
	}
}
