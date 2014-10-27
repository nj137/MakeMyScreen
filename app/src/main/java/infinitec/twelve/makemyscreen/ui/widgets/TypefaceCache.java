package infinitec.twelve.makemyscreen.ui.widgets;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * @author Sharath Pandeshwar Typeface cache to cache the typefaces
 */
public class TypefaceCache {

    private static final Hashtable<String, Typeface> CACHE             = new Hashtable<String, Typeface>();

    public static final String                       REGULAR           = "fonts/typography-times.ttf";
    
    public static Typeface get(final AssetManager manager,
                    final int typefaceCode) {
        synchronized (CACHE) {

            final String typefaceName = getTypefaceName(typefaceCode);

            if (!CACHE.containsKey(typefaceName)) {
                final Typeface t = Typeface
                                .createFromAsset(manager, typefaceName);
                CACHE.put(typefaceName, t);
            }
            return CACHE.get(typefaceName);
        }
    }

    public static Typeface get(final AssetManager manager,
                    final String typefaceName) {
        return get(manager, getCodeForTypefaceName(typefaceName));
    }

    private static int getCodeForTypefaceName(final String typefaceName) {

        if (typefaceName.equals(REGULAR)) {
            return 0;
        } else {
            return 3;
        }
    }

    private static String getTypefaceName(final int typefaceCode) {
        switch (typefaceCode) {

            case 0:
                return REGULAR;

            default:
                return REGULAR;
        }
    }

}
