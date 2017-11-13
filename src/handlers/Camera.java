package handlers;

import entities.AbstractEntity;
import main.Main;
import region.RegionManager;

public class Camera {
	private static int x, y;
	
	private static boolean followingCharacter = false;
	
	private static AbstractEntity characterToFollow;
	
	public static void setPosition(int x, int y) {
		Camera.x = x;
		Camera.y = y;
	}
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}
	
	public static void update() {
		if (followingCharacter) {
			Camera.setPosition(characterToFollow.getX() - (Main.WIDTH / 2 - 8), characterToFollow.getY() - (Main.HEIGHT / 2 - 8));
		}
		RegionManager.getCurrentArea().setPosition(-x, -y);
	}
	
	public static boolean isEntityInBounds(AbstractEntity entity) {
		int x = entity.getX() + entity.getXMapOffset();
		int y = entity.getY() + entity.getYMapOffset();
		
		return (x >= Camera.x - 16 && x < Camera.x + (Main.WIDTH + 16) + (Main.WIDTH / 2 - 8) && y >= Camera.y - 16 && y < Camera.y + (Main.HEIGHT + 16) + (Main.HEIGHT / 2 - 8));
	}
	
	public static void followCharacter(AbstractEntity entity) {
		characterToFollow = entity;
		followingCharacter = true;
	}
}
