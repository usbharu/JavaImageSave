package harujisaku.javasaveimage.util;

public enum OptionArgs{
	INTEGER("Integer"),
	STRING("String"),
	DOUBLE("Double"),
	BOOLEAN("Boolean");

	private final String text;

	private OptionArgs(final String text){
		this.text = text;
	}

	public String getString(){
		return this.text;
	}
}
