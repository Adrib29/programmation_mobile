package com.cfc.tp2;


import android.content.Intent;

public class GlobalData {

    private static GlobalData instance=null;
    private boolean playMusic=true; // 0-> NO  1--> YES
    private int modeGame; // 0-> Normal   1-> Timer
    private int timeValue;
    private boolean vibration=true; // 0-> NO  1--> YES
    private boolean playSound=true; // 0-> NO  1--> YES
    private boolean timerFinished;
    private String musique="adventures";

    public final static GlobalData getInstance(){
        //Use of a unic GlobalData for all the activites with Singleton Patern
        if(GlobalData.instance == null) {
            synchronized(GlobalData.class){
                if(GlobalData.instance==null){
                    GlobalData.instance = new GlobalData();
                }
            }
        }
        return GlobalData.instance;
    }

    private GlobalData() {


    }

    public boolean getTimerFinished() {
        return timerFinished;
    }

    public void setTimerFinished(boolean _timerFinished) {
        this.timerFinished = _timerFinished;
    }

    public boolean getPlayMusic() {
        return playMusic;
    }

    public void setPlayMusic(boolean _playMusic) {
        this.playMusic = _playMusic;
    }

    public boolean getVibration() {
        return vibration;
    }

    public void setVibration(boolean _vibration) {
        this.vibration = _vibration;
    }

    public void setPlaySound(boolean _playSound) {
        this.playSound = _playSound;
    }

    public boolean getPlaySound() {
        return playSound;
    }

    public int getModeGame() {
        return modeGame;
    }

    public void setModeGame(int _modeGame) {
        this.modeGame = _modeGame;
    }

    public int getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(int _timeValue) {
        this.timeValue = _timeValue;
    }

    public String getMusique() {
        return musique;
    }

    public void setMusique(String _musique) {
        this.musique = _musique;
    }

}
