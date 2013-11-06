package com.example.servidorecliente.rede;

import android.util.Log;

import com.example.servidorecliente.ElMatador;
import com.example.servidorecliente.MainActivity;

public class DadosDoCliente implements Runnable, Killable {

	private Conexao cliente;
	private int updateTime;

	private int x;
	private int ImpX = 0;
	private int VelX;
	private int MasX;
	private int itemEspecial = -1;
	private float PosMao;
	private boolean ativo = true;
	private boolean finalizar = false;
	private float widthfull=0;

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
			if (!finalizar) {

				cliente.write(Protocolo.PROTOCOL_MOVE + "," + x + "," + PosMao
						+ "," + ImpX +","+ widthfull);
				// cliente.write(Protocolo.PROTOCOL_ITENS + "," + ImpX + "," +
				// MasX
				// + "," + VelX);
				cliente.write(Protocolo.PROTOCOL_ITEMESP + "," + itemEspecial);
			} else {
				cliente.write(Protocolo.PROTOCOL_FINALIZAR);
			}
		}

	}

	public void finalizar() {
		finalizar = true;
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

	public void setItemEsp(int itemEsp) {
		this.itemEspecial = itemEsp;

	}

	public int getItemEsp() {
		return itemEspecial;
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

	public void setwidthFull(float width) {
		this.widthfull = width;

	}

	public float getPosMao() {
		return PosMao;
	}

	public void setPosMao(float PosMao) {
		this.PosMao = PosMao;
	}

	public void killMeSoftly() {
		
		ativo = false;
		ElMatador.getInstance().killThenAll();
	}

}
