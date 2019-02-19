package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.android.displayjokes.DisplayJokeActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String JOKE = "joke";

    ProgressBar mProgressbar;
    Button mTellJokeButton;

    @Nullable private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);
        mTellJokeButton = (Button) findViewById(R.id.bt_tell_joke);

        mTellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke() {

        EndpointsAsyncTask.getInstance(new OnJokeRecieveListener() {
            @Override
            public void onStartListener() {
                mProgressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinishListener(String joke) {
                mProgressbar.setVisibility(View.GONE);
                startDisplayActivity(joke);
            }

            @Override
            public void onFailedListener() {
                mProgressbar.setVisibility(View.GONE);
            }
        },mIdlingResource );

    }

    public void startDisplayActivity(String joke){
        Intent intent = new Intent(this, DisplayJokeActivity.class);
        intent.putExtra(JOKE, joke);
        startActivity(intent);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

}

