package infinitec.twelve.makemyscreen.fragments;

import infinitec.twelve.makemyscreen.R;
import infinitec.twelve.makemyscreen.utils.AppConstants.BundleKeys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SetScreenFragment extends Fragment {

	private String mQuote;
	private String mAuthor;
	private int mBackgroundColor;

	private TextView mQuoteView;
	private TextView mAuthorView;
	private ImageButton mSetButton;

	private View mBackgroundView;
	private String mFilepath;
	private int mTextColor;
	private int num=0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		getActivity().getActionBar().setBackgroundDrawable(new ColorDrawable(0x33000000));
		super.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_set_screen, parent, false);
		mQuoteView = (TextView) v.findViewById(R.id.text_quote);
		mAuthorView = (TextView) v.findViewById(R.id.text_author);

		Bundle recArguments = getArguments();
		mAuthor = recArguments.getString(BundleKeys.AUTHOR);
		mQuote = recArguments.getString(BundleKeys.QUOTE);
		mBackgroundColor = recArguments.getInt(BundleKeys.COLOR);
		mTextColor = recArguments.getInt(BundleKeys.TEXTCOLOR);
		mFilepath = recArguments.getString(BundleKeys.FILEPATH);
		if (mFilepath == null) {
			v.setBackgroundColor(mBackgroundColor);
		} else {
			Bitmap mBitmap = BitmapFactory.decodeFile(mFilepath);
			Drawable background = new BitmapDrawable(getResources(), mBitmap);
			v.setBackground(background);
		}
		mQuoteView.setText(mQuote);
		// mQuoteView.setTextColor(Utils.invertColor(mBackgroundColor));

		if (!TextUtils.isEmpty(mAuthor)) {
			mAuthorView.setText(mAuthor);
			// mAuthorView.setTextColor(Utils.invertColor(mBackgroundColor));
		}

		mQuoteView.setTextColor(mTextColor);
		mAuthorView.setTextColor(mTextColor);
		mBackgroundView = v;

		return v;
	}

	
	/***
	 * 
	 * @param id
	 *            Id of the {@link ViewGroup} Used to form the background
	 * @param color
	 *            Background color to applied to {@link ViewGroup}
	 * @return Bitmap {@link Bitmap} Representing the bitmap equivalent
	 */
	private Bitmap drawBitmap(View v) {
		Bitmap mBackgroundBitmap = null;
		ViewGroup group = (ViewGroup) v;
		group.removeView(mSetButton);

		group.setDrawingCacheEnabled(true);
		mBackgroundBitmap = group.getDrawingCache();

		if (mBackgroundBitmap == null) {
			Toast.makeText(getActivity(), "Failed to Paint!",
					Toast.LENGTH_SHORT).show();
		}
		return mBackgroundBitmap;
	}

	/***
	 * Function which accepts a Bitmap variable and sets it as wallpaper.
	 * 
	 * @param bmp
	 *            Bitmap variable to be set as wallpaper
	 * @throws IOException
	 */
	private void setWallPaper(Bitmap bmp) throws IOException {
		WallpaperManager mWallpaperManager = WallpaperManager
				.getInstance(getActivity());
		mWallpaperManager.setBitmap(bmp);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			FragmentManager fm = getFragmentManager();
			if (fm.getBackStackEntryCount() > 0) {
				fm.popBackStack();
			}
			return true;
		case R.id.set_wallpaper:
			set_the_wallpaper();
			return true;
		case R.id.share_wallpaper:
			save_the_wallpaper();
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}

	}


	private void set_the_wallpaper() {
		Bitmap mBackgroundBitmap = drawBitmap(mBackgroundView);
		if (mBackgroundBitmap != null) {
			try {
				setWallPaper(mBackgroundBitmap);
				Toast.makeText(getActivity(),
						"Wallpaper set Successfully!", Toast.LENGTH_SHORT)
						.show();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(getActivity(),
						"Failed to set the Wallpaper", Toast.LENGTH_SHORT)
						.show();
			}
		}
		
	}


	private void save_the_wallpaper() {
          	File directory = new File(Environment.getExternalStorageDirectory()
  					+ "/MakeMyScreen");

  			if (!directory.exists()) {

  			//	File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getPath());

  				directory.mkdirs();

  				Toast.makeText(getActivity(), "Directory created",
  						Toast.LENGTH_SHORT).show();
          }

  			else

  				Log.i("Directory", "already exists");
      File wallpaper = new File(new File(directory.toString()),
					"MakeMyScreen" + num + ".jpg");

			while (wallpaper.exists()) {

				num++;
				wallpaper = new File(new File(directory.toString()),
						"MakeMyScreen" + num + ".jpg");
				
			}
				FileOutputStream outstream;
				try {
					outstream = new FileOutputStream(wallpaper);
					Bitmap mBackgroundBitmap = drawBitmap(mBackgroundView);
					if (mBackgroundBitmap != null) 
						mBackgroundBitmap.compress(Bitmap.CompressFormat.JPEG,
							100, outstream);
					outstream.flush();
					outstream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
				onShare(num);
		
	}


	private void onShare(int num) {
		Intent share= new Intent(Intent.ACTION_SEND);
		share.setType("image/");
		String uri = Environment.getExternalStorageDirectory()
				+ "/MakeMyScreen/MakeMyScreen" + num + ".jpg";
		Uri screen = Uri.parse(uri);
		share.putExtra(Intent.EXTRA_STREAM, screen);
		startActivity(Intent.createChooser(share, "Sharing using..."));
	}
}
