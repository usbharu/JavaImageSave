package harujisaku.javasaveimage.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OptionManager extends ArrayList<Option>{

	private boolean isDebugMode = false;
	private int messageMode = 0;

	public static final int AUTO_MAKE_MESSAGE = 0;

	public static final int MANUAL_SET_MESSAGE = 1;

	public OptionManager(List<Option> optionList){
		super(optionList);
	}

	public OptionManager(){
		super();
	}

	public void setDebugMode(boolean mode){
		isDebugMode=mode;
	}

	public boolean getDebugMode(){
		return isDebugMode;
	}

	public void setMessageMode(int mode){
		if (mode==0||mode==1) {
			messageMode=mode;
		}
	}

	public int getMessageMode(){
		return messageMode;
	}

	public String getHelpMessage(){
		StringBuilder sb = new StringBuilder();
		for (Option option : this ) {
			sb.append("[ ");
			for (String str :option ) {
				sb.append(str);
				sb.append("|");
			}
			sb.replace(sb.length()-1,sb.length()," ");
			for (int i = 0,len=option.getMode().length;i<len ;i++ ) {
				if (!option.getArgumentName()[i].isEmpty()) {
					sb.append(option.getArgumentName()[i]+" ");
				}else if(option.getNeedsArgument()){
					sb.append(option.getMode()[i]);
				}
			}
			sb.append("] ");
		}
		return sb.toString();
	}

	public void optionProcess(String[] args){
		if (args.length!=0) {
			File file = new File(args[0]);
			try {
				file.getCanonicalPath();
			} catch(IOException e) {
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
		for (int i = 0,len=args.length;i<len ;i++ ) {
			int optionIndex = indexOf(args[i]);
			if (optionIndex!=-1) {
				argIndexList.add(i);
				useOption.add(get(optionIndex));
			}
		}
		System.out.println(argIndexList);
		System.out.println(useOption);
		for (int i = 0,len=argIndexList.size()-1;i<len ;i++ ) {
			int argOption = argIndexList.get(i);
			int argStart = argOption+1;
			int argEnd = argIndexList.get(i+1)-1;
			List<String> argList = new ArrayList<String>();
			for (int j = 0,lenj=argEnd-argStart+1;j<lenj ; j++) {
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
		for (int j = 0,lenj=argEnd-argStart+1;j<lenj ; j++) {
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
			}else if(messageMode==AUTO_MAKE_MESSAGE){
				System.out.println(Message.UNSUPPORTED_OPTION);
			}
			if (isDebugMode) {
				e.printStackTrace();
			}
			System.exit(1);
		}
	}

	public void optionProcessWithFile(File file){
		try {
			FileReader filereader = new FileReader(file);
			try(BufferedReader br = new BufferedReader(filereader)){
				StringBuilder sb = new StringBuilder();
				String str;
				while ((str=br.readLine())!=null) {
					sb.append(str);
					sb.append(" ");
				}
			optionProcess(sb.toString().split("\\s",0));
			}catch (Exception e) {

			}finally{
			}
		} catch(FileNotFoundException exception_name) {
			exception_name.printStackTrace();
		}
	}

	private int indexOf(List<String> searchOptionList){
		if (searchOptionList==null) {
			return -1;
		}
		for (int i=0,len=size();i<len;i++ ) {
			for (String option : searchOptionList ) {
				if (get(i).contains(option)){
					return i;
				}
			}
		}
		return -1;
	}

	private int indexOf(String arg){
		if (arg==null) {
			return -1;
		}
		int i=0;
		for (Option option : this ) {
			if (option.contains(arg)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	private boolean contains(List<String> searchOptionList){
		return indexOf(searchOptionList)!=-1;
	}

	private boolean contains(String arg){
		return indexOf(arg)!=-1;
	}

	public boolean add(Option... options){
		for (Option option :options ) {
			add(option);
		}
		return true;
	}

	private Object[] optionFormating(Option option,String[] args){
		if (!option.getNeedsArgument()) {
			return new Object[0];
		}
		OptionArgs[] optionArgs = option.getMode();
		Object[] returnObject = new Object[option.size()];
		int i = 0;
		for (OptionArgs optionArg :optionArgs ) {
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
