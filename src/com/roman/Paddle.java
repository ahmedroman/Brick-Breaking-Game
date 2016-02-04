package com.roman;

import android.graphics.RectF;

public class Paddle {
	RectF paddle;
	int height = 20;
	int width = 100;
	int screenX; 
	int screenY;
	
	public Paddle(int screenX , int screenY){
		this.screenX = screenX;
		this.screenY = screenY;
		paddle = new RectF(screenX/2-50, screenY-80, screenX/2-50 + width , screenY - 80 - height);
	}
	
	public RectF getPaddle(){
		return paddle;
	}
	
	public void movingPaddle(String position){
		if(position.equals("Left")) {
			paddle.left ++ ;
			paddle.right -- ;
		}
			
		else if(position.equals("Right")) {
			paddle.right ++;
			paddle.left --;
		}	
	}
	public void movingPaddle(float position){
		if(position+width < screenX){
			paddle.left = position;
			paddle.right = position + width;	
		}
	}
	public float left(){
		return paddle.left;
	}
	public float right(){
		return paddle.right;
	}
	public float top(){
		return paddle.top;
	}
	public float bottom(){
		return paddle.bottom;
	}

	

}
