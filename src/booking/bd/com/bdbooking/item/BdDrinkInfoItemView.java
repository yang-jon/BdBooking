package booking.bd.com.bdbooking.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import booking.bd.com.bdbooking.XutilImageLoader;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.ViewHolder;

public class BdDrinkInfoItemView extends RelativeLayout{
	
	private TextView mSold;
	private RelativeLayout mNumberSelector;
	private TextView mPrice;
	
	public BdDrinkInfoItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BdDrinkInfoItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		inflate(context, R.layout.bd_drink_info, this);
	}

	public BdDrinkInfoItemView(Context context) {
		super(context);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mSold = (TextView) findViewById(R.id.tv_drink_sold);
		mNumberSelector = (RelativeLayout) findViewById(R.id.ll_number_selector);
		mPrice = (TextView) findViewById(R.id.tv_drink_price);
	}
	
	public static BdDrinkInfoItemView createBdDrinkInfoItemView(Context context){
		return (BdDrinkInfoItemView) inflate(context, R.layout.bd_drink_info, null);
	}
	
	public void setChild(boolean isChild){
		mSold.setVisibility(isChild?View.GONE:View.VISIBLE);
		mNumberSelector.setVisibility(isChild?View.VISIBLE:View.GONE);
		mPrice.setVisibility(isChild?View.VISIBLE:View.GONE);
	}
	
	public void setName(String name){
		TextView cName = ViewHolder.get(this, R.id.tv_drink_name);
		cName.setText(name);
	}
	
	public void setPrice(String price){
		TextView cPrice = ViewHolder.get(this, R.id.tv_drink_price);
		cPrice.setText(price);
	}
	
	public void setSoldNum(String sold){
		TextView cSold = ViewHolder.get(this, R.id.tv_drink_sold);
		cSold.setText("最近售出：" + sold);
	}
	
	public void setImg(XutilImageLoader loader,String uri){
		ImageView cLogo = ViewHolder.get(this, R.id.iv_drink_img);
		loader.display(cLogo, uri);
	}
}
