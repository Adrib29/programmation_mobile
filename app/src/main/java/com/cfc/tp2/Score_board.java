package com.cfc.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Score_board extends AppCompatActivity {
    private static AccesBase accesbd;
    private Integer[] scores=new Integer[10];
    private Integer[] times=new Integer[10];
    private String[] names=new String[10];
    private TextView[] affiche_scores=new TextView[10];
    private  TextView textMode=null, textPlateau=null;
    private Button buttonNextMode=null, buttonBefMode=null, buttonBefBoard=null, buttonNextBoard=null;
    private Button backMenu=null;
    private int levelNumber, timerValue;
    private GlobalData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores_board);
        data = GlobalData.getInstance();
        Intent intent = getIntent();
        if (intent != null) {
            levelNumber = intent.getIntExtra("levelNumber2", 0);
        }
        accesbd = new AccesBase(this);

        affiche_scores[0]= (TextView) findViewById(R.id.place1);
        affiche_scores[1]= (TextView) findViewById(R.id.place2);
        affiche_scores[2]= (TextView) findViewById(R.id.place3);
        affiche_scores[3]= (TextView) findViewById(R.id.place4);
        affiche_scores[4]= (TextView) findViewById(R.id.place5);
        affiche_scores[5]= (TextView) findViewById(R.id.place6);
        affiche_scores[6]= (TextView) findViewById(R.id.place7);
        affiche_scores[7]= (TextView) findViewById(R.id.place8);
        affiche_scores[8]= (TextView) findViewById(R.id.place9);
        affiche_scores[9]= (TextView) findViewById(R.id.place10);
        textMode = (TextView) findViewById(R.id.textMode);
        textPlateau = (TextView) findViewById(R.id.textPlateau);
        buttonNextMode = (Button) findViewById(R.id.buttonNextMode);
        buttonBefMode = (Button) findViewById(R.id.buttonBefMode);
        buttonNextBoard = (Button) findViewById(R.id.buttonNextBoard);
        buttonBefBoard = (Button) findViewById(R.id.buttonBefBoard);
        backMenu = (Button) findViewById(R.id.buttonMenu);

        timerValue= data.getTimeValue();


    }

    @Override
    public void onResume(){
        super.onResume();
        refreshElements(levelNumber,timerValue);

        // Set the previous level number
        buttonBefBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levelNumber-=1;
                if(levelNumber<1){levelNumber=5;}
                refreshElements(levelNumber,timerValue);
            }
        });

        // // Set the next level number
        buttonNextBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levelNumber+=1;
                if(levelNumber>5){levelNumber=1;}
                refreshElements(levelNumber,timerValue);
            }
        });

        buttonNextMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the next timer Value
                switch (timerValue){
                    case 0:
                        timerValue=1;
                        break;
                    case 1:
                        timerValue=3;
                        break;
                    case 3:
                        timerValue=5;
                        break;
                    case 5:
                        timerValue=10;
                        break;
                    case 10:
                        timerValue=0;
                        break;
                        default:
                            timerValue=0;
                            break;
                }
                refreshElements(levelNumber,timerValue);
                }
        });

        buttonBefMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the previous timer Value
                switch (timerValue){
                    case 0:
                        timerValue=10;
                        break;
                    case 1:
                        timerValue=0;
                        break;
                    case 3:
                        timerValue=1;
                        break;
                    case 5:
                        timerValue=3;
                        break;
                    case 10:
                        timerValue=5;
                        break;
                    default:
                        timerValue=0;
                        break;
                }
                refreshElements(levelNumber,timerValue);
            }
        });

        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Score_board.this, MainActivity.class);
                Score_board.this.startActivity(intent2);
                finish();
            }

        });
    }

    public void refreshElements(int _levelNumber, int _timerValue){


        // Read score depending on the level and timer
        accesbd.readScore(_levelNumber,_timerValue);
        // get the 10 first scores
        scores=accesbd.getScores();
        names=accesbd.getNames();
        for(int i=0;i<scores.length;i++)
        {
            if(names[i] != null && scores[i] != null){
                // Display of the scores obtained
                int position = i+1;
                affiche_scores[i].setText(position+" : "+ names[i]+", "+getString(R.string.Billes_restantes)+ scores[i]);
            }else {
                // If there is less than 10 scores
                affiche_scores[i].setText("");
            }
        }

        // Refresh display of level name
        setTextBoard();
        // Refresh display of timer value
        setTextMode();
    }


    public void setTextBoard(){
        switch (levelNumber){
            case 1:
                textPlateau.setText(getString(R.string.Européen));
                break;
            case 2:
                textPlateau.setText(getString(R.string.Allemand));
                break;
            case 3:
                textPlateau.setText(getString(R.string.assylétrique_3_3_2_2));
                break;
            case 4:
                textPlateau.setText(getString(R.string.anglais));
                break;
            case 5:
                textPlateau.setText(getString(R.string.diamant));
                break;

        }
    }

    public void setTextMode(){

        switch (timerValue){
            case 0:
                textMode.setText(getString(R.string.Sans_chrono));
                break;
            case 1:
                textMode.setText("1 minute");
                break;
            case 3:
                textMode.setText("3 minutes");
                break;
            case 5:
                textMode.setText("5 minutes");
                break;
            case 10:
                textMode.setText("10 minutes");
                break;
        }
    }

}