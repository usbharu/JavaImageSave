package harujisaku.javasaveimage.imageio;

import java.io.IOException;
import java.io.OutputStream;

import java.awt.image.BufferedImage;

public interface ISaveImageWithDPI{
  String getFormatName();
  boolean saveImageWithDPI(OutputStream os,BufferedImage bi,int dpi)throws IOException;
}
