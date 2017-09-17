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

class TiledMapExample extends ApplicationListener{

	private var map:TiledMap = null
	private var renderer:TiledMapRenderer = null
	private var camera:OrthographicCamera = null
	private var cameraController:OrthoCamController = null
	private var assetManager:AssetManager = null
	private var tiles:Texture = null
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

		tiles = new Texture(Gdx.files.internal("tiles.png"))
		var splitTiles:Array[Array[TextureRegion]] = TextureRegion.split(tiles, 32, 32)
		map = new TiledMap
		var layers:MapLayers = map.getLayers
		for(l <- 0 to 19){
			var layer:TiledMapTileLayer = new TiledMapTileLayer(150, 100, 32, 32)
			for(x <- 0 to 149){
				for(y <- 0 to 99){
					val ty = (Math.random * splitTiles.length).toInt
					val tx = (Math.random * splitTiles(ty).length).toInt
					val cell:Cell = new Cell
					cell.setTile(new StaticTiledMapTile(splitTiles(ty)(tx)))
					layer.setCell(x, y, cell)
				}
			}
			layers.add(layer)
		}

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