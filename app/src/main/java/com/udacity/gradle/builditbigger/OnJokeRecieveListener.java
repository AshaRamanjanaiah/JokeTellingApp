package com.udacity.gradle.builditbigger;

public interface OnJokeRecieveListener {
    void onStartListener();
    void onFinishListener(String joke);
    void onFailedListener();
}
