package harujisaku.javasaveimage.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import harujisaku.javasaveimage.util.*;

public class OptionManager extends ArrayList<Option>{

  private List<Integer> optionIndexList = new ArrayList<Integer>();
  public OptionManager(List<Option> optionList){
    super(optionList);
  }

  public OptionManager(){
    super();
  }

  public void optionProcess(String[] args){
    List<Integer> argIndexList = new ArrayList<Integer>();
    List<Option> useOption = new ArrayList<Option>();
    for (int i = 0,len=args.length;i<len ;i++ ) {
      int optionIndex = indexOf(args[i]);
      if (optionIndex!=-1) {
        useOption.add(get(optionIndex));
        argIndexList.add(i);
      }
    }
    for (int i = 0,len=argIndexList.size()-1;i<len ;i++ ) {
      int argOption = argIndexList.get(i);
      int argStart = argOption+1;
      int argEnd = argIndexList.get(i+1)-1;
      List<String> argList = new ArrayList<String>();
      for (int j = 0,lenj=argEnd-argStart+1;j<lenj ; j++) {
        argList.add(args[j+argStart]);
      }
        useOption.get(i).run(argList.toArray(new String[argList.size()]));
    }
  }

  private Object runOption(Option opt){
    return null;
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
      i++;
      if (option.contains(arg)) {
        return i;
      }
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
}
