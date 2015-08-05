package booking.bd.com.bdbooking.item;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import booking.bd.com.bdbooking.R;
import booking.bd.com.bdbooking.XutilImageLoader;
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
	
	private static int sPaddingTB;
	private int mInnerPadding;
	private int mHeight;
	private int mWidth;

	public WorkerInfoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public WorkerInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		sPaddingTB = getResources().getDimensionPixelSize(R.dimen.bd_worker_text_paddingTB);
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
	public void setName(String name){
		TextView cName = ViewHolder.get(this, R.id.tv_worker_name);
		cName.setText(name);
	}
	
	public void setAge(String age){
		TextView cAge = ViewHolder.get(this, R.id.tv_worker_age);
		cAge.setText(age);
	}
	
	public void setSkill(String skill){
		TextView cSkill = ViewHolder.get(this, R.id.tv_worker_skill);
		cSkill.setText(skill);
	}
	
	public void setHomeTown(String hometown){
		TextView cHome = ViewHolder.get(this, R.id.tv_worker_hometown);
		cHome.setText(hometown);
	}
	
	public void setAddress(String address){
		TextView cAddress = ViewHolder.get(this, R.id.tv_worker_address);
		cAddress.setText(address);
	}
	
	public void setExperience(String experience){
		TextView cExperience = ViewHolder.get(this, R.id.tv_worker_experience);
		cExperience.setText(experience);
	}
	
	public void setImg(XutilImageLoader loader,String uri){
		ImageView cLogo = ViewHolder.get(this, R.id.iv_worker_img);
		loader.display(cLogo, uri);
	}
}
