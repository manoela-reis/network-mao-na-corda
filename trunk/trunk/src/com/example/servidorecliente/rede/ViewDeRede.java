package com.example.servidorecliente.rede;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.MaoNaCorda.ImageManager;
import com.example.servidorecliente.CoolD;
import com.example.servidorecliente.ElMatador;
import com.example.servidorecliente.MainActivity;
import com.example.servidorecliente.bean.Jogador;

public class ViewDeRede extends View implements Runnable, Killable,
		ItensAplicaveis {

	// meninas.

	private static final String TAG = "view-rede";
	private static final int UPDATE_TIME = 100;
	// private Paint paint;
	private long time = 1;

	private ConcurrentHashMap<String, Jogador> jogadores;
	private ControleDeUsuariosCliente tratadorDeDadosDoCliente;
	public DadosDoCliente dadosDoCliente;

	private boolean ativo = true;
	Jogador jogadoor;
	private String nick;

	Boolean Play1;
	// meninas
	int q;
	int r;
	int p;
	int t;
	int a;
	int b;
	Paint paint = new Paint();
	Boolean possivel = false;
	static Rect atual = new Rect();
	static Rect corda = new Rect();
	static Rect[] Barrinhas = new Rect[3];

	private int counter;
	private int period = 100;
	private int current;
	static float positionX = 40;
	private static float positionY = 30;
	private static float Width = 30;
	private static float Height = 30;
	ImageManager img;
	Bitmap imagem;
	Bitmap impulso;
	Bitmap velocidade;
	Bitmap massa;
	int Forca;
	int Num_impulso;
	Boolean impp = false;
	private int segTouchX;
	private int segTouchY;
	String SegTouch;
	String PriTouch;
	private Queue<MotionEvent> fila;
	private SparseArray<PointF> dedos = new SparseArray<PointF>();
	Conexao cliente;
	ItensManager intensManager = new ItensManager();
	// Vitoria

	int n = 0;
	CoolD coolD = new CoolD();

	Bitmap itemEsp;

	public ViewDeRede(Context context, Conexao cliente,
			ControleDeUsuariosCliente tratadorDeDadosDoCliente) {

		super(context);
		ElMatador.getInstance().add(this);

		this.tratadorDeDadosDoCliente = tratadorDeDadosDoCliente;
		// this.tratadorDeDadosDoCliente.iniciarJogo();
		this.cliente = cliente;

		// envia estado atual do cliente para o servidor
		dadosDoCliente = new DadosDoCliente(cliente, UPDATE_TIME);
		Thread threadDados = new Thread(dadosDoCliente);
		threadDados.start();
		// jogadoor=cliente.getJogador();

		Jogador meuJogador = MainActivity.GetInstance().getPlayer();
		// primeira mensagem
		cliente.write(Protocolo.PROTOCOL_ID + "," + meuJogador.getID() + ","
				+ meuJogador.getX() + "," + meuJogador.getPatente());

		nick = meuJogador.getID();
		if (nick == "Player 1") {
			Play1 = true;
		} else {
			Play1 = false;
		}
		// meninas
		setFocusableInTouchMode(true);
		setClickable(true);
		setLongClickable(true);
		fila = new LinkedList<MotionEvent>();
		paint.setColor(Color.BLACK);
		img = new ImageManager((int) positionY * 2, (int) positionY * 2);
		imagem = img.ImageManager("Game_Guerra.png", context);
		impulso = img.ImageManager("amarelo.png", context);
		velocidade = img.ImageManager("amarelo.png", context);
		massa = img.ImageManager("amarelo.png", context);

		// VITORIA
		itemEsp = img.ImageManager("amarelo.png", context);

		Thread processo = new Thread(this);
		processo.start();

	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		int larguraItem = (int) getWidth() / 10;
		int alturaItem = (int) getHeight() / 10;

		intensManager.CreateItens(larguraItem, alturaItem, Play1);

		int larguraBarra = (int) getWidth() / 15;
		int alturaBarra = (int) getHeight() / 15;
		createBarrinhas(larguraBarra, alturaBarra);

		Width = getWidth() / 2;
		Height = getHeight() / 2;
		positionY = Height;
		positionX = Width;

		// dadosDoCliente.setX((int)positionX);
		// dadosDoCliente.setY((int)positionY);

	}

	public void createBarrinhas(int larguraBarra, int alturaBarra) {

		Barrinhas[0] = new Rect(larguraBarra, 2 * alturaBarra,
				(int) (1.5f * larguraBarra), (int) (2.5f * alturaBarra));
		Barrinhas[1] = new Rect(larguraBarra, 3 * alturaBarra,
				(int) (1.5f * larguraBarra), (int) (3.5f * alturaBarra));
		Barrinhas[2] = new Rect(larguraBarra, (4 * alturaBarra),
				(int) (1.5f * larguraBarra), (int) (4.5f * alturaBarra));
		dadosDoCliente.setImpX(Barrinhas[0].right);
		dadosDoCliente.setMasX(Barrinhas[2].right);
		dadosDoCliente.setVelX(Barrinhas[1].right);
	}

	public boolean onTouchEvent(MotionEvent event) {
		fila.add(event);
		processEventQueue();

		return super.onTouchEvent(event);
	}

	public void processEventQueue() {

		MotionEvent event = (MotionEvent) fila.poll();
		if (event != null) {

			int action = MotionEventCompat.getActionMasked(event);

			if (action == MotionEvent.ACTION_DOWN) {
				int id = event.getPointerId(event.getActionIndex());

				q = (int) event.getX(id);
				r = (int) event.getY(id);
				Log.d("vamos", "" + q);
				intensManager.detectar(q, r);
				if (intensManager.Detectado()) {
					PriTouch = intensManager.ItemAtual();
					if (PriTouch != "ItemEsp") {
						BotItem();
					} else {
						BotItemEsp();
					}

				}

				if (!possivel) {
					if (corda.contains(q, r)) {
						possivel = true;
						impp = false;
						PriTouch = "corda";
					}
				}
				PointF point = new PointF(event.getX(id), event.getY(id));

				dedos.put(id, point);

				Log.i("foi", "down baby down !! " + PriTouch);

			}
			if (action == MotionEvent.ACTION_POINTER_DOWN) {

				int id = event.getPointerId(event.getActionIndex());

				segTouchX = (int) event.getX(id);
				segTouchY = (int) event.getY(id);

				Log.d("vamos", "" + segTouchX + impp.booleanValue());
				if (impp == false) {
					intensManager.detectar(segTouchX, segTouchY);
					if (intensManager.Detectado()) {
						SegTouch = intensManager.ItemAtual();
						if (SegTouch != "ItemEsp") {
							BotItem();
						} else {
							BotItemEsp();
						}

					}

				}

				if (possivel == false) {
					if (corda.contains(segTouchX, segTouchY)) {
						SegTouch = "corda";
						possivel = true;
					}
				}

				PointF point = new PointF(event.getX(id), event.getY(id));

				dedos.put(id, point);
				Log.i("foi", "dsegundo !! " + SegTouch + "primeiro:  "
						+ PriTouch + "boool:   " + impp.booleanValue());

			}

			if (action == MotionEvent.ACTION_POINTER_UP) {
				Log.i("foi", "dsegundo UP!! ");
				int id = event.getPointerId(event.getActionIndex());
				p = (int) event.getX(id);
				t = (int) event.getY(id);

				if (dedos.get(id) != null) {

					PointF point = (PointF) dedos.get(id);
					if (possivel) {

						if (corda.contains(p, t)) {

							if (PriTouch == "corda") {

								aplicarForca((int) (p - q) / 3 * Num_impulso);
								possivel = false;

							} else {
								if (SegTouch == "corda") {

									aplicarForca((int) (p - segTouchX) / 3
											* Num_impulso);
									possivel = false;

								}
							}
						}

					}
					if (impp) {
						if (point.x == q && PriTouch != "corda") {
							intensManager.detectar(p, t);
							if (intensManager.Detectado()) {
								if (intensManager.ItemAtual() != "ItemEsp") {
									calcularItens();
								} else {
									calcularItemEsp();
								}
							} else {
								impp = false;
								PriTouch = SegTouch;
								SegTouch = "";

							}
						}
						if (point.x == segTouchX && SegTouch != "corda") {
							intensManager.detectar(p, t);
							if (intensManager.Detectado()) {
								if (intensManager.ItemAtual() != "ItemEsp") {
									calcularItens();
								} else {
									calcularItemEsp();
								}
							} else {
								impp = false;
								SegTouch = "";

								Log.i("foi", "dsegundo UP!! " + SegTouch
										+ "primeiro:  " + PriTouch
										+ "boool:   " + impp.booleanValue());

							}

						}
					}

					dedos.remove(id);
				}
			}

			if (action == MotionEvent.ACTION_MOVE) {
				Log.i("fooi", "movendoooo");

				// int d = event.getPointerId(event.getActionIndex());
				for (int i = 0; i < event.getPointerCount(); i++) {
					int id = event.getPointerId(i);

					int u = (int) event.getX(id);
					int z = (int) event.getY(id);
					PointF point = (PointF) dedos.get(id);
					if (point != null) {
						if ((int) point.x == q) {
							if (possivel == true && PriTouch == "corda") {
								if (Play1 && q < event.getX(id)) {
									if (corda.contains((int) event.getX(id),
											(int) event.getY(id))) {
										positionX = event.getX(id) - 10;
									} else {
										aplicarForca((int) ((event.getX(id) - q) / 3 * Num_impulso));
										possivel = false;
										dedos.remove(id);
									}
								}
								if (!Play1 && q > event.getX(id)) {
									if (corda.contains((int) event.getX(id),
											(int) event.getY(id))) {
										positionX = event.getX(id) + 10;
									} else {
										aplicarForca((int) ((q - event.getX(id)) / 3 * Num_impulso));
										possivel = false;
										dedos.remove(id);
									}
								}

							}
						}
						if ((int) point.x == segTouchX && SegTouch == "corda") {
							if (Play1 && segTouchX < event.getX(id)) {
								if (corda.contains((int) event.getX(id),
										corda.centerY())) {
									positionX = event.getX(id) - 10;
								} else {
									aplicarForca((int) ((event.getX(id) - segTouchX) / 3));
									possivel = false;
									dedos.remove(id);
								}

							}
							if (!Play1 && segTouchX > event.getX(id)) {
								if (corda.contains((int) event.getX(id),
										corda.centerY())) {
									positionX = event.getX(id) + 10;
								} else {
									aplicarForca((int) ((segTouchX - event
											.getX(id)) / 3));
									possivel = false;
									dedos.remove(id);
								}

							}

						}

						if (impp) {
							if ((int) point.x == q && PriTouch != "corda") {
								intensManager.detectar(u, z);
								if (intensManager.Detectado()) {

								} else {

									Log.i("fooi", "entroooooooooou");
									impp = false;
									PriTouch = SegTouch;
									SegTouch = "";
									dedos.remove(id);
								}

							}
							if ((int) point.x == segTouchX
									&& SegTouch != "corda") {
								if ((int) point.x == q && PriTouch != "corda") {
									intensManager.detectar(u, z);
									if (intensManager.Detectado()) {

									} else {
										impp = false;
										SegTouch = "";
										dedos.remove(id);
									}

								}

							}
						}

					}
				}
			}
			if (action == MotionEvent.ACTION_UP) {
				int id = event.getPointerId(event.getActionIndex());

				PointF point = (PointF) dedos.get(id);
				a = (int) event.getX(id);
				b = (int) event.getY(id);
				if (possivel) {
					if (corda.contains(a, b)) {

						if (PriTouch == "corda") {

							aplicarForca((int) (a - q) / 3 * Num_impulso);
							possivel = false;

						}
						if (SegTouch == "corda") {

							aplicarForca((int) (a - segTouchX) / 3
									* Num_impulso);
							possivel = false;

						}

					}
				}
				if (impp) {
					if (point.x == q && PriTouch != "corda") {
						intensManager.detectar(a, b);
						if (intensManager.Detectado()) {
							if (intensManager.ItemAtual() != "ItemEsp") {
								calcularItens();
							} else {
								Log.i(TAG, "Entrou no up do item especial ");
								CoolD.CoolDown = true;
								CoolD.coolDownTime = 10;
								Log.i(TAG, "Entrou1:" + CoolD.CoolDown);

								calcularItemEsp();
								Log.i(TAG, "Entrou2:" + CoolD.CoolDown);
							}
						} else {
							impp = false;
							PriTouch = SegTouch;
							SegTouch = "";

						}
					}
					if (point.x == segTouchX && SegTouch != "corda") {

						intensManager.detectar(a, b);
						if (intensManager.Detectado()) {
							if (intensManager.ItemAtual() != "ItemEsp") {
								calcularItens();
							} else {
								calcularItemEsp();
							}

						} else {
							impp = false;
							SegTouch = "";

						}
					}
				}

				Log.i("foi", "UP !!!" + SegTouch + "primeiro:  " + PriTouch
						+ "boool:   " + impp.booleanValue());
				dedos.remove(id);
			}
		}

	}

	public void BotItemEsp() {
		impp = true;
		// current = period;
	}

	public void calcularItemEsp() {

		impp = false;

		Log.i("partida", "" + n);

		if (CoolD.item_Selecionado == 0) {
			Num_impulso = 10000;
			CoolD.CoolDown = true;
			CoolD.coolDownTime = 10;

		}
		if (CoolD.item_Selecionado == 1) {
			Num_impulso = 20000;
			CoolD.CoolDown = true;
			CoolD.coolDownTime = 10;

		}
		if (CoolD.item_Selecionado == 2) {
			Num_impulso = 30000;
			CoolD.CoolDown = true;
			CoolD.coolDownTime = 10;

		}

		if (PriTouch == "Velocidade") {
			PriTouch = SegTouch;
			SegTouch = "";
			q = segTouchX;
		}

	}

	public void BotItem() {
		impp = true;
		current = period;

	}

	public void aplicarForca(int i) {

		positionX = Width;

		if (Num_impulso == 30000) {
			if (Barrinhas[0].right - Barrinhas[0].left >= 3) {
				Forca = (int) (Forca * 1.3f);
				dadosDoCliente.setImpX(Barrinhas[0].right-3);
				dadosDoCliente.setX(atual.left+20);
			} else {

				Forca = (int) (Forca * 1.1f);
				dadosDoCliente.setImpX(Barrinhas[0].left);
				dadosDoCliente.setX(atual.left+20);
			}

		}
		if (Num_impulso == 20000) {
			if (Barrinhas[2].right - Barrinhas[2].left >= 3) {
				Forca = (int) (Forca * 1.4f);
				dadosDoCliente.setMasX(Barrinhas[2].right-3);
				dadosDoCliente.setX(atual.left+20);
			} else {

				Forca = (int) (Forca * 1.2f);
				dadosDoCliente.setMasX(Barrinhas[2].left);
				dadosDoCliente.setX(atual.left+20);
				paint.setColor(Color.BLUE);
			}

		}
		if (Num_impulso == 10000) {
			if (Barrinhas[1].right - Barrinhas[1].left >= 3) {
				Forca = (int) (Forca * 1.8f);
				dadosDoCliente.setX(atual.left+20);
				dadosDoCliente.setVelX(Barrinhas[1].right-3);
				
			} else {

				Forca = (int) (Forca * 1.3f);
				dadosDoCliente.setX(atual.left+20);
				dadosDoCliente.setVelX(Barrinhas[1].left);
				
			}

		}
		Forca = i;
		possivel = false;

	}

	public void calcularItens() {
		impp = false;
		dadosDoCliente.setX(0);

		int larguraBarra = (int) getWidth() / 15;
		int alturaBarra = (int) getHeight() / 15;

		Barrinhas[0].set(larguraBarra, 2 * alturaBarra,
				(int) (1.5f * larguraBarra), (int) (2.5f * alturaBarra));
		if (current - counter <= 100) {
			Num_impulso = 30000;
			current = 0;

		}
		if (PriTouch == intensManager.StatusRect) {
			PriTouch = SegTouch;
			SegTouch = "";
			q = segTouchX;
		}

	}

	public void draw(Canvas canvas) {
		super.draw(canvas);
		ConcurrentHashMap<String, Jogador> jogadores = tratadorDeDadosDoCliente
				.getJogadores();

		Iterator<String> iterator = jogadores.keySet().iterator();

		while (iterator.hasNext()) {
			String keey = iterator.next();
			Jogador jogadorLindu = jogadores.get(keey);

			if (!jogadorLindu.isVisible()) {

				atual.set(0, 0, (int) Width * 2, (int) Height * 2);
				// PLAYER1
				if (Play1) {
					corda.set((int) positionX, (int) positionY,
							(int) positionX + 200, (int) positionY + 100);
				} else {
					corda.set((int) positionX - 200, (int) positionY,
							(int) positionX, (int) positionY + 100);

				}
				canvas.drawBitmap(imagem, null, atual, paint);
				canvas.drawBitmap(velocidade, null,
						intensManager.rectsItens.get(2), paint);
/*
				Iterator<String> iterato = jogadores.keySet().iterator();

				
				while (iterato.hasNext()) {
					String key = iterato.next();*/
					Jogador jogador = jogadores.get(nick);

					Barrinhas[0].right = jogador.getImpX();
					Barrinhas[2].right = jogador.getMasX();
					Barrinhas[1].right=jogador.getVelX();

					atual.left=jogador.getX();
					Log.e("Vieew", "" + jogador.getX());
				//}

				for(int i =0;i<Barrinhas.length;i++){
					canvas.drawRect(Barrinhas[i], paint);

				}

				canvas.drawBitmap(massa, null, intensManager.rectsItens.get(1),
						paint);
				canvas.drawBitmap(itemEsp, null,
						intensManager.rectsItens.get(3), paint);
				for(int i =0;i<intensManager.rectsItens.size();i++){
					canvas.drawRect(intensManager.rectsItens.get(i), paint);

				}
				canvas.drawRect(corda, paint);
				paint.setTextSize(20);

				canvas.drawText("forcaa" + Forca + "impulsooo" + Num_impulso,
						50, 30, paint);
				paint.setTextSize(10);

				canvas.drawText("Impulso", Barrinhas[0].left + 55,
						Barrinhas[0].bottom, paint);
				canvas.drawText("Velocidade", Barrinhas[2].left + 55,
						Barrinhas[2].bottom, paint);

				canvas.drawText("Massa", Barrinhas[1].left + 55,
						Barrinhas[1].bottom, paint);
			}
		}

	}

	public void run() {
		Log.i(TAG, "Entrou no run.");
		while (ativo) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				Log.e(MainActivity.TAG, "interrupcao do run()");
			}
			update();
			postInvalidate();
		}

	}

	private void update() {

		if (period != 0) {
			counter++;
		}
		coolD.updateCoolD();

		if (counter == 1000) {
			period--;
			counter = 0;
			current += 1000;
			if (current - counter >= 100) {

				intensManager.updateItensManager(SegTouch, PriTouch, Barrinhas, dadosDoCliente, 3);
		
			}

		}

	}

	public void killMeSoftly() {
		ativo = false;
	}

}
