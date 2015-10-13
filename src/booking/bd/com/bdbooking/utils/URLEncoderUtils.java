package booking.bd.com.bdbooking.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.text.TextUtils;

public class URLEncoderUtils {
	
	public static final String DEFAULT_CHARSET = "GBK";
	
	public static String encode(String str) throws UnsupportedEncodingException{
		return encode(str, DEFAULT_CHARSET);
	}
	
	public static String encode(String str,String charset) throws UnsupportedEncodingException{
		if(TextUtils.isEmpty(str)){
			return "";
		}
		return URLEncoder.encode(str, charset).replaceAll("%23", "#");
	}
}
