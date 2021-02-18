package harujisaku.javasaveimage.util;

import java.util.List;
import java.util.ArrayList;

public class Option extends ArrayList<String>{
  private int argumentLength = 1;
  private int argumentLengthMax = 1;
  private int argumentLengthMin = 1;

  private IRunOption runOption = new IRunOption(){@Override public void runOption(Object[] obj){}};

  private OptionArgs[] optionArgs = new OptionArgs[]{OptionArgs.INTEGER};

  public Option(List<String> option){
    super(option);
  }

  public Option(List<String> option,int argumentLength){
    super(option);
    this.argumentLength = argumentLength;
  }

  public Option(List<String> option,int argumentLengthMin,int argumentLengthMax){
    super(option);
    this.argumentLengthMin = argumentLengthMin;
    this.argumentLengthMax = argumentLengthMax;
  }

  public Option(String option,int argumentLength){
    super();
    super.add(option);
    this.argumentLength = argumentLength;
  }

  public Option(String option ,int argumentLengthMin,int argumentLengthMax){
    super();
    super.add(option);
    this.argumentLengthMin = argumentLengthMin;
    this.argumentLengthMax = argumentLengthMax;
  }

  public Option(String... option){
    super();
    for (String str : option ) {
      super.add(str);
    }
  }

  public void setRun(IRunOption runa){
    runOption=runa;
  }

  public void setMode(OptionArgs... optionMode){
    optionArgs=optionMode;
  }

  OptionArgs[] getMode(){
    return optionArgs;
  }

  void run(Object[] obj){
    runOption.runOption(obj);
  }

  public boolean add(String... options){
    for (String option :options ) {
      add(option);
    }
    return true;
  }

  public void test(){
    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    System.out.println(runOption==null);
    System.out.println(hashCode());
    System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbaaaaaaaaaaaa");
  }
}
