package infinitec.twelve.makemyscreen.ui.widgets;

import infinitec.twelve.makemyscreen.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * @author Sharath Pandehwar Custom TextView to apply a font
 */
public class TypefacedTextView extends TextView {

    public TypefacedTextView(final Context context, final AttributeSet attrs) {

        super(context, attrs);

        if (attrs != null) {
            // Get Custom Attribute Name and value
            final TypedArray styledAttrs = context
                            .obtainStyledAttributes(attrs, R.styleable.TypefacedTextView);
            final int typefaceCode = styledAttrs
                            .getInt(R.styleable.TypefacedTextView_fontStyle, -1);
            styledAttrs.recycle();

            // Typeface.createFromAsset doesn't work in the layout editor.
            // Skipping...
            if (isInEditMode()) {
                return;
            }

            final Typeface typeface = TypefaceCache
                            .get(context.getAssets(), typefaceCode);
            setTypeface(typeface);
        }
    }

    public TypefacedTextView(final Context context) {
        super(context);
    }
}
