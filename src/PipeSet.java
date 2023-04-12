import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;

/**
 * The type Pipe set.
 */
public class PipeSet {


    private       Image PIPE_IMAGE;
    private final Image PLASTIC    = new Image("res/level/plasticPipe.png");
    private final Image STEEL      = new Image("res/level-1/steelPipe.png");

    private final DrawOptions ROTATOR   = new DrawOptions().setRotation(Math.PI);

    private  double   TopY;
    private  double   BottomY;
    private double    pipeX             = Window.getWidth();
    private final int PIPE_GAP          = 168;
    private final int PIPEHEIGHT        = 300;
    private final int HIGH              = 500;
    private final int LOW               = 100;
    private final int FROM_SCREEN_TOP   = -200;
    private final int FROM_SCREEN_BOTTOM= 200;
    private double pipeSpeed            = 5;
    private final int RANGE             = 3;
    private final double RATE_SCALE     = 0.5;

    private int type;

    private final double RANDOM;

    private boolean collision           = false;
    private boolean passed              = false;


    /**
     * Instantiates a new Pipe set.
     *
     * @param type    the type
     * @param levelUp the level up
     */
    public PipeSet(int type,int levelUp) {

        if(type == 0){
            PIPE_IMAGE = PLASTIC;
            this.type = 0;
        }else{
            PIPE_IMAGE = STEEL;
            this.type = 1;
        }

        // create random number to choose different gap
        Random rand             = new Random();
        double randDouble       = new Random().nextDouble();
        int randInt             = rand.nextInt(RANGE);

        // take the different gap
        if (levelUp == 0){
            if( randInt == 2){
                RANDOM = PIPEHEIGHT + FROM_SCREEN_TOP;
            }else{
                RANDOM = PIPEHEIGHT + randInt * FROM_SCREEN_BOTTOM;
            }
        }else{
            RANDOM = LOW + randDouble * (HIGH - LOW);
        }

        TopY = RANDOM-PIPE_IMAGE.getHeight()/2;
        BottomY = PIPE_GAP+RANDOM+PIPE_IMAGE.getHeight()/2;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public int getType() {
        return this.type;
    }

    /**
     * Draw pipe.
     */
    public void drawPipe() {
        if (collision != true) {
            PIPE_IMAGE.draw(pipeX, TopY);
            PIPE_IMAGE.draw(pipeX, BottomY, ROTATOR);
        }
    }

    /**
     * Update.
     *
     * @param timeScale the time scale
     */
    public void update(int timeScale) {
        pipeX -= pipeSpeed * (1+(timeScale-1)*RATE_SCALE);
        drawPipe();
    }


    /**
     * Sets collision.
     *
     * @param collision the collision
     */
    public void setCollision(boolean collision) {
        this.collision = collision;
        this.passed = true;
    }

    /**
     * Gets top box.
     *
     * @return the top box
     */
    public Rectangle getTopBox() { return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, TopY)); }

    /**
     * Gets bottom box.
     *
     * @return the bottom box
     */
    public Rectangle getBottomBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, BottomY));
    }

    /**
     * Get collision boolean.
     *
     * @return the boolean
     */
    public boolean getCollision(){ return collision; }

    /**
     * Set collision.
     */
    public void setCollision(){ collision = true; }

    /**
     * Get passed boolean.
     *
     * @return the boolean
     */
    public boolean getPassed(){ return passed; }

    /**
     * Passed.
     */
    public void passed(){ this.passed = true; }

    /**
     * Gets top y.
     *
     * @return the top y
     */
    public double getTopY() { return TopY; }

    /**
     * Gets bottom y.
     *
     * @return the bottom y
     */
    public double getBottomY() { return BottomY; }

    /**
     * Gets pipe x.
     *
     * @return the pipe x
     */
    public double getPipeX() { return pipeX; }

    /**
     * Get rotator draw options.
     *
     * @return the draw options
     */
    public DrawOptions getROTATOR(){ return ROTATOR; }





}





