package ch.cpnv.angrywirds.Providers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

import ch.cpnv.angrywirds.Vocabulary.Assignment;
import ch.cpnv.angrywirds.Vocabulary.ListVocabulary;
import ch.cpnv.angrywirds.Vocabulary.Vocabulary;
import ch.cpnv.angrywirds.Vocabulary.Language;
import ch.cpnv.angrywirds.Vocabulary.Word;

public abstract class VoxData {
    public static ArrayList<Language> languages = new ArrayList<Language>();
    public static ArrayList<Vocabulary> vocabularies = new ArrayList<Vocabulary>();
    public static ArrayList<ListVocabulary> listVocabularies = new ArrayList<ListVocabulary>();
    public static ArrayList<Assignment> assignments = new ArrayList<Assignment>();


    private static final String API = "http://voxerver.mycpnv.ch/api/v1/";
    public static final String TOKEN = "*A568F00A30B18DD78B930A4B5C8DF84501449F07";
    public enum Status { unknown, ready, cancelled, nocnx, error }

    public static Status status = Status.unknown;


    //Post the result on the API with a POST method
    static public void submitResults (int assignement, String token, int score) {
        Gdx.app.log("AJAXPOST", "Appel ajax demandé");
        HttpRequestBuilder requestSubmitResults = new HttpRequestBuilder();
        PostAssignmentsDatas datas = new PostAssignmentsDatas(assignement, token, score);
        Net.HttpRequest httpRequestSubmitResults = requestSubmitResults.newRequest()
                .method(Net.HttpMethods.POST)
                .jsonContent(datas)
                .url(API+"result")
                .build();
        Gdx.app.log("AJAXPOST", httpRequestSubmitResults.getContent());
        Gdx.net.sendHttpRequest(httpRequestSubmitResults, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.log("AJAXPOST", "Soumission des résultats");
                Gdx.app.log("AJAXPOST", httpResponse.toString());
            }

            @Override
            public void failed(Throwable t) {
                status = Status.nocnx;
                Gdx.app.log("AJAXPOST", "No connection", t);
            }

            @Override
            public void cancelled() {
                status = Status.cancelled;
                Gdx.app.log("AJAXPOST", "cancelled");
            }
        });
    }

    // GET Assignments
    public static void getAssignments(){
        // Clear the old values
        assignments.clear();
        //Request to API VIA GET METHOD
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl("http://http://voxerver.mycpnv.ch/api/v1/assignments/"+TOKEN);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    Gdx.app.log("asd", "Request Failed");
                    return;
                }

                //GET assignment_id, vocabulary_id, title, result
                try {
                    JsonValue values = new JsonReader().parse(httpResponse.getResultAsString());
                    for (JsonValue value: values.iterator()) {
                        assignments.add(new Assignment(value.getInt("assignment_id"), value.getInt("vocabulary_id"), value.getString("title"), value.getBoolean("result")));
                        status = Status.ready;
                    }
                }
                catch(Exception exception) {
                    exception.printStackTrace();
                    status = Status.error;
                }
            }



            public void failed(Throwable t) {
                status = Status.nocnx;
                Gdx.app.log("asd","Request Failed Completely");
            }

            @Override
            public void cancelled() {
                Gdx.app.log("asd","Request Cancelled");
                status = Status.cancelled;
            }

        });
    }

    //Know if the homework is done or not
    public static boolean getResult(int assignment){
        for (Assignment a:assignments){
            if (a.getAssignment_id() == (assignment))
                return true;
        }
        return false;
    }



    public static void getVoacobulary(){
        // Clear the old values
        vocabularies.clear();
        //Request to API
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl("http://voxerver.mycpnv.ch/api/v1/fullvocs");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    Gdx.app.log("asd", "Request Failed");
                    return;
                }

                try {
                    JsonValue values = new JsonReader().parse(httpResponse.getResultAsString());
                    for (JsonValue value: values.iterator()) {
                        ArrayList<Word> w = new ArrayList<Word>();
                        for(JsonValue word: value.get("Words").iterator())
                            w.add(new Word(word.getInt("mId"),word.getString("mValue1"),word.getString("mValue2")));

                        vocabularies.add(new Vocabulary(value.getInt("mId"), value.getString("mTitle"), value.getInt("mLang1"),value.getInt("mLang2"), w));
                    }
                }
                catch(Exception exception) {
                    exception.printStackTrace();
                    status = Status.error;
                }
            }

            public void failed(Throwable t) {
                status = Status.nocnx;
                Gdx.app.log("asd","Request Failed Completely");
            }

            @Override
            public void cancelled() {
                Gdx.app.log("asd","Request Cancelled");
                status = Status.cancelled;
            }

        });
    }

    public static void getListVocabularies(){
        //Clear the old values
        listVocabularies.clear();
        //Request to API
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl("http://voxerver.mycpnv.ch/api/v1/vocs");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

            public void handleHttpResponse(Net.HttpResponse httpResponse) {


                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    Gdx.app.log("asd", "Request Failed");
                    return;
                }

                //String responseJson = httpResponse.getResultAsString();
                try {
                    JsonValue values = new JsonReader().parse(httpResponse.getResultAsString());
                    for (JsonValue value: values.iterator()){
                        listVocabularies.add(new ListVocabulary(value.getInt("mId"),value.getString("mTitle")));
                        status = Status.ready;
                    }
                } catch (Exception exception) {

                    exception.printStackTrace();
                    status = Status.error;
                }
            }

            public void failed(Throwable t) {
                Gdx.app.log("asd", "Request Failed Completely");
                status = Status.nocnx;
            }

            @Override
            public void cancelled() {
                Gdx.app.log("asd", "Request Cancelled");
                status = Status.cancelled;
            }

        });
    }

    public static void getLanguages() {
        //Clear the old values
        languages.clear();
        //Request to API
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl("http://voxerver.mycpnv.ch/api/v1/languages");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

            public void handleHttpResponse(Net.HttpResponse httpResponse) {


                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    Gdx.app.log("asd", "Request Failed");
                    return;
                }

                //String responseJson = httpResponse.getResultAsString();
                try {
                    JsonValue values = new JsonReader().parse(httpResponse.getResultAsString());
                    for (JsonValue value: values.iterator()){
                        languages.add(new Language(value.getInt("lId"),value.getString("lName")));
                        status = Status.ready;
                    }
                } catch (Exception exception) {

                    exception.printStackTrace();
                    status = Status.error;
                }
            }

            public void failed(Throwable t) {
                Gdx.app.log("asd", "Request Failed Completely");
                status = Status.nocnx;
            }

            @Override
            public void cancelled() {
                Gdx.app.log("asd", "Request Cancelled");
                status = Status.cancelled;
            }

        });
    }

    private static Boolean VocExists(String voc){
        for(Vocabulary v: vocabularies)
            if (v.getTitle().equals(voc)) return true;

        return false;
    }
    //Charger les données du serveur
    public static void load(){
        getVoacobulary();
        getLanguages();
        //getAssignments();  ===> Bug lors de la tentative de connexion.. N'ai pas pu continuer avec ceci
    }



    public static int getLangueId(String langue){
        for (Language l:languages)
            if (l.getLangue().equals(langue))
                return l.getId();
        return 1; // default lang
    }

   // public static int getAssignmentId(String title){
//       for (Assignment a:assignments)
 //           if(a.getAssignment_id().equals(title))
 //               return a.getAssignment_id();
 //       return 1;
  // }

    public static Boolean checkIF_vocExsist_for_langue(int langueId) {
       for(Vocabulary v: vocabularies)
           if (v.getLang1() == langueId)
               return true;
       return false;
    }


}
