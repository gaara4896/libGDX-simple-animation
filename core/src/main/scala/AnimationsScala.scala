package my.game.pkg 

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont

class AnimationsScala extends ApplicationListener{

	private val FRAME_COLS:Int = 6 
	private val FRAME_ROWS:Int = 5

	var walkAnimation:Animation[TextureRegion] = null
	var walkSheet:Texture = null
	var spriteBatch:SpriteBatch = null
	var font:BitmapFont = null
	var walkingX:Float = -50

	var stateTime:Float = 0

	override def create{

		walkSheet = new Texture(Gdx.files.internal("sprite-animation.png"))

		font = new BitmapFont

		var tmp:scala.Array[scala.Array[TextureRegion]] = TextureRegion.split(walkSheet, 
			walkSheet.getWidth / FRAME_COLS,
			walkSheet.getHeight / FRAME_ROWS)

		var walkFrames:com.badlogic.gdx.utils.Array[TextureRegion] = new com.badlogic.gdx.utils.Array[TextureRegion]()
		for( i <- 0 to FRAME_ROWS - 1){
			for( j <- 0 to FRAME_COLS - 1){
				walkFrames.add(tmp(i)(j))
			}
		}

		walkAnimation = new Animation[TextureRegion](0.050f, walkFrames)

		spriteBatch = new SpriteBatch
	}

	override def render{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		stateTime += Gdx.graphics.getDeltaTime
		walkingX += 200 * Gdx.graphics.getDeltaTime
		if(walkingX > 800) {walkingX = -50}

		val currentFrame:TextureRegion = walkAnimation.getKeyFrame(stateTime, true)
		spriteBatch.begin
		spriteBatch.draw(currentFrame, walkingX, 50)
		font.draw(spriteBatch, s"FPS : ${Gdx.graphics.getFramesPerSecond}", 740, 480)
		spriteBatch.end
	}

	override def dispose{
		spriteBatch.dispose
		walkSheet.dispose
		font.dispose
	}

	override def resume {}

	override def resize(width:Int, height:Int) {}

	override def pause {}

}