package harujisaku.javasaveimage.imageio;

import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

import java.awt.Graphics2D;

public class AutoRotateImage {

	public static final int HORIZONTAL=0;
	public static final int VERTICAL=1;

	public static BufferedImage rotate(BufferedImage img,double rotateRadian){
		if (img==null) {
			return img;
		}
		double width=img.getWidth(),height=img.getHeight();
		int afterWidth=(int)Math.round(height*Math.abs(Math.sin(rotateRadian))+width*Math.abs(Math.cos(rotateRadian)));
		int afterHeight=(int)Math.round(width*Math.abs(Math.sin(rotateRadian))+height*Math.abs(Math.cos(rotateRadian)));
		BufferedImage outputImg = new BufferedImage(afterWidth,afterHeight,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = outputImg.createGraphics();
		AffineTransform rotate = new AffineTransform();
		AffineTransform move = new AffineTransform();
		rotate.rotate(rotateRadian,width/2,height/2);
		move.translate(afterWidth/2-width/2,afterHeight/2-height/2);
		move.concatenate(rotate);
		g2.drawImage(img,move,null);
		return outputImg;
	}

	public static NetImage rotate(NetImage image,double rotateRadian){
		return new NetImage(rotate(image.getBufferedImage(),rotateRadian),image.getUrl(),image.getName());
	}

	public static BufferedImage autoRotate(BufferedImage img,int rotateDirection){
		if (img==null) {
			return img;
		}
		int width=img.getWidth(),height=img.getHeight();
		if ((rotateDirection==HORIZONTAL&&width<height)||(rotateDirection==VERTICAL&&height<width)) {
				BufferedImage outputImg = new BufferedImage(height,width,BufferedImage.TYPE_INT_RGB);
				Graphics2D g2 = outputImg.createGraphics();
				AffineTransform rotate = new AffineTransform();
				AffineTransform move = new AffineTransform();
				rotate.rotate(Math.toRadians(90),0.0,0.0);
				move.translate(height,0.0);
				move.concatenate(rotate);
				g2.drawImage(img,move,null);
				return outputImg;
			}
			return img;
		}

		public static NetImage autoRotate(NetImage image,int rotateDirection){
			return new NetImage(autoRotate(image.getBufferedImage(),rotateDirection),image.getUrl(),image.getName());
		}
}