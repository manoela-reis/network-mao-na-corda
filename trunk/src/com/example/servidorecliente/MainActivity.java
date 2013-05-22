package com.example.servidorecliente;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.servidorecliente.rede.ControleDeUsuariosCliente;
import com.example.servidorecliente.rede.ControleDeUsuariosServidor;
import com.example.servidorecliente.rede.DepoisDeReceberDados;
import com.example.servidorecliente.rede.Killable;
import com.example.servidorecliente.util.DialogHelper;
import com.example.servidorecliente.util.RedeUtil;
import com.example.servidorecliente.util.ViewUtil;

public class MainActivity extends Activity 
{
	public static final String TAG = "rede";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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

/*	public void salvarUsuario(View sender) {
		ViewUtil.closeKeyboard(this);
		usuario = editUsuario.getText().toString();
		Log.i(TAG, "usuario salvo:" + usuario);
	}*/


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
	 
	public void killMeSoftly() {
		ElMatador.getInstance().killThenAll();
		finish();
	}*/

}

