package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import javax.xml.soap.Text;

public class Plane extends Actor {

    private Sprite sprite;
    private Vector2 baseDirection;

    /**
     * Constructs a new plane with the given texture.
     *
     * @param sprite the sprite object.
     */
    protected Plane(Sprite sprite) {
        this.sprite = sprite;
        baseDirection = new Vector2(1, 0).nor();
    }

    /**
     * Sets the Sprite for plane object.
     * @param txt
     */
    public void setSprite(Sprite txt) {
        sprite = txt;
    }

    /**
     * Gets the Sprite from plane object.
     * @return the Sprite.
     */
    public Sprite getSprite() {
        return this.sprite;
    }

    public void changeDirection() {
        Vector2 direction = new Vector2();
       // Quaternion rotation =
    }


}
