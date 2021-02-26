package harujisaku.javasaveimage.util;

public class OptionIllegalArgumentException extends IllegalArgumentException {
  private static final long serialVersionUID = 1L;

  public OptionIllegalArgumentException(Exception e){
    super(e.getMessage());
  }
}
