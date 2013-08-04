package com.example.servidorecliente;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.MaoNaCorda.Game;
import com.example.servidorecliente.rede.ControleDeUsuariosCliente;
import com.example.servidorecliente.rede.ControleDeUsuariosServidor;
import com.example.servidorecliente.rede.DepoisDeReceberDados;
import com.example.servidorecliente.rede.Killable;
import com.example.servidorecliente.util.DialogHelper;
import com.example.servidorecliente.util.RedeUtil;
import com.example.servidorecliente.util.ViewUtil;

public class Servidor extends Activity implements Killable, Runnable

{
	public static final String TAG = "servidor-cliente";
	public static final String RUN = "run";
	private static final int PORTA_PADRAO = 2121;
	private GerenteDEConexao gerente;
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

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setVolumeControlStream(AudioManager.STREAM_MUSIC);
	
		Log.i(TAG, "entrei no OnCreate servidor-cliente ");
		
		your_IP = (EditText) findViewById(R.id.yourIP);
		aguardando = (EditText) findViewById(R.id.aguardando);
		
		setContentView(R.layout.servidor);
		
		 a = new ControleDeUsuariosServidor();
		usuario="Player 1";
		MainActivity.GetInstance().getPlayer().setIdentificador(usuario);
		 thread = new Thread(this);
		 thread.start();
		 
		 ElMatador.getInstance().add(this);
	}
	
	public void criarServidor(View sender) {

		try 
		{
			
			Log.i(TAG, "criei servidor incialmente.");
			
	//		ViewUtil.closeKeyboard(this);
			//fechar o teclado assim que clicar em criar servidor
			
			if (gerente != null) {
				gerente.killMeSoftly();
			}

			gerente = new GerenteDEConexao(PORTA_PADRAO);

			// gerente.iniciarServidor(new TratadorDeRedeECO());
			gerente.iniciarServidor(new ControleDeUsuariosServidor());
			DepoisDeReceberDados tratadorDeDadosDoCliente = new ControleDeUsuariosCliente();

			Socket s = new Socket("127.0.0.1", PORTA_PADRAO);
			conexao = new Conexao(s, usuario, tratadorDeDadosDoCliente);
			//conexao.write(Protocolo.PROTOCOL_ID);
			String serverIp = RedeUtil.getLocalIpAddress();
			if (serverIp == null)
			{

				DialogHelper.message(this, "Conecte-se a alguma rede");

			} else 
				{
				setTitle("servidor : " + serverIp);

				// garante que view possa recuperar a lista de usuarios atual e
				// enviar dados pela rede

				
				viewDoJogo = new ViewDeRede(this, conexao,
						(ControleDeUsuariosCliente) tratadorDeDadosDoCliente);
				
				//conexao.write(Protocolo.PROTOCOL_CONNECT);				
				setContentView(viewDoJogo);
			}

		} catch (UnknownHostException e)
		{
			DialogHelper.error(this, "Erro ao conectar com o servidor",
					TAG, e);

		} catch (IOException e)
		{
			DialogHelper.error(this, "Erro ao comunicar com o servidor",
					TAG, e);
		}
	
			
		
		
		
		Log.i(TAG, "Esperando conexao ");
		System.out.print("run");

	}
	
	public void killMeSoftly() {
		ElMatador.getInstance().killThenAll();
		finish();
	}

		 
		 public void run() {
			 Log.i(TAG, "entrei no run ");
			 System.out.print("run");
				
				while (true) {
					try {
						Thread.sleep(60);
					} catch (InterruptedException e) {
						Log.e(MainActivity.TAG, "interrupcao do run()");
					}
					//update();
					//postInvalidate();
				}

			}

		
	
	
	public void update() 
	{
		Log.i(RUN, "entrei no Update do Servidor Cliente ");

		if (a.count == 2)
		{				
			Log.i(TAG, "Entrei na condição.");

			viewDoJogo = new ViewDeRede(this, conexao,
					(ControleDeUsuariosCliente) tratadorDeDadosDoCliente);
			
			//conexao.write(Protocolo.PROTOCOL_CONNECT);				
			setContentView(viewDoJogo);
			ControleDeUsuariosServidor.count = -1;
		}	 
	}
	
}
