package com.javakaihua.farmgame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Entity extends Actor {
    //人物移动类，用来实现人物移动

    private Vector2 velocity;

    private Direction currentDirection;
    private Direction previousDirection;

    private Animation walkLeft;
    private Animation walkRight;
    private Animation walkUp;
    private Animation walkDown;
    private Animation fallDown;

    private Array<TextureRegion> walkLeftFrames;
    private Array<TextureRegion> walkRightFrames;
    private Array<TextureRegion> walkUpFrames;
    private Array<TextureRegion> walkDownFrames;
    private Array<TextureRegion> fallDownFrames;

    private Vector2 nextPosition;
    private Vector2 currentPosition;

    private State state = State.IDLE;
    private float frameTime;
    private Sprite frameSprite;
    private TextureRegion currentFrame;
    private TextureRegion sleepFrame;

    private Texture texture;

    public static Rectangle boundingBox;
    public enum position{
       	outside,
       	inside;
       }
       public position positionState=position.inside;
       

    public enum State {
        IDLE, WALKING,
    }

    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    public Entity(){
        this.nextPosition = new Vector2();
        this.currentPosition = new Vector2();
        this.boundingBox = new Rectangle();
        this.velocity = new Vector2(8f,8f);
        frameTime = 0f;
        currentDirection = Direction.UP;
        texture = new Texture("assets/image/entity/entity.png");
        loadSprite();
        loadAnimations();
    }
    public void startingPosition(float x, float y){
        this.currentPosition.set(x,y);
        this.nextPosition.set(x,y);
    }

    public void update(float delta) {
        if(state == State.WALKING)
            frameTime = (frameTime + delta)%5;
        else
            frameTime = 0;
//        setCurrentPosition(this.currentPosition.x, this.currentPosition.y);
        boundingBox.set(nextPosition.x + 20, nextPosition.y, 24, 12);

    }

    private void loadSprite() {
        TextureRegion[][] textureFrames = TextureRegion.split(texture, 64, 64);
        frameSprite = new Sprite(textureFrames[0][0].getTexture(), 0, 0, 64, 64);
        currentFrame = textureFrames[0][0];
        sleepFrame=textureFrames[2][3];
    }

    private void loadAnimations() {

        TextureRegion[][] textureFrames = TextureRegion.split(texture, 64, 64);
        walkDownFrames = new Array<TextureRegion>(9);
        walkUpFrames = new Array<TextureRegion>(9);
        walkLeftFrames = new Array<TextureRegion>(9);
        walkRightFrames = new Array<TextureRegion>(9);
        fallDownFrames = new Array<TextureRegion>(6);

        for (int i = 0; i < 6; i++) {
            fallDownFrames.insert(i, textureFrames[20][i]);

        }

        for(int i = 0; i < 8; i++){
            walkDownFrames.insert(i, textureFrames[10][i+1]);

        }
        for(int i = 0; i < 8; i++){
            walkUpFrames.insert(i, textureFrames[8][i+1]);
        }
        for(int i = 0; i < 9; i++){
            walkLeftFrames.insert(i, textureFrames[9][i]);
        }
        for(int i = 0; i < 9; i++){
            walkRightFrames.insert(i, textureFrames[11][i]);
        }

        walkDown = new Animation(.1f, walkDownFrames, Animation.PlayMode.LOOP);
        walkUp = new Animation(.1f, walkUpFrames, Animation.PlayMode.LOOP);
        walkLeft = new Animation(.1f, walkLeftFrames, Animation.PlayMode.LOOP);
        walkRight = new Animation(.1f, walkRightFrames, Animation.PlayMode.LOOP);
        fallDown = new Animation(.1f, fallDownFrames, Animation.PlayMode.LOOP);
    }

    public void setDirection(Direction direction, float delta){
        this.previousDirection = this.currentDirection;
        this.currentDirection = direction;
        switch (currentDirection){
            case DOWN:
                currentFrame = walkDown.getKeyFrame(frameTime);
                break;
            case UP:
                currentFrame = walkUp.getKeyFrame(frameTime);
                break;
            case LEFT:
                currentFrame = walkLeft.getKeyFrame(frameTime);
                break;
            case RIGHT:
                currentFrame = walkRight.getKeyFrame(frameTime);
                break;
            default:
                break;
        }
    }

    public void move(Direction direction, float delta) {
        float x = currentPosition.x;
        float y = currentPosition.y;
        switch (direction){
            case DOWN:
                y -= velocity.y;
                break;
            case UP:
                y += velocity.y;
                break;
            case LEFT:
                x -= velocity.x;
                break;
            case RIGHT:
                x += velocity.x;
                break;
            default:
                break;
        }
        nextPosition.x = x;
        nextPosition.y = y;
    }

    public void sleep(){
        currentFrame=sleepFrame;
    }

    public void faint(){
        currentFrame=fallDown.getKeyFrame(0.5f);
    }

    public void setState(State state) {
        this.state = state;
    }

    public Sprite getFrameSprite() {
        return frameSprite;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public Direction getDirection() {
        return currentDirection;
    }

    public void setCurrentPosition(float x, float y){
        frameSprite.setX(x);
        frameSprite.setY(y);
        this.currentPosition.x = x;
        this.currentPosition.y = y;
        this.nextPosition.x = x;
        this.nextPosition.y = y;
    }

    public void setCurrentToNext(){
        setCurrentPosition(nextPosition.x, nextPosition.y);
    }

    public static Rectangle getBoundingBox() {
        return boundingBox;
    }
    public  float getPlayerX() {
        return currentPosition.x;
    }
    public  float getPlayerY() {
        return currentPosition.y;
    }

}

