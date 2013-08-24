package com.example.servidorecliente.rede;



public interface DepoisDeReceberDados {

	void execute(Conexao origem, String linha);

}
