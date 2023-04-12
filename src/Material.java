import bagel.*;

import java.lang.String;
public class Material {

    // create the constant for the drawing method
    private static final int WIDTH        = 1024;
    private static final int HEIGHT       = 768;
    private final int SCORE_POSITION      = 100;
    private final int FONT_SIZE           = 48;
    private final int SCORE_MSG_OFFSET    = 75;

    // create the information to draw
    private final String INSTRUCTION_MSG  = "PRESS SPACE TO START";
    private final String GAME_OVER_MSG    = "GAME OVER!";
    private final String LEVELUP          = "LEVEL-UP!";
    private final String CONGRATS_MSG     = "CONGRATULATIONS!";
    private final String SCORE_MSG        = "SCORE: ";
    private final String FINAL_SCORE_MSG  = "FINAL SCORE: ";
    private final String LEVELUP1_INFO    = "PRESS 'S' TO SHOOT";

    // create instance of diagram
    private final Font fontString         = new Font("res/font/slkscr.ttf", FONT_SIZE);
    private final Image BACKGROUND0       = new Image("res/level-0/background.png");
    private final Image BACKGROUND1       = new Image("res/level-1/background.png");

    public void drawScore(int score) {
        fontString.drawString(SCORE_MSG + score, SCORE_POSITION, SCORE_POSITION);
    }

    public void drawLevelUp(){
        double textWidth = fontString.getWidth("Level-Up!");
        fontString.drawString(LEVELUP,(WIDTH - textWidth) / 2.0, HEIGHT / 2.0);
//        String finalScoreMsg = FINAL_SCORE_MSG + score;
//        fontString.drawString(finalScoreMsg, (Window.getWidth()/2.0-(fontString.getWidth(finalScoreMsg)/2.0)),
//                (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    public void renderGameOverScreen(int score) {
        fontString.drawString(GAME_OVER_MSG, (Window.getWidth()/2.0-(fontString.getWidth(GAME_OVER_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));

        String finalScoreMsg = FINAL_SCORE_MSG + score;

        fontString.drawString(finalScoreMsg, (Window.getWidth()/2.0-(fontString.getWidth(finalScoreMsg)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    public void renderWinScreen(int score) {
        fontString.drawString(CONGRATS_MSG, (Window.getWidth()/2.0-(fontString.getWidth(CONGRATS_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        fontString.drawString(finalScoreMsg, (Window.getWidth()/2.0-(fontString.getWidth(finalScoreMsg)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    public void renderInstructionScreen() {
        fontString.drawString(INSTRUCTION_MSG, (Window.getWidth()/2.0-(fontString.getWidth(INSTRUCTION_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
    }

    public void renderInstructionScreen_lvl1() {
        fontString.drawString(INSTRUCTION_MSG, (Window.getWidth() / 2.0 - (fontString.getWidth(INSTRUCTION_MSG) / 2.0)),
                (Window.getHeight() / 2.0 - (FONT_SIZE / 2.0)));

        fontString.drawString(LEVELUP1_INFO, (Window.getWidth()/2.0-(fontString.getWidth(LEVELUP1_INFO)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    public void drawBackgroundlvl0(double x, double y){ BACKGROUND0.draw(x, y);}

    public void drawBackgroundlvl1(double x, double y){ BACKGROUND1.draw(x, y);}

}
