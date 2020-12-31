package harujisaku.javasaveimage;

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

import harujisaku.javasaveimage.*;

public class JavaSaveImage{
	String url = "https://www.so-net.ne.jp/search/image/";
	Pattern p = Pattern.compile("<a.*?href\\s*=\\s*[\"|'](https?://.*?)[\"|'].*? rel=\"search_result\".*?>");
	Matcher m;
	String html="",option="",texts="java",path="",extension="jpg";
	int length=1,requestCount=0,errorCount=0;
	boolean isNeedSave=true,isNeedRotate=true;
	String helpMessage = "-h,-help : help , this message ヘルプ　このメッセージ\r\n-l,-len,-length : length　検索ページの長さ\r\n-o,-option : option　検索エンジンに指定するオプション\r\n\tlanguage,ysp_q,size,end,imtype,format,ss_view,from,q_type,view,adult,start\r\n-t,-text : search text　検索するテキスト\r\n-p,-path : save path 保存する場所\r\n-s,-no-save : only search 検索のみ\r\n-e,-extension : image type 保存形式\r\n-r,-no-rotate not auto rotate 自動で横向きにしないようにします";

	public static void main(String[] args) {
		new JavaSaveImage().myMain(args);
	}
	
	private void myMain(String[] args){
		int a = 0;
		if (args.length==0) {
			System.out.println(helpMessage);
			return;
		}
		for (int i=0,len=args.length;i<len ;i++ ) {
			if ("-h".equals(args[i])||"-help".equals(args[i])) {
				System.out.println(helpMessage);
				return;
			}else if ("-len".equals(args[i])||"-l".equals(args[i])||"-length".equals(args[i])) {
				length=Integer.parseInt(args[++i]);
			}else if("-o".equals(args[i])||"-option".equals(args[i])){
				option=args[++i];
			}else if ("-t".equals(args[i])||"-text".equals(args[i])) {
				texts=args[++i];
			}else if ("-p".equals(args[i])||"-path".equals(args[i])) {
				path=args[++i];
			}else if("-s".equals(args[i])||"-no-save".equals(args[i])){
				isNeedSave=false;
			}else if("-r".equals(args[i])||"-no-rotate".equals(args[i])){
				isNeedRotate=false;
			}else if("-e".equals(args[i])||"-extension".equals(args[i])){
				if (args[++i].equals("png")||args[i].equals("jpg")||args[i].equals("jpeg")) {
					extension=args[i];
				}else{
					System.out.println("引数指定の誤り : 対応していない形式が指定されました");
					System.out.println("指定できる形式は以下のとおりです");
					System.out.println("png,jpg(default),jpeg");
					System.out.println(helpMessage);
					return;
				}
			}else{
				System.out.println("引数指定の誤り：未知の引数が指定されました");
				System.out.println(helpMessage);
				return;
			}
		}
		System.out.println(texts);
		while(length>a){
			html=getHTML(20,texts,a*20);
			save();
			a++;
		}
		System.out.print("Request : ");
		System.out.println(requestCount);
		System.out.print("Error : ");
		System.out.println(errorCount);
	}
	
	private void save(){
		m=p.matcher(html);
		File file = new File(path+"1."+extension);
		while(m.find()){
			int i=0;
			requestCount++;
			System.out.println(getURL());
			if (isNeedSave) {
				BufferedImage bi;
				if (isNeedRotate) {
					bi = rotate(getImage(getURL()));
				}else{
					bi = getImage(getURL());
				}
				if(bi==null){
					errorCount++;
					continue;
				}
				try {
					while(file.exists()){
						i++;
						file = new File(path+i+"."+extension);
					}
					FileOutputStream fo = new FileOutputStream(file);
					if (extension.equals("png")) {
						harujisaku.javasaveimage.ImageWithDpi.saveImageWithDPI(fo,bi,96,"png");
					}else{
						saveJpeg(fo,bi,1f,96);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String getHTML(int count,String text,int start){
		try {
			
		String sendUrl;
		try {
			sendUrl = url+"?count="+String.valueOf(count)+"&query="+URLEncoder.encode(text,"UTF-8")+option+"&start="+String.valueOf(start);
		} catch(UnsupportedEncodingException e) {
			sendUrl = url+"?count="+String.valueOf(count)+"&query="+text+option+"&start="+String.valueOf(start);
		}
		System.out.println(sendUrl);
		HttpURLConnection connection = (HttpURLConnection) new URL(sendUrl).openConnection();
		connection.setRequestProperty("User-Agent" , "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0");
		int responseCode = connection.getResponseCode();
		InputStream inputStream;
		if (200 <= responseCode && responseCode <= 299) {
			inputStream = connection.getInputStream();
		}else{
			System.out.println("error");
			System.out.println(responseCode);
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
	
	private String getURL(){
		return m.group(1);
	}

	private BufferedImage getImage(String imageURL){
		try {
			HttpURLConnection urlcon =(HttpURLConnection)new URL(imageURL).openConnection();
			urlcon.setConnectTimeout(5000);
			urlcon.setReadTimeout(5000);
			BufferedImage bi;
			try {
				int responseCode=urlcon.getResponseCode();
				if (200<=responseCode&&responseCode<=299) {
					bi =ImageIO.read(urlcon.getInputStream());
				}else{
					System.out.println("error");
					System.out.println(responseCode);
					bi=null;
				}
			} catch(SocketTimeoutException e) {
				System.out.println("Time Out!");
				return null;
			}
			return bi;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean saveJpeg(FileOutputStream outputStream, BufferedImage img, float compression, int dpi) {
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
		}else if(img.getType()==0){
			return false;
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
	
	private BufferedImage rotate(BufferedImage bi){
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