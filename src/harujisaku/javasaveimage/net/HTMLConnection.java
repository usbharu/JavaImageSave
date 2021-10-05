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

/**
 * htmlを取得します。
 *
 * @author usbharu
 * @version 1.6.1
 * @since 1.6.1
 */
public class HTMLConnection {

	/**
	 * デフォルトコンストラクタ
	 */
	public HTMLConnection() {

	}

	/**
	 * ユーザーエージェントとurlを指定してhtmlを取得します。
	 *
	 * @param userAgent 指定するユーザーエージェント
	 * @param url       指定するurl
	 */
	public HTMLConnection(String userAgent, String url) {
		this.userAgent = userAgent;
		this.url = url;
	}

	/**
	 * 初期のユーザーエージェント
	 */
	private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0";


	/**
	 * 初期のurl
	 */
	private String url = "https://www.so-net.ne.jp/search/image/";

	/**
	 * 検索するページ、文字、オプション、検索開始位置を指定してhtmlを取得します。
	 * @param count 検索するページ デフォルトでは20の倍数である必要があります。
	 * @param text 検索する文字 エンコードする必要はありません。自動でUTF-8にエンコードされます。
	 * @param option オプション文字列
	 * @param start 検索開始位置を指定します。 デフォルトでは20でいいです。
	 * @return 取得したhtml
	 * @throws IOException インターネット接続に問題があった場合
	 */
	public String getHTML(int count, String text, String option, int start) throws IOException {
		String sendUrl;
		sendUrl = url + "?count=" + count + "&query=" + URLEncoder.encode(text, StandardCharsets.UTF_8) + option + "&start=" + start;
		HttpURLConnection connection = (HttpURLConnection) new URL(sendUrl).openConnection();
		connection.setRequestProperty("User-Agent", userAgent);
		int responseCode = connection.getResponseCode();
		InputStream inputStream;
		if (200 <= responseCode && responseCode <= 299) {
			inputStream = connection.getInputStream();
		} else {
			System.out.println(Message.ERROR);
			System.out.println(responseCode);
			inputStream = connection.getErrorStream();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		StringBuilder response = new StringBuilder();
		String currentLine;
		while ((currentLine = in.readLine()) != null) {
			response.append(currentLine);
		}
		in.close();
		return response.toString();
	}

	/**
	 * @param count       検索する回数 指定した回数検索されます。
	 * @param searchWords 検索する文字 エンコードする必要はありません。自動でUTF-8にエンコードされます。
	 * @param option      オプション文字列
	 * @return 取得したhtml
	 * @throws IOException インターネット接続に問題があった場合
	 */
	public String getHTML(int count, String searchWords, String option) throws IOException {
		return getHTML(20, searchWords, option, count * 20);
	}

	/**
	 * @param userAgent ユーザーエージェントを指定します。
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * @param url urlを指定します。
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return 指定されたユーザーエージェントを返します。
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * @return 指定されたurlを返します。
	 */
	public String getUrl() {
		return url;
	}
}