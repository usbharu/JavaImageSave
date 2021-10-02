package harujisaku.javasaveimage.imageio;

import org.w3c.dom.Element;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * JPEG形式で画像をDPIを指定して保存するメソッドを持ったクラスです。
 *
 * @author usbharu
 * @version 1.6.1
 * @since 1.3-rc
 */
public class SaveJPEGImageWithDPI implements ISaveImageWithDPI {

	/**
	 * dpiを指定して画像を保存します。
	 * @param output  保存するファイルの{@link OutputStream}
	 * @param img  保存する画像
	 * @param dpi dpi
	 * @return 保存に成功した場合はtrueを失敗した場合はfalseを返します。
	 */
	@Override
	public boolean saveImageWithDPI(OutputStream output, BufferedImage img, int dpi) {
		if (img == null) {
			return false;
		}
		if (img.getType() >= 6) {
			BufferedImage newBufferedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
			img = newBufferedImage;
		} else if (img.getType() == 0) {
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

	private void setDPI(IIOMetadata metadata, int dpi) throws IIOInvalidTreeException {
		Element tree = (Element) metadata.getAsTree("javax_imageio_jpeg_image_1.0");
		Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
		jfif.setAttribute("resUnits", "1");
		jfif.setAttribute("Xdensity", Integer.toString(dpi));
		jfif.setAttribute("Ydensity", Integer.toString(dpi));
		metadata.setFromTree("javax_imageio_jpeg_image_1.0", tree);
	}

	/**
	 * このクラスが担当するフォーマットが扱う一般的な拡張子を返します。
	 *
	 * @return このクラスが保存できるフォーマットの一般的な拡張子を返します。
	 */
	@Override
	public String getFormatName() {
		return "jpeg";
	}
}