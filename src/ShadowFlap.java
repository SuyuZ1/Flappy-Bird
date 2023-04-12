import bagel.*;
import bagel.util.Rectangle;


import java.util.ArrayList;
import java.util.Random;

/**
 * SWEN20003 Project 1, Semester 2, 2021
 * <p>
 * <p>
 * Modify project-1 solution from Betty Lin
 *
 * @author Suyu Zhang, 1174078
 */
public class ShadowFlap extends AbstractGame {

    private Material material;
    private Bird     bird;
    private PipeSet  pipeSet;
    private LifeBar  lifeBar;

    private int score;
    private final int levelUpScore  = 10;
    private final int winScore      = 30;
    private int currentLevel        = 0;

    // flame mechanics
    private int lastFlameCollisionFrame  = -1;
    private final int PASS_FLAME_FRAME   = 16;
    private int flameFrameCount          = 0;  // used to detect flame collision

    // boolean variable to check the status of game
    private boolean gameOn;
    private boolean win;
    private boolean collision;
    private boolean check         = false;
    private boolean checkLevelUp  = false;
    private boolean levelUp       = false;

    // pipe variables
    private ArrayList<PipeSet> pipe_level_0   = new ArrayList<>();
    private ArrayList<PipeSet> pipe_level_1   = new ArrayList<>();
    private final int steelType               = 1;

    // weapon variables
    private ArrayList<Weapon> weapons         = new ArrayList<>();
    private final int bombType                = 1;


    // the variable for timescale
    private int timeScale           = 1;
    private final int MAX_TIMESCALE = 5;
    private final int MIN_TIMESCALE = 1;
    private final double TIME_RATE  = 0.5;

    // frame variables to calculate
    private long frame              = 100;
    private int frameCount          = 0;
    private final int lvlTime       = 20;

    private final double REFRESH_WEAPON = 150;
    private final double REFRESH_PIPE = 100;
    private double pipeTimeScale = REFRESH_PIPE;
    private double weaponTimeScale = REFRESH_WEAPON;

    /**
     * The Rand.
     */
    Random rand = new Random();
    /**
     * The Random.
     */
    int random;

    /**
     * Instantiates a new Shadow flap.
     */
    public ShadowFlap() {

        super(1024, 768, "ShadowFlap");

        score     = 0;

        // give the default boolean value
        gameOn    = false;
        collision = false;
        win       = false;

        //Create the instance
        bird      = new Bird();
        lifeBar   = new LifeBar();
        pipeSet   = new PipeSet(0,currentLevel);
        material  = new Material();
    }

    /**
     * The entry point for the program.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }


    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {

        // set the status to the game
        setGame();

        // check the status of each variable
        gameCheck(input);

        // game is active
        if (gameOn && bird.getNumLife()!=0 ){

            Rectangle birdRect = bird.getBox();

            // update the timescale of weapon and pipe
            updateTimeScale(input);
            updateSpawnSpeed();

            // update bird status
            bird.update(input);

            // when the game is in level 0
            if(!levelUp){

                lvl0Game(birdRect);

                // when the game is in level 0
            }else{
                // act the game of level 1
                lvl1Game(birdRect,input);
            }

            // update lifeBar to display on the screen, update frame count
            lifeBar.drawLifeBar(bird.getNumLife(),levelUp);
            frame++;
            material.drawScore(score);
        }
    }

    /**
     * Lvl 0 game.
     *
     * @param birdRect the bird rect
     */
    public void lvl0Game(Rectangle birdRect){
        /*
            add the pipe randomly,
            if the collision is happened, then remove current pipe from pipeList
            while updating each pipe, update current score
         */
        if(frame % pipeTimeScale ==0){
            pipe_level_0.add(new plasticPipe(0,currentLevel));
        }

        for(int i =0; i<pipe_level_0.size();i++){

            // add pipe according to timescale
            pipe_level_0.get(i).update(timeScale);
            updatePipe(pipe_level_0,birdRect);
            pipe_level_0.get(i).drawPipe();

            // remove the collisioning pipe
            if(pipe_level_0.get(i).getCollision()){
                pipe_level_0.remove(i);
            }
        }

        for(int i =0; i<pipe_level_0.size();i++) {
            updateScore(pipe_level_0.get(i));
        }
    }

