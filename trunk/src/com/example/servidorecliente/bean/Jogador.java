package com.example.servidorecliente.bean;

import com.example.servidorecliente.rede.ViewDeRede;

public class Jogador {
	private String identificador;
	private int x = -70;
	private float widthfull = 0;

	private float posMao = 10;
	private String patente;
	private int itemEspecial;
	private Boolean Visible = false;

	private int ImpX = 20;
	private int VelX = 20;
	private int MasX = 20;

	private Boolean Vitoria = false;
	private int ItemAcionado = -1;
	private boolean Perdeu = false;
	private boolean fechar = false;

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

	public Boolean getVitoria() {
		return Vitoria;
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

	public void finalizarPartida() {
		this.Visible = false;

	}

	public void setX(int x) {
		this.x = x;

	}

	public void setperdeu() {
		this.Perdeu = true;

	}

	public Boolean Perdeu() {
		return Perdeu;

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
		return identificador + "," + x + "," + Vitoria + "," + itemEspecial
				+ "," + posMao + "," + ImpX + "," + widthfull + ";";
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

	public float getWidthfull() {
		return widthfull;
	}

	public void setWidthfull(float width) {
		this.widthfull = width;

	}

	public void ganhou() {
		Vitoria = true;
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

	public float getPosMao() {
		return posMao;
	}

	public void setPosMAo(float PosMao) {
		this.posMao = PosMao;

	}

	public void Acionar(int Item) {
		this.ItemAcionado = Item;

	}

	public int getItemAcionado() {
		return ItemAcionado;

	}

	public void fechar() {
		this.fechar = true;
	}

	public Boolean getFechar() {
		return fechar;
	}

	public void setVitoria(Boolean Vitoria) {
		this.Vitoria = Vitoria;

	}
}
