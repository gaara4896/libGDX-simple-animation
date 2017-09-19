package my.game.pkg

import com.badlogic.gdx.backends.lwjgl._

object MainSkin extends App {
    val config = new LwjglApplicationConfiguration
    config.title = "Skin"
    config.height = 480
    config.width = 800
    config.forceExit = false
    new LwjglApplication(new SkinExample, config)
}
