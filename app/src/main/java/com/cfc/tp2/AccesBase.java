package com.cfc.tp2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

import java.util.Arrays;

// DATABASE FOR SCORES
public class AccesBase {

    private String nomBase = "scores.sqlite";
    private int versionbase = 1;
    private MySQLiteOpenHelper accesBD;
    private SQLiteDatabase bd;
    private Integer[] scores = new Integer[10];
    private String[] names = new String[10];

    //acess base MySQL
    public AccesBase(Context context) {
        accesBD = new MySQLiteOpenHelper(context, nomBase, null, versionbase);
    }

    //Add a score after a game
    public void ajout(int game, String name, int score, int timerValue) {
        // Writable access to the data base
        bd = accesBD.getWritableDatabase();
        // SQL request creation
        String req = "insert into scores (id, game, nom, score, timerValue) values ";
        req += "(" + getSizeBD() + "," + game + ",\'" + name + "\'," + score+ "," + timerValue + ")";
        // SQL request execution
        bd.execSQL(req);
        bd.close();
    }

    public void readScore(int nbGame, int timerValue) {
        //Readable access to the database
        bd = accesBD.getReadableDatabase();
        //Reinitialize names and scores data
        Arrays.fill(names, null);
        Arrays.fill(scores, null);
        // Scores search by game type and timer
        String req = "select * from scores where game="+nbGame+" and timerValue="+ timerValue +" order by score ";
        Cursor cursor = bd.rawQuery(req, null);
        int j=0;
        //Cursor placement at the beginning of the base
        cursor.moveToFirst();
        //this checks to make sure you don't have an empty set
        if (!cursor.isAfterLast()) {
            do{
                //Get the 10 first results
                if(j<10) {
                    String indexName = cursor.getString(2);
                    int indexScore = cursor.getInt(3);
                    names[j] = indexName;
                    scores[j]= indexScore;
                    j++;
                }
            } while(cursor.moveToNext());
        } else {
            Log.v("MyTag", "There is no data");
        }

    }

    public int getSizeBD()
    {
        //Readable access to the database
        bd = accesBD.getReadableDatabase();
        //SQL request creation
        String req = "select * from scores";
        Cursor cursor = bd.rawQuery(req, null);
        int count=0;
        cursor.moveToFirst();
        //this checks to make sure you don't have an empty set
        if (!cursor.isAfterLast()) {
            do {
                 count++;
            }while(cursor.moveToNext());
        } else {
            Log.v("MyTag", "There is no data");
        }
        return count;

    }


    public void razBD()
    {
        bd = accesBD.getWritableDatabase();
        String req = "delete from scores";
        bd.execSQL(req);
        bd.close();
    }

    public Integer[] getScores()
    {
        return scores;
    }

    public String[] getNames()
    {
        return names;
    }

}
