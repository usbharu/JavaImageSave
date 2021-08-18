package harujisaku.javasaveimage.net;

import harujisaku.javasaveimage.imageio.NetImage;
import harujisaku.javasaveimage.util.Message;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class ImageConnection {
	public static NetImage getImage(String imageURL, int timeOut) throws IOException {
		HttpURLConnection urlcon =(HttpURLConnection)new URL(imageURL).openConnection();
		urlcon.setConnectTimeout(timeOut);
		urlcon.setReadTimeout(timeOut);
		BufferedImage bi;
		try {
			int responseCode=urlcon.getResponseCode();
			if (200<=responseCode&&responseCode<=299) {
				bi = ImageIO.read(urlcon.getInputStream());
			}else{
				System.out.println(Message.ERROR);
				System.out.println(responseCode);
				bi=null;
			}
		} catch(SocketTimeoutException e) {
			System.out.println(Message.TIME_OUT);
			return null;
		}
		NetImage netImage = new NetImage(bi,imageURL,urlcon.getURL().getFile());
		return netImage;
	}
}