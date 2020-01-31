package com.cfc.tp2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.Locale;

public class Parameters extends AppCompatActivity {


    private Button buttonBack = null;
    // Commun class for activity contains data
    private GlobalData data;
    private Button buttonChangelangue = null;
    private Button buttonChangeMusique = null;
    private Button buttonSoundOnnOff = null;
    private Button buttonVibration = null;
    private int choixLangue;
    private int choixmusique ;
    private int sound;
    private int vibration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parameters);
        loadlocale();
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonChangeMusique = (Button) findViewById(R.id.textView3);
        buttonChangelangue = (Button) findViewById(R.id.buttonChangelangue);
        buttonSoundOnnOff = (Button) findViewById(R.id.ButtonSound) ;
        buttonVibration = (Button) findViewById(R.id.buttonVibration) ;


        data = GlobalData.getInstance();

        buttonChangelangue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //show AlertDialog to display list of languages, one can be selected
                showChangeLangueDialog();
            }
        });

        buttonChangeMusique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show AlertDialog to display list of music, one can be selected
                showChangeMusicDialog();
            }
        });
        buttonSoundOnnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show AlertDialog to display setting sound, one can be selected (On/Off)
                showSoundDialog();
            }
        });

        buttonVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show AlertDialog to display setting vibration, one can be selected (On/Off)
                showVibrationDialog();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MenuActivity = new Intent(Parameters.this, MainActivity.class);
                startActivity(MenuActivity);
                finish();
            }
        });

    }
    //dialog box for setting language (Fr/En)
    private void showChangeLangueDialog(){
        //array list of languages to display in AlertDialog
        final  String[] listLangue = {"Français", "English"};
        AlertDialog.Builder  mBuilder = new AlertDialog.Builder(Parameters.this);
        mBuilder.setTitle(getString(R.string.choix_des_langues));
        mBuilder.setSingleChoiceItems(listLangue,choixLangue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //French
                if(i ==0){
                    setLocal("fr");
                    recreate();

                }
                //English
                else{
                    setLocal("en");
                    recreate();
                }
                //dismiss alert dialog when languages selected
                dialogInterface.dismiss();

            }
        });
        AlertDialog mDialog =mBuilder.create();
        //show alert dialog
        mDialog.show();

    }

    private void setLocal(String langue) {
        Locale locale = new Locale(langue);
        //checked items alert dialog --> 0: french , 1:english
        if(langue=="en")
            choixLangue=1;
        else
            choixLangue=0;
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale ;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        //save data de shared preference
        SharedPreferences.Editor editor = getSharedPreferences("settings",MODE_PRIVATE).edit();
        editor.putString("my_lang",langue);
        editor.apply();
    }
    //load languages saved in shred preferences
    public void loadlocale(){
        SharedPreferences prefs = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String langues = prefs.getString("my_lang","");
        setLocal(langues);

    }
    //dialog box for setting music (choice music/OFF)
    private void showChangeMusicDialog(){
        //array list of music to display in AlertDialog
        final  String[] listmusique = {"A Himitsu - Adventures [Argofox]", "We Are Here by Declan DP","Warp Room - The Time Twister Extended by AlbirdVampPrince","reNovation by airtone","Off"};

        AlertDialog.Builder  mBuilder = new AlertDialog.Builder(Parameters.this);
        mBuilder.setTitle(getString(R.string.choix_des_musique));
        //checked items --> 0: adventures , 1:wearehere , 2: timetwister , 3:game_music2 , 4:music Off
        if(data.getMusique()=="adventures" && data.getPlayMusic())
            choixmusique=0;
        else if(data.getMusique()=="wearehere" && data.getPlayMusic())
            choixmusique=1;
        else if(data.getMusique()=="timetwister" && data.getPlayMusic())
            choixmusique=2;
        else if(data.getMusique()=="game_music2" && data.getPlayMusic())
            choixmusique=3;
        else
            choixmusique=4;


        mBuilder.setSingleChoiceItems(listmusique, choixmusique, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //adventures music
                if(i ==0){
                    data.setMusique("adventures");
                    data.setPlayMusic(true);

                }
                //wearehere music
                if(i==1){
                    data.setMusique("wearehere");
                    data.setPlayMusic(true);

                }
                //timewister music
                if(i==2){
                    data.setMusique("timetwister");
                    data.setPlayMusic(true);

                }
                //game_music2 music
                if(i==3){
                    data.setMusique("game_music2");
                    data.setPlayMusic(true);

                }
                //music off
                if(i==4){
                    data.setPlayMusic(false);

                }
                //dismiss alert dialog when music selected
                dialogInterface.dismiss();

            }
        });

        AlertDialog mDialog =mBuilder.create();
        //show alert dialog
        mDialog.show();

    }
    //dialog box for setting sound (ON/OFF)
    private void showSoundDialog(){
        //array list of choice  to display in AlertDialog
        final  String[] listLangue = {"On", "Off"};
        AlertDialog.Builder  mBuilder = new AlertDialog.Builder(Parameters.this);
        mBuilder.setTitle(getString(R.string.paramètre_de_son));
        //checked items --> 0: On , 1:false
        if(data.getPlaySound())
            sound=0;
        else
            sound=1;
        mBuilder.setSingleChoiceItems(listLangue, sound, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //sound On
                if(i ==0){
                    data.setPlaySound(true);

                }
                //sound off
                else{
                    data.setPlaySound(false);

                }
                //dismiss alert dialog when setting sound selected
                dialogInterface.dismiss();

            }
        });
        AlertDialog mDialog =mBuilder.create();
        //show alert dialog
        mDialog.show();

    }
    //dialog box for setting vibration (ON/OFF)
    private void showVibrationDialog(){
        //array list of choice  to display in AlertDialog
        final  String[] listLangue = {"On", "Off"};
        AlertDialog.Builder  mBuilder = new AlertDialog.Builder(Parameters.this);
        mBuilder.setTitle(getString(R.string.paramètre_de_vibration));
        //checked items --> 0: On , 1:false
        if(data.getVibration())
            vibration=0;
        else
            vibration=1;
        mBuilder.setSingleChoiceItems(listLangue, vibration, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //vibration on
                if(i ==0){
                    data.setVibration(true);

                }
                //vibration off
                else{
                    data.setVibration(false);

                }
               //dismiss alert dialog when setting vibration selected
                dialogInterface.dismiss();

            }
        });
        AlertDialog mDialog =mBuilder.create();
        //show alert dialog
        mDialog.show();

    }

}