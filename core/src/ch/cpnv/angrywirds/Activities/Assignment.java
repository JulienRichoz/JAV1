//PAGE GERANT L'AFFICHAGE DES DEVOIRS

package ch.cpnv.angrywirds.Activities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.sun.nio.sctp.AssociationChangeNotification;

import ch.cpnv.angrywirds.AngryWirds;
import ch.cpnv.angrywirds.DataStore.GameScore;
import ch.cpnv.angrywirds.DataStore.Store;
import ch.cpnv.angrywirds.GameActivity;
import ch.cpnv.angrywirds.Providers.GameConnection;
import ch.cpnv.angrywirds.Providers.VoxData;
import ch.cpnv.angrywirds.Vocabulary.Language;
import ch.cpnv.angrywirds.models.Button;
import ch.cpnv.angrywirds.models.ConvertXY;
import ch.cpnv.angrywirds.models.DrawText;
import ch.cpnv.angrywirds.models.ShowBestScore;
import jdk.nashorn.internal.runtime.NumberToString;

public class Assignment extends GameActivity implements InputProcessor{

    private static final String SPRITE_BUTTON_PLAY = "new.png";
    private static final String SPRITE_BUTTON_EXIT = "exit.png";
    public static final String BACKGROUND_IMAGE = "beige.jpg";
    public static final String DONE = "done.png";
    public static final String NOTDONE = "notdone.png";

    private static final boolean stat1 =VoxData.getResult(1);
    private static final boolean stat2 =VoxData.getResult(2);
    private static final boolean stat4 =VoxData.getResult(4);



    Texture background;
    Vector2 touch;
    Button voc1, voc2, voc3, status1, status2, status3;
    Button status11, status21, status31;
    DrawText informations;


    public Assignment(){
        super();
        background = new Texture(BACKGROUND_IMAGE);
        voc1 = new Button(new Vector2((GameActivity.WORLD_WIDTH/2-100),WORLD_HEIGHT-200), new Vector2(250, 100), "button.png", "Les Couleurs");
        voc2 = new Button(new Vector2((GameActivity.WORLD_WIDTH/2-100),WORLD_HEIGHT-400), new Vector2(250, 100), "button.png", "Le Restaurant");
        voc3 = new Button(new Vector2((GameActivity.WORLD_WIDTH/2-100),WORLD_HEIGHT-600), new Vector2(250, 100), "button.png", "Lu0027art");
        status1 = new Button(new Vector2((GameActivity.WORLD_WIDTH / 2 + 300), WORLD_HEIGHT - 200), new Vector2(100, 100), NOTDONE);
        status2 = new Button(new Vector2((GameActivity.WORLD_WIDTH/2+300),WORLD_HEIGHT-400), new Vector2(100, 100), NOTDONE);
        status3 = new Button(new Vector2((GameActivity.WORLD_WIDTH/2+300),WORLD_HEIGHT-600), new Vector2(100, 100), NOTDONE);
        status11 = new Button(new Vector2((GameActivity.WORLD_WIDTH / 2 + 300), WORLD_HEIGHT - 200), new Vector2(100, 100), DONE);
        status21 = new Button(new Vector2((GameActivity.WORLD_WIDTH/2+300),WORLD_HEIGHT-400), new Vector2(100, 100), DONE);
        status31 = new Button(new Vector2((GameActivity.WORLD_WIDTH/2+300),WORLD_HEIGHT-600), new Vector2(100, 100), DONE);
        informations = new DrawText(batch, Color.RED, 50, 100, "La generation dun nouveau monde peut parfois durer de longues secondes (45 max). Merci de patienter...", 2.2f);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, GameActivity.WORLD_WIDTH,GameActivity.WORLD_HEIGHT);
        voc1.draw(batch);
        voc2.draw(batch);
        voc3.draw(batch);
        informations.drawAny();
        if (stat1 != true){
            status1.draw(batch);
        }
        else
        {
            status11.draw(batch);
        }

        if (stat2 != true){
            status2.draw(batch);
        }
        else
        {
            status21.draw(batch);
        }

        if (stat4 != true){
            status3.draw(batch);
        }
        else
        {
            status31.draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void InitInputProcessor() {
        Gdx.input.setInputProcessor(this);
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
        if (voc1.checkIfClicked(touch.x,touch.y)) {
            AngryWirds.AssignmentId = 1;
            AngryWirds.VocabularyId = 1 - 1;
            AngryWirds.title = "Les Couleurs";
            AngryWirds.result = stat1;
            AngryWirds.gameActivityManager.push(new Play());
        }

        if (voc2.checkIfClicked(touch.x,touch.y)){

            AngryWirds.AssignmentId = 2;
            AngryWirds.VocabularyId = 2-1;
            AngryWirds.title = "Le Restaurant";
            AngryWirds.result = stat2;
            AngryWirds.gameActivityManager.push(new Play());
        }

        if (voc3.checkIfClicked(touch.x,touch.y)){

            AngryWirds.AssignmentId = 4;
            AngryWirds.VocabularyId = 4-1;
            AngryWirds.title = "Lu0027art";
            AngryWirds.result = stat4;
            AngryWirds.gameActivityManager.push(new Play());
        }

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
