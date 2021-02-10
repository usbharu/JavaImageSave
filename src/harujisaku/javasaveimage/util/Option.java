package harujisaku.javasaveimage.util;

import java.util.List;
import java.util.ArrayList;

public class Option extends ArrayList{
  private final int optionLength = 1;
  private final int optionLengthMax = 1;
  private final int optionLengthMin = 1;

  public Option(String option){
    super(option);
  }

  public Option(String option,int optionLength){
    super(option);
    this.optionLength=optionLength;
  }

  public Option(String option,int optionLengthMin,int optionLengthMax){
    super(option);
    this.optionLengthMin=optionLengthMin;
    this.optionLengthMax=optionLengthMax;
  }
}
