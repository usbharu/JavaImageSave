package harujisaku.javasaveimage.util;

/**
 * オプションの形式を定義する列挙型です。
 *
 * @author usbharu
 * @version 1.6.1
 * @since 1.5.0
 */
public enum OptionArgs {
	INTEGER("Integer"),
	STRING("String"),
	DOUBLE("Double"),
	BOOLEAN("Boolean");

	/**
	 * 形式を表すテキスト
	 */
	private final String text;

	/**
	 * 形式を表すテキストを指定します。
	 *
	 * @param text 形式を表すテキスト
	 */
	OptionArgs(final String text) {
		this.text = text;
	}

	/**
	 * 形式を表すテキストを指定します。
	 *
	 * @return 形式を表すテキスト
	 */
	public String getString() {
		return this.text;
	}
}