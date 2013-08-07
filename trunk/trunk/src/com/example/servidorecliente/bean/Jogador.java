package com.example.servidorecliente.bean;

import com.example.servidorecliente.ViewDeRede;

public class Jogador {
	private String identificador;
	private int x;
	private String patente; 
	private int itemEspecial;
	
	
	public Jogador(String id, int x, String patente) {
		this.identificador = id;
		this.x = x;
		this.patente=patente;
	}

	public String toString() {
		return "Jogador [ID=" + identificador + ", x=" + x +"]";
	}

	public String getID() {
		return identificador;
	}
	

	public void setIdentificador(String id) {
		this.identificador = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		
	}

	
	public String getPatente() {
		return patente;
	}
	public void setPatente(String patente) {
		this.patente= patente;
	}
	public int getItemEspecial() {
		return itemEspecial;
	}
	public void setItemEspecial(int item) {
		this.itemEspecial= item;
	}

	public String toStringCSV() {
		return identificador + "," + x +";";
	}

}
