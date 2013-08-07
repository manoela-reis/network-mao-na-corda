package com.example.servidorecliente.rede;

import java.util.concurrent.ConcurrentHashMap;

import android.util.Log;

import com.example.servidorecliente.Conexao;
import com.example.servidorecliente.MainActivity;
import com.example.servidorecliente.bean.Jogador;

public class ControleDeUsuariosCliente implements DepoisDeReceberDados {

	private ConcurrentHashMap<String, Jogador> jogadores;
	public Jogador jogador;

	public ConcurrentHashMap<String, Jogador> getJogadores() {
		return jogadores;
	}

	public ControleDeUsuariosCliente() {
		jogadores = new ConcurrentHashMap<String, Jogador>();
	}

	// recebe do servidor no formato : nome,x,y;nome,x,y
	public void execute(Conexao origem, String linha) {

		Log.e("ReciboCliente", linha + "  " + origem.getId());

		if (linha.startsWith(Protocolo.PROTOCOL_MOVE)) {

			String[] lista = linha.split(":");
			moveUsuario(origem, lista[1]);
		}

	}

	// recebe do servidor no formato : nome,x,y;nome,x,y
	public void moveUsuario(Conexao origem, String linha) {
		String[] lista = linha.split(";");
		for (String um : lista) {
			String[] separado = um.split(",");
			String nome = separado[0];
			int x = Integer.parseInt(separado[1]);
			

			Jogador jogador = jogadores.get(nome);
			if (jogador == null) {
				jogador = new Jogador(nome, x,"Aspirante");
				jogadores.put(nome, jogador);
			} else {
				jogador.setX(x);

			}
		}
	}

	public void iniciarJogo() {

		Jogador meuJogador = MainActivity.GetInstance().getPlayer();
		Jogador jogador = jogadores.get(meuJogador.getID());
		if (jogador == null) {
			jogadores.put(meuJogador.getID(), meuJogador);
		}
	}

}
