package com.example.servidorecliente.bean;

import com.example.servidorecliente.rede.ViewDeRede;

public class Jogador {
	private String identificador;
	private int x;
	private String patente;
	private int itemEspecial;
	private Boolean Visible = false;

	private int ImpX = 20;
	private int VelX = 20;
	private int MasX = 20;

	public Jogador(String id, int x, String patente) {
		this.identificador = id;
		this.x = x;
		this.patente = patente;
	}

	public String toString() {
		return "Jogador [ID=" + identificador + ", x=" + x + "]";
	}

	public String getID() {
		return identificador;
	}

	public Boolean isVisible() {
		return Visible;
	}

	public void setIdentificador(String id) {
		this.identificador = id;
	}

	public int getX() {
		return x;
	}

	public void iniciarPartida() {
		this.Visible = true;
	}

	public void setX(int x) {
		this.x = x;

	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public int getItemEspecial() {
		return itemEspecial;
	}

	public void setItemEspecial(int item) {
		this.itemEspecial = item;
	}

	public String toStringCSV() {
		return identificador + "," + x + ";";
	}

	public String Itens() {
		return identificador + "," + ImpX + "," + MasX + "," + VelX + ";";
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
}
