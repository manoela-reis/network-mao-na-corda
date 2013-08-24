package com.example.servidorecliente;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.servidorecliente.R;
import com.example.servidorecliente.R.layout;
import com.example.servidorecliente.bean.Jogador;

public class MainActivity extends Activity 
{
	public static final String TAG = "MainActivity";
	public static MainActivity Instance;

	private Jogador meuPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MainActivity Inst = this;
		Instance = Inst;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		

		meuPlayer = new Jogador("", 100, "Aspirante");
	}

	public static MainActivity GetInstance(){
		return Instance;
	}
	public Jogador getPlayer(){
		return meuPlayer;
	}
	public void killMeSoftly() {
		ElMatador.getInstance().killThenAll();
		finish();
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
		i.setClass(this,Cliente.class);
		startActivity(i);		
	}




	/**
	 * @see http 
	 *      ://stackoverflow.com/questions/2257963/how-to-show-a-dialog-to-confirm
	 *      -that-the-user-wishes-to-exit-an-android-activity
	 */
/*	public void onBackPressed()
	{
		Log.i(TAG,"--------- back pressed");

		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("abandonando o barco")
				.setMessage(
						"Tem certeza que vai embora ? vou sentir sua falta ...")
				.setPositiveButton("Adeus",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								killMeSoftly();
							}

						}).setNegativeButton("Então tá, fico + um pouco", null)
				.show();
	}

	/**
	 * realiza limpeza dos threads em funcionamento
	 
	*/

}

