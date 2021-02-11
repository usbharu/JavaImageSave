package harujisaku.javasaveimage.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import harujisaku.javasaveimage.util.*;

public class OptionManager extends ArrayList<Option>{
  public OptionManager(List<Option> optionList){
    super(optionList);
  }

  public OptionManager(){

  }

  public void optionProcess(String[] arg){
    indexOf(new ArrayList<String>(Arrays.asList(arg)));
  }

  private int indexOf(ArrayList<String> searchOptionList){
    for (int i=0,len=size();i<len;i++ ) {
      for (String option : searchOptionList ) {
        if (get(i).contains(option)){
          return i;
        }
      }
    }
    return -1;
  }

  private boolean contains(ArrayList<String> searchOptionList){
    return indexOf(searchOptionList)==0?true:false;
  }
}