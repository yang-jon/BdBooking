package booking.bd.com.bdbooking.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import booking.bd.com.bdbooking.XutilImageLoader;
import booking.bd.com.bdbooking.bean.Worker;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.ViewHolder;

public class WorkerListAdapter extends BaseAdapter {

	private Context context;
	private XutilImageLoader mLoader;
	private int mSelectPos = -1;
	private ArrayList<Worker> mData = new ArrayList<Worker>();
	public WorkerListAdapter(Context context){
		this.context = context;
		mLoader = new XutilImageLoader(context);
	}
	public void setData(ArrayList<Worker> workers){
		this.mData = workers;
		mSelectPos = -1;
		notifyDataSetChanged();
	}
	
	public void setSelectPos(int pos){
		this.mSelectPos = pos;
		notifyDataSetChanged();
	}
	
	public int getSelectPos(){
		return mSelectPos;
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
		if (null == convertView) {
			convertView = View.inflate(context, R.layout.bd_worker_selector,null);
		}
		
		ImageView cAppoint = (ImageView) convertView.findViewById(R.id.iv_appoint);
		if(position == mSelectPos){
			cAppoint.setImageResource(R.drawable.bd_appoint);
		}else{
			cAppoint.setImageResource(R.drawable.bd_def_appoint);
		}
		
		RatingBar cStar = ViewHolder.get(convertView, R.id.rb_worker_star);
		cStar.setRating(TextUtils.isEmpty(info.getFwpj())?3:Float.parseFloat(info.getFwpj()));

		TextView cNumber = ViewHolder.get(convertView, R.id.tv_worker_number);
		cNumber.setText("编号： " + info.getYgbh());

		TextView cSpecialty = ViewHolder.get(convertView,R.id.tv_worker_specialty);
		cSpecialty.setText("个人特长: " + info.getGrtc());

		TextView cEducation = ViewHolder.get(convertView,R.id.tv_worker_education);
		cEducation.setText("学历: " + info.getXl());

		TextView cTrain = ViewHolder.get(convertView,R.id.tv_worker_training_records);
		cTrain.setText("培训经历: " + info.getPxjl());

		TextView cName = ViewHolder.get(convertView, R.id.tv_worker_name);
		cName.setText(info.getXm());

		TextView cAge = ViewHolder.get(convertView, R.id.tv_worker_age);
		cAge.setText("年龄： " + info.getNl());

		TextView cSkill = ViewHolder.get(convertView, R.id.tv_worker_skill);
		cSkill.setText("个人能力： " + info.getGrnl());

		TextView cHome = ViewHolder.get(convertView, R.id.tv_worker_hometown);
		cHome.setText("籍贯： " + info.getJg());

		TextView cAddress = ViewHolder.get(convertView, R.id.tv_worker_address);
		cAddress.setText("目前住址： " + info.getXjzdz());

		TextView cExperience = ViewHolder.get(convertView,R.id.tv_worker_experience);
		cExperience.setText("工作经验： " + info.getGznx());

		ImageView cLogo = ViewHolder.get(convertView, R.id.iv_worker_img);
		new XutilImageLoader(context).display(cLogo, info.getZp());
		return convertView;
	}
}
