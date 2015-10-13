package booking.bd.com.bdbooking.adapter;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import booking.bd.com.bdbooking.BdApplication;
import booking.bd.com.bdbooking.OnHttpActionListener;
import booking.bd.com.bdbooking.XutilImageLoader;
import booking.bd.com.bdbooking.bean.CompanyInfo;
import booking.bd.com.bdbooking.bean.Worker;
import booking.bd.com.bdbooking.item.BdCompanyListItemView;
import booking.bd.com.bdbooking.json.DecodeResult;
import booking.bd.com.bdbooking.json.IDecodeJson;
import booking.bd.com.bdbooking.utils.PublicInterfaceFactory;
import booking.bd.com.bdbooking.utils.XutilHttpPack;

public class CompanyListAdapter extends BaseAdapter implements OnItemClickListener {
	
	private static final String TAG = "CompanyListAdapter";
	private Context context;
	private List<CompanyInfo> mData;
	private XutilImageLoader mImgLoader;
	private SelectedItemChangeListener mListener;
	
	public CompanyListAdapter(Context context) {
		this.context = context;
		mData = new ArrayList<CompanyInfo>();
		mImgLoader = new XutilImageLoader(context);
		mData = new ArrayList<CompanyInfo>();
	}
	
	public interface SelectedItemChangeListener{
		void onSelectedItemChanged(int position);
	}
	
	public void setSelectedItemChangeListener(SelectedItemChangeListener l){
		mListener = l;
	}
	
	public void setData(ArrayList<CompanyInfo> data){
		this.mData = data;
		notifyDataSetChanged();
	}
	
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
//		if(null != mListener){
//			mListener.onSelectedItemChanged(0);
//		}
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
		BdCompanyListItemView itemView;
		final CompanyInfo info = mData.get(position);
		if(null == convertView){
			convertView = BdCompanyListItemView.create(context);
		}
		itemView = (BdCompanyListItemView)convertView;
		itemView.setName(info.getGsmc());
		itemView.setStar(info.getPj());
		itemView.setLogo(mImgLoader,info.getIcon());
		return itemView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(null != mListener){
			mListener.onSelectedItemChanged(position);
		}
	}
}
