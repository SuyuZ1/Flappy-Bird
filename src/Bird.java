import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.lang.Math;

/**
 * The type Bird.
 */
public class Bird {
    private  Image WING_DOWN_IMAGE;
    private  Image WING_UP_IMAGE;
    private  Image WING_DOWN_IMAGE_1;
    private  Image WING_UP_IMAGE_1;
    private final double X = 200;
    private final double FLY_SIZE = 6;
    private final double FALL_SIZE = 0.4;
    private final double INITIAL_Y = 350;
    private final double Y_TERMINAL_VELOCITY = 10;
    private final double SWITCH_FRAME = 10;
    private int frameCount = 0;
    private double y;
    private double yVelocity;
    private Rectangle boundingBox;

    private boolean holdWeapon = false;
    private boolean lvlup = false;

    private int numLife;




    /**
     * Instantiates a new Bird.
     */
    public Bird() {

        this.WING_DOWN_IMAGE = new Image("res/level-0/birdWingDown.png");
        this.WING_UP_IMAGE = new Image("res/level-0/birdWingUp.png");

        this.WING_DOWN_IMAGE_1 = new Image("res/level-1/birdWingDown.png");
        this.WING_UP_IMAGE_1 = new Image("res/level-1/birdWingUp.png");

        y = INITIAL_Y;
        yVelocity = 0;
        boundingBox = WING_DOWN_IMAGE.getBoundingBoxAt(new Point(X, y));
    }

    /**
     * Update.
     *
     * @param input the input
     */
    public void update(Input input) {
        frameCount += 1;

        if (input.wasPressed(Keys.SPACE)) {
            pressFly();
        }
        else {
            flyWithoutPress();
        }
        y += yVelocity;

    }

    /**
     * Press fly.
     */
    public void pressFly(){
        yVelocity = -FLY_SIZE;
        birdFly(1);
    }

    /**
     * Fly without press.
     */
    public void flyWithoutPress( ){
        yVelocity = Math.min(yVelocity + FALL_SIZE, Y_TERMINAL_VELOCITY);

        if (frameCount % SWITCH_FRAME == 0) {
            birdFly(0);

            boundingBox = WING_UP_IMAGE.getBoundingBoxAt(new Point(X, y));
        }else {
            birdFly(1);

            boundingBox = WING_DOWN_IMAGE.getBoundingBoxAt(new Point(X, y));
        }

    }

    /**
     * Bird fly.
     *
     * @param flash the flash
     */
    public void birdFly(int flash){
        if (flash == 0){
            if(!lvlup){
                WING_DOWN_IMAGE.draw(X, y);
            }else{
                WING_DOWN_IMAGE_1.draw(X, y);
            }
        }else{
            if(!lvlup){
                WING_UP_IMAGE.draw(X, y);
            }else{
                WING_UP_IMAGE_1.draw(X, y);
            }
        }

    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() { return y; }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() { return X;}

    /**
     * Get hold weapon boolean.
     *
     * @return the boolean
     */
    public boolean getHoldWeapon(){ return this.holdWeapon; }

    /**
     * Set hold weapon.
     *
     * @param holdWeapon the hold weapon
     */
    public void setHoldWeapon(boolean holdWeapon){ this.holdWeapon = holdWeapon; }


    public Rectangle getBox() {return boundingBox;}

    /**
     * Set y.
     */
    public void setY(){ y = INITIAL_Y;}

    /**
     * Sets num life.
     *
     * @param lvl the lvl
     */
    public void setNumLife(int lvl) {
        if (lvl == 0){ this.numLife=3;}
        if (lvl == 1){ this.numLife=6;}
    }

    /**
     * Gets num life.
     *
     * @return the num life
     */
    public int getNumLife() { return numLife; }

    /**
     * Take life.
     */
    public void takeLife(){ this.numLife-=1;}

    /**
     * Sets lvlup.
     */
    public void setLvlup() {this.lvlup = true;}


}
