package com.example.android.jokes;

import java.util.Random;

public class MyJokes {

    private final String[] jokes = {
            "This is a funny joke",
            "This is also a funny joke",
            "And this one too!"
    };

    public String getJokes(){
        int index = new Random().nextInt(jokes.length);
        return jokes[index];
    }
}
