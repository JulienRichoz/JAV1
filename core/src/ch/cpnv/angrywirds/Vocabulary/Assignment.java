package ch.cpnv.angrywirds.Vocabulary;

import java.util.ArrayList;

import ch.cpnv.angrywirds.AngryWirds;
import ch.cpnv.angrywirds.Providers.VoxData;

import static ch.cpnv.angrywirds.Providers.VoxData.assignments;

public class Assignment {
    private int assignment_id;
    private int vocabulary_id;
    private String title;
    private boolean result;

    //private ArrayList<Word> words;

    // Getter
    public int getAssignment_id() {
        return assignment_id;
    }

    public int getVocabulary_id(){ return vocabulary_id;}

    public String getTitle(){return title;}

    public boolean getResult() {return result;}

    // Construct
    public Assignment(int assignment_id, int vocabulary_id, String title, boolean result)
    {
        this.assignment_id = assignment_id;
        this.vocabulary_id = vocabulary_id;
        this.title = title;
        this.result = result;
    }



}
