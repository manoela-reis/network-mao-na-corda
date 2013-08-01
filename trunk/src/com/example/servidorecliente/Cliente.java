package com.example.servidorecliente;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.servidorecliente.bean.Jogador;
import com.example.servidorecliente.rede.ControleDeUsuariosCliente;
import com.example.servidorecliente.rede.DepoisDeReceberDados;
import com.example.servidorecliente.rede.Killable;
import com.example.servidorecliente.rede.Protocolo;
import com.example.servidorecliente.util.DialogHelper;
import com.example.servidorecliente.util.ViewUtil;

import android.media.AudioManager;
import android.net.wifi.WifiConfiguration.Protocol;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class Cliente extends Activity implements Killable, Runnable

{
	public static final String TAG = "cliente";
	private static final int PORTA_PADRAO = 2121;
	private GerenteDEConexao gerente;

//	private EditText editUsuario;
	private EditText editIP;
	private String usuario= "Player 2";
	private ViewDeRede viewDoJogo;
	private Conexao conexao;
	public static boolean conectou;
public ControleDeUsuariosCliente tratadorDeDadosDoCliente;
public int count;
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	//	requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setVolumeControlStream(AudioManager.STREAM_MUSIC);
		setContentView(R.layout.cliente);
		Log.i(TAG, "entrei no OnCreate cliente ");
		
		editIP = (EditText) findViewById(R.id.editText1);
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
				tratadorDeDadosDoCliente = new ControleDeUsuariosCliente();
				
				Socket s = new Socket(ip, PORTA_PADRAO);
				conexao = new Conexao(s, usuario, tratadorDeDadosDoCliente);
				Log.i(TAG, usuario + "XXXXXXXXXXXX");
				conexao.write(Protocolo.PROTOCOL_CONNECT);
				
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

	public void onBackPressed()
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
	public void killMeSoftly() {
		ElMatador.getInstance().killThenAll();
		finish();
	}
	
	public void run()
	{
		Log.i(TAG, "Entrou no run");
		
	//	viewDoJogo = new ViewDeRede(this, conexao,
		//		(ControleDeUsuariosCliente) tratadorDeDadosDoCliente);

		//setContentView(viewDoJogo);
		
		
		
	}

	
}

