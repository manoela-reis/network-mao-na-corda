package com.example.servidorecliente.bean;

public class Jogador {
	private String identificador;
	// este é o identifador se vc é jogar UM ou jogador DOIS;
	private int x;
	// este x no caso de nosso jogador é o x da corda dele.
	private int y;
	// será q vai precisar do y?? uma vez que nossa corda n vai mover pra cima?

	public Jogador(String id, int x, int y) {
		this.identificador = id;
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "Jogador [ID=" + identificador + ", x=" + x + ", y=" + y + "]";
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

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toStringCSV() {
		return identificador + "," + x + "," + y + ";";
	}

}
