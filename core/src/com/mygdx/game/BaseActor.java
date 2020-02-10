package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.compression.lzma.Base;

import java.util.ArrayList;


public class BaseActor extends Actor {

    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;
    private Vector2 velocityVec;
    private Vector2 accelerationVec;
    private float acceleration;
    private float maxSpeed;
    private float deceleration;
    private static Rectangle worldBounds;
    private Polygon boundaryPolygon;


    public BaseActor(float x, float y, Stage s) {
        super();
        setPosition(x, y);
        s.addActor(this);

        animation = null;
        elapsedTime = 0;
        animationPaused = false;

        velocityVec = new Vector2(0, 0);
        accelerationVec = new Vector2(0, 0);
        acceleration = 0;
        maxSpeed = 1000;
        deceleration = 0;
    }

    public void setBoundaryRectangle() {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0, 0, 0, h, w, h, 0, h};
        boundaryPolygon = new Polygon(vertices);
    }

    public void setAnimation(Animation<TextureRegion> anim) {
        animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(h, w);
        setOrigin(w/2, h/2);

        if(boundaryPolygon == null) {
            setBoundaryRectangle();
        }
    }

    public static ArrayList<BaseActor> getList(Stage stage, String className) {

        ArrayList<BaseActor> list = new ArrayList<>();

        Class theClass = null;
        try {
            theClass = Class.forName(className);
        } catch (Exception e){
            e.printStackTrace();
        }


        for(Actor a : stage.getActors()) {
            if(theClass.isInstance(a)) {
                list.add((BaseActor) a );
            }
        }

        return list;

    }

    public void setAnimationPaused(boolean pause) {
        animationPaused = pause;
    }

    /**
     *  Replace default (rectangle) collision polygon with an n-sided polygon. <br>
     *  Vertices of polygon lie on the ellipse contained within bounding rectangle.
     *  Note: one vertex will be located at point (0,width);
     *  a 4-sided polygon will appear in the orientation of a diamond.
     *  @param numSides number of sides of the collision polygon
     */
    public void setBoundaryPolygon(int numSides)
    {
        float w = getWidth();
        float h = getHeight();

        float[] vertices = new float[2*numSides];
        for (int i = 0; i < numSides; i++)
        {
            float angle = i * 6.28f / numSides;
            // x-coordinate
            vertices[2*i] = w/2 * MathUtils.cos(angle) + w/2;
            // y-coordinate
            vertices[2*i+1] = h/2 * MathUtils.sin(angle) + h/2;
        }
        boundaryPolygon = new Polygon(vertices);

    }

    /**
     *  Returns bounding polygon for this BaseActor, adjusted by Actor's current position and rotation.
     *  @return bounding polygon for this BaseActor
     */
    public Polygon getBoundaryPolygon()
    {
        boundaryPolygon.setPosition( getX(), getY() );
        boundaryPolygon.setOrigin( getOriginX(), getOriginY() );
        boundaryPolygon.setRotation( getRotation() );
        boundaryPolygon.setScale( getScaleX(), getScaleY() );
        return boundaryPolygon;
    }

    public boolean overlaps(BaseActor other) {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())) {
            return false;
        }

        return Intersector.overlapConvexPolygons(poly1, poly2);
    }

    public void act(float dt) {
        super.act(dt);
        if(!animationPaused) {
            elapsedTime += dt;
        }
    }

    public void draw(Batch batch, float parentAlpha){
         super.draw(batch, parentAlpha);

        Color c = getColor();

        batch.setColor(c.r, c.g, c.b, c.a);

        if(animation != null && isVisible()) {
            batch.draw(animation.getKeyFrame(elapsedTime), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop) {
        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for(int i = 0; i < fileCount; i++) {
            String fileName = fileNames[i];
            Texture texture = new Texture(Gdx.files.internal(fileName));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if(loop) {
            anim.setPlayMode(Animation.PlayMode.LOOP);
        } else {
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }

        if(animation == null) {
            setAnimation(anim);
        }

        return anim;
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }

    public Animation<TextureRegion> loadTexture(String fileName) {
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }


    public void setSpeed(float speed) {
        if(velocityVec.len() == 0) {
            velocityVec.set(speed, 0);
        } else {
            velocityVec.setLength(speed);
        }

    }

    public float getSpeed() {
        return velocityVec.len();
    }

    public void setMotionAngle(float angle) {
       velocityVec.setAngle(angle);
    }

    public float getMotionAngle() {
        return velocityVec.angle();
    }

    public boolean isMoving() {
        return getSpeed() > 0;
    }

    public void setAcceleration(float acc) {
        acceleration = acc;
    }

    public void accelerateAtAngle(float angle) {
        accelerationVec.add(new Vector2(acceleration, 0).setAngle(angle));
    }
    public void accelerateForward() {
        accelerateAtAngle(getRotation());
    }

    public void setMaxSpeed(float ms) {
        maxSpeed = ms;
    }

    public void setDeceleration(float dec) {
        deceleration = dec;
    }

    public void applyPhysics(float dt) {
       //apply acceleration
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);

        float speed = getSpeed();

        //decelerate when not accelerating
        if(accelerationVec.len() == 0) {
            speed -= deceleration * dt;
        }

        //keep speed within proper bounds
        speed = MathUtils.clamp(speed, 0, maxSpeed);

        //update velocity
        setSpeed(speed);

        //apply velocity
        moveBy(velocityVec.x * dt, velocityVec.y * dt);

        //reset acceleration
        accelerationVec.set(0, 0);

    }

    public void centerAtPosition(float x, float y)
    {
        setPosition( x - getWidth()/2, y - getHeight()/2 );
    }

    /**
     *  Repositions this BaseActor so its center is aligned
     *  with center of other BaseActor. Useful when one BaseActor spawns another.
     *  @param other BaseActor to align this BaseActor with
     */
    public void centerAtActor(BaseActor other)
    {
        centerAtPosition( other.getX() + other.getWidth()/2, other.getY() + other.getHeight()/2 );
    }

    public static void setWorldBounds(float width, float height) {
        worldBounds = new Rectangle(0, 0, width, height);
    }

    public static void setWorldBounds(BaseActor actor) {
        setWorldBounds(actor.getWidth(), actor.getHeight());
    }

    public void boundToWorld() {
        //check Left side
        if(getX() < 0) {
            setX(worldBounds.width);
        }
        //check right isde
        if(getX() > worldBounds.width) {
            setX(0);
        }
        //check bottom edge
        if(getY() < 0) {
            setY(worldBounds.height);
        }
        //check top edge
        if(getY() > worldBounds.height) {
            setY(0);
        }
    }


}
