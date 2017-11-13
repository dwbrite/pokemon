package entities;

import entities.particles.Particle;
import handlers.Camera;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class EntityManager {
	private static HashMap<String, AbstractEntity> entities = new HashMap<String, AbstractEntity>();
	
	private static ArrayList<String> entityList = new ArrayList<String>();
	
	public static void add(String name, AbstractEntity entity) {
		entities.put(name, entity);
		entityList.add(name);
	}
	
	public static void remove(String name) {
		entities.remove(name);
		entityList.remove(name);
	}
	
	public static void clear() {
		entities.clear();
		entityList.clear();
	}
	
	public static void updateList() {
		Collections.sort(entityList, new Comparator<String>() {
			@Override
			public int compare(String p1, String p2) {
				double result = entities.get(p1).getDepth() - entities.get(p2).getDepth();
				if (result > 0) {
					return 1;
				}
				if (result < 0) {
					return -1;
				} else {
					return 0;
				}
			}
		});
	}
	
	public static void update(GameContainer gc) {
		for (int i = 0; i < entityList.size(); i++) {
			if (Camera.isEntityInBounds(entities.get(entityList.get(i)))) {
				entities.get(entityList.get(i)).setCollision();
			}
		}
		for (int i = 0; i < entityList.size(); i++) {
			if (entities.get(entityList.get(i)) instanceof Particle || Camera.isEntityInBounds(entities.get(entityList.get(i)))) {
				entities.get(entityList.get(i)).update(gc);
			}
		}
		updateList();
	}
	
	public static void render(GameContainer gc, Graphics g) {
		for (int i = 0; i < entityList.size(); i++) {
			if (Camera.isEntityInBounds(entities.get(entityList.get(i)))) {
				entities.get(entityList.get(i)).render(gc, g);
			}
		}
	}
	
	public static AbstractEntity getEntity(String name) {
		if (entities.containsKey(name)) {
			return entities.get(name);
		} else {
			return null;
		}
	}
}
