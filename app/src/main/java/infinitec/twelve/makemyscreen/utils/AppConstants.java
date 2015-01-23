package infinitec.twelve.makemyscreen.utils;

import android.content.SharedPreferences;

/**
 * Class that holds the App Constants
 * 
 * @author Sharath Pandeshwar
 */
public class AppConstants {

	public static final boolean DEBUG = true;

	/**
	 * Keys used in Bundle Objects
	 */
	public static interface BundleKeys {
		public static final String QUOTE = "quote";
		public static final String AUTHOR = "author";
		public static final String COLOR = "color";
		public static final String TEXTCOLOR = "text_color";
		public  static final String FILEPATH = "filepath";
	}

	/**
	 * 
	 * Holds some keys of {@linkplain SharedPreferences} object.
	 * 
	 */
	public static interface PrefKeys {
		public static final String PREF_BUTTON_LOGOUT = "button_logout";
		public static final String PREF_MPOWER_URL = "pref_mpower_url";
	}

	/**
	 * Holds Tags attached to fragments. Constant interface, DO NOT IMPLEMENT
	 * 
	 * @author Sharath Pandeshwar
	 */
	public static interface FragmentTags {
		public static final String CREATE_SCREEN_FRAGMENT = "create_screen_fragment";
		public static final String SET_SCREEN_FRAGMENT = "set_screen_fragment";

		/* Tags for fragment backstack popping and providing up navigation */
	}
}
