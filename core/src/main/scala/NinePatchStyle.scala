package my.game.pkg

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport

class NinePatchStyle extends ApplicationListener{

	var patch:NinePatch = null
	var patchDrawable:NinePatchDrawable = null
	var style:TextButtonStyle = null
	var button:TextButton = null
	var button2:TextButton = null
	var spriteBatch:SpriteBatch = null
	var stage:Stage = null
	var font:BitmapFont = null

	override def create{
		patch = new NinePatch(new Texture(Gdx.files.internal("ninepatches1.png")), 12, 12, 12, 12)
		patchDrawable = new NinePatchDrawable(patch)

		style = new TextButtonStyle(patchDrawable, patchDrawable, patchDrawable, new BitmapFont()){
			fontColor = new Color(0.3f, 0.2f, 0.8f, 1f)
		}

		button = new TextButton("Hello World", style)
		button.setPosition(200, 100)

		button2 = new TextButton("This is a longer one", style)
		button2.setPosition(200, 200)

		spriteBatch = new SpriteBatch
		stage = new Stage(new ScreenViewport)
		stage.addActor(button)
		stage.addActor(button2)

		font = new BitmapFont
	}

	override def render{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		stage.act(Gdx.graphics.getDeltaTime)
		stage.draw
		spriteBatch.begin
		patch.draw(spriteBatch, 50, 0, patch.getTotalWidth, patch.getTotalHeight)
		patchDrawable.draw(spriteBatch, 50, 200, 100, 100)
		font.draw(spriteBatch, 
			"Draw from spriteBatch will follow window size, stage will follow true size, resize will affect spriteBatch",
			0, 400 )
		font.draw(spriteBatch, "Resize window to get more understanding", 0, 450)
		spriteBatch.end
	}

	override def resize(width:Int, height:Int) {
		stage.getViewport().update(width, height, true);
	}

	override def pause {}

	override def resume {}

	override def dispose {}
}