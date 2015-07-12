package com.example.mine;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

public class operateKeyboard extends Dialog implements android.view.View.OnClickListener {
	int i;
	int j;
	mineView myMineView;
	public operateKeyboard(Context context,mineView myMineView,int i,int j) {
		super(context);
		// TODO Auto-generated constructor stub
		setTitle("²Ù×÷");
		setContentView(R.layout.keyboard);
		View MarkButton= findViewById(R.id.MarkButton);
		View DigButton=findViewById(R.id.DigButton);
		MarkButton.setOnClickListener(this);
		DigButton.setOnClickListener(this); 
		this.i=i;
		this.j=j;
		this.myMineView=myMineView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		
		case R.id.MarkButton:
			myMineView.Mark( i, j);
			dismiss();
			break;
		case R.id.DigButton:
			myMineView.Dig( i, j);
			dismiss();
			break;
		default :
			dismiss();
			
		
		}
	}

}
