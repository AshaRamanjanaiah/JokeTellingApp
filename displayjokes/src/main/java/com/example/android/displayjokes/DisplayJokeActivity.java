package com.example.android.displayjokes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    private TextView mDisplayJokeTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        mDisplayJokeTextview = (TextView) findViewById(R.id.tv_display_joke);

        String joke = getIntent().getStringExtra("joke");

        if(!joke.isEmpty()){
            mDisplayJokeTextview.setText(joke);
        }
    }
}
