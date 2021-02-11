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

  public void optionProcess(String[] arg){
    int index = indexOf(new ArrayList<String>(Arrays.asList(arg)));
    get(index).run();
  }

  public int indexOf(List<String> searchOptionList){
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

  public boolean contains(ArrayList<String> searchOptionList){
    return indexOf(searchOptionList)==0?true:false;
  }
}
