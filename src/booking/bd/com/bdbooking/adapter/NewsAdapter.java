package booking.bd.com.bdbooking.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import booking.bd.com.bdbooking.XutilImageLoader;
import booking.bd.com.bdbooking.bean.NewsV1;
import booking.bd.com.bdbooking.bean.NewsV2;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.ViewHolder;

public class NewsAdapter<T> extends BaseAdapter {

	private ArrayList<T> mData = new ArrayList<T>();
	private Context mContext;
	private XutilImageLoader mLoader;
	
	public NewsAdapter (Context context){
		mContext = context;
		mLoader = new XutilImageLoader(context);
	}
	
	public void setData(ArrayList<T> data){
		this.mData = data;
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
		T t = mData.get(position);
		if(convertView == null){
			convertView = View.inflate(mContext, R.layout.bd_news_list_item, null);
		}
		if(t instanceof NewsV1){
			NewsV1 info = (NewsV1) t;
			ViewHolder.get(convertView, R.id.iv_news_icon).setVisibility(View.GONE);
			((TextView)ViewHolder.get(convertView, R.id.tv_news_title)).setText(info.getTitle());
			((TextView)ViewHolder.get(convertView, R.id.tv_news_time)).setText(info.getCjsj());
		}else if(t instanceof NewsV2){
			NewsV2 info = (NewsV2) t;
			ImageView icon = ViewHolder.get(convertView, R.id.iv_news_icon);
			icon.setVisibility(View.VISIBLE);
			mLoader.display(icon, info.getTitlePicUrl());
			((TextView)ViewHolder.get(convertView, R.id.tv_news_title)).setText(info.getTitle());
			((TextView)ViewHolder.get(convertView, R.id.tv_news_time)).setText(info.getCjsj());
		}else{
			throw new RuntimeException("xxxx 新闻类型不匹配");
		}
		
		return convertView;
	}

}
