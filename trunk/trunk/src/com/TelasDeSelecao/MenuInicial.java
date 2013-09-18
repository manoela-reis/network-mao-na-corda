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
import com.example.servidorecliente.MainActivity;
import com.example.servidorecliente.Servidor;

public class MenuInicial extends View
{
	private Bitmap fundo;
	private Bitmap background;
	private Bitmap [] options;
	private Rect [] areaOptions;
	Paint paint;
	
	Activity activity;
	BitmapManager bitmaps;

	private Rect rectFundo = new Rect();
	private Rect rectBGMenu = new Rect();
	
	View creditos;
	View instrucoes;
	
	public MenuInicial(Context context) 
	{	
		super(context);
		
		setFocusableInTouchMode(true);
		setClickable(true);
		setLongClickable(true);
	
		paint = new Paint();
		activity = (Activity) context; 
		
		options = new Bitmap[3];
		areaOptions= new Rect[3];
		areaOptions = new Rect[3];

		
		areaOptions[0] = new Rect();
		areaOptions[1] = new Rect();
		areaOptions[2] = new Rect();

		bitmaps = new BitmapManager(context);

		background = BitmapManager.GetInstance().getFundoMenu();
		options[0] = BitmapManager.GetInstance().getOpcaoBatalha();
		options[1] = BitmapManager.GetInstance().getOpcaoInstrucoes();
		options[2] = BitmapManager.GetInstance().getOpcaoCreditos();
		fundo = BitmapManager.GetInstance().getImageFundo();
		// TODO Auto-generated constructor stub
	}
	
	public void draw(Canvas canvas)
	{
		super.draw(canvas);

		rectFundo.set(0,0,getWidth(),getHeight());
		rectBGMenu.set(getWidth()/25,getHeight()/25,(int)(getWidth()/1.04f),(int)(getHeight()/1.1));
		areaOptions[0].set((int)(getWidth()/2.4),(int)(getHeight()/2.66),(int)(getWidth()/1.04),(int)(getHeight()/1.9));
		areaOptions[1].set((int)(getWidth()/2.4),(int)(getHeight()/1.83),(int)(getWidth()/1.04),(int)(getHeight()/1.45));
		areaOptions[2].set((int)(getWidth()/5.4),(int)(getHeight()/1.26),(int)(getWidth()/1.3),(int)(getHeight()/1.05));

		canvas.drawBitmap(fundo, null, rectFundo, paint);
		canvas.drawBitmap(background, null, rectBGMenu, paint);
		canvas.drawBitmap(options[0],null, areaOptions[0], paint);
		canvas.drawBitmap(options[1],null, areaOptions[1], paint);
//		canvas.drawBitmap(options[2],null, areaOptions[2], paint);
		
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

			if (areaOptions[0].contains(a,b))
			{
				Intent i = new Intent();				
				activity.startActivity(i.setClass(getContext(), Servidor.class));
			}
			
			if (areaOptions[1].contains(a,b))
			{
				Log.i(MainActivity.TAG, "Instrucoes");
				instrucoes = new Instrucoes(activity);
				activity.setContentView(instrucoes);
			}
			

			if (areaOptions[2].contains(a,b))
			{
				Log.i(MainActivity.TAG, "Creditos");
				creditos = new Creditos(activity);
				activity.setContentView(creditos);
			}
			

		}
		
		return super.onTouchEvent(event);
	}	
}
