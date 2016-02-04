package com.roman;

import android.graphics.RectF;

public class Ball {
	
	RectF ball ; 
	int redious = 30;
//	int screenX;
//	int screenY;
	int velocityX;
	int velocityY;
	public Ball(int screenX , int screenY){
//		this.screenX = screenX;
//		this.screenY = screenY;
		//ball = new RectF(230,700,260,670);
		velocityX = 100;
		velocityY = -200;
		ball = new RectF(screenX/2 - 15 , screenY-100, screenX/2 -15 + redious , screenY- 100 - redious);
	}
	
	public RectF getBall(){
		return ball;
	}
	public void animation(double fps){
		
		ball.left += (float) (velocityX/fps);
		ball.top += (float) (velocityY/fps);
		ball.right = ball.left + redious;
		ball.bottom = ball.top - redious;
	}
	
    public void reverseYVelocity(){
    	velocityY = -velocityY;
    }

    public void reverseXVelocity(){
    	velocityX = - velocityX;
    }
    
    public boolean intersect(float left, float top, float right, float bottom){
    	if(ball.intersect(left,top,right,bottom)) return true;
    	else return false;
    }
}
