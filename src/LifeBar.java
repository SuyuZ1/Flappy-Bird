import bagel.Image;

/**
 * The type Life bar.
 */
public class LifeBar {

    // the gap of different level
    private final int gapLVL1     = 400;
    private final int gapLVL0     = 250;

    private final int GAP         = 50;
    private final int LIFE_HEIGHT = 100;
    private final int LIFE_WIDTH  = 15;

    // define the number of life
    private final int NUM_LIFE1 = 6;
    private final int NUM_LIFE0 = 3;

    // create the image
    private final Image fullLife = new Image("res/level/fullLife.png");
    private final Image noLife   = new Image("res/level/noLife.png");


    /**
     * Draw life bar.
     *
     * @param num the num
     * @param lev the lev
     */
    public void drawLifeBar(int num,boolean lev){

        for (int i=0;i<num;i++) {
            fullLife.drawFromTopLeft(LIFE_HEIGHT + GAP * i, LIFE_WIDTH);
        }

        if(lev) {
            for (int j = NUM_LIFE1 - num; j > 0; j--) {
                noLife.drawFromTopLeft(gapLVL1 - (GAP * j), LIFE_WIDTH);
                }
        }else{
            for (int j = NUM_LIFE0 - num; j > 0; j--) {
                noLife.drawFromTopLeft(gapLVL0 - (GAP * j), LIFE_WIDTH);
            }
        }

    }


}
