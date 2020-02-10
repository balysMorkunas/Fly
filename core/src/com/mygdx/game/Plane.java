package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Plane extends BaseActor {

    public Plane(float x, float y, Stage s) {
        super(x, y, s);

        String[] fileNames = {"/home/balys/Programming/Fun Programming/Fly/core/assets/plane-frame-0.png",
                "/home/balys/Programming/Fun Programming/Fly/core/assets/plane-frame-1.png",
                "/home/balys/Programming/Fun Programming/Fly/core/assets/plane-frame-2.png"};

        loadAnimationFromFiles(fileNames, 0.1f, true);

        setBoundaryPolygon(8);

        setAcceleration(300);
        setMaxSpeed(100);
        setDeceleration(100);


    }

    public void act(float dt) {
        super.act(dt);

        accelerateForward();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            accelerateAtAngle(180);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            accelerateAtAngle(0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            accelerateAtAngle(90);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            accelerateAtAngle(270);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            shoot();
        }

        applyPhysics(dt);

        setAnimationPaused(!isMoving());

        if (getSpeed() > 0) {
            setRotation(getMotionAngle());
        }

        boundToWorld();
    }

    public void shoot() {
        if (getStage() == null) {
            return;
        }

        Projectile projectile = new Projectile(0, 0, this.getStage());
        projectile.centerAtActor(this);
        projectile.setRotation(this.getRotation());
        projectile.setMotionAngle(this.getRotation());

    }
}

