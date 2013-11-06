package com.example.servidorecliente;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.example.servidorecliente.bean.Jogador;
import com.example.servidorecliente.rede.Conexao;
import com.example.servidorecliente.rede.ControleDeUsuariosCliente;
import com.example.servidorecliente.rede.DepoisDeReceberDados;
import com.example.servidorecliente.rede.GerenteDEConexao;
import com.example.servidorecliente.rede.Killable;
import com.example.servidorecliente.rede.ViewDeRede;
import com.example.servidorecliente.util.DialogHelper;
import com.example.servidorecliente.util.ViewUtil;

public class EsperaClient extends Activity implements Killable, Runnable

{
	public static final String TAG = "cliente";
	private static final int PORTA_PADRAO = 2121;
	private GerenteDEConexao gerente;

	// private EditText editUsuario;
	private EditText editIP;
	private String usuario = "Player 2";
	private ViewDeRede viewDoJogo;
	private Conexao conexao;
	public static boolean conectou;
	public ControleDeUsuariosCliente tratadorDeDadosDoCliente;
	public int count;

	public boolean ativo=true;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		setContentView(R.layout.cliente);
		Log.i(TAG, "entrei no OnCreate cliente ");

		ativo=true;
		usuario = "Player 2";
		MainActivity.GetInstance().getPlayer().setIdentificador(usuario);
		editIP = (EditText) findViewById(R.id.editText2);
		conexao=null;
	}

	public void conectar(View sender) {

		Log.i(TAG, "entrei no conectar");
		conectou = true;
		String ip = editIP.getText().toString();

		if (ip.trim().length() == 0) {
			DialogHelper.message(this,
					"endereco do servidor não pode ser vazio");

		} else {
			ViewUtil.closeKeyboard(this);
		}

		try {
			DepoisDeReceberDados tratadorDeDadosDoCliente = new ControleDeUsuariosCliente();

			Socket s = new Socket(ip, PORTA_PADRAO);
			conexao = new Conexao(s, usuario, tratadorDeDadosDoCliente);
			Log.i(TAG, usuario + "XXXXXXXXXXXX");

			viewDoJogo = new ViewDeRede(this, conexao,
					(ControleDeUsuariosCliente) tratadorDeDadosDoCliente);

			 setContentView(viewDoJogo);
			// garante que view possa recuperar a lista de usuarios atual e
			// enviar dados pela rede

			Log.i(TAG, "Cliquei no conectar do cliente.");

		} catch (UnknownHostException e) {
			DialogHelper.error(this, "Erro ao conectar com o servidor",
					MainActivity.TAG, e);

		} catch (IOException e) {
			DialogHelper.error(this, "Erro ao comunicar com o servidor",
					MainActivity.TAG, e);
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
		ativo=false;
		if(conexao!=null){
		conexao.killMeSoftly();
		conexao=null;
		}
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

				Log.i("fooooi", "Run");
				
				Thread.sleep(60);
			} catch (InterruptedException e) {
				Log.e(MainActivity.TAG, "interrupcao do run()");
			}
		}

	}

}