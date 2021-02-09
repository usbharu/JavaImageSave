package harujisaku.javasaveimage.image;

import java.util.List;
import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.io.IOException;

public class SaveImageWithDPI {
  public static List<ISaveImageWithDPI> formatList = new ArrayList<ISaveImageWithDPI>();

  public static boolean saveImageWithDPI(OutputStream output,BufferedImage img,int dpi,String formatName)throws IOException{
    for (ISaveImageWithDPI format : formatList ) {
      if (format.getFormatName().equals(formatName)) {
        return format.saveImageWithDPI(output,img,dpi);
      }
    }
    return false;
  }
}
