import bagel.Image;
import bagel.Input;

/**
 * The type Bomb.
 */
public class Bomb extends Weapon{
    private final int SHOOT_RANGE = 50;

    /**
     * Instantiates a new Bomb.
     *
     * @param type the type
     */
    public Bomb(int type){ super(type); }

    public void update(Input input,Bird bird,int timeScale){
        super.update(input,bird,timeScale);
        super.drawWeapon(SHOOT_RANGE);
    }
}