    /**
     * Lvl 1 game.
     *
     * @param birdRect the bird rect
     * @param input    the input
     */
    public void lvl1Game(Rectangle birdRect,Input input){
        /*
            add the pipe and weapons randomly
            if the collision is happened, then remove current pipe from pipeList
            while updating each pipe, update current score and current weapon.
         */
        if(frame%pipeTimeScale == 0){
            randomWeaponPipe(0);
        }
        if(frame % weaponTimeScale == 0){
            randomWeaponPipe(1);
            checkOverlap(weapons, pipe_level_1);
        }

        // add pipe according to timescale and different type
        for(int i = 0; i< pipe_level_1.size(); i++){
            pipe_level_1.get(i).update(timeScale);

            updatePipe(pipe_level_1,birdRect);
            pipe_level_1.get(i).drawPipe();

            if(pipe_level_1.get(i).getCollision() ){ pipe_level_1.remove(i); }
        }

        // update score
        for(int i = 0; i< pipe_level_1.size(); i++) {
            updateScore(pipe_level_1.get(i));
        }

        for(int i=0;i<weapons.size();i++){
            // check the status of current weapon
            weapons.get(i).update(input,bird,timeScale);
            checkWeaponPipe(weapons.get(i));
            /*
                if bird catch up weapons and the weapon is not overlap with pipe,
                also the bird does not hold weapon at this time,
                then bird should pick up the weapon
             */
            if(!weapons.get(i).isPicked() &&
                    birdRect.intersects(weapons.get(i).getWeaponBox()) && !weapons.get(i).isOverlap()){

                if(!bird.getHoldWeapon()){
                    weapons.get(i).setPickedUp(true);
                    bird.setHoldWeapon(true);
                }
            }
        }
    }


