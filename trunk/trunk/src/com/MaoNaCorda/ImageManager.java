package com.MaoNaCorda;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;


import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

public  class ImageManager 
{
	Bitmap geometric_figures;	
	int positionX;
	int positionY;
	Context context;
	
	/*public ImageManager(int width, int heigth){
		positionX=width;
		positionY=heigth;
		
	}*/
	
    public ImageManager (Context context)
    {
    	this.context = context;
    }
	
	public Bitmap ImageManager(String name) 
	{
		try 
		{
			InputStream img = context.getAssets().open(name);
			geometric_figures = BitmapFactory.decodeStream(img);
		}
	catch (IOException e) 
	{
		Log.e("fooi", "Erro carregando imagem");
	}
		return geometric_figures;
}
}
