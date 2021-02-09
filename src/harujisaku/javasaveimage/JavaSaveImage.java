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

import org.w3c.dom.*;

import harujisaku.javasaveimage.*;

public class JavaSaveImage{
	String url = "https://www.so-net.ne.jp/search/image/";
	Pattern p = Pattern.compile("<a.*?href\\s*=\\s*[\"|'](https?://.*?)[\"|'].*? rel=\"search_result\".*?>");
	Matcher m;
	String html="",option="",texts="java",path="",extension="jpg";
	int length=5,requestCount=0,errorCount=0,rotateDegree=0;
	double rotateRadian=Math.toRadians(rotateDegree);
	boolean isNeedSave=true,isNeedRotate=true;
	/*
	 String helpMessage = "java -jar JacaSaveImage.jar [-l|-len|-length search count] -t or -text search text [-o|-option search option] [-p|-path save path] [-s|-no-save] [-e|-extension image type] [-r|-rotate|-no-rotate [rotate degree]]\r\n"+
	 "-h,-help : help , this message ヘルプ　このメッセージ\r\n"+
	 "-l,-len,-length : length　検索ページの長さ\r\n"+
	 "-o,-option : option　検索エンジンに指定するオプション\r\n"+
	 "\tlanguage,ysp_q,size,end,imtype,format,ss_view,from,q_type,view,adult,start\r\n"+
	 "-t,-text : search text　検索するテキスト\r\n"+
	 "-p,-path : save path 保存する場所\r\n"+
	 "-s,-no-save : only search 検索のみ\r\n"+
	 "-e,-extension : image type 保存形式\r\n"+
	 "-r,-no-rotate,-rotate : set rotate degree 回転する方向を指定します。何も指定しなかった場合は横向きになります。";
	*/


	String helpMessage = """
	\tJacaSaveImage.bat [-l|-len|-length search count] -t or -text search text [-o|-option search option] [-p|-path save path] [-s|-no-save] [-e|-extension image type] [-r|-rotate|-no-rotate [rotate degree]]
		-h,-help : help , this message ヘルプ　このメッセージ
		-l,-len,-length : length　検索ページの長さ
		-o,-option : option　検索エンジンに指定するオプション
		\tlanguage,ysp_q,size,end,imtype,format,ss_view,from,q_type,view,adult,start
		-t,-text : search text　検索するテキスト
		-p,-path : save path 保存する場所
		-s,-no-save : only search 検索のみ
		-e,-extension : image type 保存形式
		-r,-no-rotate,-rotate : set rotate degree 回転する方向を指定します。何も指定しなかった場合は横向きになります。
	""";
	public static void main(String[] args) {
		new JavaSaveImage().myMain(args);
	}

	private void myMain(String[] args){
		int a = 0;
		if (args.length==0) {
			System.out.println(helpMessage);
			return;
		}
		SaveImageWithDPI.formatList.add(new SavePNGImageWithDPI());
		SaveImageWithDPI.formatList.add(new SaveJPEGImageWithDPI());
		SaveImageWithDPI.formatList.add(new SaveJPGImageWithDPI());
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
			}else if("-r".equals(args[i])||"-no-rotate".equals(args[i])||"-rotate".equals(args[i])){
				try {
					isNeedRotate=false;
					if (args.length>i+1) {
						rotateDegree=Integer.parseInt(args[++i]);
						rotateRadian=Math.toRadians(rotateDegree);
						System.out.println(rotateDegree);
						System.out.println(rotateRadian);
					}
				} catch(NumberFormatException e) {
					isNeedRotate=false;
					--i;
				}
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
				bi = rotate(getImage(getURL()));
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
					SaveImageWithDPI.saveImageWithDPI(fo,bi,96,extension);
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

	private BufferedImage rotate(BufferedImage bi){
		if (bi==null) {
			return null;
		}
		if (!isNeedRotate) {
			double width=bi.getWidth(),height=bi.getHeight();
			int afterWidth=(int)Math.round(height*Math.abs(Math.sin(rotateRadian))+width*Math.abs(Math.cos(rotateRadian)));
			int afterHeight=(int)Math.round(width*Math.abs(Math.sin(rotateRadian))+height*Math.abs(Math.cos(rotateRadian)));
			BufferedImage out = new BufferedImage(afterWidth,afterHeight,BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = out.createGraphics();
			AffineTransform rotate = new AffineTransform();
			AffineTransform move = new AffineTransform();
			rotate.rotate(rotateRadian,width/2,height/2);
			move.translate(afterWidth/2-width/2,afterHeight/2-height/2);
			move.concatenate(rotate);
			g2.drawImage(bi,move,null);
			return out;
		}else{
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
}
