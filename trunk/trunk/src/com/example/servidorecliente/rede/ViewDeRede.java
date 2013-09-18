package com.example.servidorecliente.rede;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.content.res.Resources;
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

import com.MaoNaCorda.BitmapManager;
import com.MaoNaCorda.ImageManager;
import com.example.servidorecliente.CoolD;
import com.example.servidorecliente.ElMatador;
import com.example.servidorecliente.MainActivity;
import com.example.servidorecliente.bean.Jogador;

public class ViewDeRede extends View implements Runnable, Killable,
		ItensAplicaveis {
	private static final String TAG = "view-rede";
	private String nick;
	String SegTouch;
	String PriTouch;

	private static final int UPDATE_TIME = 100;
	private long time = 1;

	private boolean ativo = true;
	Boolean possivel = false;
	Boolean Play1;
	Boolean impp = false;
	public static int larguraView;
	public static int alturaView;

	int q;
	int r;
	int p;
	int t;
	int a;
	int b;
	int Forca;
	int Num_impulso;
	private int counter;
	private int period = 100;
	private int current;
	private int segTouchX;
	private int segTouchY;
	int n = 0;

	static float positionX = 40;
	private static float positionY = 30;
	private static float Width = 30;
	private static float Height = 30;

	static Rect rectFundo = new Rect();
	static Rect maoPlayer = new Rect();
	static Rect[] Barrinhas = new Rect[3];

	private Rect rectPatente;
	private Rect rectZ;
	private Rect rectDivisor;

	private Bitmap fundo;
	private Bitmap impulso;
	private Bitmap velocidade;
	private Bitmap massa;
	private Bitmap itemEsp;
	private Bitmap patente;
	private Bitmap Z;
	private Bitmap divisor;

	Conexao cliente;
	Paint paint = new Paint();
	ImageManager img;
	ItensManager intensManager;
	CoolD coolD = new CoolD();
	BitmapManager bitmapManager;

	private Queue<MotionEvent> fila;
	private SparseArray<PointF> dedos = new SparseArray<PointF>();
	private ConcurrentHashMap<String, Jogador> jogadores;
	private ControleDeUsuariosCliente tratadorDeDadosDoCliente;
	public DadosDoCliente dadosDoCliente;

	public static Resources res;
	Rect atual = new Rect();
	

	//thyago
	Sprite sprite;
	Bitmap mao;
	private Rect rectMao;	
	//thyago
	

	public static long deltaTime;
	public long lastTimeCount;

	Rect rectzinhoTeste;

	int posicaoCorda = 0;

	private int acionar = -1;

	private int itemEspecial = -1;

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
		
		setFocusableInTouchMode(true);
		setClickable(true);
		setLongClickable(true);
		fila = new LinkedList<MotionEvent>();
		paint.setColor(Color.BLACK);

		bitmapManager = new BitmapManager(context);
		intensManager = new ItensManager();
		img = new ImageManager(context);

		
		mao = img.ImageManager("tileset_maotileset_maoPlayer1.png");		
		sprite = new Sprite(mao, 4, 1);
	

		Thread processo = new Thread(this);
		processo.start();

		res = getResources();

	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		int larguraItem = (int) getWidth() / 10;
		int alturaItem = (int) getHeight() / 10;

		// intensManager.CreateItens(larguraItem, alturaItem, Play1);
		larguraView = getWidth();
		alturaView = getHeight();
		int larguraBarra = (int) getWidth() / 15;
		int alturaBarra = (int) getHeight() / 15;
		createBarrinhas(larguraBarra, alturaBarra);

		bitmapManager.setRect(this);
		intensManager.setDim(this, Play1);
		Width = getWidth() / 2;
		Height = getHeight() / 2;
		positionY = Height;
		positionX = Width;

		rectzinhoTeste = new Rect();
		rectzinhoTeste.set((int) Width - getWidth() / 6, (int) Height
				- getHeight() / 10, (int) Width + getWidth() / 6, (int) Height
				+ getHeight() / 10);

		// Carregando Bitmpas.
		fundo = BitmapManager.GetInstance().getImageFundo();
		impulso = BitmapManager.GetInstance().getImageImpulso();
		massa = BitmapManager.GetInstance().getImageMassa();
		velocidade = BitmapManager.GetInstance().getImageVelocidade();
		itemEsp = BitmapManager.GetInstance().getImageEnergitco();
		patente = BitmapManager.GetInstance().getImagePatente();
		Z = BitmapManager.GetInstance().getImageZ();
		divisor = BitmapManager.GetInstance().getImageDivisor();

		// Carregando rects.
		rectFundo = BitmapManager.GetInstance().getRectFundo();
		rectPatente = BitmapManager.GetInstance().getRectPatente();
		rectZ = BitmapManager.GetInstance().getRectZ();
		rectDivisor = BitmapManager.GetInstance().getRectDivisor();

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
					if (maoPlayer.contains(q, r)) {
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
					if (maoPlayer.contains(segTouchX, segTouchY)) {
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

						if (maoPlayer.contains(p, t)) {

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
									if (maoPlayer.contains((int) event.getX(id),
											(int) event.getY(id))) {
										positionX = event.getX(id) - 10;
									} else {
										aplicarForca((int) ((event.getX(id) - q) / 3 * Num_impulso));
										possivel = false;
										dedos.remove(id);
									}
								}
								if (!Play1 && q > event.getX(id)) {
									if (maoPlayer.contains((int) event.getX(id),
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
								if (maoPlayer.contains((int) event.getX(id),
										maoPlayer.centerY())) {
									positionX = event.getX(id) - 10;
								} else {
									aplicarForca((int) ((event.getX(id) - segTouchX) / 3));
									possivel = false;
									dedos.remove(id);
								}

							}
							if (!Play1 && segTouchX > event.getX(id)) {
								if (maoPlayer.contains((int) event.getX(id),
										maoPlayer.centerY())) {
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
					if (maoPlayer.contains(a, b)) {

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

		// Log.i("partida", "" + n);
		dadosDoCliente.setItemEsp(coolD.item_Selecionado);

		if (CoolD.item_Selecionado == 0) {
			Num_impulso = 10000;
			CoolD.CoolDown = true;
			CoolD.coolDownTime = 2;
		}
		if (CoolD.item_Selecionado == 1) {
			Num_impulso = 20000;
			CoolD.CoolDown = true;
			CoolD.coolDownTime = 2;
		}
		if (CoolD.item_Selecionado == 2) {
			Num_impulso = 30000;
			CoolD.CoolDown = true;
			CoolD.coolDownTime = 2;
		}

		// if (PriTouch == "Velocidade") {
		// PriTouch = SegTouch;
		// SegTouch = "";
		// q = segTouchX;
		// }

	}

	public void BotItem() {
		impp = true;
		current = period;

	}

	public void aplicarForca(int i) {

		positionX = Width;
		if (Play1) {
			dadosDoCliente.setX(posicaoCorda + 1);
		} else {
			dadosDoCliente.setX(posicaoCorda - 1);
		}
		if (Num_impulso == 30000) {
			if (Barrinhas[0].right - Barrinhas[0].left >= 3) {
				Forca = (int) (Forca * 1.3f);
				dadosDoCliente.setImpX(Barrinhas[0].right - 3);
			} else {

				Forca = (int) (Forca * 1.1f);
				dadosDoCliente.setImpX(Barrinhas[0].left);
			}

		}
		if (Num_impulso == 20000) {
			if (Barrinhas[2].right - Barrinhas[2].left >= 3) {
				Forca = (int) (Forca * 1.4f);
				dadosDoCliente.setMasX(Barrinhas[2].right - 3);
			} else {

				Forca = (int) (Forca * 1.2f);
				dadosDoCliente.setMasX(Barrinhas[2].left);
				// dadosDoCliente.setX(atual.left + 20);
				paint.setColor(Color.BLUE);

			}

		}
		if (Num_impulso == 10000) {
			if (Barrinhas[1].right - Barrinhas[1].left >= 3) {
				Forca = (int) (Forca * 1.8f);
				dadosDoCliente.setVelX(Barrinhas[1].right - 3);

			} else {

				Forca = (int) (Forca * 1.3f);
				dadosDoCliente.setVelX(Barrinhas[1].left);

			}

		}
		Forca = i;
		possivel = false;
	}

	public void calcularItens() {
		impp = false;
		dadosDoCliente.setX(-70);

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

		// rectFundo.set(0, 0, getWidth(),getHeight());

		while (iterator.hasNext()) {
			String keey = iterator.next();
			Jogador jogadorLindu = jogadores.get(keey);
			if (jogadorLindu.getVitoria()) {
				paint.setColor(Color.MAGENTA);
				canvas.drawRect(0, 0, getWidth(), getHeight(), paint);// como se
																		// fosse
																		// a
																		// tela
																		// de
																		// vit�ria(fim
																		// de
																		// jogo)
			}
			if (!jogadorLindu.isVisible()) {

				if (jogadorLindu.getX() != posicaoCorda
						&& jogadorLindu.getX() != -70) {
					posicaoCorda = jogadorLindu.getX();
					rectzinhoTeste.left = (20 + posicaoCorda) * getWidth() / 40
							- getWidth() / 6;
					rectzinhoTeste.right = (int) (rectzinhoTeste.left + 2 * getWidth() / 6);
					dadosDoCliente.setX(-70);
				}

				// PLAYER1
				if (Play1) {
					maoPlayer.set((int) positionX, (int) positionY,
							(int) positionX + 200, (int) positionY + 100);
					if (posicaoCorda >= 20) {
						dadosDoCliente.finalizar();
					}
				} else {
					maoPlayer.set((int) positionX - 200, (int) positionY,
							(int) positionX, (int) positionY + 100);
					if (posicaoCorda <= -20) {
						dadosDoCliente.finalizar();
					}

				}

				canvas.drawBitmap(fundo, null, rectFundo, paint);
				canvas.drawBitmap(impulso, null,
						intensManager.rectsItens.get(0), paint);
				canvas.drawBitmap(massa, null, intensManager.rectsItens.get(1),
						paint);
				canvas.drawBitmap(velocidade, null,
						intensManager.rectsItens.get(2), paint);
				canvas.drawBitmap(itemEsp, null,
						intensManager.rectsItens.get(3), paint);

				canvas.drawBitmap(patente, null, rectPatente, paint);
				canvas.drawBitmap(Z, null, rectZ, paint);
				canvas.drawBitmap(divisor, null, rectDivisor, paint);

				sprite.Draw(canvas,maoPlayer);

				Iterator<String> iterato = jogadores.keySet().iterator();

				Jogador jogador = jogadores.get(nick);

				Barrinhas[0].right = jogador.getImpX();
				Barrinhas[2].right = jogador.getMasX();
				Barrinhas[1].right = jogador.getVelX();

				canvas.drawRect(rectzinhoTeste, paint);// desenha o rect de este
														// de vi�ria

				for (int i = 0; i < Barrinhas.length; i++) {
					canvas.drawRect(Barrinhas[i], paint);

				}

				if (itemEspecial < 0 && jogador.getItemEspecial() != -1) {

					itemEspecial = jogador.getItemEspecial();
					// Aqui vem o trecho em que trata o item especial , as
					// modificacoes que ocorrerao no jogador
				}
				if (acionar <= 0 && jogador.getItemAcionado() != -1) {

					acionar = jogador.getItemAcionado();

				}
				if (itemEspecial >= 0 && jogador.getItemEspecial() < 0) {

					itemEspecial = jogador.getItemEspecial();
				}
				if (acionar >= 0 && jogador.getItemAcionado() < 0) {

					acionar = jogador.getItemAcionado();
				}
				
				paint.setTextSize(20);

				if (itemEspecial != -1 || acionar != -1) {
					canvas.drawText("Item Esp" + itemEspecial + "Item Acio"
							+ acionar, 50, 70, paint);
				}
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
				
		this.deltaTime = System.currentTimeMillis() - this.lastTimeCount;
		this.lastTimeCount = System.currentTimeMillis();		
				
		this.sprite.Update(this.deltaTime);

		if (period != 0) {
			counter++;
		}
		coolD.updateCoolD(dadosDoCliente);

		if (counter == 1000) {
			period--;
			counter = 0;
			current += 1000;
			if (current - counter >= 100) {

				intensManager.updateItensManager(SegTouch, PriTouch, Barrinhas,
						dadosDoCliente, 3);

			}

		}

	}

	public void killMeSoftly() {
		ativo = false;
	}

}