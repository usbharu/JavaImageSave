package harujisaku.javasaveimage.util;

/**
 * オプションの引数の数が不正な場合に発生する例外です。
 *
 * @author usbharu
 * @version 1.6.1
 * @since 1.6.1
 */
public class OptionIndexOutOfBoundsException extends IndexOutOfBoundsException {
	private static final long serialVersionUID = 1L;

	/**
	 * @param message 指定された詳細メッセージを持つOptionIndexOutOfBoundsExceptionクラスを構築します。
	 */
	public OptionIndexOutOfBoundsException(String message) {
		super(message);
	}
}