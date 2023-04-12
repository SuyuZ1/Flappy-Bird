import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * The type Steel pipe.
 */
public class steelPipe extends PipeSet{

    private final int DURATION              = 30;
    private final int FRAME_REFRESH         = 20;

    private int frameCount                  = 0;
    private int flameDuration               = 0;

    private final int FLAME_SEGMENT         = 15;

    private static final Image FLAME_IMAGE = new Image("res/level-1/flame.png");


    /**
     * Instantiates a new Steel pipe.
     *
     * @param type    the type
     * @param levelUp the level up
     */
    public steelPipe(int type, int levelUp){
        super(type,levelUp);
    }

    /**
     * update weapon
     *
     * @param timeScale the int
     */
    public void update(int timeScale){

        frameCount += 1;

        if(frameCount % FRAME_REFRESH == 0) {
            if (!super.getCollision() && flameDuration < DURATION) {
                restartFrame(0);
            }
        }

        if(flameDuration==DURATION){
            restartFrame(1);
        }

        super.update(timeScale);
    }

    /**
     * Restart frame.
     *
     * @param check the int
     */
    public void restartFrame(int check){
        if (check == 0){
            frameCount -= 1;
            flameDuration += 1;

            // draw flame
            FLAME_IMAGE.draw(super.getPipeX(),super.getTopY()+ Window.getHeight()/2.0+FLAME_SEGMENT);
            FLAME_IMAGE.draw(super.getPipeX(),super.getBottomY()- Window.getHeight()/2.0
                    -FLAME_SEGMENT,super.getROTATOR());
        }else{
            flameDuration = 0;
            frameCount +=DURATION;
        }
    }




    /**
     * Get bottom flame rectangle.
     *
     * @return the rectangle
     */
    public Rectangle getBottomFlame(){
        return FLAME_IMAGE.getBoundingBoxAt(new Point(super.getPipeX(),
                super.getTopY() + Window.getHeight()/2.0 + 18));
    }

    /**
     * Get top flame rectangle.
     *
     * @return the rectangle
     */
    public Rectangle getTopFlame(){
        return FLAME_IMAGE.getBoundingBoxAt(new Point(super.getPipeX(),
                super.getBottomY() - Window.getHeight()/2.0 - 18));

    }



}

