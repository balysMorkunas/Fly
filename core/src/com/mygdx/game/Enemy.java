package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Enemy extends BaseActor {

    public Enemy(float x, float y, Stage s) {
        super(x, y, s);

        String[] fileNames = {"/home/balys/Programming/Fun Programming/Fly/core/assets/plane-frame-0.png"};

        loadAnimationFromFiles(fileNames, 0.1f, true);

        setBoundaryPolygon(8);
    }

}
