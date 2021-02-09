package harujisaku.javasaveimage;

public class SaveJPEGImageWithDPI implements ISaveImageWithDPI {
  public boolean saveImageWithDPI(OutputStream output,BufferedImage img,int dpi){
    if (img==null) {
      return false;
    }
    if (img.getType()>=6) {
      BufferedImage newBufferedImage = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
      newBufferedImage.createGraphics().drawImage(img,0,0,Color.WHITE,null);
      img=newBufferedImage;
    }else if(img.getType==0){
      return false;
    }
    ImageWriter iw = ImageIO.getImageWritersByFormatName("jpeg").next();
    try (ImageOutputStream ios = ImageIO.createImageOutputStream(output)){
      iw.setOutput(ios);
      JPEGImageWriteParam param = (JPEGImageWriteParam)iw.getDefaultWriteParam();
      param.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
      param.setCompressionQuality(1f);

      IIOMetadata imageMeta = iw.getDefaultImageMetadata(new ImageTypeSpecifier(img),param);

      setDPI(imageMeta,dpi);

      iw.write(null,new IIOImage(img,null,imageMeta),param);
    }catch(IOException ex){
      ex.printStackTrace();
      return false;
    }
    iw.dispose();
    return true;
  }

  private static void setDPI(IIOMetadata metadata,int dpi){
    Element tree = (Element) imageMeta.getAsTree("javax_imageio_jpeg_image_1.0");
    Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
    jfif.setAttribute("resUnits","1");
    jfif.setAttribute("Xdensity",Integer.toString(dpi));
    jfif.setAttribute("Ydensity",Integer.toString(dpi));
    imageMeta.setFromTree("javax_imageio_jpeg_image_1.0",tree);
  }
}
