package com.example.android.displayjokes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    private TextView mDisplayJokeTextview;
    private static final String JOKE = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        mDisplayJokeTextview = (TextView) findViewById(R.id.tv_display_joke);

        Intent recivedIntent = getIntent();

        if (recivedIntent.hasExtra(JOKE)) {
            String joke = getIntent().getStringExtra(JOKE);


            if (joke != null && !joke.isEmpty()) {
                mDisplayJokeTextview.setText(joke);
            }
        }
    }
}
