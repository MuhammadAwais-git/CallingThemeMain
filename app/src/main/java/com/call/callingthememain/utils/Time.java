package com.call.callingthememain.utils;

public class Time {
    private int freq;

    public long getSleepTime() {
        return (long) Math.floor((11-freq)*100);
    }

    public void setSleepTime(int freq) {
        this.freq = freq;
    }
}
