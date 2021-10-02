package harujisaku.javasaveimage.imageio;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 画像をdpiを指定して保存します。
 *
 * @author usbharu
 * @version 1.6.1
 * @since 1.3-rc
 */
public interface ISaveImageWithDPI {
	/**
	 * このクラスが担当するフォーマットが扱う一般的な拡張子を返します。
	 *
	 * @return このクラスが保存できるフォーマットの一般的な拡張子を返します。
	 */
	String getFormatName();

	/**
	 * @param os  保存するファイルの{@link OutputStream}
	 * @param bi  保存する画像
	 * @param dpi dpi
	 * @return 保存に成功した場合はtrueを失敗した場合はfalseを返します。
	 * @throws IOException インターネット接続に問題があった場合
	 */
	boolean saveImageWithDPI(OutputStream os, BufferedImage bi, int dpi) throws IOException;
}