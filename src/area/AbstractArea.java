package area;

import entities.AbstractEntity;
import entities.EntityManager;
import entities.objectEntities.GameCharacter;
import entities.objectEntities.Player;
import entities.particles.ParticleManager;
import handlers.Misc;
import main.Main;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;
import region.RegionManager;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractArea {
	public static final int COLLIDE_NONE = 0;
	
	public static final int COLLIDE_NORMAL = 1;
	
	public static final int COLLIDE_GRASS = 2;
	
	public static final int COLLIDE_DARK_GRASS = 3;
	
	public static final int COLLIDE_LEFT_CLIFF = 4;
	
	public static final int COLLIDE_RIGHT_CLIFF = 5;
	
	public static final int COLLIDE_DOWN_CLIFF = 6;
	
	public static final int COLLIDE_SIGN = 7;
	
	public static final int COLLIDE_PLAYER = 8;
	
	public static final int COLLIDE_NPC = 9;
	
	protected TiledMap area;
	
	protected String name = "";
	
	protected int northAreaValue, southAreaValue, westAreaValue, eastAreaValue;
	
	protected int northOffsetX, southOffsetX, eastOffsetY, westOffsetY;
	
	protected AbstractArea northArea, southArea, westArea, eastArea;
	
	protected int x, y;
	
	protected int width, height;
	
	protected int[][] collisionMap;
	
	protected List<CSVRecord> CSV_WILDLIFE;
	
	protected Map<String, AbstractEntity> entities = new HashMap<String, AbstractEntity>();
	
	protected ArrayList<String> entityList = new ArrayList<String>();
	
	public AbstractArea(String path, String wildlifecsv) {
		try {
			area = new TiledMap(path);
			
			File csvData = new File(wildlifecsv);
			CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.EXCEL);
			CSV_WILDLIFE = parser.getRecords();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		width = area.getWidth();
		height = area.getHeight();
	}
	
	public void render(GameContainer gc, Graphics g) {
		northArea.updateRenderedAnimations();
		westArea.updateRenderedAnimations();
		updateRenderedAnimations();
		eastArea.updateRenderedAnimations();
		southArea.updateRenderedAnimations();
		
		northArea.renderFloor(x, y - (northArea.getHeight() * 16));
		renderFloor(x, y);
		southArea.renderFloor(x, y + (getHeight() * 16));
		
		EntityManager.render(gc, g);
		
		northArea.renderSky(x, y - (northArea.getHeight() * 16));
		renderSky(x, y);
		southArea.renderSky(x, y + (getHeight() * 16));
	}
	
	public void renderFloor(int x, int y) {
		area.render(x, y, area.getLayerIndex("Floor"));
		area.render(x, y, area.getLayerIndex("Interactive"));
	}
	
	public void renderSky(int x, int y) {
		area.render(x, y, area.getLayerIndex("Sky"));
	}
	
	public int getCollisionValue(int x, int y) {
		return collisionMap[x + westArea.getWidth()][y + northArea.getHeight()];
	}
	
	public void update(GameContainer gc) {
		updateAnimationFrames();
		updateCollisionMap();
		EntityManager.update(gc);
		checkAreaSwitch();
	}
	
	protected void initCollisionMap() {
		collisionMap = new int[width + westArea.getWidth() + eastArea.getWidth()][height + southArea.getHeight() + northArea.getHeight()];
		
		for (int z = 0; z < 5; z++) {
			AbstractArea tempArea = (z == 0 ? northArea : (z == 1 ? westArea : (z == 2 ? this : (z == 3 ? eastArea : southArea))));
			int xOffset = (z == 0 ? westArea.width : (z == 1 ? 0 : (z == 2 ? westArea.width : (z == 3 ? westArea.width + width : westArea.width))));
			int yOffset = (z == 0 ? 0 : (z == 1 ? northArea.height : (z == 2 ? northArea.height : (z == 3 ? northArea.height : northArea.height + height))));
			
			for (int i = 0; i < tempArea.height; i++) {
				for (int j = 0; j < tempArea.width; j++) {
					switch (tempArea.getTileId(j, i, tempArea.getLayerIndex("Interactive"))) {
						// No collision
						case 0:
							collisionMap[j + xOffset][i + yOffset] = COLLIDE_NONE;
							break;
						//Cliff left
						case 7:
						case 11: //4
							collisionMap[j + xOffset][i + yOffset] = COLLIDE_LEFT_CLIFF;
							break;
						//Cliff right
						case 10:
						case 12: //5
							collisionMap[j + xOffset][i + yOffset] = COLLIDE_RIGHT_CLIFF;
							break;
						//Cliff down // 6
						case 27:
						case 28:
						case 29:
						case 30:
							collisionMap[j + xOffset][i + yOffset] = COLLIDE_DOWN_CLIFF;
							break;
						default:
							collisionMap[j + xOffset][i + yOffset] = COLLIDE_NORMAL;
							break;
					}
					switch (tempArea.getTileId(j, i, tempArea.getLayerIndex("Floor"))) {
						//Grass
						case 3:
							//this.addEntity("Grass_"+j+"."+i+this, new Grass(j * 16, i * 16, "resources/TileSets/Sprites/grassRustle"));
							collisionMap[j + xOffset][i + yOffset] = 2;
							break;
						//Dark grass
						case 6:
							//this.addEntity("Grass_"+j+"."+i+this, new Grass(j * 16, i * 16, "resources/TileSets/Sprites/grassRustle"));
							break;
					}
				}
			}
		}
	}
	
	private void updateCollisionMap() {
		collisionMap = new int[width + westArea.getWidth() + eastArea.getWidth()][height + southArea.getHeight() + northArea.getHeight()];
		
		for (int z = 0; z < 5; z++) {
			AbstractArea tempArea = (z == 0 ? northArea : (z == 1 ? westArea : (z == 2 ? this : (z == 3 ? eastArea : southArea))));
			int xOffset = (z == 0 ? westArea.width : (z == 1 ? 0 : (z == 2 ? westArea.width : (z == 3 ? westArea.width + width : westArea.width))));
			int yOffset = (z == 0 ? 0 : (z == 1 ? northArea.height : (z == 2 ? northArea.height : (z == 3 ? northArea.height : northArea.height + height))));
			
			for (int i = 0; i < tempArea.height; i++) {
				for (int j = 0; j < tempArea.width; j++) {
					switch (tempArea.getTileId(j, i, tempArea.getLayerIndex("Interactive"))) {
						// No collision
						case 0:
							collisionMap[j + xOffset][i + yOffset] = COLLIDE_NONE;
							break;
						//Cliff left
						case 7:
						case 11: //4
							collisionMap[j + xOffset][i + yOffset] = COLLIDE_LEFT_CLIFF;
							break;
						//Cliff right
						case 10:
						case 12: //5
							collisionMap[j + xOffset][i + yOffset] = COLLIDE_RIGHT_CLIFF;
							break;
						//Cliff down // 6
						case 27:
						case 28:
						case 29:
						case 30:
							collisionMap[j + xOffset][i + yOffset] = COLLIDE_DOWN_CLIFF;
							break;
						default:
							collisionMap[j + xOffset][i + yOffset] = COLLIDE_NORMAL;
							break;
					}
					switch (tempArea.getTileId(j, i, tempArea.getLayerIndex("Floor"))) {
						//Grass
						case 3:
							//this.addEntity("Grass_"+j+"."+i+this, new Grass(j * 16, i * 16, "resources/TileSets/Sprites/grassRustle"));
							collisionMap[j + xOffset][i + yOffset] = 2;
							break;
						//Dark grass
						case 6:
							//this.addEntity("Grass_"+j+"."+i+this, new Grass(j * 16, i * 16, "resources/TileSets/Sprites/grassRustle"));
							break;
					}
				}
			}
		}
	}
	
	protected void updateAnimationFrames() {
		if (Main.ticks % 16 == 1) {
			Misc.INSTANCE.setFlowerFrame(Misc.INSTANCE.getFlowerFrame() + 1);
			if (Misc.INSTANCE.getFlowerFrame() > 4) {
				Misc.INSTANCE.setFlowerFrame(0);
			}
		}
		if (Main.ticks % 16 == 0) {
			Misc.INSTANCE.setWaterFrame(Misc.INSTANCE.getWaterFrame() + 1);
			if (Misc.INSTANCE.getWaterFrame() > 7) {
				Misc.INSTANCE.setWaterFrame(0);
			}
		}
	}
	
	public void updateRenderedAnimations() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int floorTile = (area.getTileId(j, i, area.getLayerIndex("Floor")));
				int interactiveTile = (area.getTileId(j, i, area.getLayerIndex("Interactive")));
				int skyTile = (area.getTileId(j, i, area.getLayerIndex("Sky")));
				
				if (floorTile >= 52 && floorTile <= 57) {
					floorTile = 52;
					area.setTileId(j, i, area.getLayerIndex("Floor"), floorTile + Misc.INSTANCE.getFlowerFrame());
				}
				if (floorTile >= 44 && floorTile <= 51) {
					floorTile = 44;
					area.setTileId(j, i, area.getLayerIndex("Floor"), floorTile + Misc.INSTANCE.getWaterFrame());
				}
				if (interactiveTile >= 64 && interactiveTile <= 79) {
					interactiveTile = (interactiveTile % 2 == 0 ? 64 : 65);
					area.setTileId(j, i, area.getLayerIndex("Interactive"), interactiveTile + 2 * Misc.INSTANCE.getWaterFrame());
				}
				if (interactiveTile >= 84 && interactiveTile <= 99) {
					interactiveTile = (interactiveTile % 2 == 0 ? 84 : 85);
					area.setTileId(j, i, area.getLayerIndex("Interactive"), interactiveTile + 2 * Misc.INSTANCE.getWaterFrame());
				}
			}
		}
	}
	
	private void checkAreaSwitch() {
		Player player = (Player) EntityManager.getEntity("player");
		
		if (player.getY() < 0 && player.getDirection() == GameCharacter.Direction.UP) {
			int yDifferential = player.getY();
			resetEntityRenderPositions();
			EntityManager.clear();
			RegionManager.getCurrentRegion().setCurrentArea(northAreaValue);
			player.setPosition(player.getX() + northOffsetX, yDifferential + (RegionManager.getCurrentArea().getHeight()) * 16);
			ParticleManager.areaSwitch(player.getDirection().ordinal());
			EntityManager.add("player", player);
		} else if (player.getY() > (RegionManager.getCurrentArea().getHeight() - 1) * 16 && player.getDirection() == GameCharacter.Direction.DOWN) {
			int yDifferential = player.getY() - (RegionManager.getCurrentArea().getHeight()) * 16;
			EntityManager.clear();
			RegionManager.getCurrentRegion().setCurrentArea(southAreaValue);
			player.setPosition(player.getX() + southOffsetX, yDifferential);
			ParticleManager.areaSwitch(player.getDirection().ordinal());
			EntityManager.add("player", player);
		} else if (player.getX() < 0 && player.getDirection() == GameCharacter.Direction.LEFT) {
			EntityManager.clear();
			RegionManager.getCurrentRegion().setCurrentArea(westAreaValue);
			//TODO: Math the xDifferential (and in next else) //Future note: This might be okay
			player.setPosition(5, player.getY() + westOffsetY);
			ParticleManager.areaSwitch(player.getDirection().ordinal());
			EntityManager.add("player", player);
		} else if (player.getX() > ((RegionManager.getCurrentArea().getWidth()) - 1) * 16 && player.getDirection() == GameCharacter.Direction.RIGHT) {
			EntityManager.clear();
			RegionManager.getCurrentRegion().setCurrentArea(eastAreaValue);
			player.setPosition(5, player.getY() + eastOffsetY);
			ParticleManager.areaSwitch(player.getDirection().ordinal());
			EntityManager.add("player", player);
		}
	}
	
	private void resetEntityRenderPositions() {
		for (int i = 0; i < entityList.size(); i++) {
			String name = entityList.get(i);
			AbstractEntity entity = entities.get(name);
			entity.setMapOffset(0, 0);
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getTileId(int x, int y, int layer) {
		return area.getTileId(x, y, layer);
	}
	
	public int getLayerIndex(String str) {
		return area.getLayerIndex(str);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Map<String, AbstractEntity> getEntities() {
		return entities;
	}
	
	public ArrayList<String> getEntityList() {
		return entityList;
	}
	
	public void addEntity(String name, AbstractEntity entity) {
		entities.put(name, entity);
		entityList.add(name);
	}
	
	public void removeEntity(String name) {
		entities.remove(name);
		entityList.remove(name);
	}
	
	public void setActiveEntities() {
		for (int i = 0; i < entityList.size(); i++) {
			String name = entityList.get(i);
			AbstractEntity entity = entities.get(name);
			entity.setMapOffset(0, 0);
			EntityManager.add(name, entity);
		}
		
		for (int i = 0; i < northArea.entityList.size(); i++) {
			String name = northArea.entityList.get(i);
			AbstractEntity entity = northArea.entities.get(name);
			entity.setMapOffset(northOffsetX, -(northArea.getHeight() * 16));
			EntityManager.add(name, entity);
		}
		
		for (int i = 0; i < southArea.entityList.size(); i++) {
			String name = southArea.entityList.get(i);
			AbstractEntity entity = southArea.entities.get(name);
			entity.setMapOffset(southOffsetX, getHeight() * 16);
			EntityManager.add(name, entity);
		}
	}
	
	public void setCollisionValue(int x, int y, int type) {
		collisionMap[x + westArea.getWidth()][y + northArea.getHeight()] = type;
	}
	
	public abstract void init(GameContainer gc);
	
	public AbstractArea getNorthArea() {
		return northArea;
	}
	
	public AbstractArea getSouthArea() {
		return southArea;
	}
	
	public AbstractArea getWestArea() {
		return westArea;
	}
	
	public AbstractArea getEastArea() {
		return eastArea;
	}
	
	public List<CSVRecord> getWildlifeData() {
		return CSV_WILDLIFE;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
