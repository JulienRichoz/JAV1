package ch.cpnv.angrywirds.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawText {

    private final SpriteBatch batch;
    private final Color color;
    private final int x;
    private final int y;
    private final String value;
    private float size=2;
    private BitmapFont text;

    //Set text with color, font, position and size
    public DrawText(SpriteBatch batch, Color color, int x, int y, String value, float size) {
        this.batch = batch;
        this.color = color;
        this.x = x;
        this.y = y;
        this.value = value;
        this.size = size;
        text = new BitmapFont();
        text.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        text.getData().setScale(size, size);
    }

    //Set text with color, font and position
    public DrawText(SpriteBatch batch, Color color, int x, int y, String value) {
        this.batch = batch;
        this.color = color;
        this.x = x;
        this.y = y;
        this.value = value;
        text = new BitmapFont();
        text.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        text.getData().setScale(2, 2);
    }

    public void drawAny(){
        text.setColor(color);
        text.draw(batch, value, x, y);
    }
}
