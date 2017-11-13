package entities.particles;

import entities.AbstractEntity;
import entities.Animation;
import main.Main;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import region.RegionManager;

public class Particle extends AbstractEntity {
	protected Animation animation;
	
	protected long startingTick, tickLimit, endingTick, tickSwitch;
	
	protected double depthOffset;
	
	public Particle(int x, int y, Image spritesheet, double depthOffset) {
		super(x, y);
		this.setSpritesheet(spritesheet);
		
		this.depthOffset = depthOffset;
		
		Image[] images = new Image[spriteSheet.getWidth() / width];
		for (int i = 0; i < images.length; i++) {
			images[i] = this.spriteSheet.getSubImage(i * width, 0, 16, 16);
		}
		this.animation = new Animation(images);
		this.currentImage = animation.getCurrentFrame();
		this.collisionType = 0;
		
		this.startingTick = Main.ticks;
		this.tickLimit = 16;
		this.tickSwitch = tickLimit;
		this.endingTick = startingTick + tickLimit;
	}
	
	public boolean deathCondition() {
		return Main.ticks >= endingTick;
	}
	
	public void update(GameContainer gc) {
		uniqueUpdates();
		depth = (y / 16.0) + depthOffset;
		currentImage = animation.getCurrentFrame();
	}
	
	public void render(GameContainer gc, Graphics g) {
		currentImage.draw(x + RegionManager.getCurrentArea().getX(), y + RegionManager.getCurrentArea().getY());
	}
	
	@Override
	public void uniqueUpdates() {
		if ((Main.ticks - startingTick) % tickSwitch == 0 && Main.ticks - startingTick != 0) {
			animation.nextFrame();
		}
		checkDeathCondition();
	}
	
	protected void checkDeathCondition() {
		if (deathCondition() == true) {
			ParticleManager.remove(this.toString());
		}
	}
	
	public void setTickLimit(int frames) {
		tickLimit = frames;
		endingTick = startingTick + tickLimit;
	}
	
	public void setSwitchTicks(int frames) {
		tickSwitch = frames;
	}
}