package com.example.mine;

import java.security.InvalidAlgorithmParameterException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class mineView extends View {

	int hNum;
	int vNum;
	int cellHeight;
	int cellwidth;
	int height;
	int width;
	int mine[][];
	int hint[][];
	int mark[][];
	double probability=0.1;
	boolean gameOver=false;
	Rect safeField=new Rect(0,0,0,0);
	Rect invalidField=new Rect(0,0,0,0);
	Rect selection=new Rect(0,0, 0, 0);
	
	NewgameActivity myNewgameActivity;
	
	public void setInvalidField(int i, int j){
		invalidField.set(((int)(i))*cellwidth, ((int)(j))*cellHeight, ((int)(i)+1)*cellwidth, ((int)(j)+1)*cellHeight);
	}
	
	public void Mark(int i,int j){
		//未标记变成标记
		if(mark[i][j]==-2)mark[i][j]=-1;
		//已标记则取消标记			
		else if(mark[i][j]==-1)mark[i][j]=-2;
		setInvalidField(i,j);
		invalidate(invalidField);
	}
	
	
	
	public void findSafeFields(int i, int j){
		
		LinkedList  <persion> candidateFields=new LinkedList <persion>();
		candidateFields.add(new persion(i,j));
		HashSet <Integer> safeFields=new HashSet <Integer>();
		
		while(candidateFields.size()>0){
			
			persion currentPersion=(persion)candidateFields.removeFirst();
			Log.d("currentPersion.x", currentPersion.x+"");
			Log.d("currentPersion.y", currentPersion.y+"");
			if(mine[currentPersion.x][currentPersion.y]==0){
				safeFields.add(new Integer(currentPersion.x*hNum+currentPersion.y));
				mark[currentPersion.x][currentPersion.y]=hint[currentPersion.x][currentPersion.y];
				setInvalidField(currentPersion.x,currentPersion.y);
				invalidate(invalidField);
				if(hint[currentPersion.x][currentPersion.y]==0){
					if(currentPersion.x-1>=0&&!safeFields.contains(new Integer((currentPersion.x-1)*hNum+currentPersion.y)))candidateFields.add(new persion(currentPersion.x-1,currentPersion.y));
					if(currentPersion.y-1>=0&&!safeFields.contains(new Integer(currentPersion.x*hNum+(currentPersion.y-1))))candidateFields.add(new persion(currentPersion.x,currentPersion.y-1));
					if(currentPersion.x+1<hNum&&!safeFields.contains(new Integer((currentPersion.x+1)*hNum+currentPersion.y)))candidateFields.add(new persion(currentPersion.x+1,currentPersion.y));
					if(currentPersion.y+1<vNum&&!safeFields.contains(new Integer(currentPersion.x*hNum+currentPersion.y+1)))candidateFields.add(new persion(currentPersion.x,currentPersion.y+1));
					
				}
				
			}
		}
	}
	
	public void Dig(int i, int j){
		if(mine[i][j]==1){
			//invalidate();
			gameOver=true;
			invalidate();
		}else if(mark[i][j]==-2){
			if(hint[i][j]==0){
				
				findSafeFields(i,j);
			}else{
				mark[i][j]=hint[i][j];
				setInvalidField(i,j);
				invalidate(invalidField);
			}
			
		}
	}
	
	
	
	public void generateMine(){
		mine=new int[hNum][vNum];
		hint=new int[hNum][vNum];
		mark=new int[hNum][vNum];//-2为未处理 -1为扫雷标记  0 为空地 数字为hint内容
		for(int i=0;i<hNum;i++){
			for(int j=0;j<vNum;j++){
				mark[i][j]=-2; 
				if(Math.random()<probability)mine[i][j]=1;
				else mine[i][j]=0;
			}
		}
		
		
		for(int i=0;i<hNum;i++){
			for(int j=0;j<vNum;j++){
				int hintNum=0;
				for(int k=i-1;k<=(i+1<hNum?i+1:hNum-1);k++){
					if(k<0)continue;
					
					for(int l=j-1;l<=(j+1<vNum?j+1:vNum-1);l++){
						if(l<0)continue;
						hintNum+=mine[k][l];
					}
				}
				hint[i][j]=hintNum;
			}
		}
	}
	
	public void drawSafeField(Canvas myCanvas){
		for(int i=0;i<hNum;i++){
			for(int j=0;j<vNum;j++){
				
				
				if(mark[i][j]>=0){
					Paint myPaint=new Paint();
					myPaint.setColor(getResources().getColor(R.color.safefieldcolor));
					
					safeField.set(((int)(i))*cellwidth, ((int)(j))*cellHeight, ((int)(i)+1)*cellwidth, ((int)(j)+1)*cellHeight);
					myCanvas.drawRect(safeField, myPaint);
				}
			}
		}
	}
	
	
	public void drawFindHint(Canvas myCanvas){
		for(int i=0;i<hNum;i++){
			for(int j=0;j<vNum;j++){
				
				
				if(mark[i][j]>0){
					Paint myPaint=new Paint();
					myPaint.setColor(Color.RED);
					myPaint.setTextAlign(Align.CENTER);
					myPaint.setTextScaleX(cellwidth/cellHeight);
					myPaint.setTextSize(cellHeight);
					FontMetrics fm =myPaint.getFontMetrics(); 
					
					myCanvas.drawText(hint[i][j]+"", i*cellwidth+cellwidth/2, j*cellHeight+cellHeight/2-fm.ascent/2-fm.descent/2, myPaint);
					
				}
			}
		}
	}
	
	
	public void drawFlag(Canvas myCanvas){
		for(int i=0;i<hNum;i++){
			for(int j=0;j<vNum;j++){
				
				
				if(mark[i][j]==-1){
					Paint myPaint=new Paint();
					myPaint.setColor(Color.RED);
					myPaint.setTextAlign(Align.CENTER);
					myPaint.setTextScaleX(cellwidth/cellHeight);
					myPaint.setTextSize(cellHeight);
					FontMetrics fm =myPaint.getFontMetrics(); 
					
					myCanvas.drawText("F", i*cellwidth+cellwidth/2, j*cellHeight+cellHeight/2-fm.ascent/2-fm.descent/2, myPaint);
					
				}
			}
		}
	}
	public void drawNum(Canvas myCanvas){
		for(int i=0;i<hNum;i++){
			for(int j=0;j<vNum;j++){
				
				if(mine[i][j]==1)drawMine(myCanvas, i,j);
				else drawHint(myCanvas, i,j);
				
			}
		}
	}
	
	public void drawHint(Canvas myCanvas,int x ,int y){
		Paint myPaint=new Paint();
		myPaint.setColor(Color.BLUE);
		myPaint.setTextSize(cellHeight);
		myPaint.setTextAlign(Paint.Align.CENTER);
		myPaint.setTextScaleX(cellwidth/cellHeight);
		FontMetrics fm= myPaint.getFontMetrics();
		
		myCanvas.drawText(hint[x][y]+"", x*cellwidth+cellwidth/2, y*cellHeight+cellHeight/2-fm.ascent/2-fm.descent/2, myPaint);
		
	}
	
	public void drawMine(Canvas myCanvas,int x ,int y){
		Paint minePaint=new Paint();
		minePaint.setTextSize((float)(cellHeight*0.75));
		minePaint.setTextScaleX(cellwidth/cellHeight);
		minePaint.setColor(Color.RED);
		minePaint.setTextAlign(Paint.Align.CENTER);
		FontMetrics fm=minePaint.getFontMetrics();
		
		myCanvas.drawText("M", x*cellwidth+cellwidth/2, y*cellHeight+cellHeight/2-fm.ascent/2-fm.descent/2, minePaint);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getAction()!=MotionEvent.ACTION_DOWN)return super.onTouchEvent(event);
		
		onSelection(event.getX(),event.getY());
		myNewgameActivity.operate(this,(int)(event.getX()/cellwidth),(int)(event.getY()/cellHeight));
		return true;
	}
	
	public void onSelection(double x,double y){
		invalidate(selection);
		
		selection.set(((int)(x/cellwidth))*cellwidth, ((int)(y/cellHeight))*cellHeight, ((int)(x/cellwidth)+1)*cellwidth, ((int)(y/cellHeight)+1)*cellHeight);
		Log.d("x", ((int)(x/cellwidth))*cellwidth+"");
		Log.d("y", ((int)(y/cellHeight)+1)*cellHeight+"");
		Log.d("x1", ((int)(x/cellwidth)+1)*cellwidth+"");
		Log.d("y1", ((int)(y/cellHeight))*cellHeight+"");
		invalidate(selection);
	}
	
	public mineView(Context context,int hNum,int vNum) {
		
		super(context);
		this.hNum=hNum;
		this.vNum=vNum;
		generateMine();
		myNewgameActivity=(NewgameActivity)context;
		
		
		// TODO Auto-generated constructor stub
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		
		 height= h;
		
		 width=w;
		 cellHeight= h/hNum;
			
		 cellwidth=w/vNum;
		 
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	protected void onDraw(Canvas myCanvas){
		setBackgroundColor(getResources().getColor(R.color.backgroundcolor)); 
		Paint light=new Paint();
		light.setColor(getResources().getColor(R.color.light));
		Paint hilite=new Paint();
		light.setColor(getResources().getColor(R.color.hilite));
		
		for(int i=1;i<vNum;i++){
			myCanvas.drawLine(cellwidth*i, 0, cellwidth*i,height,  light);
			myCanvas.drawLine(cellwidth*i+1, 0, cellwidth*i+1,height,  hilite);
			
		}
		for(int i=1;i<hNum;i++){
			myCanvas.drawLine(0, cellHeight*i, width,cellHeight*i,  light);
			myCanvas.drawLine(0, cellHeight*i+1, width,cellHeight*i+1,  hilite);
			
		}
		
		if(gameOver==true)drawNum(myCanvas);
		else{
			
			drawSafeField(myCanvas);
			drawFindHint(myCanvas);
			drawFlag(myCanvas);
		}
		
		
		
		
		
	}
}
