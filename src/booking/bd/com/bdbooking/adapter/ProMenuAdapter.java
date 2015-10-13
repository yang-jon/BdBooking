package booking.bd.com.bdbooking.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import booking.bd.com.bdbooking.XutilImageLoader;
import booking.bd.com.bdbooking.bean.JzProjectInfo;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.ViewHolder;

public class ProMenuAdapter extends BaseAdapter {

	private ArrayList<JzProjectInfo> mProInfos;
	private Context context;
	private XutilImageLoader mImgLoader;
	private boolean isForWx;
	
	public ProMenuAdapter(Context context){
		this.context = context;
		mProInfos = new ArrayList<JzProjectInfo>();
		mImgLoader = new XutilImageLoader(context);
		isForWx = false;
	}
	
	public void setMenuForWx(){
		isForWx = true;
	}
	
	public void setData(ArrayList<JzProjectInfo> data){
		this.mProInfos = data;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mProInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return mProInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final JzProjectInfo info = mProInfos.get(position);
		if(null == convertView){
			convertView = View.inflate(context, R.layout.bd_jzproject_menu, null);
		}
	/*	if(isForWx){
			((LinearLayout)convertView).setOrientation(LinearLayout.HORIZONTAL);
		}*/
		ImageView icon = ViewHolder.get(convertView, R.id.iv_jz_pro_menu_img);
		mImgLoader.display(icon, info.IconUri);
		TextView text = ViewHolder.get(convertView, R.id.iv_jz_pro_menu_text);
		text.setText(info.pro);
		return convertView;
	}

}
