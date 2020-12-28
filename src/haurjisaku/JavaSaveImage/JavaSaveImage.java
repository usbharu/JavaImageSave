import java.io.*;

import java.util.regex.*;

import java.net.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class JavaSaveImage{
	static String url = "https://www.so-net.ne.jp/search/image/?query=";
	// static Pattern p = Pattern.compile("<img.*?src\\s*=\\s*[\"|'](.*?)[\"|'].*?>");
	static Pattern p = Pattern.compile("<a.*?href\\s*=\\s*[\"|'](.*?)[\"|'].*? rel=\"search_result\".*?>");
	static Matcher m;
	static String html="";
	public JavaSaveImage(){
		
	}
	
	public static void main(String[] args) {
		if (args.length==0) {
			html=getHTML("raspberry");
		}else if (args.length!=1) {
			return;
		}else {
			html=getHTML(args[0]);
		}
		System.out.println(html);
		m=p.matcher(html);
		while(m.find()){
			System.out.println(getURL());
		}
	}
	
	private static String getHTML(String text){
		try {
			
		String sendUrl;
		try {
			sendUrl = url+URLEncoder.encode(text,"UTF-8");
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
			return null;
		}
	}
	
	private static String getURL(){
		return m.group(1);
	}
}