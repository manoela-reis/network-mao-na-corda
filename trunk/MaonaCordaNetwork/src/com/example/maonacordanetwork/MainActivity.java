package com.example.maonacordanetwork;

import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity 
{
	public static final String TAG = "MaoNaCorda";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setVolumeControlStream(AudioManager.STREAM_MUSIC);
		setContentView(R.layout.activity_main);
	}
	
	public void play(View sender)
	{
		Log.i(MainActivity.TAG, "Clicou no Play!! ");
		Intent i = new Intent();
		i.setClass(this,Choose_Your_Function.class);
		startActivity(i);
	}
}
