package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Projectile extends BaseActor{

    private boolean collided;

    public Projectile(float x, float y, Stage s) {
        super(x, y, s);

        String[] fileNames = {
                "/home/balys/Programming/Fun Programming/Fly/core/assets/projectile-frame-0.png",
                "/home/balys/Programming/Fun Programming/Fly/core/assets/projectile-frame-1.png",
                "/home/balys/Programming/Fun Programming/Fly/core/assets/projectile-frame-2.png",
                "/home/balys/Programming/Fun Programming/Fly/core/assets/projectile-frame-3.png",
                "/home/balys/Programming/Fun Programming/Fly/core/assets/projectile-frame-4.png"};

        loadAnimationFromFiles(fileNames, 0.1f, true);

        setBoundaryPolygon(8);

        collided = false;

        addAction(Actions.delay(2));
        addAction(Actions.after(Actions.fadeOut(0.5f)));
        addAction(Actions.after(Actions.removeActor()));

        setSpeed(300);
        setAcceleration(300);
        setDeceleration(0);

        setZIndex(0);
    }

    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);

        boundToWorld();
    }

    public boolean didCollide() {
        return collided;
    }

    public void collide() {
        collided = true;
        clearActions();
        addAction(Actions.removeActor());
    }
}
