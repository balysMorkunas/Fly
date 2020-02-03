package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.security.Key;
import java.security.Policy;

public class FlyMain extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private KeyboardController controller;
	private Plane plane;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		controller = new KeyboardController();


		Gdx.input.setInputProcessor(controller);
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("plane.png"));
		sprite = new Sprite(texture);
		sprite.setPosition(w/2 - sprite.getWidth(), h/2 - sprite.getHeight());
		sprite.setOriginCenter();

		plane = new Plane(sprite);

	}

	@Override
	public void render () {

		plane.getSprite().setPosition(plane.getSprite().getX() + 1.0f, plane.getSprite().getY());
		if(controller.up) {
		    plane.setRotation(sprite.getRotation() + 1f);
		}
		if(controller.down) {
		    plane.ge.rotateBy(-1.0f);
		    System.out.println("down");
		}

 		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		plane.getSprite().draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		texture.dispose();
	}
}
