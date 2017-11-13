package area.kanto.routes;

import area.AbstractArea;
import org.newdawn.slick.GameContainer;
import region.Kanto;
import region.RegionManager;

public class Route18 extends AbstractArea {
	public Route18(String resource, String wildlifecsv) {
		super(resource, wildlifecsv);
	}
	
	public void init(GameContainer gc) {
		southAreaValue = Kanto.PALLET;
		northAreaValue = Kanto.VIRIDIAN;
		
		northArea = RegionManager.getArea(RegionManager.KANTO, northAreaValue);
		southArea = RegionManager.getArea(RegionManager.KANTO, southAreaValue);
		westArea = RegionManager.getArea(RegionManager.KANTO, westAreaValue);
		eastArea = RegionManager.getArea(RegionManager.KANTO, eastAreaValue);
	}
}
