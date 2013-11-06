package com.example.servidorecliente;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.MaoNaCorda.Game;
import com.example.servidorecliente.bean.Jogador;
import com.example.servidorecliente.rede.Conexao;
import com.example.servidorecliente.rede.ControleDeUsuariosCliente;
import com.example.servidorecliente.rede.ControleDeUsuariosServidor;
import com.example.servidorecliente.rede.DadosDoCliente;
import com.example.servidorecliente.rede.DepoisDeReceberDados;
import com.example.servidorecliente.rede.GerenteDEConexao;
import com.example.servidorecliente.rede.Killable;
import com.example.servidorecliente.rede.ViewDeRede;
import com.example.servidorecliente.util.DialogHelper;
import com.example.servidorecliente.util.RedeUtil;
import com.example.servidorecliente.util.ViewUtil;

public class EsperaServer extends Activity implements Killable, Runnable

{
	public static final String TAG = "servidor-cliente";
	public static final String RUN = "run";
	private static final int PORTA_PADRAO = 2121;
	private GerenteDEConexao gerente;
	private String usuario = "Player 1";
	private ViewDeRede viewDoJogo;
	private Conexao conexao;
	public TextView your_IP;
	public Boolean Conectou = false;
	public EditText aguardando;
	static ControleDeUsuariosServidor a = new ControleDeUsuariosServidor();
	Game game;
	Activity b;
	//Thread thread;

	DepoisDeReceberDados tratadorDeDadosDoCliente;
	private EditText editIP;
	public Button conect;
	public Button createServ;
	DadosDoCliente dadosDoCliente;
	private int contador;
	private boolean ativo = true;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		setContentView(R.layout.activity_main);

		ativo=true;
		your_IP = (TextView) findViewById(R.id.textView1);

		a = new ControleDeUsuariosServidor();
		usuario = "Player 1";

		MainActivity.GetInstance().getPlayer().setIdentificador(usuario);
		editIP = (EditText) findViewById(R.id.editText1);

		//thread = new Thread(this);
		//thread.start();

		ElMatador.getInstance().add(this);

		try {
			if (gerente != null) {
				gerente.killMeSoftly();
			}

			gerente = new GerenteDEConexao(PORTA_PADRAO);

			// gerente.iniciarServidor(new TratadorDeRedeECO());
			gerente.iniciarServidor(new ControleDeUsuariosServidor());
			tratadorDeDadosDoCliente = new ControleDeUsuariosCliente();

			Socket s = new Socket("127.0.0.1", PORTA_PADRAO);
			conexao = new Conexao(s, usuario, tratadorDeDadosDoCliente);
			// conexao.write(Protocolo.PROTOCOL_ID);
			String serverIp = RedeUtil.getLocalIpAddress();
			if (serverIp == null) {
				DialogHelper.message(this, "Conecte-se a alguma rede");

			} else {
				your_IP.setText(serverIp);

				// setTitle("servidor : " + serverIp);

				viewDoJogo = new ViewDeRede(this, conexao,
						(ControleDeUsuariosCliente) tratadorDeDadosDoCliente);

				setContentView(viewDoJogo);

				// viewDoJogo.myview=viewDoJogo;
				// setContentView(viewDoJogo);
			}

		} catch (UnknownHostException e) {
			DialogHelper.error(this, "Erro ao conectar com o servidor", TAG, e);

		} catch (IOException e) {
			DialogHelper
					.error(this, "Erro ao comunicar com o servidor", TAG, e);
		}

	}

	public void onBackPressed() {
		Log.i(TAG, "--------- back pressed");

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

						})
				.setNegativeButton("Então tá, fico + um pouco", null).show();
	}

	public void killMeSoftly() {
			ElMatador.getInstance().killThenAll();
		conexao.killMeSoftly();

		gerente.killMeSoftly();
		ativo=false;
		finish();
	}
	public void OnBackPressed() {
		//conexao.killMeSoftly();
		//finish();
		killMeSoftly();
	}

	public void run() {

		while (ativo) {
			try {
				// conectou();
				Log.i("fooooi", "Run" + contador);

				Thread.sleep(60);

			} catch (InterruptedException e) {
				Log.e(MainActivity.TAG, "interrupcao do run()");
			}
		}

	}

}