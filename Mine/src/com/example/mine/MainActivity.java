package com.example.mine;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Notification.Action;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends android.app.ActionBar implements OnClickListener{

	public MediaPlayer mp;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mp=MediaPlayer.create(this, R.raw.ydgqq9);
        mp.start();
        View startButton = findViewById(R.id.newGameButton);
        startButton.setOnClickListener(this);
        View continueButton= findViewById(R.id.continueButton);
        continueButton.setOnClickListener(this);
        View aboutButton=findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(this);
       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.main_settings) {
        	startActivity(new Intent(this,Pref.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.newGameButton:
			Intent newgameIntent=new Intent(this,NewgameActivity.class);
			startActivity(newgameIntent);
			break;
		case R.id.continueButton:
			Intent myintent=new Intent(this,videoPlayActivity.class);
			startActivity(myintent);
			break;
			
		case R.id.aboutButton:
			Intent aboutIntent=new Intent(this,AboutActivity.class);
			startActivity(aboutIntent);
			break;
		
		}
	}

    /**
     * A placeholder fragment containing a simple view.
     */
    protected void onPause(){
    	super.onPause();
    	mp.pause();
    }
    
    protected void onResume(){
    	super.onResume();
    	mp.start();
    	}
}
