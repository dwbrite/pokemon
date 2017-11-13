package area.kanto.cities;

import area.AbstractArea;
import org.newdawn.slick.GameContainer;
import region.Kanto;
import region.RegionManager;

public class FuchsiaCity extends AbstractArea {
	public FuchsiaCity(String resource, String wildlifecsv) {
		super(resource, wildlifecsv);
	}
	
	public void init(GameContainer gc) {
		southAreaValue = Kanto.ROUTE1;
		northAreaValue = Kanto.ROUTE1;
		
		northArea = RegionManager.getArea(RegionManager.KANTO, northAreaValue);
		southArea = RegionManager.getArea(RegionManager.KANTO, southAreaValue);
		westArea = RegionManager.getArea(RegionManager.KANTO, westAreaValue);
		eastArea = RegionManager.getArea(RegionManager.KANTO, eastAreaValue);
	}
}