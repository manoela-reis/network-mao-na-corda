package com.example.servidorecliente;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.MaoNaCorda.Game;

import com.example.servidorecliente.rede.Conexao;
import com.example.servidorecliente.rede.ControleDeUsuariosServidor;
import com.example.servidorecliente.rede.DepoisDeReceberDados;
import com.example.servidorecliente.rede.Killable;
import com.example.servidorecliente.rede.ViewDeRede;

public class Servidor extends Activity implements Killable, Runnable

{
	private String usuario="Player 1";
	private ViewDeRede viewDoJogo;
	private Conexao conexao;
	public EditText your_IP;
	public EditText aguardando;
	static ControleDeUsuariosServidor a = new ControleDeUsuariosServidor();
	Game game;
	Activity b;
	Thread thread;

	DepoisDeReceberDados tratadorDeDadosDoCliente ;
	private EditText editIP;
	public static boolean conectou;
	public Button conect;
	public Button createServ;

	private boolean ativo = true;
	
	protected void onCreate(Bundle savedInstanceState) 
	{

		super.onCreate(savedInstanceState);
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setVolumeControlStream(AudioManager.STREAM_MUSIC);
	
		
//		your_IP = (EditText) findViewById(R.id.yourIP);
//		aguardando = (EditText) findViewById(R.id.aguardando);
		ativo=true;
		setContentView(R.layout.servidor);
		
		 a = new ControleDeUsuariosServidor();
		usuario="Player 1";
	
		MainActivity.GetInstance().getPlayer().setIdentificador(usuario);

		thread = new Thread(this);
		thread.start();
		 
		 ElMatador.getInstance().add(this);
	}
	
	public void criarServidor(View sender) {

		killMeSoftly();
		Intent i = new Intent();
		i.setClass(this, EsperaServer.class);
		startActivity(i);
	}
	
	public void onBackPressed()
	{

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

	
	
	public void killMeSoftly() {
		ElMatador.getInstance().killThenAll();
		ativo=false;
		finish();
	}
	public void cliente(View sender) {
		
		killMeSoftly();
		Intent i = new Intent();
		i.setClass(this, EsperaClient.class);
		startActivity(i);
		}
		 
		 public void run() {
				
				while (ativo) {
					try {
						Thread.sleep(60);
						Log.e("Servidoooor", "estou no ruuuuuuuuun");
				
					} catch (InterruptedException e) {
						Log.e(MainActivity.TAG, "interrupcao do run()");
					}
					
				}

			}

}
