package com.example.servidorecliente.rede;


import interfaces.Const;
import interfaces.DepoisDeReceberDados;
import android.util.Log;

public class TratadorDeRedeECO implements DepoisDeReceberDados {

	@Override
	public void execute(Conexao origem, String linha) {

		Log.i(Const.TAG, "<<" + linha);
		if (linha != null) {
			origem.write("eco : " + linha);
		}

	}

}
