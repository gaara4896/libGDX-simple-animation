package my.game.pkg

import com.badlogic.gdx.backends.lwjgl._

object MainTiledMap extends App {
    val config = new LwjglApplicationConfiguration
    config.title = "TiledMap"
    config.height = 480
    config.width = 800
    config.forceExit = false
    new LwjglApplication(new TiledMapExample, config)
}
