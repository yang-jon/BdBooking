package booking.bd.com.bdbooking;

import java.util.Stack;

import android.app.Activity;

public class AppActivityStack extends Stack<Activity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean empty() {
		return super.empty();
	}

	@Override
	public synchronized Activity peek() {
		return super.peek();
	}

	@Override
	public synchronized Activity pop() {
		return super.pop();
	}

	@Override
	public Activity push(Activity object) {
		return super.push(object);
	}

	@Override
	public synchronized int search(Object o) {
		return super.search(o);
	}

	
	
}
