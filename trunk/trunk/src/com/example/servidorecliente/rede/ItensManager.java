package com.example.servidorecliente.rede;

import java.util.ArrayList;

import com.example.servidorecliente.CoolD;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class ItensManager {

	private Rect Casca = new Rect();
	private Rect Massa = new Rect();
	private Rect Dinamite = new Rect();

	public ArrayList<Rect> rectsItens = new ArrayList<Rect>();
	public String StatusRect;
	Boolean detectado = false;

	// vitoria
	private Rect Energetico = new Rect();
	int altura;
	int largura;

	public ItensManager() {

		rectsItens.add(Casca);
		rectsItens.add(Massa);
		rectsItens.add(Dinamite);
		rectsItens.add(Energetico);

	}

	public void setDim(ViewDeRede view, Boolean player1, Bitmap Energ,Bitmap TNT, Bitmap cascaBan) {
		largura = view.larguraView;
		altura = view.alturaView;
		//int energ = Energ.getWidth()*(8*altura/30)/Energ.getHeight();
				int tnt=TNT.getWidth()*(8*altura/30)/TNT.getHeight();
				int energ=tnt;
				int casca = cascaBan.getWidth()*(8*altura/30)/cascaBan.getHeight();
				if(player1) {
			Energetico.set(largura / 20, (int) (21*altura / 30),
					(int) (largura /20+energ), (int) (29*altura / 30));
			Casca.set((int) (largura /20+energ+largura/30), (int) (21*altura / 30),
					(int) ((largura /20+energ+largura/30)+tnt), (int) (29*altura / 30));
			Dinamite.set((int) (((largura /20+energ+largura/30)+tnt)+largura/30), (int) (21*altura / 30),
					(int) ((((largura /20+energ+largura/30)+tnt)+largura/30)+casca), (int) (29*altura / 30));
		//	Energetico.set((int) (largura / 1.75), (int) (altura / 1.4f),
			//		(int) (largura / 1.45f), (int) (altura / 1.04f));

			/*
			 * Velocidade.set((int) (2.3f * larguraItem), 6 * alturaItem, (int)
			 * (3.3f * larguraItem), (int) (7.5f * alturaItem)); Massa.set((int)
			 * (3.5f * larguraItem), 6 * alturaItem, (int) (4.5f * larguraItem),
			 * (int) (7.5f * alturaItem)); ItemEsp.set((int) (6.5f *
			 * larguraItem), 2 * alturaItem, (int) (7.8f * larguraItem), (int)
			 * (3 * alturaItem));
			 */
		} else {
			


			Energetico.set((((19*largura /20-tnt)-largura/30))-casca-largura/30-energ, (int) (21*altura / 30),
					(int) (((19*largura /20-tnt)-largura/30))-casca-largura/30, (int) (29*altura / 30));
			Casca.set((int) ((19*largura /20-tnt)-largura/30)-casca, (int) (21*altura / 30),
					(int) (19*largura /20-tnt)-largura/30, (int) (29*altura / 30));
			Dinamite.set((int) (19*largura /20-tnt), (int) (21*altura / 30),
					(int) (19*largura /20), (int) (29*altura / 30));

			/*
			 * Impulso.set((int)(5.5*larguraItem), 6 * alturaItem, (int) (6.5f *
			 * larguraItem), (int) (7.5 * alturaItem)); Velocidade.set((int)
			 * (6.8 * larguraItem), 6 * alturaItem, (int) (7.8 * larguraItem),
			 * (int) (7.5f * alturaItem)); Massa.set((int) (8 * larguraItem), 6
			 * * alturaItem, (int) (9 * larguraItem), (int) (7.5f *
			 * alturaItem));
			 */
		}

	}

	/*
	 * public void CreateItens(int larguraItem, int alturaItem, Boolean player1)
	 * {
	 * 
	 * rectsItens.add(Impulso); rectsItens.add(Massa);
	 * rectsItens.add(Velocidade); rectsItens.add(ItemEsp);
	 * 
	 * if(player1){ Impulso.set(larguraItem, 6 * alturaItem, (int) (2.1f *
	 * larguraItem), (int) (7.5f * alturaItem)); if(player1) {
	 * Impulso.set(larguraItem, 6 * alturaItem, (int) (2.1f * larguraItem),
	 * (int) (7.5f * alturaItem)); Velocidade.set((int) (2.3f * larguraItem), 6
	 * * alturaItem, (int) (3.3f * larguraItem), (int) (7.5f * alturaItem));
	 * Massa.set((int) (3.5f * larguraItem), 6 * alturaItem, (int) (4.5f *
	 * larguraItem), (int) (7.5f * alturaItem)); ItemEsp.set((int) (6.5f *
	 * larguraItem), 2 * alturaItem, (int) (7.8f * larguraItem), (int) (3 *
	 * alturaItem)); }else{ Impulso.set((int)(5.5*larguraItem), 6 * alturaItem,
	 * (int) (6.5f * larguraItem), (int) (7.5 * alturaItem)); } else {
	 * Impulso.set((int)(5.5*larguraItem), 6 * alturaItem, (int) (6.5f *
	 * larguraItem), (int) (7.5 * alturaItem)); Velocidade.set((int) (6.8 *
	 * larguraItem), 6 * alturaItem, (int) (7.8 * larguraItem), (int) (7.5f *
	 * alturaItem)); Massa.set((int) (8 * larguraItem), 6 * alturaItem, (int) (9
	 * * larguraItem), (int) (7.5f * alturaItem)); ItemEsp.set((int) (6.5f *
	 * larguraItem), 2 * alturaItem, (int) (7.8f * larguraItem), (int) (3 *
	 * alturaItem)); }
	 * 
	 * } }
	 */

	public void detectar(int xPonto, int yPonto) {
		detectado = false;

		if (rectsItens.get(0).contains(xPonto, yPonto)) {
			// BotImpulso();
			detectado = true;
			StatusRect = "Casca";

		}
		if (rectsItens.get(2).contains(xPonto, yPonto)) {
			// BotVelocidade();
			detectado = true;
			StatusRect = "Dinamite";

		}
		// if (rectsItens.get(1).contains(xPonto, yPonto)) {
		// // BotMassa();
		// detectado = true;
		// StatusRect = "Impulso";
		//
		// }
		if (rectsItens.get(3).contains(xPonto, yPonto)) {
			// //BotIten();
			detectado = true;
			StatusRect = "Energetico";

			// CoolD.CoolDown = true;

		}

	}

	public Boolean Detectado() {
		return detectado;
	}

	public String ItemAtual() {
		return StatusRect;
	}

	public void updateItensManager(String SegTouch, String PriTouch,
			Rect[] Barrinhas, DadosDoCliente dadosDoCliente, int numberSet,
			int x, Boolean Energ, Boolean Casca, Boolean Dinamite) {

		if (Energ) {
			if (SegTouch == "Energetico" || PriTouch == "Energetico") {
				dadosDoCliente.setImpX((x - 3));
				CoolD.item_Selecionado = 0;

			}
		}
		if (Casca) {
			if (SegTouch == "Casca" || PriTouch == "Casca") {
				dadosDoCliente.setImpX((x - 7));
				CoolD.item_Selecionado = 1;

			}
		}
		if (Dinamite) {
			if (SegTouch == "Dinamite" || PriTouch == "Dinamite") {
				dadosDoCliente.setImpX((x - 10));
				CoolD.item_Selecionado = 2;

			}
		}
	}
}