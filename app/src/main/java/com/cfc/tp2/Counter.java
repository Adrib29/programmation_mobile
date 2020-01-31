package com.cfc.tp2;

public class Counter {
    //dumb counter that can only count up
    private static final Counter ourInstance = new Counter();
    private static int count = 0;

    public static Counter getInstance() {
        return ourInstance;
    }

    private Counter() {
    }

    public void increment(){
        count++;
    }

    public int getCount(){
        return count;
    }
}