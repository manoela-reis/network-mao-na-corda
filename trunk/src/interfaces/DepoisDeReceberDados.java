package interfaces;

import com.example.servidorecliente.rede.Conexao;


public interface DepoisDeReceberDados {

	void execute(Conexao origem, String linha);

}
