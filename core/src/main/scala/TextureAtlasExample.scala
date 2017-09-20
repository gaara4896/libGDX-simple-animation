package my.game.pkg

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType

class TextureAtlasExample extends ApplicationListener{
    var batch:SpriteBatch = null
    var star:Sprite = null
    var badlogic:Sprite = null
    var badlogicSmall:Sprite = null
    var atlas:TextureAtlas = null
    var jumpAtlas:TextureAtlas = null
    var jumpAnimation:Animation[TextureRegion] = null
    var font:BitmapFont = null
    var time:Float = 0
    var renderer:ShapeRenderer = null

    override def create{
        batch = new SpriteBatch
        renderer = new ShapeRenderer

        atlas = new TextureAtlas(Gdx.files.internal("atlas/pack"))
        jumpAtlas = new TextureAtlas(Gdx.files.internal("atlas/jump.txt"))

        jumpAnimation = new Animation[TextureRegion](0.25f, jumpAtlas.findRegions("ALIEN_JUMP_"))

        badlogic = atlas.createSprite("badlogicslice")
        badlogic.setPosition(50, 50)

        badlogicSmall = atlas.createSprite("badlogicsmall-rotated")
        badlogicSmall.setPosition(10, 10)

        var region:AtlasRegion = atlas.findRegion("badlogicsmall")
        println(s"badlogic original size: ${region.originalWidth}, ${region.originalHeight}")
        println(s"badlogicSmall packed size: ${region.packedWidth}, ${region.packedHeight}")

        star = atlas.createSprite("particle-star")
        star.setPosition(10, 70)

        font = new BitmapFont(Gdx.files.internal("skin/font.fnt"), atlas.findRegion("font"), false)

        Gdx.gl.glClearColor(0, 1, 0, 1)

        Gdx.input.setInputProcessor(new InputAdapter{
            override def keyUp(keycode:Int):Boolean = {
                if(keycode == Keys.UP){
                    badlogicSmall.flip(false, true)
                } else if (keycode == Keys.RIGHT) {
					badlogicSmall.flip(true, false)
				} else if (keycode == Keys.LEFT) {
					badlogicSmall.setSize(512, 512)
				} else if (keycode == Keys.DOWN) {
					badlogicSmall.rotate90(true)
				}
                return super.keyUp(keycode);
            }
        })
    }

    override def render{
        time += Gdx.graphics.getDeltaTime
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.begin(ShapeType.Line)
        renderer.rect(10, 10, 256, 256)
        renderer.end

        batch.begin
        badlogicSmall.draw(batch)
        batch.end
    }

    override def resize(width:Int, height:Int) {}

    override def pause {}

    override def resume {}

    override def dispose {}
}
