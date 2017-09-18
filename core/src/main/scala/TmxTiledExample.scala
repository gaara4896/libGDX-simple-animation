package my.game.pkg

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.MapLayers
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.maps.tiled.TmxMapLoader

class TmxTiledExample extends ApplicationListener{
	
	private var map:TiledMap = null
	private var renderer:TiledMapRenderer = null
	private var camera:OrthographicCamera = null
	private var cameraController:OrthoCamController = null
	private var assetManager:AssetManager = null
	private var font:BitmapFont = null
	private var batch:SpriteBatch = null

	override def create{
		val w = Gdx.graphics.getWidth
		val h = Gdx.graphics.getHeight

		camera = new OrthographicCamera
		camera.setToOrtho(false, (w/h) * 320, 320)
		camera.update

		cameraController = new OrthoCamController(camera)
		Gdx.input.setInputProcessor(cameraController)

		font = new BitmapFont
		batch = new SpriteBatch

		map = new TmxMapLoader().load("tiled.tmx")

		renderer = new OrthogonalTiledMapRenderer(map)
	}

	override def render{
		Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		camera.update
		renderer.setView(camera)
		renderer.render
		batch.begin
		font.draw(batch, s"FPS: ${Gdx.graphics.getFramesPerSecond}", 10, 20)
		batch.end
	}

	override def dispose {}

	override def pause {}

	override def resize(width:Int, height:Int) {}

	override def resume {}

}