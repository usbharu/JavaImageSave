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
    super();
  }

  public void optionProcess(String[] args){
    for (Option option : this ) {
      for (String arg :args ) {
        if (option.contains(arg)) {
          option.run();
        }
      }
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

  private boolean contains(List<String> searchOptionList){
    return indexOf(searchOptionList)==0;
  }
}
