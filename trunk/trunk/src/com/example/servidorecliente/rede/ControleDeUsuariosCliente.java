package com.example.servidorecliente.rede;

import java.util.concurrent.ConcurrentHashMap;

import com.example.servidorecliente.Conexao;
import com.example.servidorecliente.bean.Jogador;

public class ControleDeUsuariosCliente implements DepoisDeReceberDados {

	private ConcurrentHashMap<String, Jogador> jogadores;

	public ConcurrentHashMap<String, Jogador> getJogadores() {
		return jogadores;
	}

	public ControleDeUsuariosCliente() {
		jogadores = new ConcurrentHashMap<String, Jogador>();
	}

	// recebe do servidor no formato : nome,x,y;nome,x,y
	public void execute(Conexao origem, String linha) {
		String[] lista = linha.split(";");
		for (String um : lista) {
			String[] separado = um.split(",");
			
			//int id = Integer.parseInt(separado[0]); 
			String id = separado[0]; 
			// alterei pra enviar o int que mostra o id de jogador um ou dois
			
			int x = Integer.parseInt(separado[1]);
			int y = Integer.parseInt(separado[2]);

			Jogador jogador = jogadores.get(id);
			
			if (jogador == null) {
				
				jogador = new Jogador(id, x, y);
				jogadores.put(id , jogador);
				
			} else {
				jogador.setX(x);
				jogador.setY(y);
			}
		}

	}

}
