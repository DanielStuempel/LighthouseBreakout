package stuff;

public abstract class SwitchButton extends MenuButton {
	String text;
	
	public SwitchButton(String text, boolean value) {
		this(text, Style.font.getSize(), value);
	}
	
	public SwitchButton(String text, int fontSize, boolean value) {
		super(text, fontSize);
		this.text = text;
		changeValue(value);
	}
	
	public void changeValue(boolean value) {
		setText(text + " : " + (value ? "on" : "off"));
	}
}
