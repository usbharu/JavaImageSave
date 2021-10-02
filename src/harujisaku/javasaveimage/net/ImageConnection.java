package harujisaku.javasaveimage.net;

import harujisaku.javasaveimage.imageio.NetImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * urlから画像を返す
 *
 * @author usbharu
 * @version 1.6.1
 * @since 1.6.1
 */
public class ImageConnection {
	/**
	 * urlから{@link NetImage}を返します。インターネット接続をします。
	 * @param imageURL 画像のURL
	 * @param timeOut 接続、読み込みのタイムアウトを設定します。
	 * @return 取得した画像 HTTPステータスコードが200~299以外だった場合{@code null}を返します。
	 * @throws IOException {@link HttpURLConnection}で発生したエラー
	 */
	public static NetImage getImage(String imageURL, int timeOut) throws IOException {
		HttpURLConnection urlcon = (HttpURLConnection) new URL(imageURL).openConnection();
		urlcon.setConnectTimeout(timeOut);
		urlcon.setReadTimeout(timeOut);
		BufferedImage bi;
		int responseCode = urlcon.getResponseCode();
		if (200 <= responseCode && responseCode <= 299) {
			bi = ImageIO.read(urlcon.getInputStream());
		} else {
			return null;
		}
		NetImage netImage = new NetImage(bi, imageURL, new File(String.valueOf(urlcon.getURL())).getName());
		return netImage;
	}
}