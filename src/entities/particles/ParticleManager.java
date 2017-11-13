package entities.particles;

import entities.EntityManager;
import region.RegionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParticleManager {
	private static Map<String, Particle> particles = new HashMap<String, Particle>();
	
	private static ArrayList<String> particleList = new ArrayList<String>();
	
	public static void add(String name, Particle entity) {
		particles.put(name, entity);
		particleList.add(name);
		
		EntityManager.add(name, entity);
	}
	
	public static void remove(String name) {
		particles.remove(name);
		particleList.remove(name);
		
		EntityManager.remove(name);
	}
	
	public static void clear() {
		particles.clear();
		particleList.clear();
	}
	
	public static void areaSwitch(int direction) {
		final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
		int xOffset = 0, yOffset = 0;
		
		switch (direction) {
			case UP:
				yOffset = (RegionManager.getCurrentArea().getHeight()) * 16;
				break;
			case DOWN:
				yOffset = -RegionManager.getNorthArea().getHeight() * 16;
				break;
			case LEFT:
				xOffset = RegionManager.getWestArea().getHeight() * 16;
				break;
			case RIGHT:
				xOffset = -RegionManager.getCurrentArea().getHeight() * 16;
				break;
		}
		
		for (int i = 0; i < particleList.size(); i++) {
			Particle temp = particles.get(particleList.get(i));
			temp.setPosition(temp.getX() + xOffset, temp.getY() + yOffset);
			//System.out.println(temp.getDepth());
			//System.out.println(EntityManager.getEntity("player").getDepth());
			//System.out.println();
			
			EntityManager.add(particleList.get(i), particles.get(particleList.get(i)));
		}
	}
}
