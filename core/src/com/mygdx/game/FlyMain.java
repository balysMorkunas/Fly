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
import java.util.ArrayList;
import java.util.List;

public class FlyMain extends GameBeta {

	private Plane plane;
	private Enemy enemy;

	@Override
	public void initialize () {
		plane = new Plane(20, 20, mainStage);
		enemy = new Enemy(206, 206, mainStage);
		mainStage.addActor(plane);
		mainStage.addActor(enemy);

		BaseActor.setWorldBounds(512, 512);

	}

	@Override
	public void update (float dt) {
	    //win condition here and other stuff related to gameplay.
        for(BaseActor projectile : BaseActor.getList(mainStage, "com.mygdx.game.Projectile")) {
        	Projectile prj = (Projectile) projectile;

        	if(prj.overlaps(enemy)) {
        		prj.collide();
        		System.out.println("COLLIDED");
			}
		}
	}

	
	@Override
	public void dispose () {
	}

}
