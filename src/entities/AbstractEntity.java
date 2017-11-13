package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import region.RegionManager;

public abstract class AbstractEntity {
	protected int x, y, width, height;
	
	protected double depth;
	
	protected int xMapOffset, yMapOffset;
	
	protected int collisionType = 1;
	
	protected Image spriteSheet;
	
	protected Image currentImage;
	
	public AbstractEntity(int x, int y) {
		this.x = x;
		this.y = y;
		
		width = 16;
		height = 16;
	}
	
	protected abstract void uniqueUpdates();
	
	public abstract void update(GameContainer gc);
	
	public abstract void render(GameContainer gc, Graphics g);
	
	public double getDepth() {
		return depth;
	}
	
	public void setDepth(double d) {
		this.depth = d;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setCollision() {
		if (collisionType != 0) {
			int x1 = (x + xMapOffset) / 16;
			int y1 = (y + yMapOffset) / 16;
			
			int x2 = (x + xMapOffset + (width - 1)) / 16;
			int y2 = (y + yMapOffset + (height - 1)) / 16;
			
			RegionManager.getCurrentArea().setCollisionValue(x1, y1, collisionType);
			RegionManager.getCurrentArea().setCollisionValue(x1, y2, collisionType);
			RegionManager.getCurrentArea().setCollisionValue(x2, y1, collisionType);
			RegionManager.getCurrentArea().setCollisionValue(x2, y2, collisionType);
		}
	}
	
	public void setMapOffset(int xOffset, int yOffset) {
		xMapOffset = xOffset;
		yMapOffset = yOffset;
	}
	
	public void setSpritesheet(Image spriteSheet) {
		this.spriteSheet = spriteSheet;
	}
	
	public int getXMapOffset() {
		return xMapOffset;
	}
	
	public int getYMapOffset() {
		return yMapOffset;
	}
	
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
