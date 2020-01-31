package com.cfc.tp2;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Levels extends AppCompatActivity {

    private Button buttonLevel1 = null;
    private Button buttonLevel2 = null;
    private Button buttonLevel3 = null;
    private Button buttonLevel4 = null;
    private Button buttonLevel5 = null;
    private Button buttonBack = null;
    private Button buttonNormalMode = null;
    private Button buttonTimerMode = null;
    private Button[] buttonTime = null;
    private TextView textChooseTimer=null;
    private ImageView imageBois=null;
    private MediaPlayer buttonPressed=null;
    private GlobalData data;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level);
        buttonTime = new Button[4];
        buttonLevel1 = (Button)findViewById(R.id.buttonLevel1);
        buttonLevel2 = (Button)findViewById(R.id.buttonLevel2);
        buttonLevel3 = (Button)findViewById(R.id.buttonLevel3);
        buttonLevel4 = (Button)findViewById(R.id.buttonLevel4);
        buttonLevel5 = (Button)findViewById(R.id.buttonLevel5);
        buttonBack = (Button)findViewById(R.id.buttonBack);
        buttonNormalMode = (Button)findViewById(R.id.normal_mode);
        buttonTimerMode = (Button)findViewById(R.id.timer_mode);
        buttonTime[0]=(Button)findViewById(R.id.button1min);
        buttonTime[1]=(Button)findViewById(R.id.button3min);
        buttonTime[2]=(Button)findViewById(R.id.button5min);
        buttonTime[3]=(Button)findViewById(R.id.button10min);
        textChooseTimer = (TextView) findViewById(R.id.textChooseTimer);
        imageBois = (ImageView) findViewById(R.id.imageFondTimer);
        buttonPressed = MediaPlayer.create(this, R.raw.press_button);
        data = GlobalData.getInstance();
        refreshElements();

    }

    @Override
    protected void onResume(){
        super.onResume();
        // Mode game selection 1 with timer 0 without timer
        buttonNormalMode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                data.setModeGame(0);
                refreshElements();
            }
        });

        buttonTimerMode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                data.setModeGame(1);
                refreshElements();
            }
        });

        // Timer selection
        buttonTime[0].setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                data.setTimeValue(1);
                refreshElements();
            }
        });

        buttonTime[1].setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                data.setTimeValue(3);
                refreshElements();
            }
        });

        buttonTime[2].setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                data.setTimeValue(5);
                refreshElements();
            }
        });

        buttonTime[3].setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                data.setTimeValue(10);
                refreshElements();
            }
        });

        // board selection
        buttonLevel1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(data.getPlaySound()){buttonPressed.start();}
                Intent intent = new Intent(Levels.this, Game.class);
                intent.putExtra("levelNumber", 1);
                Levels.this.startActivity(intent);
                finish();
            }
        });
        buttonLevel2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(data.getPlaySound()){buttonPressed.start();}
                Intent intent = new Intent(Levels.this, Game.class);
                intent.putExtra("levelNumber", 2);
                Levels.this.startActivity(intent);
                finish();
            }
        });
        buttonLevel3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(data.getPlaySound()){buttonPressed.start();}
                Intent intent = new Intent(Levels.this, Game.class);
                intent.putExtra("levelNumber", 3);
                Levels.this.startActivity(intent);
                finish();
            }
        });
        buttonLevel4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(data.getPlaySound()){buttonPressed.start();}
                Intent intent = new Intent(Levels.this, Game.class);
                intent.putExtra("levelNumber", 4);
                Levels.this.startActivity(intent);
                finish();
            }
        });
        buttonLevel5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(data.getPlaySound()){buttonPressed.start();}
                Intent intent = new Intent(Levels.this, Game.class);
                intent.putExtra("levelNumber", 5);
                Levels.this.startActivity(intent);
                finish();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(Levels.this, MainActivity.class);
                Levels.this.startActivity(intent);
                finish();
            }
        });
    }

    public void refreshElements()
    {
        //Timer mode,
        if(data.getModeGame()==1){
            //Selected mode is highlighted
            buttonTimerMode.setBackgroundResource(R.drawable.selected);
            buttonNormalMode.setBackgroundColor(00000000);
            imageBois.setVisibility(View.VISIBLE);
            textChooseTimer.setVisibility(View.VISIBLE);


                //Selected button is putt forward
                for(int i =0; i<4;i++){
                    buttonTime[i].setVisibility(View.VISIBLE); //display timer possibilities
                    buttonTime[i].setBackgroundColor(00000000);
                }
                // highlight selected button
                switch(data.getTimeValue()) {
                    case 1:
                        buttonTime[0].setBackgroundResource(R.drawable.selected);
                        break;
                    case 3:
                        buttonTime[1].setBackgroundResource(R.drawable.selected);
                        break;
                    case 5:
                        buttonTime[2].setBackgroundResource(R.drawable.selected);
                        break;
                    case 10:
                        buttonTime[3].setBackgroundResource(R.drawable.selected);
                        break;
                    default: data.setTimeValue(1);
                        buttonTime[0].setBackgroundResource(R.drawable.selected);
                        break;
                }

            //Normal mode
        }else{
            //Normal mode is highlighted
            buttonTimerMode.setBackgroundColor(00000000);
            buttonNormalMode.setBackgroundResource(R.drawable.selected);
            data.setTimeValue(0);
            // the timer possibilities area is deleted
            imageBois.setVisibility(View.GONE);
            textChooseTimer.setVisibility(View.GONE);
            for(int i =0; i<4;i++) {
                buttonTime[i].setVisibility(View.GONE); //hide timers values
            }
        }

    }
}
