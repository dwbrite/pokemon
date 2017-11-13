package entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Animation {
	private Image[] frames;
	
	private int[] frameOrder;
	
	private int currentFrame;
	
	public Animation(SpriteSheet sheet) {
		//hush bb is ok
		//this assumes none are empty sorry
		Image[] topsecret = new Image[sheet.getVerticalCount() * sheet.getHorizontalCount()];
		int tileWidth = sheet.getWidth() / sheet.getHorizontalCount();
		int tileHeight = sheet.getHeight() / sheet.getVerticalCount();
		for (int j = 0; j < sheet.getVerticalCount(); j++) {
			for (int i = 0; i < sheet.getHorizontalCount(); i++) {
				int n = j * sheet.getHorizontalCount() + i;
				int x = i * tileWidth, y = j * tileHeight;
				int ox = x + tileWidth, oy = y + tileHeight;
				topsecret[n] = sheet.getSprite(i, 0);
			}
		}
		
		this.frames = topsecret;
		this.frameOrder = new int[frames.length];
		for (int i = 0; i < frames.length; i++) {
			frameOrder[i] = i;
		}
	}
	
	public Animation(int[] frameOrder, Image... frames) {
		this.frameOrder = frameOrder;
		this.frames = frames;
	}
	
	public Animation(Image... frames) {
		this.frames = frames;
		this.frameOrder = new int[frames.length];
		for (int i = 0; i < frames.length; i++) {
			frameOrder[i] = i;
		}
	}
	
	public void nextFrame() {
		currentFrame++;
		if (currentFrame >= frameOrder.length) {
			currentFrame = 0;
		}
	}
	
	public void setFrame(int frameNum) {
		currentFrame = frameNum;
	}
	
	public Image getCurrentFrame() {
		return frames[frameOrder[currentFrame]];
	}
	
	public int getCurrentFrameNum() {
		return currentFrame;
	}
}
