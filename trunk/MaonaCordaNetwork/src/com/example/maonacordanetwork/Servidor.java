package com.example.maonacordanetwork;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class Servidor extends Activity

{
	public static final String TAG = "rede";
	private static final int PORTA_PADRAO = 2121;
	private GerenteDEConexao gerente;

//	private EditText editUsuario; //variavel que é a caixa de texto do "nome" do usuário.
//	private EditText editIP; 
//	private String usuario;
//	private ViewDeRede viewDoJogo;
	private Conexao conexao;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setVolumeControlStream(AudioManager.STREAM_MUSIC);
		setContentView(R.layout.conectar_servidor);
	}
	
	public void conectarServidor(View sender)
	{
		try 
		{
			if (gerente != null)
			{
				gerente.adeus();
			}

			gerente = new GerenteDEConexao(PORTA_PADRAO);
			gerente.iniciarServidor();
			Socket s = new Socket("127.0.0.1", gerente.getPorta());
			conexao = new Conexao(s);

			DialogHelper.message(this,
					"endereco do servidor : " + RedeUtil.getLocalIpAddress());

	//		setContentView(viewDoJogo);

		  }
		
		catch (UnknownHostException e) 
		{
			DialogHelper.error(this, "Erro ao conectar com o servidor",
			MainActivity.TAG, e);
		} 
		catch (IOException e) 
		{
			DialogHelper.error(this, "Erro ao comunicar com o servidor",
					MainActivity.TAG, e);
		}
		
	}
}
