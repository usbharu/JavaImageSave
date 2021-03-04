package harujisaku.javasaveimage.imageio;

import java.util.Iterator;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.ImageWriter;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

public class SavePNGImageWithDPI implements ISaveImageWithDPI {
	public boolean saveImageWithDPI(OutputStream output,BufferedImage img,int dpi)throws IOException{
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
		return false;
	}

	public String getFormatName(){
		return "png";
	}

	private static void setDPI(IIOMetadata metadata,int dpi)throws IIOInvalidTreeException{
		String dotsPerMilli = "3.779528";
		IIOMetadataNode horizontal = new IIOMetadataNode("HorizontalPixelSize");
		horizontal.setAttribute("value",dotsPerMilli);
		IIOMetadataNode vertical = new IIOMetadataNode("VerticalPixelSize");
		vertical.setAttribute("value",dotsPerMilli);
		IIOMetadataNode dimension = new IIOMetadataNode("Dimension");
		dimension.appendChild(horizontal);
		dimension.appendChild(vertical);
		IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
		root.appendChild(dimension);
		metadata.mergeTree("javax_imageio_1.0",root);
	}
}
