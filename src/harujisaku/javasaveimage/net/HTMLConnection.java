package harujisaku.javasaveimage.net;

import harujisaku.javasaveimage.util.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HTMLConnection {

	public HTMLConnection() {
	}

	public HTMLConnection(String userAgent, String url) {
		this.userAgent = userAgent;
		this.url = url;
	}

	private String userAgent="Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0";



	private String url = "https://www.so-net.ne.jp/search/image/";

	public String getHTML(int count, String text, String option, int start) throws IOException {
		String sendUrl;
		sendUrl = url+"?count="+ count +"&query="+ URLEncoder.encode(text, StandardCharsets.UTF_8)+option+"&start="+ start;
		HttpURLConnection connection = (HttpURLConnection) new URL(sendUrl).openConnection();
		connection.setRequestProperty("User-Agent" ,userAgent );
		int responseCode = connection.getResponseCode();
		InputStream inputStream;
		if (200 <= responseCode && responseCode <= 299) {
			inputStream = connection.getInputStream();
		}else{
			System.out.println(Message.ERROR);
			System.out.println(responseCode);
			inputStream = connection.getErrorStream();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		StringBuilder response = new StringBuilder();
		String currentLine;
		while((currentLine = in.readLine())!=null){
			response.append(currentLine);
		}
		in.close();
		return response.toString();
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public String getUrl() {
		return url;
	}
}
