package com.example.servidorecliente.rede;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import android.util.Log;

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

		//Log.e("ReciboCliente", linha + "  " + origem.getId());

		if (linha.startsWith(Protocolo.PROTOCOL_MOVE)) {

			String[] lista = linha.split(":");
			moveUsuario(origem, lista[1]);
		}
		if (linha.startsWith(Protocolo.PROTOCOL_INICIAR)) {

			IniciarPartida();
		}
		if (linha.startsWith(Protocolo.PROTOCOL_FINALIZAR)) {
			String[] lista = linha.split(":");
			FinalizarPartida(lista[1]);
		}

		if (linha.startsWith(Protocolo.PROTOCOL_ITENS)) {

			String[] lista = linha.split(":");
			attItens(origem, lista[1]);
		}
		if (linha.startsWith(Protocolo.PROTOCOL_ITEMESP)) {

			String[] lista = linha.split(":");
			attItensEsp(origem, lista[1]);
		}
		if (linha.startsWith(Protocolo.PROTOCOL_ACIONAR)) {

			String[] lista = linha.split(":");
			Acionar(origem, lista[1]);
		}

	}

	// recebe do servidor no formato : nome,x,y;nome,x,y
	public void moveUsuario(Conexao origem, String linha) {
		String[] lista = linha.split(";");
		for (String um : lista) {
			String[] separado = um.split(",");
			String nome = separado[0];
			int x = Integer.parseInt(separado[1]);
			Boolean vitoria = Boolean.parseBoolean(separado[2]);
			int itemEsp = Integer.parseInt(separado[3]);

			Jogador jogador = jogadores.get(nome);
			if (jogador == null) {
				jogador = new Jogador(nome, x, "Aspirante");
				jogador.setItemEspecial(itemEsp);
				jogador.setVitoria(vitoria);
				jogadores.put(nome, jogador);
			} else {
				jogador.setX(x);
				jogador.setItemEspecial(itemEsp);
				jogador.setVitoria(vitoria);

			}
			if (!jogador.isVisible() && jogador.getVitoria()) {
				FinalizarPartida(linha);
			}
		}
	}

	public void attItens(Conexao origem, String linha) {
		String[] lista = linha.split(";");
		for (String um : lista) {
			String[] separado = um.split(",");
			String nome = separado[0];
			int ImpX = Integer.parseInt(separado[1]);

			int MasX = Integer.parseInt(separado[2]);

			int VelX = Integer.parseInt(separado[3]);

			Jogador jogador = jogadores.get(nome);
			if (jogador == null) {

			} else {
				jogador.setImpX(ImpX);
				jogador.setMasX(MasX);
				jogador.setVelX(VelX);

			}
		}
	}
	public void attItensEsp(Conexao origem, String linha) {
		String[] lista = linha.split(";");
		for (String um : lista) {
			String[] separado = um.split(",");
			int ItemEsp = Integer.parseInt(separado[0]);


			Jogador jogador = jogadores.get(origem.getId());
			if (jogador == null) {

			} else {
				jogador.setItemEspecial(ItemEsp);

			}
		}
	}
	
	public void Acionar(Conexao origem, String linha) {
		String[] lista = linha.split(";");
		for (String um : lista) {
			String[] separado = um.split(",");
			int ItemEspAcionado = Integer.parseInt(separado[0]);


			Jogador jogador = jogadores.get(origem.getId());
			if (jogador == null) {

			} else {
				jogador.Acionar(ItemEspAcionado);

			}
		}
	}

	public void IniciarPartida() {
		Iterator iterator = jogadores.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Jogador jogador = jogadores.get(key);
			// jogador.update();
			if (!jogador.getVitoria()) {
				jogador.iniciarPartida();
			}

		}

	}

	public void FinalizarPartida(String linha) {
		Iterator iterator = jogadores.keySet().iterator();
		while (iterator.hasNext()) {

			String key = (String) iterator.next();
			Jogador jogador = jogadores.get(key);
			// jogador.update();
			jogador.ganhou();
			jogador.finalizarPartida();

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
