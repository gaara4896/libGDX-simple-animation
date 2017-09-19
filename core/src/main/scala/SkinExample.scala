package my.game.pkg

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent

class SkinExample extends ApplicationListener{

	var skin:Skin = null
	var stage:Stage = null
	var batch:SpriteBatch = null

	override def create{

		batch = new SpriteBatch
		stage = new Stage
		Gdx.input.setInputProcessor(stage)

		skin = new Skin

		val pixmap:Pixmap = new Pixmap(1, 1, Format.RGBA8888)
		pixmap.setColor(Color.WHITE)
		pixmap.fill()
		skin.add("white", new Texture(pixmap))

		skin.add("default", new BitmapFont)

		var textButtonStyle:TextButtonStyle = new TextButtonStyle
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY)
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY)
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE)
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY)
		textButtonStyle.font = skin.getFont("default")
		skin.add("default", textButtonStyle)

		val table:Table = new Table
		table.setFillParent(true)
		stage.addActor(table)

		val button:TextButton = new TextButton("Click Me!", skin)
		table.add(button)

		button.addListener(new ChangeListener{
			def changed(event:ChangeEvent, actor:Actor){
				println(s"Clicked! Is checked: ${button.isChecked}")
				if(button.isChecked) {button.setText("Good Job!")}
				else {button.setText("Click Me!")}
			}
		})

		table.add(new Image(skin.newDrawable("white", Color.RED))).size(64)
	}

	override def render{
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f))
		stage.draw()
	}

	override def dispose {}

	override def pause {}

	override def resize(width:Int, height:Int) {
		stage.getViewport().update(width, height, true)
	}

	override def resume {}
}