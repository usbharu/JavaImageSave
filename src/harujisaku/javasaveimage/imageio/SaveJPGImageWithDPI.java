package harujisaku.javasaveimage.imageio;

/**
 * JPG形式で画像をdpiを指定して保存します。
 *
 * @author usbharu
 * @version 1.3-rc
 */
public class SaveJPGImageWithDPI extends SaveJPEGImageWithDPI {
	/**
	 * このクラスが担当するフォーマットが扱う一般的な拡張子を返します。
	 *
	 * @return このクラスが保存できるフォーマットの一般的な拡張子を返します。
	 */
	@Override
	public String getFormatName() {
		return "jpg";
	}
}