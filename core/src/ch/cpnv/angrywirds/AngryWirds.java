package ch.cpnv.angrywirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ch.cpnv.angrywirds.Activities.Play;
import ch.cpnv.angrywirds.Activities.Welcome;
import ch.cpnv.angrywirds.Providers.PostAssignmentsDatas;
import ch.cpnv.angrywirds.Providers.VoxData;
import ch.cpnv.angrywirds.Vocabulary.Assignment;
import ch.cpnv.angrywirds.Vocabulary.Language;

public class AngryWirds extends Game{

    static public GameActivityManager gameActivityManager = new GameActivityManager();
    static public int LangueId = VoxData.getLangueId(Language.LANGUE_FR); // defualt fr
    //static public ArrayList<Assignment> assignmentArray = VoxData.getAssignments();
    static public int AssignmentId;
    static public int VocabularyId;
    static public String title;
    static public boolean result = false;
    public static int score = 50; // initial score
    public static int timer = 120; // initial score

    @Override
    public void create () {
        gameActivityManager.push(new Welcome());
    }

    @Override
    public void render () {
      gameActivityManager.handleInput();
      gameActivityManager.update();
      gameActivityManager.render();
    }

    @Override
    public void dispose () {
    }
}
