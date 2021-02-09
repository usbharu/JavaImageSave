package harujisaku.JavaSaveImage;

public class SavePNGImageWithDPI implements ISaveImageWithDPI {
  public static boolean saveImageWithDPI(OutputStream output,BufferedImage img,int dpi){
    for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName("png");iw.hasNext() ;) {
      ImageWriter writer = iw.next();
      ImageWriteParam writeParam = writer.getDefaultWriteParam();
      ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
      IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier,writeParam);
      if (metadata.isReadOnly()||!metadata.isStandardMetadataFormatSupported()) {
        continue;
      }
      setDPI(metadata,dpi);

      ImageOutputStream stream = ImageIO.createImageOutputStream(output);
      try {
        writer.setOutput(stream);
        writer.write(metadata,new IIOImage(img,null,metadata),writeParam);
      } finally{
        stream.close();
      }
      break;
    }
  }

  private static void setDPI(IIOMetadata metadata,int dpi) throws IIOInvalidTreeException{
    String dotsPerMilli = "3.779528";
    IIOMetadataNode horizontal = new IIOMetadataNode("HorizontalPixelSize");
    horizontal.setAttribute("value",dotsPerMilli);
    IIOMetadataNode vertical = new IIOMetadataNode("VerticalPixelSize");
    vertical.setAttribute("value",dotsPerMilli);
    IIOMetadataNode dimension = new IIOMetadataNode("Dimension");
    dimension.appendChild(horizontal);
    dimension.appendChild(vertical);
    IIOMetadataNode root = new IIOMetadataNode("javax_imageio_jpeg_image_1.0");
    root.appendChild(dim);
    metadata.mergeTree("javax_imageio_jpeg_image_1.0",root);
  }
}
