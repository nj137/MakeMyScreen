package infinitec.twelve.makemyscreen;

import infinitec.twelve.makemyscreen.fragments.CreateScreenFragment;
import infinitec.twelve.makemyscreen.fragments.SetScreenFragment;
import infinitec.twelve.makemyscreen.utils.AppConstants.BundleKeys;
import infinitec.twelve.makemyscreen.utils.AppConstants.FragmentTags;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;


public class MainActivity extends Activity implements HostActivityInterface {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loadFragment(R.id.fragmentContainer, new CreateScreenFragment(),
				FragmentTags.CREATE_SCREEN_FRAGMENT, false, null);
	}

	/**
	 * Helper method to load fragments into layout
	 * 
	 * @param containerResId
	 *            The container resource Id in the content view into which to
	 *            load the fragment
	 * @param fragment
	 *            The fragment to load
	 * @param tag
	 *            The fragment tag
	 * @param addToBackStack
	 *            Whether the transaction should be addded to the backstack
	 * @param backStackTag
	 *            The tag used for the backstack tag
	 */
	public void loadFragment(final int containerResId, final Fragment fragment,
			final String tag, final boolean addToBackStack, final String backStackTag) {

		final FragmentManager fragmentManager = getFragmentManager();
		final FragmentTransaction transaction = fragmentManager.beginTransaction();

		transaction.replace(containerResId, fragment, tag);

		if (addToBackStack) {
			transaction.addToBackStack(backStackTag);
		}
		transaction.commitAllowingStateLoss();
	}

	@Override
	public void previewColorWallpaper(int color, String quote, String author,int textColor) {
		Fragment mSetScreenFragment = new SetScreenFragment();
		Bundle screenBundle = new Bundle();
		screenBundle.putInt(BundleKeys.COLOR, color);
		screenBundle.putString(BundleKeys.QUOTE, quote);
		screenBundle.putString(BundleKeys.AUTHOR, author);
		screenBundle.putInt(BundleKeys.TEXTCOLOR, textColor);
		mSetScreenFragment.setArguments(screenBundle);

		loadFragment(R.id.fragmentContainer, mSetScreenFragment, FragmentTags.SET_SCREEN_FRAGMENT, true, null);
	}

	@Override
	public void previewImageWallpaper(String filepath, String quote, String author,int textColor) {
		Fragment mSetScreenFragment = new SetScreenFragment();
		Bundle screenBundle = new Bundle();
		screenBundle.putString(BundleKeys.FILEPATH,filepath);
		screenBundle.putString(BundleKeys.QUOTE, quote);
		screenBundle.putString(BundleKeys.AUTHOR, author);
		screenBundle.putInt(BundleKeys.TEXTCOLOR,textColor);
		mSetScreenFragment.setArguments(screenBundle);

		loadFragment(R.id.fragmentContainer, mSetScreenFragment, FragmentTags.SET_SCREEN_FRAGMENT, true, null);
	}

}
