package com.example.maonacordanetwork;

import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.app.Activity;
import android.content.Intent;

public class Choose_Your_Function extends Activity

{
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setVolumeControlStream(AudioManager.STREAM_MUSIC);
		setContentView(R.layout.choose_your_function_game);
	}
	
	// Servidor/Cliente
	public void servidor(View sender)
	{
		Log.i(MainActivity.TAG, "Escolhi ser servidor/cliente!! ");
		Intent i = new Intent();
		i.setClass(this,Servidor.class);
		startActivity(i);		
	}
	
	// Cliente
	public void cliente(View sender)
	{
		Log.i(MainActivity.TAG, "Escolhi ser cliente!! ");
		Intent i = new Intent();
		i.setClass(this,Choose_Your_Function.class);
		startActivity(i);		
	}
}
