package com.example.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnCreateContextMenuListener;
import android.widget.VideoView;

public class videoPlayActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);
		VideoView myVideoView=(VideoView)findViewById(R.id.videoV);
		myVideoView.setVideoPath("/data/1.mp4");
		myVideoView.start();
	}
	
}
