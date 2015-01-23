package infinitec.twelve.makemyscreen.fragments;

import infinitec.twelve.makemyscreen.HostActivityInterface;
import infinitec.twelve.makemyscreen.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chiralcode.colorpicker.ColorPickerDialog;
import com.chiralcode.colorpicker.ColorPickerDialog.OnColorSelectedListener;

public class CreateScreenFragment extends Fragment implements OnClickListener {

	private static final int GET_PHOTO = 1;
	private EditText mQuoteEditText;
	private EditText mAuthorEditText;
	private String mQuote;
	private String mAuthor;
	private int mBackgroundColor;
	private int mTextColor;

	private HostActivityInterface myHost;
	private String filePath;
	private boolean background;
	private boolean photo = false;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		myHost = (HostActivityInterface) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_create_screen, parent,false);
		initiallizeViews(v);
		getActivity().getActionBar().show();
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
		getActivity().getActionBar().setBackgroundDrawable(
				new ColorDrawable(0xffCC6633));
		return v;
	}

	private void initiallizeViews(View v) {
		mQuoteEditText = (EditText) v.findViewById(R.id.text_quote);
		mAuthorEditText = (EditText) v.findViewById(R.id.text_author);
		ImageButton color_picker = (ImageButton) v
				.findViewById(R.id.color_picker);
		ImageButton gallery_picker = (ImageButton) v
				.findViewById(R.id.gallery_picker);
		ImageButton text_color = (ImageButton) v.findViewById(R.id.text_color);
		text_color.setOnClickListener(this);
		color_picker.setOnClickListener(this);
		gallery_picker.setOnClickListener(this);

	}

	public void pick_a_photo() {
		photo = true;
		Intent toChoose = new Intent(Intent.ACTION_GET_CONTENT);
		toChoose.setType("image/*");
		startActivityForResult(Intent.createChooser(toChoose, "pick a photo"),
				GET_PHOTO);
	}

	@Override
	public void onActivityResult(int arg0, int arg1, Intent arg2) {

		if (arg0 == GET_PHOTO) {
			if (arg2 == null) {
				return;
			}

			Uri uri = arg2.getData();
			String[] imagePath = { MediaStore.Images.Media.DATA };
			Cursor cursor = getActivity().getContentResolver().query(uri,
					imagePath, null, null, null);

			cursor.moveToFirst();
			filePath = cursor.getString(cursor.getColumnIndex(imagePath[0]));

		}
	}

	private void showColorPickerDialogDemo() {

		int initialColor = Color.WHITE;

		ColorPickerDialog colorPickerDialog = new ColorPickerDialog(
				getActivity(), initialColor, new OnColorSelectedListener() {

					@Override
					public void onColorSelected(int color) {
						if (background)
							mBackgroundColor = color;
						else
							mTextColor = color;

					}

				});
		colorPickerDialog.show();

	}

	public void preview_wallpaper() {
		mQuote = mQuoteEditText.getText().toString();
		mAuthor = mAuthorEditText.getText().toString();
		if (mQuote.trim().length() == 0) {
			mQuoteEditText.setError("Enter the quote");
		} else {
			if (filePath == null && mBackgroundColor == 0) {
				Toast.makeText(getActivity(), "Select a background",
						Toast.LENGTH_SHORT).show();
			} else {
				if (mTextColor == 0) {
					Toast.makeText(getActivity(), "Select Text Color",
							Toast.LENGTH_SHORT).show();
				} else {
					if (photo) {
						String mFilePath = filePath;
						myHost.previewImageWallpaper(mFilePath, mQuote,
								mAuthor, mTextColor);
					} else {
						myHost.previewColorWallpaper(mBackgroundColor, mQuote,
								mAuthor, mTextColor);
					}
				}
			}
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.create_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.preview_wallpaper:
			preview_wallpaper();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gallery_picker:
			photo = true;
			pick_a_photo();
			break;
		case R.id.color_picker:
			background = true;
			photo = false;
			showColorPickerDialogDemo();
			break;
		case R.id.text_color:
			background = false;
			showColorPickerDialogDemo();
			break;
		default:
			Toast.makeText(getActivity(), "Invalid Option", Toast.LENGTH_SHORT)
					.show();
		}
	}

}
