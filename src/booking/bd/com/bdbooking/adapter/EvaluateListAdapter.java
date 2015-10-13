package booking.bd.com.bdbooking.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import booking.bd.com.bdbooking.bean.EvaluationData;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.ViewHolder;

public class EvaluateListAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<EvaluationData> mData;
	
	public EvaluateListAdapter(Context context) {
		this.mContext = context;
		mData = new ArrayList<EvaluationData>();
	}
	
	public void setData(ArrayList<EvaluationData> data){
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
		final EvaluationData info = mData.get(position);
		if(convertView == null){
			convertView = View.inflate(mContext, R.layout.bd_evaluate_item, null);
		}
		TextView phonenumber = ViewHolder.get(convertView, R.id.tv_phone_number);
		phonenumber.setText(info.getPjryxm());
		TextView date = ViewHolder.get(convertView, R.id.tv_date);
		date.setText(info.getPjsj());
		TextView content = ViewHolder.get(convertView, R.id.tv_eva_content);
		content.setText(info.getPjms());
		RatingBar star = ViewHolder.get(convertView, R.id.rb_eva_star);
		star.setRating((info.getFwsspj()+info.getFwtdpj()+info.getFwxlpj()+info.getFwzlpj())/4);
		return convertView;
	}

}
