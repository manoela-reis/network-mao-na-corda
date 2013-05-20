package com.MaoNaCorda;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Puxando extends View{
	
	
	int q ;
	int r;
	Boolean possivel =false;
	static Rect atual=new Rect();

	
	public Puxando(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public boolean onTouchEvent(MotionEvent event) 
	{	
		if (event.getAction() == MotionEvent.ACTION_DOWN) 
		{
			Log.i("foi", "down baby down !! ");
			q = (int)event.getRawX();
			r = (int)event.getRawY();
			if(atual.contains(q, r)){
			possivel=true;
			}
			else{
				possivel=false;
			}

		}
		

		if (event.getAction() == MotionEvent.ACTION_MOVE) 
		{
			int a = (int)event.getRawX();
			int b = (int)event.getRawY();
			if(atual.contains(a, b)){
				aplicarForca(1);
			}
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP) 
		{
			Log.i("foi", "UP !!!");
			int a = (int)event.getRawX();
			int b = (int)event.getRawY();
			
			if(possivel){
				if(a-q >=4){
				
					aplicarForca(10);
				}
				else{
					possivel=false;
				}
			
			}
			
		}
		return super.onTouchEvent(event);
	}
		private void aplicarForca(int i) {
				
				Game.positionX+=i;
				
				// TODO Auto-generated method stub
				
			}

	

}
