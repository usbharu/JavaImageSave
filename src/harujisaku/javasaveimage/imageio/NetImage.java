package harujisaku.javasaveimage.imageio;

import java.awt.image.BufferedImage;

/**
 * 画像、url、名前を保持するクラスです。
 *
 * @author usbharu
 * @version 1.6.1
 * @since 1.6.1
 */
public class NetImage {
	/**
	 * 保持する画像
	 */
	private BufferedImage bufferedImage;
	/**
	 * 保持するurl
	 */
	private String url;
	/**
	 * 保持する名前
	 */
	private String name;

	/**
	 * デフォルトコンストラクタ
	 * 画像、url、名前を保持した{@code NetImage}を作成します。
	 *
	 * @param bufferedImage 保持する画像
	 * @param url           保持するurl
	 * @param name          保持する名前
	 */
	public NetImage(BufferedImage bufferedImage, String url, String name) {
		this.bufferedImage = bufferedImage;
		this.url = url;
		this.name = name;
	}

	/**
	 * @return 保持している画像を返します。
	 */
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	/**
	 * @param bufferedImage 保持する画像を設定します。
	 */
	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	/**
	 * @return 保持しているurlを返します。
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url 保持するurlを設定します。
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return 保持している名前を返します。
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 保持する名前を設定します。
	 */
	public void setName(String name) {
		this.name = name;
	}
}