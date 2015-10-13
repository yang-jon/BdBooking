package booking.bd.com.bdbooking.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import booking.bd.com.bdbooking.jz.R;
import booking.bd.com.bdbooking.utils.PublicInterfaceFactory;

public class BookingFragment extends DialogFragment {
	
	public static final String FRAGMENT_TAG = "BookingFragment";
	private View mFragment;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setStyle(AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, 0);
		super.onCreate(savedInstanceState);
		mFragment = View.inflate(getActivity(),R.layout.bd_activity_check_booking, null);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
         AlertDialog.Builder builder = new AlertDialog.Builder(this
                 .getActivity())
                 .setView(mFragment)
                 .setNegativeButton(R.string.cancel_booking, null)
                 .setPositiveButton(R.string.submit_booking, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivity(PublicInterfaceFactory.createOrderFormIntent(getActivity()));
					}
				});
         return builder.create();
	}
}
