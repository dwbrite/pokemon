package region;

import area.Clear;
import area.kanto.cities.*;
import area.kanto.routes.*;

public class Kanto extends AbstractRegion {
	public static final int ROUTE1 = 1;
	
	public static final int ROUTE2 = 2;
	
	public static final int ROUTE3 = 3;
	
	public static final int ROUTE4 = 4;
	
	public static final int ROUTE5 = 5;
	
	public static final int ROUTE6 = 6;
	
	public static final int ROUTE7 = 7;
	
	public static final int ROUTE8 = 8;
	
	public static final int ROUTE9 = 9;
	
	public static final int ROUTE10 = 10;
	
	public static final int ROUTE11 = 11;
	
	public static final int ROUTE12 = 12;
	
	public static final int ROUTE13 = 13;
	
	public static final int ROUTE14 = 14;
	
	public static final int ROUTE15 = 15;
	
	public static final int ROUTE16 = 16;
	
	public static final int ROUTE17 = 17;
	
	public static final int ROUTE18 = 18;
	
	public static final int ROUTE19 = 19;
	
	public static final int ROUTE20 = 20;
	
	public static final int ROUTE21 = 21;
	
	public static final int ROUTE22 = 22;
	
	public static final int ROUTE23 = 23;
	
	public static final int ROUTE24 = 24;
	
	public static final int PALLET = 25;
	
	public static final int VIRIDIAN = 26;
	
	public static final int PEWTER = 27;
	
	public static final int CERULEAN = 28;
	
	public static final int LAVENDER = 29;
	
	public static final int CELADON = 30;
	
	public static final int VERMILLION = 31;
	
	public static final int SAFFRON = 32;
	
	public static final int FUCHSIA = 33;
	
	public static final int CINNIBAR = 34;
	
	public static final int INDIGO = 35;
	
	public static final int WATER = 36;
	
	public static final int TREES = 37;
	
	public static final int MOUNT = 38;
	
	public Kanto() {
		name = "Kanto";
		areas.add(new Clear("Maps/Clear.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route01("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route02("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route03("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route04("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route05("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route06("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route07("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route08("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route09("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route10("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route11("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route12("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route13("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route14("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route15("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route16("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route17("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route18("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route19("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route20("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route21("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route22("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route23("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Route24("Maps/Route1.tmx", "resources/Kanto Region - Route 1.csv"));
		
		areas.add(new PalletTown("Maps/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")); //Pallet
		areas.add(new ViridianCity("Maps/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")); //Viridian
		areas.add(new PewterCity("Maps/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")); //Pewter
		areas.add(new CeruleanCity("Maps/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")); //Cerulean
		areas.add(new LavenderTown("Maps/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")); //Lavender
		areas.add(new CeladonCity("Maps/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")); //Celadon
		areas.add(new VermilionCity("Maps/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")); //Vermilion
		areas.add(new SaffronCity("Maps/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")); //Saffron
		areas.add(new FuchsiaCity("Maps/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")); //Fuchsia
		areas.add(new CinnabarIsland("Maps/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")); //Cinnabar
		areas.add(new IndigoPlateau("Maps/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")); //Indigo
		
		areas.add(new Clear("Maps/Clear.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Clear("Maps/Clear.tmx", "resources/Kanto Region - Route 1.csv"));
		areas.add(new Clear("Maps/Clear.tmx", "resources/Kanto Region - Route 1.csv"));
		
		areas.get(ROUTE1).setName("Route 1");
		areas.get(ROUTE2).setName("Route 2");
		areas.get(ROUTE3).setName("Route 3");
		areas.get(ROUTE4).setName("Route 4");
		areas.get(ROUTE5).setName("Route 5");
		areas.get(ROUTE6).setName("Route 6");
		areas.get(ROUTE7).setName("Route 7");
		areas.get(ROUTE8).setName("Route 8");
		areas.get(ROUTE9).setName("Route 9");
		areas.get(ROUTE10).setName("Route 10");
		areas.get(ROUTE11).setName("Route 11");
		areas.get(ROUTE12).setName("Route 12");
		areas.get(ROUTE13).setName("Route 13");
		areas.get(ROUTE14).setName("Route 14");
		areas.get(ROUTE15).setName("Route 15");
		areas.get(ROUTE16).setName("Route 16");
		areas.get(ROUTE17).setName("Route 17");
		areas.get(ROUTE18).setName("Route 18");
		areas.get(ROUTE19).setName("Route 19");
		areas.get(ROUTE20).setName("Route 20");
		areas.get(ROUTE21).setName("Route 21");
		areas.get(ROUTE22).setName("Route 22");
		areas.get(ROUTE23).setName("Route 23");
		areas.get(ROUTE24).setName("Route 24");
		
		
		areas.get(PALLET).setName("Pallet Town");
		areas.get(VIRIDIAN).setName("Viridian City");
		areas.get(PEWTER).setName("Pewter City");
		areas.get(CERULEAN).setName("Cerulean City");
		areas.get(LAVENDER).setName("Lavender Town");
		areas.get(CELADON).setName("Celadon City");
		areas.get(VERMILLION).setName("Vermillion City");
		areas.get(SAFFRON).setName("Saffron City");
		areas.get(FUCHSIA).setName("Fuchsia City");
		areas.get(CINNIBAR).setName("Cinnibar Island");
		areas.get(INDIGO).setName("Indigo Plateau");
	}
}
