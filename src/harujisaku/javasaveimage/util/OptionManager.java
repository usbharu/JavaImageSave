package harujisaku.javasaveimage.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link Option}のマネージャーです
 *
 * @author usbharu
 * @version 1.6.1
 * @since 1.5.0
 */
public class OptionManager extends ArrayList<Option> {

	/**
	 * デバッグモードか
	 */
	private boolean isDebugMode = false;
	/**
	 * 自動で生成されたメッセージを使うか
	 */
	private int messageMode = 0;

	/**
	 * 自動で生成されたメッセージを使う
	 */
	public static final int AUTO_MAKE_MESSAGE = 0;

	/**
	 * 手動で指定されたメッセージを使う
	 */
	public static final int MANUAL_SET_MESSAGE = 1;

	/**
	 * オプションの{@link List}を指定してマネージャーを作成します。
	 * @param optionList 指定する{@link Option}の{@link List}
	 */
	public OptionManager(List<Option> optionList) {
		super(optionList);
	}

	/**
	 * マネージャーを作成します。
	 */
	public OptionManager() {
		super();
	}

	/**
	 * デバッグモードを指定します。
	 * @param mode 指定するデバッグモード
	 */
	public void setDebugMode(boolean mode) {
		isDebugMode = mode;
	}

	/**
	 * 指定されているデバッグモードを返します。
	 * @return 指定されているデバッグモード
	 */
	public boolean getDebugMode() {
		return isDebugMode;
	}

	/**
	 * メッセージモードを指定します。
	 * @param mode 指定するメッセージモード
	 */
	public void setMessageMode(int mode) {
		if (mode == 0 || mode == 1) {
			messageMode = mode;
		}
	}

	/**
	 * 指定されているメッセージモードを返します。
	 * @return 指定されているメッセージモード
	 */
	public int getMessageMode() {
		return messageMode;
	}

	/**
	 * コマンドでhelpが呼ばれたときに出されるメッセージを返します。
	 * @return helpメッセージ
	 */
	public String getHelpMessage() {
		StringBuilder sb = new StringBuilder();
		for (Option option : this) {
			sb.append("[ ");
			for (String str : option) {
				sb.append(str);
				sb.append("|");
			}
			sb.replace(sb.length() - 1, sb.length(), " ");
			for (int i = 0, len = option.getMode().length; i < len; i++) {
				if (!option.getArgumentName()[i].isEmpty()) {
					sb.append(option.getArgumentName()[i] + " ");
				} else if (option.getNeedsArgument()) {
					sb.append(option.getMode()[i]);
				}
			}
			sb.append("] ");
		}
		return sb.toString();
	}

	/**
	 * このプログラムが実行されたときにオプションを処理します。
	 *
	 * @param args プログラムに渡された引数。
	 */
	public void optionProcess(String[] args) {
		if (args.length != 0) {
			File file = new File(args[0]);
			try {
				file.getCanonicalPath();
			} catch (IOException e) {
				if (isDebugMode) {
					e.printStackTrace();
				}
				System.exit(1);
			}
			if(file.exists()){
				optionProcessWithFile(file);
			}
		}
		List<Integer> argIndexList = new ArrayList<Integer>();
		List<Option> useOption = new ArrayList<Option>();
		for (int i = 0, len = args.length; i<len ; i++ ) {
			int optionIndex = indexOf(args[i]);
			if (optionIndex!=-1) {
				argIndexList.add(i);
				useOption.add(get(optionIndex));
			}
		}
		System.out.println(argIndexList);
		System.out.println(useOption);
		for (int i = 0,len=argIndexList.size()-1; i<len ; i++ ) {
			int argOption = argIndexList.get(i);
			int argStart = argOption+1;
			int argEnd = argIndexList.get(i+1)-1;
			List<String> argList = new ArrayList<String>();
			for (int j = 0,lenj=argEnd-argStart+1; j<lenj ; j++) {
				argList.add(args[j+argStart]);
			}
			try {
				useOption.get(i).run(optionFormating(useOption.get(i),argList.toArray(new String[argList.size()])));
			} catch(IllegalArgumentException e) {
				if (messageMode==AUTO_MAKE_MESSAGE) {
					System.out.println(Message.UNSUPPORTED_OPTION);
				}else if (messageMode==MANUAL_SET_MESSAGE) {
					System.out.println(useOption.get(i).getFormatErrorMessage());
				}
				if (isDebugMode) {
					e.printStackTrace();
				}
				System.exit(1);
			}
		}
		if(argIndexList.size()<=0){
			return;
		}
		int i = argIndexList.size()-1;
		int argOption = argIndexList.get(i);
		int argStart = argOption+1;
		int argEnd = args.length-1;
		List<String> argList = new ArrayList<String>();
		for (int j = 0,lenj=argEnd-argStart+1; j<lenj ; j++) {
			argList.add(args[j+argStart]);
		}
		try {
			useOption.get(i).run(optionFormating(useOption.get(i),argList.toArray(new String[argList.size()])));
		} catch(OptionIllegalArgumentException e) {
			if (messageMode==MANUAL_SET_MESSAGE) {
				System.out.println(useOption.get(i).getFormatErrorMessage());
			}else if (messageMode==AUTO_MAKE_MESSAGE) {
				System.out.println(Message.UNSUPPORTED_OPTION);
			}
			if (isDebugMode) {
				e.printStackTrace();
			}
			System.exit(1);
		}catch (OptionIndexOutOfBoundsException e) {
			if (messageMode==MANUAL_SET_MESSAGE) {
				System.out.println(useOption.get(i).getFormatErrorMessage());
			} else if (messageMode == AUTO_MAKE_MESSAGE) {
				System.out.println(Message.UNSUPPORTED_OPTION);
			}
			if (isDebugMode) {
				e.printStackTrace();
			}
			System.exit(1);
		}
	}

