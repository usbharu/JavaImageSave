package harujisaku.javasaveimage.util;

import java.util.List;
import java.util.ArrayList;

public class Option extends ArrayList<String>{

  private IRunOption runOption = new IRunOption(){@Override public void runOption(Object[] obj){}};

  private OptionArgs[] optionArgs = new OptionArgs[]{OptionArgs.INTEGER};

  private String formatErrorMessage = "";
  private String argumentLengthErrorMessage = "";
  private String description = "write description here";
  private String[] argumentName = {""};
  private boolean needsArgument = true;

  public Option(List<String> option){
    super(option);
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

  public void setFormatErrorMessage(String message){
    formatErrorMessage=message;
  }

  public String getFormatErrorMessage(){
    return formatErrorMessage;
  }

  public void setArgumentLengthErrorMessage(String message){
    argumentLengthErrorMessage=message;
  }

  public String getArgumentLengthErrorMessage(){
    return argumentLengthErrorMessage;
  }

  public void setDescription(String str){
    description=str;
  }

  public String getDescription(){
    return description;
  }

  public void setArgumentName(String... argumentName){
    this.argumentName=argumentName;
  }

  public String[] getArgumentName(){
    return argumentName;
  }

  public void setMode(OptionArgs... optionMode){
    optionArgs=optionMode;
  }

  public void setNeedsArgument(boolean needsArgument){
    this.needsArgument=needsArgument;
  }

  public boolean getNeedsArgument(){
    return needsArgument;
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
}
