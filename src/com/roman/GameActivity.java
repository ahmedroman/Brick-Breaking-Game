package com.roman;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class GameActivity extends Activity {
    /** Called when the activity is first created. */
	
	
	BreakoutView breakoutView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        breakoutView = new BreakoutView(this);
        setContentView(breakoutView);
    }
    
    class BreakoutView extends SurfaceView implements Runnable{

    	Thread gameThread = null;
    	SurfaceHolder ourHolder;
    	Ball ball; 
    	Paddle paddle;
        Brick[] bricks = new Brick[200];
        int numBricks = 0;
    	int count = 3;
    	Canvas canvas;
        Paint paint;
        boolean isPlaying;
        int screenX;
        int screenY;
    	boolean paused = true;
    	long timeThisFrame;
    	long fps;    	
    	boolean die = true;
    	Context context;
    	
		public BreakoutView(Context context) {
			super(context);
			this.context = context;
            ourHolder = getHolder();
            paint = new Paint();
            Display display = getWindowManager().getDefaultDisplay(); 
            screenX = display.getWidth();  
            screenY = display.getHeight();
            
            ball = new Ball(screenX, screenY);
            paddle = new Paddle(screenX, screenY);
            
            int brickWidth = screenX / 6;
            int brickHeight = screenY / 20;
 
            numBricks = 0;
            for(int column = 0; column < 6; column ++ ){
                for(int row = 0; row < 3; row ++ ){
                    bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                    numBricks ++;
                }
            }
            
		}
		
		@Override
		public void run() {
			while(isPlaying){
				long startFrameTime = System.currentTimeMillis();
				
					if(!paused){
	                	update();
	                }	
				
				if(die){
					gameCanvas();	
				}
				
				timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame >= 1) {
                    fps = 1000 / timeThisFrame;
                }
			}
		}
		
		public void update(){
			ball.animation(fps);
			
	         // Check for ball colliding with a brick
            for(int i = (numBricks-1); i > 0; i--){
 
                if (bricks[i].getVisibility()){
// 
//                    if(RectF.intersects(bricks[i].getBrick(), ball.getBall())) {
//                        bricks[i].setInvisible();
//                        ball.reverseYVelocity();
//                        break;
//                    }
                	
                    if((ball.getBall().centerX() >= bricks[i].getBrick().left)
                    		&& (ball.getBall().centerX() <= bricks[i].getBrick().right)
                    		&& (ball.getBall().centerY() >= bricks[i].getBrick().top)
                    		&& (ball.getBall().centerY() <= bricks[i].getBrick().bottom)) {
                        bricks[i].setInvisible();
                        ball.reverseYVelocity();
                        break;
                    }
                }
            }
			
            if((ball.getBall().centerX() >= paddle.left()) && (ball.getBall().centerX() <= paddle.right())&&
            		((ball.getBall().centerY()-30) <= paddle.top()) && ((ball.getBall().centerY()+30) >= paddle.bottom())) {
                ball.reverseYVelocity();
 
            }
			
            if(ball.getBall().bottom > screenY){
                //die = false;
            	//pause();
            	//resume();
            	count --;
            	//Toast.makeText(context, "You have "+ count-- +" life reamining", Toast.LENGTH_SHORT);
            	ball.reverseYVelocity();
            }
            else if(ball.getBall().top < 0){
                ball.reverseYVelocity();
            }
            else if(ball.getBall().left <= 0){
                ball.reverseXVelocity(); 
            }
            else if(ball.getBall().right > screenX-10){
                ball.reverseXVelocity();
            }
		}
		
    	public void gameCanvas(){
            if (ourHolder.getSurface().isValid()) {
            	canvas = ourHolder.lockCanvas();
            	Paint p = new Paint();
            	
                paint.setColor(Color.BLACK);
                canvas.drawColor(Color.BLUE);
                
                
//            	canvas.drawPaint(p);
//            	p.setColor(Color.CYAN);
//            	p.setTextSize(90);
//            	canvas.drawText("Faaa", 100 , 200, p);
//                
            	
            	canvas.drawOval(ball.getBall() , paint);
            	paint.setColor(Color.GREEN);
            	canvas.drawRect(paddle.getPaddle() , paint);
            	paint.setColor(Color.WHITE);

            	for(int i = 0; i < numBricks; i++){
                    if(bricks[i].getVisibility()) {
                        canvas.drawRect(bricks[i].getBrick(), paint);
                    }
                }
            	
            	ourHolder.unlockCanvasAndPost(canvas);
            }
    	}
    	
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
 
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            	
                 case MotionEvent.ACTION_MOVE:
                	paused = false;
                	if((motionEvent.getX() < screenX-100) || (motionEvent.getX() > 0)){
                		paddle.movingPaddle(motionEvent.getX());
                	}                    
                    break;
            }
            return true;
        }
    	
    	
        public void pause() {
        	isPlaying = false;
            try {
                gameThread.join();
            	} catch (InterruptedException e) {
            }
 
        }
 
        public void resume() {
        	die = true;
        	isPlaying = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
        
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        breakoutView.resume();
    }
 
    @Override
    protected void onPause() {
        super.onPause();
        breakoutView.pause();
    }
    
}