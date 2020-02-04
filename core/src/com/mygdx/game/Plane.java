package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Plane extends ActorBeta {

    public Plane() {
        super();
    }

    public void act(float dt) {
        super.act(dt);

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
         this.moveBy(-5, 0);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
         this.moveBy(5, 0);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
         this.moveBy(0, 5);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
         this.moveBy(0, -5);
        }
    }
}
