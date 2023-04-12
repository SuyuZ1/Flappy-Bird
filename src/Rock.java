import bagel.Input;
/**
 * The type Rock.
 */
public class Rock extends Weapon{
    private final int SHOOT_RANGE = 25;

    /**
     * Instantiates a new Rock.
     *
     * @param type the type
     */
    public Rock(int type){ super(type); }
    public void update(Input input, Bird bird, int timeScale){
        super.update(input,bird,timeScale);
        super.drawWeapon(SHOOT_RANGE);
    }
}