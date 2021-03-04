package harujisaku.javasaveimage.util;

import java.util.Locale;
import java.util.ResourceBundle;

import java.io.File;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.MissingResourceException;
import java.net.MalformedURLException;

public enum Message{
	HELP,
	UNSUPPORTED_FORMAT,
	UNKNOWN_OPTION,
	REQUEST,
	ERROR,
	TIME_OUT,
	UNSUPPORTED_OPTION,
	SECURITY_ERROR;

	@Override public String toString(){
		File dicDir = new File("lang").getAbsoluteFile();
		URLClassLoader urlLoader = null;
		try {
			urlLoader = new URLClassLoader(new URL[]{dicDir.toURI().toURL()});
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			String re = ResourceBundle.getBundle("MessageList",Locale.getDefault(),urlLoader).getString(name());
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
