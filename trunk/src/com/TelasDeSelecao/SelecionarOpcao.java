package com.TelasDeSelecao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.MaoNaCorda.BitmapManager;
import com.MaoNaCorda.ImageManager;
import com.example.servidorecliente.MainActivity;
import com.example.servidorecliente.Servidor;

public class SelecionarOpcao extends View
{
	private Bitmap background;
	private Bitmap instrucoes;
	private Bitmap [] options;
	private Rect voltar;
	Paint paint;
	View fase01;
	Activity activity;
//	BitmapManager bitmaps;
	ImageManager img;
	
	private Rect rectFundo = new Rect();
	private Rect rectBGMenu = new Rect();
	
	public SelecionarOpcao(Context context) 
	{	
		super(context);
		
		setFocusableInTouchMode(true);
		setClickable(true);
		setLongClickable(true);
	
		paint = new Paint();
		activity = (Activity) context; 
		
		img = new ImageManager(context);
		
		background = img.ImageManager("BG_Mao.png");
		instrucoes = img.ImageManager("instrucoes_quadro.png");

		// TODO Auto-generated constructor stub
	}
	
	public void draw(Canvas canvas)
	{
		super.draw(canvas);

		rectFundo.set(0,0,getWidth(),getHeight());
		rectBGMenu.set(getWidth()/25,getHeight()/25,(int)(getWidth()/1.04f),(int)(getHeight()/1.05));
		
		canvas.drawBitmap(background, null, rectFundo, paint);
		canvas.drawBitmap(instrucoes, null, rectBGMenu, paint);
}
	
	public boolean onTouchEvent(MotionEvent event) 
	{	
		if (event.getAction() == MotionEvent.ACTION_DOWN) 
		{
			Log.i(MainActivity.TAG, "Entrou no action down");
		}
		
		if (event.getAction() == MotionEvent.ACTION_MOVE) 
		{
			Log.i(MainActivity.TAG, "Entrou no action move");
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP) 
		{
			Log.i(MainActivity.TAG, "Entrou no action up");
			int a = (int)event.getX();
			int b = (int)event.getY();

/*			if(rectFundo.contains(a,b))
			{
				activity.finish();
			}
	*/		
			
			
		}
		
		return super.onTouchEvent(event);
	}
	
	public void onBackPressed()
	{
		//activity.finish();
	}
	
}
