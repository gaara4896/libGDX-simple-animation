package my.game.pkg

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.BitmapFontCache
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun
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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.graphics.Colors
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.graphics.Texture.TextureFilter

class SkinExample extends ApplicationListener{

	var stage:Stage = null
	var batch:SpriteBatch = null
	var font:BitmapFont = null
	var renderer:ShapeRenderer = null
	var multiPageFont:BitmapFont = null
	var layout:GlyphLayout = null
	var label:Label = null

	override def create{

		batch = new SpriteBatch

		font = new BitmapFont(Gdx.files.internal("skin/arial-32-pad.fnt"), false)
		font.getData.markupEnabled = true
		font.getData.breakChars = Array[Char]('-')

		multiPageFont = new BitmapFont(Gdx.files.internal("skin/multipagefont.fnt"))

		//add user defined color
		Colors.put("PERU", Color.valueOf("CD853F"))

		renderer = new ShapeRenderer
		renderer.setProjectionMatrix(batch.getProjectionMatrix)

		stage = new Stage(new ScreenViewport)

		var jsonSkin:Skin = new Skin(Gdx.files.internal("skin/uiskin.json"))

		var labelFont:BitmapFont = jsonSkin.get("default-font", classOf[BitmapFont])
		labelFont.getData.markupEnabled = true

		label = new Label("<<[BLUE]M[RED]u[YELLOW]l[GREEN]t[OLIVE]ic[]o[]l[]o[]r[]*[MAROON]Label[][] [Unknown Color]>>", jsonSkin);
		label.setPosition(100, 200);
		stage.addActor(label);

		var window:Window = new Window("[RED]Multicolor[GREEN] Title", jsonSkin)
		window.setPosition(400, 300);
		window.pack();
		stage.addActor(window);

		layout = new GlyphLayout

		Gdx.input.setInputProcessor(stage)

		var skin:Skin = new Skin

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
		var viewHeight:Int = Gdx.graphics.getHeight

		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		// Test wrapping or truncation with the font directly.
		if (!true) {
			// BitmapFont font = label.getStyle().font;
			font.getRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest)

			font.getData().setScale(2f)
			renderer.begin(ShapeRenderer.ShapeType.Line)
			renderer.setColor(0, 1, 0, 1)
			var w:Float = Gdx.input.getX()
			// w = 855;
			renderer.rect(10, 10, w, 500)
			renderer.end()

			batch.begin()
			var text:String = "your new"
			text = "How quickly [RED]daft jumping zebras vex."
			// text = "Another font wrap is-sue, this time with    multiple whitespace characters.";
			text = "test with AGWlWi      AGWlWi issue"
			if (true) { // Test wrap.
				layout.setText(font, text, 0, text.length(), font.getColor(), w, Align.center, true, null)
			} else { // Test truncation.
				layout.setText(font, text, 0, text.length(), font.getColor(), w, Align.center, false, "...")
			}
			var meowy:Float = (500 / 2 + layout.height / 2 + 5)
			font.draw(batch, layout, 10, 10 + meowy)
			batch.end()

			renderer.begin(ShapeRenderer.ShapeType.Line)
			renderer.setColor(0, 1, 0, 1)
			for(i <- 0 to layout.runs.size - 1){
				var r:GlyphRun = layout.runs.get(i)
				renderer.rect(10 + r.x, 10 + meowy + r.y, r.width, -font.getLineHeight())
			}
			renderer.end()
			font.getData().setScale(1f)
			return
		}

		// Test wrapping with label.
		if (false) {
			label.debug()
			label.getStyle().font = font
			label.setStyle(label.getStyle())
			label.setText("How quickly [RED]daft[] jumping zebras vex.")
			label.setWrap(true)
		//			label.setEllipsis(true);
			label.setAlignment(Align.center, Align.right)
			label.setWidth(Gdx.input.getX() - label.getX())
			label.setHeight(label.getPrefHeight())
		} else {
			// Test various font features.
			batch.begin();

			var text:String = "Sphinx of black quartz, judge my vow."
			font.setColor(Color.RED)

			var x:Int = 100
			var y:Int = 20
			var alignmentWidth:Float = 0

			if (false) {
				alignmentWidth = 0
				font.draw(batch, text, x, viewHeight - y, alignmentWidth, Align.right, false)
			}

			if (true) {
				alignmentWidth = 280
				font.draw(batch, text, x, viewHeight - y, alignmentWidth, Align.right, true)
			}

			font.draw(batch, "[", 50, 60, 100, Align.left, true)
			font.getData().markupEnabled = true
			font.draw(batch, "[", 100, 60, 100, Align.left, true)
			font.getData().markupEnabled = false

			// 'R' and 'p' are in different pages
			var txt2:String = "this font uses " + multiPageFont.getRegions().size + " texture pages: RpRpRpRpRpNM"
			batch.renderCalls = 0

			// regular draw function
			multiPageFont.setColor(Color.BLUE)
			multiPageFont.draw(batch, txt2, 10, 100)

			// expert usage.. drawing with bitmap font cache
			var cache:BitmapFontCache = multiPageFont.getCache()
			cache.clear()
			cache.setColor(Color.BLACK)
			cache.setText(txt2, 10, 50)
			cache.setColors(Color.PINK, 3, 6)
			cache.setColors(Color.ORANGE, 9, 12)
			cache.setColors(Color.GREEN, 16, txt2.length())
			cache.draw(batch, 5, txt2.length() - 5)

			cache.clear()
			cache.setColor(Color.BLACK)
			var textX:Float = 10
			textX += cache.setText("[black] ", textX, 150).width
			multiPageFont.getData().markupEnabled = true
			textX += cache.addText("[[[PINK]pink[]] ", textX, 150).width
			textX += cache.addText("[PERU][[peru] ", textX, 150).width
			cache.setColor(Color.GREEN)
			textX += cache.addText("green ", textX, 150).width
			textX += cache.addText("[#A52A2A]br[#A52A2ADF]ow[#A52A2ABF]n f[#A52A2A9F]ad[#A52A2A7F]in[#A52A2A5F]g o[#A52A2A3F]ut ",
				textX, 150).width
			multiPageFont.getData().markupEnabled = false

			cache.draw(batch)

			// tinting
			cache.tint(new Color(1f, 1f, 1f, 0.3f))
			cache.translate(0f, 40f)
			cache.draw(batch)

			batch.end()
			// System.out.println(batch.renderCalls);

			renderer.begin(ShapeType.Line)
			renderer.setColor(Color.BLACK)
			renderer.rect(x, viewHeight - y - 200, alignmentWidth, 200)
			renderer.end()
		}

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f))
		stage.draw()
	}

	override def dispose {}

	override def pause {}

	override def resize(width:Int, height:Int) {
		batch.getProjectionMatrix.setToOrtho2D(0, 0, width, height)
		renderer.setProjectionMatrix(batch.getProjectionMatrix)
		stage.getViewport().update(width, height, true)
	}

	override def resume {}
}
