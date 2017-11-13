package region;

import area.AbstractArea;
import handlers.Camera;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public class RegionManager {
	public static final int KANTO = 0;
	
	private static ArrayList<AbstractRegion> regions = new ArrayList<AbstractRegion>();
	
	private static int currentRegion;
	
	public static void init(GameContainer gc) {
		regions.add(new Kanto());
		setCurrentRegion(KANTO);
		for (int i = 0; i < regions.size(); i++)
			regions.get(i).init(gc);
		getCurrentRegion().setCurrentArea(Kanto.PALLET);
		Camera.update(); //I'm not going to make an init() method in the Camera class just to call update. I can do it here, and it's more logically sound, even though it's in an init() method, okay?!
	}
	
	public static void render(GameContainer gc, Graphics g) {
		regions.get(currentRegion).render(gc, g);
	}
	
	public static void update(GameContainer gc) {
		regions.get(currentRegion).update(gc);
	}
	
	public static AbstractRegion getCurrentRegion() {
		return regions.get(currentRegion);
	}
	
	public static void setCurrentRegion(int region) {
		currentRegion = region;
	}
	
	public static AbstractRegion getRegion(int region) {
		return regions.get(region);
	}
	
	public static AbstractArea getArea(int region, int area) {
		return getRegion(region).getArea(area);
	}
	
	public static AbstractArea getCurrentArea() {
		return regions.get(currentRegion).getCurrentArea();
	}
	
	public static AbstractArea getNorthArea() {
		return getCurrentArea().getNorthArea();
	}
	
	public static AbstractArea getSouthArea() {
		return getCurrentArea().getSouthArea();
	}
	
	public static AbstractArea getWestArea() {
		return getCurrentArea().getWestArea();
	}
	
	public static AbstractArea getEastArea() {
		return getCurrentArea().getEastArea();
	}
}
