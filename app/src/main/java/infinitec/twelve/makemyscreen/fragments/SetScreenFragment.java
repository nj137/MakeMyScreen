package infinitec.twelve.makemyscreen.fragments;


import java.io.IOException;
import infinitec.twelve.makemyscreen.R;
import infinitec.twelve.makemyscreen.utils.AppConstants.BundleKeys;
import android.app.Fragment;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SetScreenFragment extends Fragment implements OnClickListener{
	
	private String mQuote;
	private String mAuthor;
	private int mBackgroundColor;
	
	private TextView mQuoteView;
	private TextView mAuthorView;
	private ImageButton mSetButton;
	
	private View mBackgroundView;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_set_screen, parent, false);
		mQuoteView = (TextView) v.findViewById(R.id.text_quote);
		
		
		
		mAuthorView = (TextView) v.findViewById(R.id.text_author);
		mSetButton = (ImageButton) v.findViewById(R.id.button_set_wallpaper);
		mSetButton.setOnClickListener(this);
		
		Bundle recArguments = getArguments();
		mQuote = recArguments.getString(BundleKeys.QUOTE);
		mBackgroundColor = recArguments.getInt(BundleKeys.COLOR);
		mAuthor = recArguments.getString(BundleKeys.AUTHOR);
		
		v.setBackgroundColor(mBackgroundColor);
		mQuoteView.setText(mQuote);
		//mQuoteView.setTextColor(Utils.invertColor(mBackgroundColor));
		
		
		if(!TextUtils.isEmpty(mAuthor)){
			mAuthorView.setText(mAuthor);
			//mAuthorView.setTextColor(Utils.invertColor(mBackgroundColor));
		}
		getActivity().getActionBar().hide();
		
		mBackgroundView = v;
		
		return v;
	}	


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_set_wallpaper:

			Bitmap mBackgroundBitmap = drawBitmap(mBackgroundView);
			if (mBackgroundBitmap != null) {
				try {
					setWallPaper(mBackgroundBitmap);
					Toast.makeText(getActivity(), "Wallpaper set Successfully!", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(getActivity(), "Failed to set the Wallpaper", Toast.LENGTH_SHORT).show();
				}
			}
			break;

		default:
			break;
		}
	} 
	
	
	/***
	 * 
	 * @param id Id of the {@link ViewGroup} Used to form the background
	 * @param color Background color to applied to {@link ViewGroup}
	 * @return Bitmap {@link Bitmap} Representing the bitmap equivalent
	 */
	private Bitmap drawBitmap(View v) {
		Bitmap mBackgroundBitmap = null;
		ViewGroup group = (ViewGroup) v;
		group.removeView(mSetButton);
		
		group.setDrawingCacheEnabled(true);
		mBackgroundBitmap = group.getDrawingCache();
		
		
		if (mBackgroundBitmap == null) {
			Toast.makeText(getActivity(), "Failed to Paint!", Toast.LENGTH_SHORT).show();
		} 
		return mBackgroundBitmap;
	}
	
	
	
	/***
	 * Function which accepts a Bitmap variable and sets it as wallpaper.
	 * @param bmp Bitmap variable to be set as wallpaper
	 * @throws IOException 
	 */
	private void setWallPaper(Bitmap bmp) throws IOException {
		WallpaperManager mWallpaperManager = WallpaperManager.getInstance(getActivity());
		mWallpaperManager.setBitmap(bmp);
	}
}
