package booking.bd.com.bdbooking.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import booking.bd.com.bdbooking.R;
import booking.bd.com.bdbooking.utils.ViewHolder;

public class BdOderFormInfoView extends RelativeLayout{
	public static final int TAG_UNFINISHED = 0;
	public static final int TAG_UNEVALUATE = 1;
	public static final int TAG_EVALUATE = 2;
	private TextView mScore;
	private RatingBar mStar;
	private Button mCancel;
	
	public BdOderFormInfoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BdOderFormInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BdOderFormInfoView(Context context) {
		super(context);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mScore = (TextView) findViewById(R.id.tv_score);
		mStar = (RatingBar) findViewById(R.id.rb_order);
		mCancel = (Button) findViewById(R.id.btn_cancel);
	}
	
	public static BdOderFormInfoView createBdOderFormInfoView(Context context){
		return (BdOderFormInfoView) inflate(context, R.layout.bd_booking_info, null);
	}
	
	public void setTag(int tag) {
		switch (tag) {
		case TAG_UNFINISHED:
			mScore.setVisibility(View.GONE);
			mStar.setVisibility(View.GONE);
			mCancel.setVisibility(View.VISIBLE);
			break;
		case TAG_UNEVALUATE:
			mScore.setVisibility(View.VISIBLE);
			mStar.setVisibility(View.GONE);
			mCancel.setVisibility(View.GONE);
			break;
		case TAG_EVALUATE:
			mScore.setVisibility(View.GONE);
			mStar.setVisibility(View.VISIBLE);
			mCancel.setVisibility(View.GONE);
			break;

		default:
			throw new RuntimeException("BdOderFormInfoView tag is unsupport");
		}
	}
	
	public void setDate(String date){
		TextView cDate = ViewHolder.get(this, R.id.tv_date);
		cDate.setText(date);
	}
	
	public void setScore(String score){
		TextView cScore = ViewHolder.get(this, R.id.tv_score);
		cScore.setText(score);
	}
	
	public void setPersonName(String name){
		TextView cName = ViewHolder.get(this, R.id.tv_name);
		cName.setText(name);
	}
	
	public void setCompany(String company){
		TextView cCompany = ViewHolder.get(this, R.id.tv_company);
		cCompany.setText(company);
	}
	
	public void setStar(float f){
		RatingBar cStar = ViewHolder.get(this, R.id.rb_order);
		cStar.setRating(f);
	}
	
}
