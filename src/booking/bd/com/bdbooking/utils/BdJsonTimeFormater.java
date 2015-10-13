package booking.bd.com.bdbooking.utils;

import java.util.ArrayList;

public class BdJsonTimeFormater {
	
	
	private static ArrayList<String> sTimeString = new ArrayList<String>();
	
	static{
		sTimeString.add("日");
		sTimeString.add("月");
		sTimeString.add("年");
	}
	
	public static String format(String time){
		
		String[] split = time.split("~");
		if(split.length <2)
			return time;
		return build(split[0])+" -- "+build(split[1]);
	}	
	
	//15:08:17:12:00 --> 15年08月17日   12:00
	public static String build(String time){
		StringBuilder sb = new StringBuilder();
		String[] split = time.split(":");
		if(split.length < 2 || split.length > 5) 
			return time;
		sb.append(reverse(split[split.length-1])).append(":").append(reverse(split[split.length-2])).append("  ");
		for (int i = split.length-3; i >= 0; i--) {
			sb.append(sTimeString.get(split.length-3-i)).append(reverse(split[i]));
		}
		return sb.reverse().toString();
	}
	
	public static String reverse(String str) {
		if (null != str) {
			char[] c = str.toCharArray();
			int l = str.length();
			for (int i = 0; i < l / 2; i++) {
				char t = c[i];
				c[i] = c[l - i - 1];
				c[l - i - 1] = t;
			}
			return new String(c);
		}
		return str;
	}
}
