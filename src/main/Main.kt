package main

import org.lwjgl.LWJGLUtil
import org.lwjgl.opengl.Display
import org.newdawn.slick.AppGameContainer
import org.newdawn.slick.ScalableGame
import org.newdawn.slick.SlickException

object Main {
    const val WIDTH = 240
    const val HEIGHT = 160

    var ticks: Long = 0

    private var scale = 2

    private lateinit var app: AppGameContainer

    @JvmStatic
    fun main(args: Array<String>) {
        System.setProperty("org.lwjgl.librarypath",
                System.getProperty("user.dir") + "/native/" + LWJGLUtil.getPlatformName())

        try {
            app = AppGameContainer(ScalableGame(Game("Pokemon"), WIDTH, HEIGHT, true))
            //setFullscreen();
            resizeGame(WIDTH * scale, HEIGHT * scale)
            app.setTargetFrameRate(60)
            app.setClearEachFrame(true)
            app.setMinimumLogicUpdateInterval(15) //default is 15/18
            app.setMaximumLogicUpdateInterval(18)
            app.setUpdateOnlyWhenVisible(true)
            app.alwaysRender = false
            app.setShowFPS(true)
            app.setIcons(arrayOf("res/Icons/master64.png"))
            Display.setInitialBackground(1f, 1f, 1f)
            app.start()
        } catch (e: SlickException) {
            e.printStackTrace()
        }

    }

    private fun resizeGame(width: Int, height: Int) {
        try {
            app.setDisplayMode(width, height, false)
            app.setVSync(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setFullscreen() {
        try {
            val width = app.screenWidth
            val height = app.screenHeight
            app.setDisplayMode(width, height, true)
            app.setVSync(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
