package com.example.servidorecliente;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.servidorecliente.rede.ControleDeUsuariosCliente;
import com.example.servidorecliente.rede.ControleDeUsuariosServidor;
import com.example.servidorecliente.rede.DepoisDeReceberDados;
import com.example.servidorecliente.rede.Killable;
import com.example.servidorecliente.rede.Protocolo;
import com.example.servidorecliente.util.DialogHelper;
import com.example.servidorecliente.util.RedeUtil;
import com.example.servidorecliente.util.ViewUtil;

import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;

public class Servidor extends Activity implements Killable, Runnable

{
	public static final String TAG = "servidor";
	private static final int PORTA_PADRAO = 2121;
	private GerenteDEConexao gerente;
	private String usuario;
	private ViewDeRede viewDoJogo;
	private Conexao conexao;
	public EditText your_IP;
	public EditText aguardando;
	static ControleDeUsuariosServidor a = new ControleDeUsuariosServidor();

	DepoisDeReceberDados tratadorDeDadosDoCliente = new ControleDeUsuariosCliente();


	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setVolumeControlStream(AudioManager.STREAM_MUSIC);
	
		Log.i(TAG, "entrei no OnCreate servidor ");
		
		your_IP = (EditText) findViewById(R.id.yourIP);
		aguardando = (EditText) findViewById(R.id.aguardando);
		
		setContentView(R.layout.servidor);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		 a = new ControleDeUsuariosServidor();
	}
	
	public void criarServidor(View sender) {

		try 
		{
			String serverIp = RedeUtil.getLocalIpAddress();
			
			Log.i(TAG, "criei servidor incialmente.");
			
	//		ViewUtil.closeKeyboard(this);
			//fechar o teclado assim que clicar em criar servidor
			
			if (gerente != null) {
				gerente.killMeSoftly();
			}

			gerente = new GerenteDEConexao(PORTA_PADRAO);

			// gerente.iniciarServidor(new TratadorDeRedeECO());
			gerente.iniciarServidor(new ControleDeUsuariosServidor());

			Socket s = new Socket("127.0.0.1", PORTA_PADRAO);
			conexao = new Conexao(s, usuario, tratadorDeDadosDoCliente);
			
//			Log.i(TAG, "teste.");
			
			//your_IP.setVisibility(View.VISIBLE);
			//aguardando.setVisibility(View.VISIBLE);
			//	DialogHelper.message(this, "endereco do servidor : " + serverIp);
			
			
		    setTitle("servidor : " + serverIp);

			// garante que view possa recuperar a lista de usuarios atual e
			// enviar dados pela rede
		
			
		
				
				Toast.makeText(Servidor.this,
				"Seu IP é: " + serverIp.toString(),	Toast.LENGTH_SHORT).show();
				
				Toast.makeText(Servidor.this,
						"aguardando conexão!!",	Toast.LENGTH_SHORT).show();
			//}
			

		} catch (UnknownHostException e) {
			DialogHelper.error(this, "Erro ao conectar com o servidor",
					MainActivity.TAG, e);

		} catch (IOException e) {
			DialogHelper.error(this, "Erro ao comunicar com o servidor",
					MainActivity.TAG, e);
		}

	}
	
	public void killMeSoftly() {
		ElMatador.getInstance().killThenAll();
		finish();
	}

	public void run() 
	{
		 while (true)
		 {
			   try 
			   {
				   Log.i(TAG, "entrei no run ");
				   Thread.sleep(60);
			   }
			   catch (InterruptedException e) 
			   {
				   Log.e(MainActivity.TAG, "interrupcao do run()");
			   }
			   	update();
		 }

		
	}	
	
	public void update() 
	{
		Log.i(TAG, "entrei no Update ");

		if (ControleDeUsuariosServidor.count == 1)
		{	
			viewDoJogo = new ViewDeRede(this, conexao,
					(ControleDeUsuariosCliente) tratadorDeDadosDoCliente);

			//conexao.write(Protocolo.PROTOCOL_CONNECT);				
			setContentView(viewDoJogo);
			
		}

		 
		 }
	
}
