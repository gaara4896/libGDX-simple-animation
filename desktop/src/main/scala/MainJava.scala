package my.game.pkg

import com.badlogic.gdx.backends.lwjgl._

object MainJava extends App {
    val config = new LwjglApplicationConfiguration
    config.title = "Animation"
    config.height = 480
    config.width = 800
    config.forceExit = false
    new LwjglApplication(new Animations, config)
}
