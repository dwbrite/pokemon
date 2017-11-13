package gui.childElements;

/**
 * Created by dwbrite on 5/12/16.
 */
public class GuiMenuOption {
	private String string;
	
	public GuiMenuOption(String str) {
		this.string = str;
	}
	
	public void activate() {
		System.out.println("Haha! Tricked you! I do nothing! \u25B6 \u25B7 \u25B8 \u25B9" + this);
	}
	
	public void draw(int x, int y) {
		GuiTextBuilder txt = new GuiTextBuilder(string);
		txt.setFont(GuiTextBuilder.getFont(GuiTextBuilder.KEY_FNT_GREEN)).draw(x, y);
	}
	
	@Override
	public String toString() {
		return string;
	}
}
