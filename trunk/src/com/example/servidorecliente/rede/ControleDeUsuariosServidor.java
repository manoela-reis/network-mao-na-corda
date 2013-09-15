package com.example.servidorecliente.rede;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import android.util.Log;

import com.example.servidorecliente.bean.Jogador;

public class ControleDeUsuariosServidor implements DepoisDeReceberDados {

	// notar que este tipo de hashmap eh sincronizado
	// suportando acessos de multiplos threads
	public static final String TAG = "controle";
	public ConcurrentHashMap<String, Jogador> jogadores;
	// private boolean conectou;
	public static int count = 0;

	public ControleDeUsuariosServidor() {
		jogadores = new ConcurrentHashMap<String, Jogador>();
	}

	// comandos possiveis dos clientes
	// ID,nome do usuario,x
	// MOVE,x
	public ConcurrentHashMap<String, Jogador> Jogadores() {

		return jogadores;
	}

	public void execute(Conexao origem, String linha) {

		//Log.i(Const.TAG, "<<" + linha);

		if (linha.startsWith(Protocolo.PROTOCOL_ID)) {
			//Log.i(TAG, "Entreou no Protocolo ID");
			adicionaNovoUsuario(origem, linha);
		}

		if (linha.startsWith(Protocolo.PROTOCOL_MOVE)) {
			moveUsuario(origem, linha);
		}

		if (linha.startsWith(Protocolo.PROTOCOL_FINALIZAR)) {
			finalizarPartida(origem, linha);
		}
		if (linha.startsWith(Protocolo.PROTOCOL_ITENS)) {
			AttItens(origem, linha);
		}
		if (linha.startsWith(Protocolo.PROTOCOL_ITEMESP)) {
			AttItensEsp(origem, linha);
		}

		if (!linha.startsWith(Protocolo.PROTOCOL_ITENS)
				&& !linha.startsWith(Protocolo.PROTOCOL_ITEMESP)) {
			informaTodosUsuarios(origem, linha);
		}
	}

	private void finalizarPartida(Conexao origem, String Linha) {

		Jogador jogador = jogadores.get(origem.getId());
		jogador.ganhou();
		jogador.finalizarPartida();
		origem.write(Protocolo.PROTOCOL_FINALIZAR + ":" + origem.getId());

	}

	private void informaTodosUsuarios(Conexao origem, String linha) {
		StringBuffer buffer = new StringBuffer();
		Iterator iterator = jogadores.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Jogador jogador = jogadores.get(key);
			buffer.append(jogador.toStringCSV());
			if (jogador != null) {
				if (!jogador.getVitoria()) {
					if (jogadores.get("Player 2") != null) {

						origem.write(Protocolo.PROTOCOL_INICIAR);
						jogador.iniciarPartida();

					}
					origem.write(Protocolo.PROTOCOL_MOVE + ":"
							+ buffer.toString());
					if (origem.getId() != jogador.getID()) {
						if (jogador.getItemEspecial() != 0) {

							jogadores.get(origem.getId()).Acionar(
									jogador.getItemEspecial());
							origem.write(Protocolo.PROTOCOL_ACIONAR+":"
									+ jogador.getItemEspecial());
						}
					}
				} else {
					finalizarPartida(origem, linha);
				}
			}

		}
		// Este método serve para atualizar o "Move", finalizar e iniciar...
		// Há uma questão: se um dos jogadores finalizar a partida,o mesmo não
		// mandava mais o "move".
		// Mas, o outro continuava a atualizar sua movimentação, desta forma a
		// solução foi testar
		// se a vitoria está falsam, ou seja, se vc não ganhou, só assim ele
		// mandará o move,
		// bem como depois testará se o 2º cliente já se
		// conectou(jogadores.get("Player 2")!= null),
		// só assim ele inicia a partida, eliminando eros e ambiuidade
	}

	private void AttItens(Conexao origem, String linha) {
		String[] array = linha.split(",");
		int ImpX = Integer.parseInt(array[1]);
		int MasX = Integer.parseInt(array[2]);
		int VelX = Integer.parseInt(array[3]);

		Jogador jogador = jogadores.get(origem.getId());
		jogador.setImpX(ImpX);
		jogador.setMasX(MasX);
		jogador.setVelX(VelX);
		origem.write(Protocolo.PROTOCOL_ITENS + ":" + jogador.Itens());
		// Jogador jogador = jogadores.get(origem.getId());
		// jogador.setX(x);
		// jogador.setY(y);
	}

	private void AttItensEsp(Conexao origem, String linha) {
		String[] array = linha.split(",");
		int itemEsp = Integer.parseInt(array[1]);

		Jogador jogador = jogadores.get(origem.getId());
		jogador.setItemEspecial(itemEsp);
		origem.write(Protocolo.PROTOCOL_ITEMESP + ":"
				+ jogador.getItemEspecial());
		// Jogador jogador = jogadores.get(origem.getId());
		// jogador.setX(x);
		// jogador.setY(y);
	}

	private void moveUsuario(Conexao origem, String linha) {
		String[] array = linha.split(",");
		int x = Integer.parseInt(array[1]);

		Jogador jogador = jogadores.get(origem.getId());
		jogador.setX(x);
		// Jogador jogador = jogadores.get(origem.getId());
		// jogador.setX(x);
	}

	private void adicionaNovoUsuario(Conexao origem, String linha) {
		String[] array = linha.split(",");
		String nome = array[1];
		int x = Integer.parseInt(array[2]);
		String patente = array[3];

		origem.setId(nome);
		Jogador jogador = new Jogador(nome, x, patente);
		jogadores.put(nome, jogador);

	}
}
