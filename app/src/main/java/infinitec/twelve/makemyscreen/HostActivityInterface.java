package infinitec.twelve.makemyscreen;


/**
 * Callback the hosting activity has to implement
 * @author Sharath Pandeshwar
 *
 */
public interface HostActivityInterface {
  public void previewColorWallpaper(int color, String quote, String author,int textColor);
  public void previewImageWallpaper(String filePath,String quote,String author,int textColor);
}
