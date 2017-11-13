package gui.childElements;

import handlers.KeyList;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.io.FileInputStream;

/**
 * Created by dwbrite on 5/11/16.
 */
public class GuiTextBuilder {
	
	public static final String KEY_FNT_RED_GREEN = "Power Red and Green";
	
	public static final String KEY_FNT_CLEAR = "Power Clear";
	
	public static final String KEY_FNT_GREEN = "Power Green";
	
	public static final String KEY_FNT_GREEN_NARROW = "Power Green Narrow";
	
	public static final String KEY_FNT_GREEN_SMALL = "Power Green Thin";
	
	public static final String KEY_FNT_RED_BLUE = "Power Red and Blue";
	
	public static final String KEY_CS_NORMAL = "Normal";
	
	public static final String KEY_CS_VARIANT = "Variant";
	
	public static final String KEY_CS_LIGHT = "Light";
	
	public static final String KEY_CS_LIGHTVAR = "Light Variant";
	
	public static final String KEY_CS_MALE = "Male";
	
	public static final String KEY_CS_MALEDATA = "Male Data";
	
	public static final String KEY_CS_FEMALE = "Female";
	
	public static final String KEY_CS_FEMALEDATA = "Female Data";
	
	public static final String KEY_CS_OPTIONS = "Options";
	
	private static KeyList<Color[]> colorSets = new KeyList<>();
	
	private static KeyList<UnicodeFont> fontList = new KeyList<>();
	
	private static UnicodeFont defaultFont;
	
	private static Color[] defaultColorset;
	
	//TODO: figure out this
// static... Setting color sets... It *might* just be "static { ... }, even.  Maybe init?
	static {
		colorSets.set(KEY_CS_FEMALEDATA, new Color[]{new Color(248, 24, 168), new Color(208, 208, 200)});
		colorSets.set(KEY_CS_OPTIONS, new Color[]{new Color(248, 184, 112), new Color(224, 8, 8)});
		defaultColorset = colorSets.get("Normal");
	}
	
	/*************** End of static data ******************/
	
	private String string;
	
	private UnicodeFont font = defaultFont;
	
	private int x = 0, y = 0;
	
	private int lineHeight = 15;
	
	//*/
	private Color[] colorset = defaultColorset;
	
	private boolean shadows = true;
	
	public GuiTextBuilder(String str) {
		this.string = str;
	}
	
	public static UnicodeFont generateFontFromName(String name, int defaultSize, String additionalGlyphs) {
		UnicodeFont uf = new UnicodeFont(new Font(name, Font.PLAIN, defaultSize));
		uf.addAsciiGlyphs();
		uf.addGlyphs(additionalGlyphs);
		uf.getEffects().add(new ColorEffect());
		try { uf.loadGlyphs(); } catch (Exception e) { e.printStackTrace(); }
		return uf;
	}
	
	public static UnicodeFont generateFontFromName(String name, int defaultSize) {
		return generateFontFromName(name, defaultSize, "");
	}
	
	public static UnicodeFont generateFontFromURL(String url, int defaultSize, String additionalGlyphs) {
		Font f = null;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(url));
			f = f.deriveFont(defaultSize);
		} catch (Exception e) { e.printStackTrace(); }
		UnicodeFont uf = new UnicodeFont(f);
		uf.addAsciiGlyphs();
		uf.addGlyphs(additionalGlyphs);
		uf.getEffects().add(new ColorEffect());
		try { uf.loadGlyphs(); } catch (Exception e) { e.printStackTrace(); }
		return uf;
	}
	
	public static UnicodeFont generateFontFromURL(String url, int defaultSize) {
		return generateFontFromURL(url, defaultSize, "\u20BD\u00E9"); //Poke-dollar and pokemon special chars
	}
	
	public static void setDefaultFont(UnicodeFont font) { defaultFont = font; }
	
	public static void setDefaultColorset(Color[] colorset) { defaultColorset = colorset; }
	
	public static Color[] getColorSet(String key) { return colorSets.get(key); }
	
	public static Color[] getColorSet(int key) { return colorSets.get(key); }
	
	public static UnicodeFont getFont(String key) { return fontList.get(key); }
	
	public static UnicodeFont getFont(int key) { return fontList.get(key); }
	
	public static KeyList<Color[]> getColorSetList() { return colorSets; }
	
	public static KeyList<UnicodeFont> getFontList() { return fontList; }
	
	public GuiTextBuilder setX(int x) {
		this.x = x;
		return this;
	}
	
	public GuiTextBuilder setY(int y) {
		this.y = y;
		return this;
	}
	
	public GuiTextBuilder setPosition(int x, int y) { return this.setX(x).setY(y); }
	
	public GuiTextBuilder setLineHeight(int height) {
		this.lineHeight = height;
		return this;
	}
	
	public GuiTextBuilder setColorSet(Color[] colorSet) {
		this.colorset = colorSet;
		return this;
	}
	
	public GuiTextBuilder setColorSet(String colorSet) {
		this.colorset = getColorSet(colorSet);
		return this;
	}
	
	public GuiTextBuilder setShadows(boolean shadows) {
		this.shadows = shadows;
		return this;
	}
	
	public GuiTextBuilder setFont(UnicodeFont font) {
		this.font = font;
		return this;
	}
	
	public GuiTextBuilder setFont(String font) {
		this.font = getFont(font);
		return this;
	}
	
	public GuiTextBuilder setString(String str) {
		this.string = str;
		return this;
	}
	
	//This may need a graphicsContext?
	public void draw() {
		draw(x, y);
	}
	
	public void draw(int x, int y) {
		draw(x, y, colorset[0]);
	}
	
	public void draw(int x, int y, Color color) {
		String nstr = " " + string.replaceAll("\n", "\n ");
		int xOffset = -(font.getSpaceWidth() + 1);
		int yOffset = 0;
		
		String[] strs = nstr.split("\n");
		
		int height = lineHeight;
		int xn = x + xOffset;
		for (int i = 0; i < strs.length; i++) {
			int yn = (y + yOffset + (height * (i)));
			font.drawString(xn, yn, " " + strs[i], color);
		}
	}
}