package harujisaku.javasaveimage.util;

/**
 * オプションが不正な場合に発生する例外です。
 *
 * @author usbhaur
 * @version 1.6.1
 * @since 1.6.1
 */
public class OptionIllegalArgumentException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	/**
	 * @param e 指定された詳細メッセージを持つOptionIllegalArgumentExceptionクラスを構築します。
	 */
	public OptionIllegalArgumentException(Exception e) {
		super(e.getMessage());
	}
}