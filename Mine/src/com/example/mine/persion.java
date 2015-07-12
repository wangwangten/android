package com.example.mine;

import java.util.HashSet;



public class persion implements Comparable<persion>{
		public int x;
		public int y;
		persion(int x,int y){
			this.x=x;
			this.y=y;
		}
		
		public int getd(){
			return x;
		}
		@Override

		public int compareTo(persion another) {
			// TODO Auto-generated method stub
			if(x==another.x&&y==another.y)return 0;
			else if(x+y>another.x+another.y)return 1;
			else return -1;
		}
		
		public static void main(){
			HashSet <persion> safeFields=new HashSet <persion>();
			safeFields.add(new persion(1,1));
			System.out.println(safeFields.contains(new persion(1,1)));
		}
	}