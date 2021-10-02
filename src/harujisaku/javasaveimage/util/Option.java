package harujisaku.javasaveimage.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * オプションを指定します。
 *
 * @author usbharu
 * @version 1.6.1
 * @since 1.5.0
 */
public class Option extends ArrayList<String> {

	/**
	 * オプションが指定されたときに実行される{@link IRunOption}を保持します。
	 * 初期状態は何も実行されません。
	 */
	private IRunOption runOption = new IRunOption() {
		@Override
		public void runOption(Object[] obj) {
		}
	};

	/**
	 * オプションの引数の形式{@link OptionArgs}を保持します。
	 */
	private OptionArgs[] optionArgs = new OptionArgs[]{OptionArgs.INTEGER};

	/**
	 * フォーマットのエラーメッセージ
	 */
	private String formatErrorMessage = "";
	/**
	 * 引数の長さが異常なときにのエラーメッセージ
	 */
	private String argumentLengthErrorMessage = "";
	/**
	 * オプションの簡単な説明メッセージ
	 */
	private String description = "write description here";
	/**
	 * 引数の名前
	 */
	private String[] argumentName = {""};
	/**
	 * 引数を要求するか
	 */
	private boolean needsArgument = true;

	/**
	 * オプションを指定します。
	 *
	 * @param option オプションを{@link List}で指定します。
	 */
	public Option(List<String> option) {
		super(option);
	}

	/**
	 * オプションを指定します。
	 *
	 * @param option オプションを配列で指定できます。
	 */
	public Option(String... option) {
		super();
		for (String str : option) {
			super.add(str);
		}
	}

	/**
	 * オプションが指定されたときに実行される動作を指定します。
	 *
	 * @param runa オプションが指定されたときに実行される{@link IRunOption}を指定します。
	 */
	public void setRun(IRunOption runa) {
		runOption = runa;
	}

	/**
	 * フォーマットエラーが発生したときのメッセージを指定します。
	 *
	 * @param message フォーマットエラーが発生したときのメッセージ
	 */
	public void setFormatErrorMessage(String message) {
		formatErrorMessage = message;
	}

	/**
	 * フォマットエラーが発生したときのメッセージを返します。
	 *
	 * @return フォマットエラーが発生したときのメッセージ
	 */
	public String getFormatErrorMessage() {
		return formatErrorMessage;
	}

	/**
	 * 引数の数が異常なときのエラーのエラーメッセージを指定します。
	 *
	 * @param message 引数の数が異常なときのエラーのエラーメッセージ
	 */
	public void setArgumentLengthErrorMessage(String message) {
		argumentLengthErrorMessage = message;
	}

	/**
	 * 引数の数が異常なときのエラーのエラーメッセージを返します。
	 *
	 * @return 引数の数が異常なときのエラーのエラーメッセージ
	 */
	public String getArgumentLengthErrorMessage() {
		return argumentLengthErrorMessage;
	}

	/**
	 * オプションの簡単な説明を指定します。
	 *
	 * @param str オプションの簡単な説明
	 */
	public void setDescription(String str) {
		description = str;
	}

	/**
	 * オプションの簡単な説明を返します。
	 *
	 * @return オプションの簡単な説明
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param argumentName 引数の名前を指定します。
	 */
	public void setArgumentName(String... argumentName) {
		this.argumentName = argumentName;
	}

	/**
	 * 引数の名前を返します。
	 *
	 * @return 引数の名前
	 */
	public String[] getArgumentName() {
		return argumentName;
	}

	/**
	 * オプションの形式を指定します。
	 *
	 * @param optionMode オプションの形式
	 */
	public void setMode(OptionArgs... optionMode) {
		optionArgs = optionMode;
	}

	/**
	 * 引数を要求するか指定します。
	 *
	 * @param needsArgument 引数を要求するか
	 */
	public void setNeedsArgument(boolean needsArgument) {
		this.needsArgument = needsArgument;
	}

	/**
	 * 引数を要求するか返します。
	 *
	 * @return 引数を要求するか
	 */
	public boolean getNeedsArgument() {
		return needsArgument;
	}

	/**
	 * オプションの形式を返します。
	 *
	 * @return オプションの形式
	 */
	OptionArgs[] getMode() {
		return optionArgs;
	}

	/**
	 * オプションが指定されたときに実行されます。
	 *
	 * @param obj 指定されたとき渡される引数
	 */
	void run(Object[] obj) {
		runOption.runOption(obj);
	}

	/**
	 * 配列でオプションを追加します。
	 * {@link List#addAll(Collection)}を使ってください。
	 *
	 * @param options 追加するオプション
	 * @return trueを返します。
	 * @deprecated このメソッドは使う必要がありません。
	 */
	@Deprecated
	public boolean add(String... options) {
		this.addAll(Arrays.asList(options));
		return true;
	}
}