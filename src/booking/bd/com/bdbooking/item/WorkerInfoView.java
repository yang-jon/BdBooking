package booking.bd.com.bdbooking.item;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import booking.bd.com.bdbooking.XutilImageLoader;
import booking.bd.com.bdbooking.bean.Worker;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.ViewHolder;

public class WorkerInfoView extends FrameLayout {
	
	
	private static final String TAG = "WorkerInfoView";
	private ImageView mImg;
	private TextView mName;
	private TextView mAge;
	private TextView mSkill;
	private TextView mHomeTown;
	private TextView mAddress;
	private TextView mExperience;
	private TextView mTrain;
	private TextView mEducation;
	private TextView mSpecialty;
	private TextView mNumber;
	private TextView mEvaluation;
	

	public WorkerInfoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public WorkerInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.bd_worker_info, this);
	}

	public WorkerInfoView(Context context) {
		super(context);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mImg = (ImageView) findViewById(R.id.iv_worker_img);
		mName = (TextView) findViewById(R.id.tv_worker_name);
		mAge = (TextView) findViewById(R.id.tv_worker_age);
		mSkill = (TextView) findViewById(R.id.tv_worker_skill);
		mHomeTown = (TextView) findViewById(R.id.tv_worker_hometown);
		mAddress = (TextView) findViewById(R.id.tv_worker_address);
		mExperience = (TextView) findViewById(R.id.tv_worker_experience);
		mTrain = (TextView) findViewById(R.id.tv_worker_training_records);
		mEducation = (TextView) findViewById(R.id.tv_worker_education);
		mSpecialty = (TextView) findViewById(R.id.tv_worker_specialty);
		mNumber = (TextView) findViewById(R.id.tv_worker_number);
		mEvaluation = (TextView) findViewById(R.id.tv_worker_evaluation);
	}
	
	public void configWorkerInfo(Worker worker){
		setName(worker.getXm());
		setAge(worker.getNl());
		setSkill(worker.getGrnl());
		setHomeTown(worker.getJg());
		setAddress(worker.getXjzdz());
		setExperience(worker.getGznx());
		setImg(new XutilImageLoader(getContext()),worker.getZp());
		setNumber(worker.getYgbh());
		setSpecialty(worker.getGrtc());
		setEducation(worker.getXl());
		setTrain(worker.getPxjl());
		setStar(formatStar(worker.getFwpj()));
	}
	
	private float formatStar(String fwpj) {
		try {
			return Float.parseFloat(fwpj);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public void setStar(float star){
		RatingBar cStar = ViewHolder.get(this, R.id.rb_worker_star);
		cStar.setRating(star);
	}
	
	public void setNumber(String number){
		TextView cNumber = ViewHolder.get(this, R.id.tv_worker_number);
		cNumber.setText("编号： "+number);
	}
	
	public void setSpecialty(String specialty){
		TextView cSpecialty = ViewHolder.get(this, R.id.tv_worker_specialty);
		cSpecialty.setText("个人特长: "+specialty);
	}
	
	public void setEducation(String education){
		TextView cEducation = ViewHolder.get(this, R.id.tv_worker_education);
		cEducation.setText("学历: "+education);
	}
	
	public void setTrain(String train){
		TextView cTrain = ViewHolder.get(this, R.id.tv_worker_training_records);
		cTrain.setText("培训经历: "+train);
	}
	
	public void setName(String name){
		TextView cName = ViewHolder.get(this, R.id.tv_worker_name);
		cName.setText(name);
	}
	
	public void setAge(String age){
		TextView cAge = ViewHolder.get(this, R.id.tv_worker_age);
		cAge.setText("年龄： "+age);
	}
	
	public void setSkill(String skill){
		TextView cSkill = ViewHolder.get(this, R.id.tv_worker_skill);
		cSkill.setText("个人能力： "+skill);
	}
	
	public void setHomeTown(String hometown){
		TextView cHome = ViewHolder.get(this, R.id.tv_worker_hometown);
		cHome.setText("籍贯： "+hometown);
	}
	
	public void setAddress(String address){
		TextView cAddress = ViewHolder.get(this, R.id.tv_worker_address);
		cAddress.setText("目前住址： "+address);
	}
	
	public void setExperience(String experience){
		TextView cExperience = ViewHolder.get(this, R.id.tv_worker_experience);
		cExperience.setText("工作经验： "+experience);
	}
	
	public void setImg(XutilImageLoader loader,String uri){
		ImageView cLogo = ViewHolder.get(this, R.id.iv_worker_img);
		loader.display(cLogo, uri);
	}
}
