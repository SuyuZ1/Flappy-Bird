import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.Random;

/**
 * The type Weapon.
 */
public class Weapon {


    private final Image WEAPON_IMAGE;
    private final Image ROCK    = new Image("res/level-1/rock.png");
    private final Image BOMB    = new Image("res/level-1/bomb.png");


    private double weaponX      = Window.getWidth();
    private double weaponY;
    private final double RANDOM_POSITION;

    private boolean isPicked    = false;
    private boolean isShoot     = false;
    private boolean isDisappear = false;
    private boolean isOverlap   = false;


    private double weaponSpeed      = 5;
    private final int INTIAL_SPEED = 5;
    private final double START      = 1;
    private final double END        = 500;
    private final double RATE_SCALE = 0.5;

    private int frameCount = 0;

    private int type;

    /**
     * Instantiates a new Weapon.
     *
     * @param type the type
     */
    public Weapon(int type){

        double rand   = new Random().nextDouble();

        if (type==0){
            this.WEAPON_IMAGE = ROCK;
            this.type = 0;
        }else{
            this.WEAPON_IMAGE = BOMB;
            this.type = 1;
        }
        RANDOM_POSITION = START + (rand*(END - START));
        this.weaponY = RANDOM_POSITION;

    }


    /**
     * Update.
     *
     * @param input     the input
     * @param bird      the bird
     * @param timescale the timescale
     */
    public void update(Input input, Bird bird, int timescale){

         // check pick up the weapon or not
        if(!isPicked){
            this.weaponX -= weaponSpeed;
        }else{
            if(!isShoot && bird.getHoldWeapon() ){

                shotWeapon(bird,input);
            }
        }

        weaponSpeed = INTIAL_SPEED * (1+(timescale-1)*RATE_SCALE);
    }

    /**
     * Shot weapon.
     *
     * @param bird the Bird
     * @param input the Input
     */
    public void shotWeapon(Bird bird,Input input){

        if(input.wasPressed(Keys.S)){
            // change the status of weapon
            bird.setHoldWeapon(false);
            this.isShoot = true;

        }else {
            this.setWeaponPosition(bird.getBox().right(),bird.getY());
        }
    }



    /**
     * Draw weapon.
     *
     * @param shootRange the shoot range
     */
    public void drawWeapon(int shootRange){

        if(!isDisappear && !isOverlap){
            WEAPON_IMAGE.draw(weaponX,weaponY);
        }
        // check the weapon is in range or not
        if(isShoot){
            if(frameCount <= shootRange){
                this.weaponX += weaponSpeed;
            }else{
                this.isDisappear = true;
            }
            frameCount++;
        }

    }


    /**
     * Gets type.
     *
     * @return the type
     */
    public int getType() { return type; }

    /**
     * Is picked boolean.
     *
     * @return the boolean
     */
    public boolean isPicked() {
        return isPicked;
    }

    /**
     * Set picked up.
     *
     * @param PickedUp the picked up
     */
    public void setPickedUp(boolean PickedUp){
        this.isPicked = PickedUp;
    }

    /**
     * Is overlap boolean.
     *
     * @return the boolean
     */
    public boolean isOverlap() {
        return isOverlap;
    }

    /**
     * Is disappear boolean.
     *
     * @return the boolean
     */
    public boolean isDisappear() {
        return isDisappear;
    }

    /**
     * Sets disappear.
     *
     * @param disappear the disappear
     */
    public void setDisappear(boolean disappear) {
        this.isDisappear = disappear;
    }

    /**
     * Is shoot boolean.
     *
     * @return the boolean
     */
    public boolean isShoot() {
        return isShoot;
    }

    /**
     * Gets weapon box.
     *
     * @return the weapon box
     */
    public Rectangle getWeaponBox() { return  WEAPON_IMAGE.getBoundingBoxAt(new Point(weaponX, weaponY)); }

    /**
     * Sets overlap.
     *
     * @param overlap the overlap
     */
    public void setOverlap(boolean overlap) {
        this.isOverlap = overlap;
    }

    /**
     * Sets weapon position.
     *
     * @param weaponX the weapon x
     * @param weaponY the weapon y
     */
    public void setWeaponPosition(double weaponX,double weaponY) {
        this.weaponX = weaponX;
        this.weaponY = weaponY;
    }
}