package com.roman;

import android.graphics.RectF;

public class Brick {
	
	RectF brick;
	
	private boolean isVisible;
	
	public Brick(int row, int column, int width, int height){
        int padding = 1;
        isVisible = true;
        brick = new RectF(column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);
	}
	
	public RectF getBrick(){
		return brick;
	}
    public void setInvisible(){
        isVisible = false;
    }
 
    public boolean getVisibility(){
        return isVisible;
    }
	
}
