package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.security.Key;
import java.security.Policy;

public class FlyMain extends GameBeta {

	private Plane plane;

	@Override
	public void initialize () {
		plane = new Plane();
		plane.setTexture(new Texture(Gdx.files.internal("plane.png")));
		plane.setPosition(20, 20);
		mainStage.addActor(plane);

	}

	@Override
	public void update (float dt) {
	    //win condition here and other stuff related to gameplay.
	}

	
	@Override
	public void dispose () {
	}
}
