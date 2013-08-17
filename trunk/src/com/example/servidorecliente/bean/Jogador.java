package com.example.servidorecliente.bean;

import com.example.servidorecliente.ViewDeRede;

public class Jogador {
	private String identificador;
	private int x;
	private String patente;
	private int itemEspecial;
	private Boolean Visible = false;

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
	public void iniciarPartida(){
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

}
