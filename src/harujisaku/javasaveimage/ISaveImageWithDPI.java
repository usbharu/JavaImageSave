package harujisaku.javasaveimage;

import java.io.IOException;
import java.io.OutputStream;

import java.awt.image.BufferedImage;

public interface ISaveImageWithDPI{
  boolean saveImageWithDPI(OutputStream os,BufferedImage bi,int resolution);
}