	/**
	 * ファイルに書かれたオプションを処理します。
	 *
	 * @param file 処理するファイル
	 */
	public void optionProcessWithFile(File file) {
		try {
			FileReader filereader = new FileReader(file);
			try (BufferedReader br = new BufferedReader(filereader)) {
				StringBuilder sb = new StringBuilder();
				String str;
				while ((str = br.readLine()) != null) {
					sb.append(str);
					sb.append(" ");
				}
				optionProcess(sb.toString().split("\\s", 0));
			} catch (Exception e) {

			} finally {
			}
		} catch (FileNotFoundException exception_name) {
			exception_name.printStackTrace();
		}
	}

	/**
	 * 指定された要素がこのリスト内で最後に検出された位置のインデックスを返します。指定された要素がこのリストにない場合は-1を返します。
	 *
	 * @param searchOptionList 検索する要素
	 * @return 指定された要素がこのリスト内で最後に検出された位置のインデックス。その要素がこのリストにない場合は-1
	 */
	private int indexOf(List<String> searchOptionList) {
		if (searchOptionList == null) {
			return -1;
		}
		for (int i = 0, len = size(); i < len; i++) {
			for (String option : searchOptionList) {
				if (get(i).contains(option)) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * 指定された要素がこのリスト内で最後に検出された位置のインデックスを返します。指定された要素がこのリストにない場合は-1を返します。
	 *
	 * @param arg 検索する要素
	 * @return 指定された要素がこのリスト内で最後に検出された位置のインデックス。その要素がこのリストにない場合は-1
	 */
	private int indexOf(String arg) {
		if (arg == null) {
			return -1;
		}
		int i = 0;
		for (Option option : this) {
			if (option.contains(arg)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	/**
	 * 指定した要素がこのマネージャーに含まれている場合にtrueを返します。
	 *
	 * @param searchOptionList このリスト内にあるがどうかが判定される要素
	 * @return 指定された要素がこのリスト内にある場合はtrue
	 */
	private boolean contains(List<String> searchOptionList) {
		return indexOf(searchOptionList) != -1;
	}

	/**
	 * 指定された要素がこのリスト内で最後に検出された位置のインデックスを返します。指定された要素がこのリストにない場合は-1を返します。
	 *
	 * @param arg 検索する要素
	 * @return 指定された要素がこのリスト内で最後に検出された位置のインデックス。その要素がこのリストにない場合は-1
	 */
	private boolean contains(String arg) {
		return indexOf(arg) != -1;
	}

	/**
	 * {@link Option}を追加します。
	 *
	 * @param options 追加する{@link Option}
	 * @return trueを返します。
	 */
	public boolean add(Option... options) {
		for (Option option : options) {
			add(option);
		}
		return true;
	}

	/**
	 * プログラムに入力された文字列を{@link OptionArgs}で定義された形式に変換します。
	 *
	 * @param option 変換するオプション
	 * @param args   変換するオプションの文字列
	 * @return 変換後のオプションの文字列
	 */
	private Object[] optionFormating(Option option, String[] args) {
		if (!option.getNeedsArgument()) {
			return new Object[0];
		}
		OptionArgs[] optionArgs = option.getMode();
		Object[] returnObject = new Object[option.size()];
		int i = 0;
		for (OptionArgs optionArg : optionArgs) {
			try {
				switch (optionArg) {
					case INTEGER:
						returnObject[i] = Integer.valueOf(args[i]);
						break;
					case STRING:
						returnObject[i] = args[i];
						break;
					case DOUBLE:
						returnObject[i] = Double.valueOf(args[i]);
						break;
					case BOOLEAN:
						returnObject[i] = Boolean.valueOf(args[i]);
						break;
				}
			} catch(NumberFormatException e) {
				throw new OptionIllegalArgumentException(e);
			} catch (IndexOutOfBoundsException e) {
				throw new OptionIndexOutOfBoundsException(e.getMessage());
			}
			i++;
		}
		return returnObject;
	}
}