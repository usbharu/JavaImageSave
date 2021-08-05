package harujisaku.javasaveimage;

import harujisaku.javasaveimage.imageio.*;
import harujisaku.javasaveimage.net.HTMLConnection;
import harujisaku.javasaveimage.util.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaSaveImage{

	String url = "https://www.so-net.ne.jp/search/image/";
	Pattern p = Pattern.compile("<a.*?href\\s*=\\s*[\"|'](https?://.*?)[\"|'].*? rel=\"search_result\".*?>");
	Matcher m;
	String html="",option="",texts="java",path="",extension="jpg",userAgent="Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0";
	int length=5,requestCount=0,errorCount=0,rotateDegree=0,timeOut=5000;
	double rotateRadian=Math.toRadians(rotateDegree);
	boolean isNeedSave=true,isNeedRotate=false,isDebugMode=false;

	public static void main(String[] args) {
		try {
			new JavaSaveImage().myMain(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void myMain(String[] args) throws IOException {
		int a = 0;
		if (args.length==0) {
			System.out.println(Message.HELP);
			return;
		}

		Option debugOption = new Option("--dev");
		Option helpOption = new Option("-h","--help");
		Option lengthOption = new Option("-l","--length");
		Option optionOption = new Option("-o","--op","--option");
		Option textOption = new Option("-t","--text");
		Option pathOption = new Option("-p","--path");
		Option saveOption = new Option("-s","--no-save");
		Option rotateOption = new Option("-r","--no-rotate","--rotate");
		Option extensionOption = new Option("-e","--extension");
		OptionManager opm = new OptionManager();

		opm.add(debugOption,helpOption,lengthOption,optionOption,textOption,pathOption,saveOption,rotateOption,extensionOption);

		debugOption.setDescription("debug mode");
		debugOption.setNeedsArgument(false);
		debugOption.setRun(new IRunOption(){
			@Override public void runOption(Object[] obj){
				if (obj.length==0) {
					isDebugMode=true;
					opm.setDebugMode(true);
					return;
				}
				isDebugMode=(boolean)obj[0];
				opm.setDebugMode((boolean)obj[0]);
			}
		});

		helpOption.setNeedsArgument(false);
		helpOption.setDescription("help message");
		helpOption.setRun(new IRunOption(){
			@Override	public void runOption(Object[] obj){
				System.out.println(Message.HELP);
				System.exit(0);
			}
		});

		lengthOption.setArgumentName("length");
		lengthOption.setDescription("search count");
		lengthOption.setMode(OptionArgs.INTEGER);
		lengthOption.setRun(new IRunOption(){
			@Override	public void runOption(Object[] obj){
				length=(int)obj[0];
			}
		});

		optionOption.setArgumentName("option");
		optionOption.setDescription("search option");
		optionOption.setMode(OptionArgs.STRING);
		optionOption.setRun(new IRunOption(){
			@Override	public void runOption(Object[] obj){
				option=(String)obj[0];
			}
		});

		textOption.setArgumentName("words");
		textOption.setDescription("search term");
		textOption.setMode(OptionArgs.STRING);
		textOption.setRun(new IRunOption(){
			@Override	public void runOption(Object[] obj){
				texts=(String)obj[0];
			}
		});

		pathOption.setArgumentName("path");
		pathOption.setDescription("save path");
		pathOption.setMode(OptionArgs.STRING);
		pathOption.setRun(new IRunOption(){
			@Override	public void runOption(Object[] obj){
				path=(String)obj[0];
				try {
					File file = new File(path);
					if (file.getParent()!=null) {
						file.getParentFile().mkdirs();
					}
				} catch(SecurityException e) {
					System.out.println(Message.SECURITY_ERROR);
				}
			}
		});

		saveOption.setDescription("not save");
		saveOption.setMode(OptionArgs.BOOLEAN);
		saveOption.setRun(new IRunOption(){
			@Override	public void runOption(Object[] obj){
				if (obj.length==0) {
					isNeedSave=false;
					return;
				}
				isNeedSave=(boolean)obj[0];
			}
		});

		rotateOption.setDescription("rotate degree");
		rotateOption.setArgumentName("degree (default auto horizontal)");
		rotateOption.setMode(OptionArgs.INTEGER);
		rotateOption.setRun(new IRunOption(){
			@Override	public void runOption(Object[] obj){
				isNeedRotate=true;
				if (obj.length!=0) {
					rotateDegree=(int)obj[0];
				}
			}
		});

		extensionOption.setArgumentName("extension name (default "+extension+")");
		extensionOption.setDescription("extension");
		extensionOption.setMode(OptionArgs.STRING);
		extensionOption.setRun(new IRunOption(){
			@Override	public void runOption(Object[] obj){
				extension=(String)obj[0];
			}
		});

		opm.optionProcess(args);
		System.out.println(opm.getHelpMessage());
		SaveImageWithDPI.formatList.add(new SavePNGImageWithDPI());
		SaveImageWithDPI.formatList.add(new SaveJPEGImageWithDPI());
		SaveImageWithDPI.formatList.add(new SaveJPGImageWithDPI());

		System.out.println("search text : "+texts);
		while(length>a){
			HTMLConnection htmlConnection = new HTMLConnection(userAgent,url);
			html=htmlConnection.getHTML(20,texts,option,a*20);
			save();
			a++;
		}
		System.out.print(Message.REQUEST+" : ");
		System.out.println(requestCount);
		System.out.print(Message.ERROR+" : ");
		System.out.println(errorCount);
	}

	private void save(){
		m=p.matcher(html);
		File file = new File(path+"1."+extension);
		while(m.find()){
			int i=0;
			requestCount++;
			if (isDebugMode) {
				System.out.println(getURL());
			}
			if (isNeedSave) {
				BufferedImage bi;
				if (isNeedRotate) {
					bi = AutoRotateImage.rotate(getImage(getURL()),rotateRadian);
				}else{
					System.out.println("auto rotate");
					bi = AutoRotateImage.autoRotate(getImage(getURL()),AutoRotateImage.HORIZONTAL);
				}
				if(bi==null){
					errorCount++;
					continue;
				}
				try {
					while(file.exists()){
						i++;
						file = new File(path+i+"."+extension);
					}
					FileOutputStream fo = new FileOutputStream(file);
					SaveImageWithDPI.saveImageWithDPI(fo,bi,96,extension);
				} catch(Exception e) {
					if (isDebugMode) {
						e.printStackTrace();
					}else{
						System.out.println(Message.ERROR);
					}
				}
			}
		}
	}

	private String getURL(){
		return m.group(1);
	}

	private BufferedImage getImage(String imageURL){
		try {
			HttpURLConnection urlcon =(HttpURLConnection)new URL(imageURL).openConnection();
			urlcon.setConnectTimeout(timeOut);
			urlcon.setReadTimeout(timeOut);
			BufferedImage bi;
			try {
				int responseCode=urlcon.getResponseCode();
				if (200<=responseCode&&responseCode<=299) {
					bi =ImageIO.read(urlcon.getInputStream());
				}else{
					System.out.println(Message.ERROR);
					System.out.println(responseCode);
					bi=null;
				}
			} catch(SocketTimeoutException e) {
				System.out.println(Message.TIME_OUT);
				return null;
			}
			return bi;
		} catch(Exception e) {
			if (isDebugMode) {
				e.printStackTrace();
			}else{
				System.out.println(Message.ERROR);
			}
			return null;
		}
	}

	public void setDebugMode(boolean mode){
		isDebugMode=mode;
	}

	public boolean getDebugMode(){
		return isDebugMode;
	}
}
