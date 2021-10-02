package harujisaku.javasaveimage.imageio;

import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

/**
 * PNG形式でdpiを指定して保存します。
 *
 * @author usbharu
 * @version 1.6.1
 * @since 1.3-rc
 */
public class SavePNGImageWithDPI implements ISaveImageWithDPI {
	/**
	 * dpiを指定して画像を保存します。
	 * @param output 保存するファイルの{@link OutputStream}
	 * @param img 保存する画像
	 * @param dpi dpi
	 * @return 保存に成功した場合はtrueを失敗した場合はfalseを返します。
	 * @throws IOException 保存に失敗した場合。
	 */
	@Override
	public boolean saveImageWithDPI(OutputStream output, BufferedImage img, int dpi) throws IOException {
		for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName("png"); iw.hasNext(); ) {
			ImageWriter writer = iw.next();
			ImageWriteParam writeParam = writer.getDefaultWriteParam();
			ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
			IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
			if (metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported()) {
				continue;
			}
			setDPI(metadata, dpi);

			ImageOutputStream stream = ImageIO.createImageOutputStream(output);
			try {
				writer.setOutput(stream);
				writer.write(metadata, new IIOImage(img, null, metadata), writeParam);
			} finally {
				stream.close();
			}
			break;
		}
		return false;
	}

	/**
	 * @return pngを返します。
	 */
	@Override
	public String getFormatName() {
		return "png";
	}

	private static void setDPI(IIOMetadata metadata, int dpi) throws IIOInvalidTreeException {
		String dotsPerMilli = "3.779528";
		IIOMetadataNode horizontal = new IIOMetadataNode("HorizontalPixelSize");
		horizontal.setAttribute("value", dotsPerMilli);
		IIOMetadataNode vertical = new IIOMetadataNode("VerticalPixelSize");
		vertical.setAttribute("value", dotsPerMilli);
		IIOMetadataNode dimension = new IIOMetadataNode("Dimension");
		dimension.appendChild(horizontal);
		dimension.appendChild(vertical);
		IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
		root.appendChild(dimension);
		metadata.mergeTree("javax_imageio_1.0",root);
	}
}