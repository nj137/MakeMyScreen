package infinitec.twelve.makemyscreen.fragments;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;

import infinitec.twelve.makemyscreen.HostActivityInterface;
import infinitec.twelve.makemyscreen.R;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CreateScreenFragment extends Fragment implements OnClickListener {


	private ColorPicker mColorPicker;
	private SVBar mSVBar;
	private OpacityBar mOpacityBar;
	
	private EditText mQuoteEditText;
	private EditText mAuthorEditText;
	private Button mSetScreenButton;

	private String mQuote;
	private String mAuthor;
	private int mBackgroundColor;

	private HostActivityInterface myHost;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		myHost = (HostActivityInterface) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_create_screen, parent,
				false);
		initiallizeViews(v);
		getActivity().getActionBar().show();
		return v;
	}

	private void initiallizeViews(View v) {
		mQuoteEditText = (EditText) v.findViewById(R.id.text_quote);
		mAuthorEditText = (EditText) v.findViewById(R.id.text_author);
		
		
		mColorPicker = (ColorPicker) v.findViewById(R.id.picker);
		mOpacityBar = (OpacityBar) v.findViewById(R.id.opacitybar);
		mSVBar = (SVBar) v.findViewById(R.id.svbar);
		mColorPicker.addOpacityBar(mOpacityBar);
		mColorPicker.addSVBar(mSVBar);
		
		mColorPicker.setShowOldCenterColor(false);
		mColorPicker.setColor(Color.parseColor("#FFCC00"));
		
		mSetScreenButton = (Button) v.findViewById(R.id.button_set);
		mSetScreenButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_set:
			mQuote = mQuoteEditText.getText().toString();
			mAuthor = mAuthorEditText.getText().toString();
			mBackgroundColor = mColorPicker.getColor();
			myHost.previewWallpaperCalled(mBackgroundColor, mQuote, mAuthor);

			break;

		default:
			break;
		}
	}

}
