package harujisaku.javasaveimage;

import java.util.List;
import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.io.IOException;

public class SaveImageWithDPI {
  public static List<ISaveImageWithDPI> formatList = new ArrayList<ISaveImageWithDPI>();
  static{
    formatList.add(new SavePNGImageWithDPI());
    formatList.add(new SaveJPEGImageWithDPI());
    formatList.add(new SaveJPGImageWithDPI());
  }
  
  public static boolean saveImageWithDPI(OutputStream output,BufferedImage img,int dpi,String formatName)throws IOException{
    System.out.println(formatName);
    for (ISaveImageWithDPI format : formatList ) {
      if (format.getFormatName().equals(formatName)) {
        return format.saveImageWithDPI(output,img,dpi);
      }
    }
    return false;
  }
}
