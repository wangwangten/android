package com.example.mine;


import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class NewgameActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new mineView(this,13,13));

		
	}
	
	public void  operate(mineView myMineView,int i,int j){
		Log.d("Dialog", "begin");
		Dialog operateDialog =new operateKeyboard(this,myMineView,i,j);
		operateDialog.show();
		Log.d("Dialog", "end");
	}
	
	

}
