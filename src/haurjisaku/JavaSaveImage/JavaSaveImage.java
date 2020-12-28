import java.io.*;

import java.util.regex.*;

import java.net.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import javax.imageio.stream.*;
import javax.imageio.plugins.jpeg.*;
import javax.imageio.metadata.*;

// import javax.lang.model.element.*;
import org.w3c.dom.*;

public class JavaSaveImage{
	static String url = "https://www.so-net.ne.jp/search/image/";
	static Pattern p = Pattern.compile("<a.*?href\\s*=\\s*[\"|'](https?://.*?)[\"|'].*? rel=\"search_result\".*?>");
	static Matcher m;
	static String html="";
	public JavaSaveImage(){
		
	}
	
	public static void main(String[] args) {
		if (args.length==0) {
			html=getHTML("とある科学の超電磁砲");
		}else if (args.length!=1) {
			return;
		}else {
			html=getHTML(args[0]);
		}
		save();
		int a = 0;
		while(true){
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			a+=20;
			System.out.println(a);
			if (args.length==0) {
				html=getHTML(20,"raspberry pi",a);
			}else if (args.length!=1) {
				return;
			}else {
				html=getHTML(20,args[0],a);
			}
			save();
		}
	}
	
	private static void save(){
		System.out.println(html);
		m=p.matcher(html);
		File file = new File("1.jpg");
		int i=0;
		while(m.find()){
			System.out.println(getURL());
			BufferedImage bi = rotate(getImage(getURL()));
			if(bi==null){
				continue;
			}
			try {
				while(file.exists()){
					i++;
					file = new File(i+".jpg");
				}
				FileOutputStream fo = new FileOutputStream(file);
				BufferedOutputStream bw = new BufferedOutputStream(fo);
				saveJpeg(fo,bi,1f,96);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String getHTML(String text){
		try {
			
		String sendUrl;
		try {
			sendUrl = url+"?query="+URLEncoder.encode(text,"UTF-8");
		} catch(UnsupportedEncodingException e) {
			sendUrl = url+text;
		}
		HttpURLConnection connection = (HttpURLConnection) new URL(sendUrl).openConnection();
		int responseCode = connection.getResponseCode();
		InputStream inputStream;
		if (200 <= responseCode && responseCode <= 299) {
			inputStream = connection.getInputStream();
		}else{
			inputStream = connection.getErrorStream();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
		StringBuilder response = new StringBuilder();
		String currentLine;
		while((currentLine = in.readLine())!=null){
			response.append(currentLine);
		}
		in.close();
		return response.toString();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String getHTML(int count,String text,int start){
		try {
			
		String sendUrl;
		try {
			sendUrl = url+"?count="+String.valueOf(count)+"&query="+URLEncoder.encode(text,"UTF-8")+"&start="+String.valueOf(start);
		} catch(UnsupportedEncodingException e) {
			sendUrl = url+"?count="+String.valueOf(count)+"&query="+text+"&start="+String.valueOf(start);
		}
		HttpURLConnection connection = (HttpURLConnection) new URL(sendUrl).openConnection();
		int responseCode = connection.getResponseCode();
		InputStream inputStream;
		if (200 <= responseCode && responseCode <= 299) {
			inputStream = connection.getInputStream();
		}else{
			inputStream = connection.getErrorStream();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
		StringBuilder response = new StringBuilder();
		String currentLine;
		while((currentLine = in.readLine())!=null){
			response.append(currentLine);
		}
		in.close();
		return response.toString();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String getURL(){
		return m.group(1);
	}

	private static BufferedImage getImage(String imageURL){
		try {
			System.out.println("get");
			HttpURLConnection urlcon =(HttpURLConnection)new URL(imageURL).openConnection();
			System.out.println("start connect");
			// HttpURLConnection urlcon =new HttpURLConnection(imageURL);
			System.out.println("return image");
			urlcon.setConnectTimeout(5000);
			urlcon.setReadTimeout(5000);
			System.out.println("set timeout");
			BufferedImage bi =ImageIO.read(urlcon.getInputStream());
			System.out.println("read");
			return bi;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean saveJpeg(FileOutputStream outputStream, BufferedImage img, float compression, int dpi) {
		// this program made by https://hemohemo.air-nifty.com/hemohemo/2014/07/java-jpeg-d768.html
		if(compression < 0 || compression > 1f) {
			return false;
		}
		if (img==null) {
			return false;
		}
		// 元画像が透過pngだった場合エラーが出るので透明部分を白色に変える
		if (img.getType()>=6) {
			BufferedImage newBufferedImage = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
			img=newBufferedImage;
		}
		ImageWriter iw = ImageIO.getImageWritersByFormatName("jpeg").next();
		try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream)) {
			iw.setOutput(ios);

			JPEGImageWriteParam param = (JPEGImageWriteParam)iw.getDefaultWriteParam();
			param.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(compression);

			IIOMetadata imageMeta = iw.getDefaultImageMetadata(new ImageTypeSpecifier(img), param);
			Element tree = (Element) imageMeta.getAsTree("javax_imageio_jpeg_image_1.0");
			Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
			jfif.setAttribute("resUnits", "1");
			jfif.setAttribute("Xdensity", Integer.toString(dpi));
			jfif.setAttribute("Ydensity", Integer.toString(dpi));
			imageMeta.setFromTree("javax_imageio_jpeg_image_1.0", tree);
			iw.write(null, new IIOImage(img, null, imageMeta), param);
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
		iw.dispose();
		return true;
	}
	
	private static BufferedImage rotate(BufferedImage bi){
		if (bi==null) {
			return null;
		}
		int width=bi.getWidth(),height=bi.getHeight();
		if (width<height) {
			BufferedImage out = new BufferedImage(height,width,BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = out.createGraphics();
			AffineTransform rotate = new AffineTransform();
			AffineTransform move = new AffineTransform();
			rotate.rotate(Math.toRadians(90),0.0,0.0);
			move.translate(height,0.0);
			move.concatenate(rotate);
			g2.drawImage(bi,move,null);
			return out;
		}
		return bi;
	}
}