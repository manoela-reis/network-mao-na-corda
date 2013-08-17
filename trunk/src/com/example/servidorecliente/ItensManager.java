package com.example.servidorecliente;

import java.util.ArrayList;

import android.graphics.PointF;
import android.graphics.Rect;

public class ItensManager {

	private Rect Impulso = new Rect();
	private Rect Massa = new Rect();
	private Rect Velocidade = new Rect();

	public ArrayList<Rect> rectsItens = new ArrayList<Rect>();
	String StatusRect;
	Boolean detectado = false;

	// vitoria
	private Rect ItemEsp = new Rect();

	public void CreateItens(int larguraItem, int alturaItem) {

		rectsItens.add(Impulso);
		rectsItens.add(Massa);
		rectsItens.add(Velocidade);
		rectsItens.add(ItemEsp);

		Impulso.set(larguraItem, 6 * alturaItem, (int) (2.1f * larguraItem),
				(int) (7.5f * alturaItem));
		Velocidade.set((int) (2.3f * larguraItem), 6 * alturaItem,
				(int) (3.3f * larguraItem), (int) (7.5f * alturaItem));
		Massa.set((int) (3.5f * larguraItem), 6 * alturaItem,
				(int) (4.5f * larguraItem), (int) (7.5f * alturaItem));
		ItemEsp.set((int) (6.5f * larguraItem), 2 * alturaItem,
				(int) (7.8f * larguraItem), (int) (3 * alturaItem));
	}

	public void detectar(int xPonto, int yPonto) {
		detectado=false;

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

}
