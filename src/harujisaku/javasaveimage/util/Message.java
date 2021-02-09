package harujisaku.javasaveimage.util;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Message{
  HELP,
  UNSUPPORTED_FORMAT,
  UNKNOWN_OPTION,
  REQUEST,
  ERROR,
  TIME_OUT;

  @Override public String toString(){
    try {
      return ResourceBundle.getBundle("harujisaku.javasaveimage.util.MessageList",Locale.getDefault()).getString(name());
    } catch(Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }
}
