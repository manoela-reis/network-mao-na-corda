package com.example.servidorecliente.rede;


import interfaces.Const;
import interfaces.DepoisDeReceberDados;
import interfaces.Protocolo;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import android.util.Log;

import com.example.servidorecliente.bean.Jogador;

public class ControleDeUsuariosServidor implements DepoisDeReceberDados {

	// notar que este tipo de hashmap eh sincronizado
	// suportando acessos de multiplos threads
	public static final String TAG = "controle";
	public ConcurrentHashMap<String, Jogador> jogadores;
//	private boolean conectou;
	public static int count = 0;

	public ControleDeUsuariosServidor() {
		jogadores = new ConcurrentHashMap<String, Jogador>();
	}

	// comandos possiveis dos clientes
	// ID,nome do usuario,x,y
	// MOVE,x,y
	public ConcurrentHashMap<String, Jogador> Jogadores (){
		
		return jogadores;
	}

	public void execute(Conexao origem, String linha) {

		Log.i(Const.TAG, "<<" + linha);

		if (linha.startsWith(Protocolo.PROTOCOL_ID)) {
			Log.i(TAG, "Entreou no Protocolo ID");
			adicionaNovoUsuario(origem, linha);
		}

		if (linha.startsWith(Protocolo.PROTOCOL_MOVE)) {
			moveUsuario(origem, linha);
		}
		
		
		
		informaTodosUsuarios(origem, linha);
	}

	private void informaTodosUsuarios(Conexao origem, String linha) {
		StringBuffer buffer = new StringBuffer();
		Iterator iterator = jogadores.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Jogador jogador = jogadores.get(key);
			//jogador.update();
			buffer.append(jogador.toStringCSV());
		}

		if(jogadores.get("Player 2")!=null)
		{
			origem.write(Protocolo.PROTOCOL_INICIAR +":"+ buffer.toString());

		}
		origem.write(Protocolo.PROTOCOL_MOVE +":"+ buffer.toString());
		
		//origem.write(linha);

	}
	
	

	
	private void moveUsuario(Conexao origem, String linha) {
		String[] array = linha.split(",");
		int x = Integer.parseInt(array[1]);
		int y = Integer.parseInt(array[2]);

		
		Jogador jogador = jogadores.get(origem.getId());
		jogador.setX(x);
		//Jogador jogador = jogadores.get(origem.getId());
		//jogador.setX(x);
		//jogador.setY(y);
	}

	private void adicionaNovoUsuario(Conexao origem, String linha) {
		String[] array = linha.split(",");
		String nome = array[1];
		int x = Integer.parseInt(array[2]);
		String patente= array[3];
		
		origem.setId(nome);
		Jogador jogador = new Jogador(nome, x, patente);
		jogadores.put(nome, jogador);
		
	}
}
