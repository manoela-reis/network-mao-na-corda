package com.example.servidorecliente.rede;

import java.util.ArrayList;

import android.graphics.Rect;

public class ItensManager {

	private Rect Impulso = new Rect();
	private Rect Massa = new Rect();
	private Rect Velocidade = new Rect();

	public ArrayList<Rect> rectsItens = new ArrayList<Rect>();
	public String StatusRect;
	Boolean detectado = false;

	// vitoria
	private Rect ItemEsp = new Rect();

	public void CreateItens(int larguraItem, int alturaItem, Boolean player1) {

		rectsItens.add(Impulso);
		rectsItens.add(Massa);
		rectsItens.add(Velocidade);
		rectsItens.add(ItemEsp);

		if(player1){
			Impulso.set(larguraItem, 6 * alturaItem, (int) (2.1f * larguraItem),
					(int) (7.5f * alturaItem));
			Velocidade.set((int) (2.3f * larguraItem), 6 * alturaItem,
					(int) (3.3f * larguraItem), (int) (7.5f * alturaItem));
			Massa.set((int) (3.5f * larguraItem), 6 * alturaItem,
					(int) (4.5f * larguraItem), (int) (7.5f * alturaItem));
			ItemEsp.set((int) (6.5f * larguraItem), 2 * alturaItem,
					(int) (7.8f * larguraItem), (int) (3 * alturaItem));
		}else{
			Impulso.set((int)(5.5*larguraItem), 6 * alturaItem, (int) (6.5f * larguraItem),
					(int) (7.5 * alturaItem));
			Velocidade.set((int) (6.8 * larguraItem), 6 * alturaItem,
					(int) (7.8 * larguraItem), (int) (7.5f * alturaItem));
			Massa.set((int) (8 * larguraItem), 6 * alturaItem,
					(int) (9 * larguraItem), (int) (7.5f * alturaItem));
			ItemEsp.set((int) (6.5f * larguraItem), 2 * alturaItem,
					(int) (7.8f * larguraItem), (int) (3 * alturaItem));
		}
	
	}

	public void detectar(int xPonto, int yPonto) {
		detectado = false;

		if (rectsItens.get(0).contains(xPonto, yPonto)) {
			// BotImpulso();
			detectado = true;
			StatusRect = "Impulso";

		}
		if (rectsItens.get(2).contains(xPonto, yPonto)) {
			// BotVelocidade();
			detectado = true;
			StatusRect = "Velocidade";

		}
		if (rectsItens.get(1).contains(xPonto, yPonto)) {
			// BotMassa();
			detectado = true;
			StatusRect = "Massa";

		}
		if (rectsItens.get(3).contains(xPonto, yPonto)) {
			// //BotIten();
			detectado = true;
			StatusRect = "ItemEsp";

			// CoolD.CoolDown = true;

		}

	}

	
	public Boolean Detectado() {
		return detectado;
	}

	public String ItemAtual() {
		return StatusRect;
	}	
	
	public void updateItensManager(String SegTouch, String PriTouch, Rect[]Barrinhas, DadosDoCliente dadosDoCliente, int numberSet) {

		if (SegTouch == "Impulso" || PriTouch == "Impulso") {
			if (Barrinhas[0].right - Barrinhas[0].left < 50) {
				if (Barrinhas[0].right + 3 - Barrinhas[0].left > 50) {
					Barrinhas[0].right = 50 + Barrinhas[0].left;

				} else {
					dadosDoCliente.setX(numberSet);
				}
			}
		}
		if (SegTouch == "Velocidade" || PriTouch == "Velocidade") {
			if (Barrinhas[1].right - Barrinhas[1].left < 50) {
				if (Barrinhas[1].right + 3
						- Barrinhas[1].left > 50) {
					Barrinhas[1].right = 50 + Barrinhas[1].left;

				} else {
					dadosDoCliente.setX(numberSet);
				}
			}
		}
		if (SegTouch == "Massa" || PriTouch == "Massa") {
			if (Barrinhas[2].right - Barrinhas[2].left < 50) {
				if (Barrinhas[2].right + 3 - Barrinhas[2].left > 50) {
					Barrinhas[2].right = 50 + Barrinhas[2].left;

				} else {
					dadosDoCliente.setX(numberSet);
				}
			}
		}
	}
}
