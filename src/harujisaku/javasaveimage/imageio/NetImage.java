package harujisaku.javasaveimage.imageio;

import java.awt.image.BufferedImage;

public class NetImage {
	private BufferedImage bufferedImage;
	private String url;
	private String name;

	public NetImage() {
	}

	public NetImage(BufferedImage bufferedImage, String url, String name) {
		this.bufferedImage = bufferedImage;
		this.url = url;
		this.name = name;
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}