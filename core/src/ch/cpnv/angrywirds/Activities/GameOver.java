//PAGE AFFICHANT LE GAME OVER AVEC RESULTAT FINAL; SCORE; ET TEMPS RESTANT

package ch.cpnv.angrywirds.Activities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ch.cpnv.angrywirds.AngryWirds;
import ch.cpnv.angrywirds.DataStore.GameScore;
import ch.cpnv.angrywirds.GameActivity;
import ch.cpnv.angrywirds.GameActivityManager;
import ch.cpnv.angrywirds.Providers.PostAssignmentsDatas;
import ch.cpnv.angrywirds.Providers.VoxData;
import ch.cpnv.angrywirds.models.Button;
import ch.cpnv.angrywirds.models.ConvertXY;
import ch.cpnv.angrywirds.models.DrawText;
import ch.cpnv.angrywirds.models.Game;

public class GameOver extends GameActivity implements InputProcessor{

    private static final String SPRITE_BUTTON_PLAY = "new.png";
    private static final String SPRITE_BUTTON_EXIT = "exit.png";
    private static final String SPRITE_BUTTON_HIGHSCORE = "highscore.png";

    public static final String BACKGROUND_IMAGE = "gameover.png";
    public static int endScore = 50;
    public static int endTime = 50;

    Texture background;
    Vector2 touch;
    Button play,exit, highScore;
    int points;
    DrawText textPlay, textExit, textHighScore, tempsRestant, score, scoreFinal;


    public GameOver(){
        super();
       // Game.music = false;
        points = endScore - (endTime/10) ;

        background = new Texture(BACKGROUND_IMAGE);

        play = new Button(new Vector2((GameActivity.WORLD_WIDTH/2)-40,50), new Vector2(100,100), SPRITE_BUTTON_PLAY);
        textPlay = new DrawText(batch, Color.SKY, (GameActivity.WORLD_WIDTH/2)-43, 35, "New Game", 1.5f);

        exit = new Button(new Vector2((GameActivity.WORLD_WIDTH/2)+150,50), new Vector2(100,100), SPRITE_BUTTON_EXIT);
        textExit = new DrawText(batch, Color.SKY, (GameActivity.WORLD_WIDTH/2)+180, 35, "Exit", 1.5f);

        highScore = new Button(new Vector2((GameActivity.WORLD_WIDTH/2)-230,50), new Vector2(100,100), SPRITE_BUTTON_HIGHSCORE);
        textHighScore = new DrawText(batch, Color.SKY, (GameActivity.WORLD_WIDTH/2)-233, 35, "HighScores", 1.5f);

        scoreFinal = new DrawText(batch, Color.YELLOW, WORLD_WIDTH/2-400, WORLD_HEIGHT-150, "Votre score a été enregistré sur l'API: ".concat(String.valueOf(points)));
        tempsRestant = new DrawText(batch, Color.WHITE, WORLD_WIDTH/2-150, WORLD_HEIGHT-100, "TEMPS RESTANT: ".concat(String.valueOf(endTime)));
        score = new DrawText(batch, Color.WHITE, WORLD_WIDTH/2-150, WORLD_HEIGHT-50, "SCORE: ".concat(String.valueOf(endScore)));

        //SEND DATA TO API
        VoxData.submitResults(AngryWirds.AssignmentId, VoxData.TOKEN, points);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update() {

    }

    //  public void render( float dt )
    //{
    //    batch.setProjectionMatrix(camera.combined); //or your matrix to draw GAME WORLD, not UI

    //    batch.begin();

    //    batch.end();
    //}
    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, GameActivity.WORLD_WIDTH,GameActivity.WORLD_HEIGHT);
        //draw background, objects, etc.
        play.draw(batch);
        exit.draw(batch);
        scoreFinal.drawAny();
        highScore.draw(batch);
       // scoreFinal.drawAny();
        tempsRestant.drawAny();
        textHighScore.drawAny();
        textExit.drawAny();
        textPlay.drawAny();
        score.drawAny();
        batch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void InitInputProcessor() {

    }

    // Imput Processor

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch = new ConvertXY(new Vector2(screenX,screenY), camera);
        if (play.checkIfClicked(touch.x,touch.y)){
            AngryWirds.gameActivityManager.rePlay(new Play());
        }
        if (highScore.checkIfClicked(touch.x, touch.y)){
            AngryWirds.gameActivityManager.rePlay(new GameOverScore(new GameScore(endScore,endTime)));
        }

        if (exit.checkIfClicked(touch.x,touch.y))Gdx.app.exit();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
