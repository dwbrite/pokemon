package region;

import area.AbstractArea;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public abstract class AbstractRegion {
	
	
	protected int currentArea;
	
	protected int areaX, areaY;
	
	protected int mapX, mapY;
	
	protected int width, height;
	
	protected int[][] areaArray;
	
	protected ArrayList<AbstractArea> areas = new ArrayList<AbstractArea>();
	
	protected String name;
	
	public AbstractRegion() {
	}
	
	public void init(GameContainer gc) {
		for (int i = 0; i < areas.size(); i++) {
			areas.get(i).init(gc);
		}
	}
	
	public void render(GameContainer gc, Graphics g) {
		areas.get(currentArea).render(gc, g);
	}
	
	public void update(GameContainer gc) {
		areas.get(currentArea).update(gc);
	}
	
	public AbstractArea getCurrentArea() {
		return areas.get(currentArea);
	}
	
	public void setCurrentArea(int area) {
		currentArea = area;
		getArea(currentArea).setActiveEntities();
	}
	
	public AbstractArea getArea(int area) {
		return areas.get(area);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getName() {
		return name;
	}
}