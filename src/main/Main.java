package main;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;

import java.io.File;

public class Main {
	public static final int WIDTH = 240;
	
	public static final int HEIGHT = 160;
	
	public static long ticks;
	
	public static int scale = 2;
	
	public static AppGameContainer app;
	
	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "dep/native"), LWJGLUtil.getPlatformName()).getAbsolutePath());
		
		try {
			app = new AppGameContainer(new ScalableGame(new Game("Pokemon"), WIDTH, HEIGHT, true));
			//setFullscreen();
			resizeGame(WIDTH * scale, HEIGHT * scale);
			app.setTargetFrameRate(60);
			app.setClearEachFrame(true);
			app.setMinimumLogicUpdateInterval(15); //default is 15/18
			app.setMaximumLogicUpdateInterval(18);
			app.setUpdateOnlyWhenVisible(true);
			app.setAlwaysRender(false);
			app.setShowFPS(true);
			app.setIcons(new String[]{"Icons/master64.png"});
			Display.setInitialBackground(1, 1, 1);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void resizeGame(int width, int height) {
		try {
			app.setDisplayMode(width, height, false);
			app.setVSync(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setFullscreen() {
		try {
			int width = app.getScreenWidth();
			int height = app.getScreenHeight();
			app.setDisplayMode(width, height, true);
			app.setVSync(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
