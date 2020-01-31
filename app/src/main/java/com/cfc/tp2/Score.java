package com.cfc.tp2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Score extends AppCompatActivity {
    private TextView nbPaws = null;
    private TextView textSpendTime=null;
    private EditText name = null;
    private Button submit = null;
    private int levelNumber, minutes, secondes;
    private long tempsRestant;
    private AccesBase accesbd;
    private GlobalData data;
    private MediaPlayer buttonPressed=null;
    public int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
        buttonPressed = MediaPlayer.create(this, R.raw.press_button);
        accesbd = new AccesBase(this);
        data = GlobalData.getInstance();
        Intent intent = getIntent();
        if (intent != null) {
            score = intent.getIntExtra("pawns", 0);
            levelNumber = intent.getIntExtra("levelNumber", 0);
            if(data.getModeGame()==1){
                tempsRestant = intent.getLongExtra("tempsRestant",0);
            }
        }
        minutes = (int)tempsRestant / 60000;
        secondes = (int)tempsRestant%60000 /1000;
        nbPaws = (TextView) findViewById(R.id.score);
        submit = (Button) findViewById(R.id.submit);
        name = (EditText) findViewById(R.id.name);
        textSpendTime = (TextView) findViewById(R.id.textSpendTime);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(data.getModeGame()==1)
        {
            textSpendTime.setVisibility(View.VISIBLE);
            textSpendTime.setText(String.format(getString(R.string.Temps_restant)+"%d min et %d sec", minutes, secondes));
        }else{
            textSpendTime.setVisibility(View.GONE);
        }
        nbPaws.setText(String.format(getString(R.string.Billes_restantes)+"%d", score));



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().matches(""))
                {}
                else{
                if(data.getPlaySound()){buttonPressed.start();}
                // Add score to data base
                accesbd.ajout(levelNumber, name.getText().toString(), score, data.getTimeValue());
                Intent intent2 = new Intent(Score.this, Score_board.class);
                intent2.putExtra("levelNumber2", levelNumber);
                Score.this.startActivity(intent2);
                finish();}
            }
        });


    }

}

