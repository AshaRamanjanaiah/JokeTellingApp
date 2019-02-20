package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.android.displayjokes.DisplayJokeActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String JOKE = "joke";

    ProgressBar mProgressbar;
    Button mTellJokeButton;

    // create Interstitial ad object
    private InterstitialAd mInterstitialAd;

    @Nullable private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);
        mTellJokeButton = (Button) findViewById(R.id.bt_tell_joke);

        mInterstitialAd = new InterstitialAd(this);
        // using test Ads unit ID
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_id));

        //create Ad request
        AdRequest adRequest =  new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        //load and Ad
        mInterstitialAd.loadAd(adRequest);

        mTellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show the Ad
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d(TAG, "The interstitial wasn't loaded yet.");
                }
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

