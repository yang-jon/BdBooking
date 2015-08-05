package booking.bd.com.bdbooking.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import booking.bd.com.bdbooking.R;
import booking.bd.com.bdbooking.XutilImageLoader;
import booking.bd.com.bdbooking.utils.ViewHolder;

public class BdCompanyListItemView extends RelativeLayout {
	
	public ImageView mLogo;
	public TextView mName;
	public RatingBar mStar;
	
	public BdCompanyListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BdCompanyListItemView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public static BdCompanyListItemView create(Context context){
		return (BdCompanyListItemView) LayoutInflater.from(context).inflate(R.layout.bd_company_info, null);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mLogo = (ImageView) findViewById(R.id.iv_company_logo);
		mName = (TextView) findViewById(R.id.tv_company_name);
		mStar = (RatingBar) findViewById(R.id.rb_stars);
	}
	
	public void setName(String name){
		TextView cName = ViewHolder.get(this, R.id.tv_company_name);
		cName.setText(name);
	}
	
	public void setStar(float f){
		RatingBar cStar = ViewHolder.get(this, R.id.rb_stars);
		cStar.setRating(f);
	}
	
	public void setLogo(XutilImageLoader loader,String uri){
		ImageView cLogo = ViewHolder.get(this, R.id.iv_company_logo);
		loader.display(cLogo, uri);
	}
}
