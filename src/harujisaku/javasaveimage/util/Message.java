package harujisaku.javasaveimage.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * システムメッセージを定義する列挙型です。
 *
 * @author usbharu
 * @version 1.6.1
 * @since 1.4.3
 */
public enum Message {
	HELP,
	UNSUPPORTED_FORMAT,
	UNKNOWN_OPTION,
	REQUEST,
	ERROR,
	TIME_OUT,
	UNSUPPORTED_OPTION,
	SECURITY_ERROR;

	/**
	 * 言語ごとに別れたメッセージを返します。
	 * 取得できた言語によって返されるメッセージが変わります。
	 *
	 * @return 言語によって指定されたメッセージを返します。
	 */
	@Override
	public String toString() {
		File dicDir = new File("lang").getAbsoluteFile();
		URLClassLoader urlLoader = null;
		try {
			urlLoader = new URLClassLoader(new URL[]{dicDir.toURI().toURL()});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			String re = ResourceBundle.getBundle("MessageList", Locale.getDefault(), urlLoader).getString(name());
			return re;
		} catch(MissingResourceException ex){
			ex.printStackTrace();
			return ResourceBundle.getBundle("MessageList",Locale.US,urlLoader).getString(name());
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}