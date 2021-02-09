package harujisaku.javasaveimage;

import java.util.Iterator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

public class ImageWithDPI{

public static void saveImageWithDPI(OutputStream output, BufferedImage img, int resolution, String formatName) throws IOException {
	for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName(formatName); iw.hasNext();) {
		ImageWriter writer = iw.next();
		ImageWriteParam writeParam = writer.getDefaultWriteParam();
		ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
		IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
		if (metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported()) {
			continue;
		}

		setDPI(metadata, resolution);

		final ImageOutputStream stream = ImageIO.createImageOutputStream(output);
		try {
			writer.setOutput(stream);
			writer.write(metadata, new IIOImage(img, null, metadata), writeParam);
		} finally {
			stream.close();
		}
		break;
	}
}

public static void saveImageWithDPI(OutputStream output,BufferedImage img,int resolution) throws IOException{
	saveImageWithDPI(output,img,resolution,"png");
}

private static final double INCH_2_MM = 25.4;

private static void setDPI(IIOMetadata metadata, int dpi) throws IIOInvalidTreeException {
	double dotsPerMilli = 3.779528;
	IIOMetadataNode horiz = new IIOMetadataNode("HorizontalPixelSize");
	horiz.setAttribute("value", Double.toString(dotsPerMilli));
	IIOMetadataNode vert = new IIOMetadataNode("VerticalPixelSize");
	vert.setAttribute("value", Double.toString(dotsPerMilli));
	IIOMetadataNode dim = new IIOMetadataNode("Dimension");
	dim.appendChild(horiz);
	dim.appendChild(vert);
	IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
	root.appendChild(dim);
	metadata.mergeTree("javax_imageio_1.0", root);
}
}
