package my.game.pkg

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils

class OrthographicsCameraExample extends ApplicationListener{

	val WORLD_WIDTH:Int = 1920
	val WORLD_HEIGHT:Int = 1080

	private var cam:OrthographicCamera = null
	private var batch:SpriteBatch = null
	private var mapSprite:Sprite = null
	private val rotationSpeed:Float = 0.5f

	override def create{
		mapSprite = new Sprite(new Texture(Gdx.files.internal("background.jpe")))
		mapSprite.setPosition(0,0)
		mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT)

		val w:Float = Gdx.graphics.getWidth
		val h:Float = Gdx.graphics.getHeight

		cam = new OrthographicCamera(150, 150 * (h / w))

		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0)
		cam.update

		batch = new SpriteBatch
	}

	override def render{
		handleInput
		cam.update
		batch.setProjectionMatrix(cam.combined)

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		batch.begin
		mapSprite.draw(batch)
		batch.end
	}

	override def resize(width:Int, height:Int){
		cam.viewportWidth = 150f
		cam.viewportHeight = 150f * height / width
		cam.update
	}

	def handleInput {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			cam.zoom += 0.02f
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			cam.zoom -= 0.02f
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			cam.translate(-3, 0, 0)
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			cam.translate(3, 0, 0)
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			cam.translate(0, -3, 0)
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			cam.translate(0, 3, 0)
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			cam.rotate(-rotationSpeed, 0, 0, 1)
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			cam.rotate(rotationSpeed, 0, 0, 1)
		}

		cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 1920/cam.viewportWidth)

		var effectiveViewportWidth:Float = cam.viewportWidth * cam.zoom
		var effectiveViewportHeight:Float = cam.viewportHeight * cam.zoom

		cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 1920 - effectiveViewportWidth / 2f);
		cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 1920 - effectiveViewportHeight / 2f);
	}

	override def resume {}

	override def dispose {}

	override def pause {}

}