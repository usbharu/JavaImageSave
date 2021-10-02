package harujisaku.javasaveimage.imageio;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 画像をdpiを指定して保存します。
 *
 * @author usbharu
 * @version 1.6.1
 * @since 1.3-rc
 */
public class SaveImageWithDPI {
	/**
	 * 登録されているフォーマットのリスト
	 */
	public static List<ISaveImageWithDPI> formatList = new ArrayList<>();

	/**
	 * @param output     保存するファイルの{@link OutputStream}
	 * @param img        保存する画像
	 * @param dpi        dpi
	 * @param formatName フォーマットの一般的な拡張子
	 * @return 保存に成功した場合はtrueを失敗した場合はfalseを返します。
	 * @throws IOException 保存に失敗した場合
	 */
	public static boolean saveImageWithDPI(OutputStream output, BufferedImage img, int dpi, String formatName) throws IOException {
		for (ISaveImageWithDPI format : formatList) {
			if (format.getFormatName().equals(formatName)) {
				return format.saveImageWithDPI(output, img, dpi);
			}
		}
		return false;
	}

	/**
	 * 登録されているフォーマットの一般的な拡張子を返します。
	 *
	 * @return 登録されているフォーマットの一般的な拡張子のリストを返します。
	 */
	public static List<String> getFormatNameList() {
		List<String> formatNameList = new ArrayList<>();
		for (ISaveImageWithDPI format : formatList) {
			formatNameList.add(format.getFormatName());
		}
		return formatNameList;
	}
}