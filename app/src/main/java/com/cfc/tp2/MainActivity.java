package com.cfc.tp2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final String BROADCAST = "";
    private Button buttonLevel = null;
    private Button buttonScores = null;
    private Button buttonParam = null;
    private MediaPlayer buttonPressed=null;
    private ImageView boardGame=null;
    private GlobalData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLevel = (Button) findViewById(R.id.buttonLevel);
        buttonScores = (Button) findViewById(R.id.buttonScores);
        buttonParam = (Button) findViewById(R.id.buttonParam);
        data = GlobalData.getInstance();
        buttonPressed = MediaPlayer.create(this, R.raw.press_button);

        boardGame = (ImageView) this.findViewById(R.id.boardGame);

     }

     @Override
    protected void onStart(){
        super.onStart();

        // Start game
        buttonLevel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(data.getPlaySound()){buttonPressed.start();}
                Intent intent = new Intent(MainActivity.this, Levels.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        // Parameters page
         buttonParam.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View view){
                 if(data.getPlaySound()){buttonPressed.start();}
                 Intent intent = new Intent(MainActivity.this, Parameters.class);
                 MainActivity.this.startActivity(intent);
                 finish();
             }
         });

         // Scores board
         buttonScores.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View view){
                 if(data.getPlaySound()){buttonPressed.start();}
                 Intent intent = new Intent(MainActivity.this, Score_board.class);
                 intent.putExtra("levelNumber2",1);
                 MainActivity.this.startActivity(intent);
                 finish();
             }
         });

         Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_rotate_center_point);
         boardGame.startAnimation(animation);
     }

}
