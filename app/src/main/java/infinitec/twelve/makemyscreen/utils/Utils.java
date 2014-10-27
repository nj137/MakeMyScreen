package infinitec.twelve.makemyscreen.utils;

import android.graphics.Color;

public class Utils {
	public static int invertColor(int bgColor){
		int textColor = Color.rgb(255-Color.red(bgColor), 255-Color.green(bgColor), 255-Color.blue(bgColor));	
		return textColor;
	}
}
