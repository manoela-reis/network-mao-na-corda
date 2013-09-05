package com.example.servidorecliente.rede;

import android.util.Log;

import com.example.servidorecliente.ElMatador;
import com.example.servidorecliente.MainActivity;

public class DadosDoCliente implements Runnable, Killable {

	private Conexao cliente;
	private int updateTime;

	private int x;
	private int ImpX;
	private int VelX;
	private int MasX;
	private int y;
	private boolean ativo = true;

	public DadosDoCliente(Conexao cliente, int updateTime) {
		this.cliente = cliente;
		this.updateTime = updateTime;
		ElMatador.getInstance().add(this);
	}

	public void run() {
		while (ativo) {
			try {
				Thread.sleep(updateTime);
			} catch (InterruptedException e) {
				Log.e(MainActivity.TAG, "interrupcao do run()");
			}
			cliente.write(Protocolo.PROTOCOL_MOVE + "," + x + "," + y);
			cliente.write(Protocolo.PROTOCOL_ITENS +","+ImpX+","+MasX+","+VelX );

		}

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;

	}
	public int getImpX() {
		return ImpX;
	}

	public void setImpX(int x) {
		this.ImpX = x;

	}
	public int getVelX() {
		return VelX;
	}

	public void setVelX(int x) {
		this.VelX = x;

	}
	public int getMasX() {
		return MasX;
	}

	public void setMasX(int x) {
		this.MasX = x;

	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void killMeSoftly() {
		ativo = false;
	}

}
