package harujisaku.javasaveimage.imageio;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * 画像を回転させるメソッドを持ったクラスです。
 *
 * @author usbharu
 * @version 1.6.1
 * @since 1.6.1
 */
public class AutoRotateImage {

	/**
	 * 縦向き
	 */
	public static final int HORIZONTAL = 0;
	/**
	 * 横向き
	 */
	public static final int VERTICAL = 1;

	/**
	 * 画像を回転します。
	 * 角度の単位ははラジアンです。
	 *
	 * @param img          回転をする画像です。
	 * @param rotateRadian 回転の角度です。単位はラジアンです。
	 * @return 回転したあとの画像。入力がnullの場合nullを返します。
	 */

	public static BufferedImage rotate(BufferedImage img, double rotateRadian) {
		if (img == null) {
			return null;
		}
		double width = img.getWidth(), height = img.getHeight();
		int afterWidth = (int) Math.round(height * Math.abs(Math.sin(rotateRadian)) + width * Math.abs(Math.cos(rotateRadian)));
		int afterHeight = (int) Math.round(width * Math.abs(Math.sin(rotateRadian)) + height * Math.abs(Math.cos(rotateRadian)));
		BufferedImage outputImg = new BufferedImage(afterWidth, afterHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = outputImg.createGraphics();
		AffineTransform rotate = new AffineTransform();
		AffineTransform move = new AffineTransform();
		rotate.rotate(rotateRadian, width / 2, height / 2);
		move.translate((afterWidth >> 1) - width / 2, (afterHeight >> 1) - height / 2);
		move.concatenate(rotate);
		g2.drawImage(img, move, null);
		return outputImg;
	}

	/**
	 * 画像を回転します。
	 * 角度の単位ははラジアンです。
	 *
	 * @param image        回転をする画像です。
	 * @param rotateRadian 回転の角度です。単位はラジアンです。
	 * @return 回転したあとの画像。入力がnullの場合nullを返します。
	 */
	public static NetImage rotate(NetImage image, double rotateRadian) {
		return new NetImage(rotate(image.getBufferedImage(), rotateRadian), image.getUrl(), image.getName());
	}

	/**
	 * 画像を横長、縦長に回転します。
	 *
	 * @param img             回転させる画像です。
	 * @param rotateDirection 回転させる方向です。
	 * @return 回転させたあとの画像。入力がnullの場合nullを返します。
	 */
	public static BufferedImage autoRotate(BufferedImage img, int rotateDirection) {
		if (img == null) {
			return null;
		}
		int width = img.getWidth(), height = img.getHeight();
		if ((rotateDirection == HORIZONTAL && width < height) || (rotateDirection == VERTICAL && height < width)) {
			BufferedImage outputImg = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = outputImg.createGraphics();
			AffineTransform rotate = new AffineTransform();
			AffineTransform move = new AffineTransform();
			rotate.rotate(Math.toRadians(90), 0.0, 0.0);
			move.translate(height, 0.0);
			move.concatenate(rotate);
			g2.drawImage(img, move, null);
			return outputImg;
		}
		return img;
	}

	/**
	 * 画像を横長、縦長に回転します。
	 *
	 * @param image           回転させる画像です。
	 * @param rotateDirection 回転させる方向です。
	 * @return 回転させたあとの画像。入力がnullの場合nullを返します。
	 */
	public static NetImage autoRotate(NetImage image, int rotateDirection) {
		return new NetImage(autoRotate(image.getBufferedImage(), rotateDirection), image.getUrl(), image.getName());
	}
}