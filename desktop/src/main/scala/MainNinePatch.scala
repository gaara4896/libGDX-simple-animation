package my.game.pkg

import com.badlogic.gdx.backends.lwjgl._

object MainNinePatch extends App {
    val config = new LwjglApplicationConfiguration
    config.title = "NinePatch"
    config.height = 480
    config.width = 800
    config.forceExit = false
    new LwjglApplication(new NinePatchStyle, config)
}