    /**
     * Set game.
     */
    public void setGame(){

        // create a random number, get ready for next stage
        random = rand.nextInt(100);

        // initialize total life of bird
        if(currentLevel==0 && check ==false){
            bird.setNumLife(0);
        }else if(currentLevel==1 && check ==false){
            bird.setNumLife(1);
        }
        check = true;

        // draw matching background with different level
        if(currentLevel==0) {
            material.drawBackgroundlvl0(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        }else{
            material.drawBackgroundlvl1(Window.getWidth()/2.0, Window.getHeight()/2.0);
        }
    }

    /**
     * Game check.
     *
     * @param input the input
     */
    public void gameCheck(Input input ){

        if (input.wasPressed(Keys.ESCAPE)) { Window.close();}

        // game has not started, display the prompt information
        if (!gameOn) {
            if(!levelUp) { material.renderInstructionScreen(); }

            if (input.wasPressed(Keys.SPACE)) { gameOn = true; }
        }

        // game over, display game over message
        if (bird.getNumLife()<=0) { material.renderGameOverScreen(score); }

        // game won, display game won message
        if (win) { material.renderWinScreen(score); }

        /*
            game upgrade, display the upgrade message,
            give prompt information in order to get ready for next level
         */
        if(levelUp && !checkLevelUp){

            if(frameCount < lvlTime ) {
                material.drawBackgroundlvl0(Window.getWidth()/2.0, Window.getHeight()/2.0);
                material.drawLevelUp();
                gameOn = false;
            }else{
                if(!gameOn){ material.renderInstructionScreen_lvl1();}
            }

            // restart game after lvl0
            if (input.wasPressed(Keys.SPACE)){
                // Transition from A to B, reset the checking variable
                restart();
            }
            frameCount++;
        }

        flameFrameCount++;

        // detect the bird if inside the gaming screen
        if(birdOutOfBound()){
            bird.takeLife();
            bird.setY();
        }

    }


    /**
     * Restart.
     */
    public void restart(){
        gameOn = true;
        timeScale = 1;
        checkLevelUp = true;
        score  = 0;
        frameCount = 160;
    }

    /**
     * Update time scale.
     *
     * @param input the input
     */
    public void updateTimeScale(Input input){
    /*
        According to the different key input of user,
        to alter the speed of game
    */
        if(input.wasPressed(Keys.K) &&timeScale> MIN_TIMESCALE){
            timeScale--;
        }
        if(input.wasPressed(Keys.L) &&timeScale< MAX_TIMESCALE){
            timeScale++;
        }

    }

    /**
     * Update spawn speed.
     */
    public void updateSpawnSpeed(){
        // change speed according to timescale
        weaponTimeScale = Math.round(REFRESH_WEAPON / (1+ (timeScale-1) * TIME_RATE));
        pipeTimeScale = Math.round(REFRESH_PIPE / (1+ (timeScale-1) * TIME_RATE));
    }

    /**
     * Bird out of bound boolean.
     *
     * @return the boolean
     */
    public boolean birdOutOfBound() {
        return (bird.getY() > Window.getHeight()) || (bird.getY() < 0);
    }

    /**
     * Detect collision boolean.
     *
     * @param birdBox       the bird box
     * @param topPipeBox    the top pipe box
     * @param bottomPipeBox the bottom pipe box
     * @return the boolean
     */
    public boolean detectCollision(Rectangle birdBox, Rectangle topPipeBox, Rectangle bottomPipeBox) {
        // check for collision
        return birdBox.intersects(topPipeBox) || birdBox.intersects(bottomPipeBox);
    }

    /**
     * Update score.
     *
     * @param pipeSet the pipe set
     */
// Update score in real time
    public void updateScore(PipeSet pipeSet) {

        // finish game after reaching the final score
        if(score == winScore ){
            win    = true;
            gameOn = false;
        }

        //  when the score achieve the score to upgrade
        if (score == levelUpScore && levelUp==false) {
            levelUp      = true;
            bird.setLvlup();
            timeScale    = 1;
            check        = false;
            gameOn       = false;
            currentLevel = 1;
        }

        // add score, when bird safely pass pipes
        if (!pipeSet.getPassed() && bird.getX() > pipeSet.getTopBox().right()){
            pipeSet.passed();
            score += 1;
        }

    }

    /**
     * Random weapon pipe.
     *
     * @param level the level
     */
// randomly create weapons and pipes to game
    public void randomWeaponPipe(int level){

        if (level == 0){
            if(random  % 2 == 0){
                pipe_level_1.add(new plasticPipe(0,currentLevel));
            }else{
                pipe_level_1.add(new steelPipe(1, currentLevel));
            }
        }else{
            if(random%2==0){
                weapons.add(new Rock(0));
            }else{
                weapons.add(new Bomb(1));
            }
        }
    }

    /**
     * Update pipe.
     *
     * @param pipeArray the pipe array
     * @param birdBox   the bird box
     */
    public void updatePipe(ArrayList<PipeSet> pipeArray,Rectangle birdBox){

        for(int i =0; i< pipeArray.size();i++){

            updateScore(pipeSet);


            if(pipeArray.get(i) instanceof steelPipe){

                steelPipe steelPipe = (steelPipe) pipeArray.get(i);
                boolean flameCollided = detectCollision(birdBox,
                        steelPipe.getTopFlame(), steelPipe.getBottomFlame());

                if (flameCollided && flameFrameCount - lastFlameCollisionFrame >= PASS_FLAME_FRAME) {
                    bird.takeLife();
                    pipeArray.get(i).setCollision(true);
                    lastFlameCollisionFrame = flameFrameCount ;
                }
            }

            collision = detectCollision(birdBox,
                    pipeArray.get(i).getTopBox(),pipeArray.get(i).getBottomBox());

            if(collision && !pipeArray.get(i).getCollision()){
                bird.takeLife();
                pipeArray.get(i).setCollision();
                if(pipeArray.get(i) instanceof steelPipe){
                    lastFlameCollisionFrame = flameFrameCount;
                }
            }
        }
    }

    /**
     * check Overlap.
     *
     * @param weapons  the weapons
     * @param pipeSets the pipe sets
     */
    // detect if weapon overlap with pipe
    public void checkOverlap(ArrayList<Weapon> weapons, ArrayList<PipeSet> pipeSets) {
        for (Weapon weapon : weapons) {
            for (PipeSet pipe:pipeSets){
                if (detectCollision(weapon.getWeaponBox(), pipe.getTopBox(),
                        pipe.getBottomBox())){
                    weapon.setOverlap(true);
                }
            }
        }
    }


    /**
     * Check weapon pipe.
     *
     * @param weapon the weapon
     */
// detect the collision between pipe and weapons
    public void checkWeaponPipe(Weapon weapon){

        /*
            detect the weapon on the screen which is not been shot,
            then check the collision between this weapon and pipe,
            if the current weapon held by bird touches the pipe which is attachable,
            then update the status of pipe.
         */
        for(int i = 0; i< pipe_level_1.size(); i++){

            if(weapon.isShoot() && !weapon.isDisappear()){

                boolean weaponCollision = detectCollision(weapon.getWeaponBox(),
                        pipe_level_1.get(i).getTopBox(), pipe_level_1.get(i).getBottomBox());

                // bomb can destroy any type of pipe
                if(weapon.getType() == steelType && weaponCollision){
                    weapon.setDisappear(true);
                    pipe_level_1.get(i).setCollision(true);
                    score+=1;
                }

                // rock can only destroy plastic pipe
                if(weapon.getType()!= bombType
                        && ( pipe_level_1.get(i).getType() != steelType)
                        && weaponCollision){
                    weapon.setDisappear(true);
                    pipe_level_1.get(i).setCollision(true);
                    score+=1;
                }
            }
        }
    }
}




















