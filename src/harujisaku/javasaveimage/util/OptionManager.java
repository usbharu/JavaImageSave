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
    for (int i = 0,len=args.length;i<len ;i++ ) {
      System.out.println(args[i]);
      if (contains(args[i])) {
        argIndexList.add(i);
      }
    }
    System.out.println("---------------");
    for (int i = 0,len=argIndexList.size()-1;i<len ;i++ ) {
    int argOption = argIndexList.get(i);
    int argStart = argOption+1;
    int argEnd = argIndexList.get(i+1)-1;
    System.out.println("op"+argOption);
    System.out.println("st"+argStart);
    System.out.println("en"+argEnd);
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
