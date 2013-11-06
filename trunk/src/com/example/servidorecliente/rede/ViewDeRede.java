package com.example.servidorecliente.rede;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import android.R;
import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.MaoNaCorda.BitmapManager;
import com.MaoNaCorda.ImageManager;
import com.example.servidorecliente.CoolD;
import com.example.servidorecliente.ElMatador;
import com.example.servidorecliente.EsperaServer;
import com.example.servidorecliente.MainActivity;
import com.example.servidorecliente.bean.Jogador;
import com.example.servidorecliente.util.RedeUtil;

public class ViewDeRede extends View implements Runnable, Killable,
		ItensAplicaveis {
	private static final String TAG = "view-rede";
	private String nick;
	String SegTouch;
	String PriTouch;

	private static final int UPDATE_TIME = 100;
	private Bitmap Serverfundo;
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
	private Bitmap ImgCasca;
	private Bitmap ImgTNT;
	private Bitmap ImgEnergetico;
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

	// thyago
	Sprite sprite;
	Bitmap mao;
	private Rect rectMao;
	// thyago

	public static long deltaTime;
	public long lastTimeCount;

	int posicaoCorda = 0;

	private int acionar = -1;

	private int itemEspecial = -1;

	private Rect Corda;
	Bitmap ImgCorda;
	private Rect CordaFull;
	Bitmap ImgCordaF;

	Bitmap ImgMaoRosa;

	private int status = 0;
	private boolean Energetico = false;
	private boolean Casca = false;
	private boolean TNT = false;
	Bitmap barra;
	private Rect maoPlayerAdv = new Rect();
	private Bitmap maoAdv;
	Sprite spriteMaoAdv;

	private Rect CascaRect = new Rect();
	private Bitmap ImgCasca1;
	private Bitmap ImgCasca2;
	Sprite spriteCasca1;
	Sprite spriteCasca2;

	private Bitmap ImgTNT1;
	private Bitmap ImgTNT2;
	Sprite spriteTNT1;
	Sprite spriteTNT2;

	public Boolean autorizado = true;
	Activity espera;

	private Rect rectMarcador;

	private Bitmap marcador;
	private Bitmap Vitoria;
	private Bitmap Derrota;

	public ViewDeRede(Activity context, Conexao cliente,
			ControleDeUsuariosCliente tratadorDeDadosDoCliente) {

		super(context);
		ElMatador.getInstance().add(this);

		// this.myview=view;
		this.espera = (Activity) context;
		this.tratadorDeDadosDoCliente = tratadorDeDadosDoCliente;
		// this.tratadorDeDadosDoCliente.iniciarJogo();
		this.cliente = cliente;

		dadosDoCliente = new DadosDoCliente(cliente, UPDATE_TIME);
		Thread threadDados = new Thread(dadosDoCliente);
		threadDados.start();
		// envia estado atual do cliente para o servido

		Jogador meuJogador = MainActivity.GetInstance().getPlayer();

		// primeira mensagem
		cliente.write(Protocolo.PROTOCOL_ID + "," + meuJogador.getID() + ","
				+ meuJogador.getX() + "," + meuJogador.getPatente());

		nick = meuJogador.getID();
		img = new ImageManager(context);

		ImgCorda = img.ImageManager("corda2.png");
		barra = img.ImageManager("barrinha_vermelha.png");
		if (nick == "Player 1") {
			Play1 = true;
			mao = img.ImageManager("tileset_maoPlayer2.png");
			maoAdv = img.ImageManager("tileset_maotileset_maoPlayer1.png");

			ImgMaoRosa = BitmapManager.GetInstance().getImageMaoRosa1();
		} else {
			Play1 = false;
			mao = img.ImageManager("tileset_maotileset_maoPlayer1.png");
			maoAdv = img.ImageManager("tileset_maoPlayer2.png");
			ImgMaoRosa = BitmapManager.GetInstance().getImageMaoRosa2();

		}

		setFocusableInTouchMode(true);
		setClickable(true);
		setLongClickable(true);
		fila = new LinkedList<MotionEvent>();
		paint.setColor(Color.BLACK);

		bitmapManager = new BitmapManager(context);
		intensManager = new ItensManager();

		ImgCordaF = BitmapManager.GetInstance().getimgCordaFull();
		sprite = new Sprite(mao, 4, 1);
		spriteMaoAdv = new Sprite(maoAdv, 4, 1);
		ImgCasca1 = BitmapManager.GetInstance().getImageCascaSprite1();

		ImgCasca2 = BitmapManager.GetInstance().getImageCascaSprite2();
		ImgTNT1 = BitmapManager.GetInstance().getImageTNTSprite1();

		ImgTNT2 = BitmapManager.GetInstance().getImageTNTSprite2();
		Serverfundo = BitmapManager.GetInstance().getfundoServer();
		Vitoria = BitmapManager.GetInstance().getImageVitoria();
		Derrota = BitmapManager.GetInstance().getImageDerrota();

		spriteCasca2 = new Sprite(ImgCasca2, 14, 7);
		spriteCasca1 = new Sprite(ImgCasca1, 14, 7);
		spriteTNT1 = new Sprite(ImgTNT1, 12, 4);
		spriteTNT2 = new Sprite(ImgTNT2, 12, 4);

		marcador = BitmapManager.GetInstance().getImageMarcador();
		// casca1 tem que ir de trás para frente
		// spriteCascaLanc.status=false;

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
		dadosDoCliente.setwidthFull(larguraView);

		int altIdealCorda = ImgCorda.getHeight() * getWidth()
				/ ImgCorda.getWidth();
		Corda = new Rect(0, 13 * getHeight() / 30, getWidth(), 13 * getHeight()
				/ 30 + altIdealCorda);

		CordaFull = new Rect(0, 13 * getHeight() / 30, getWidth(), 13
				* getHeight() / 30 + altIdealCorda);
		bitmapManager.setRect(this);
		Width = getWidth() / 2;
		Height = getHeight() / 2;
		positionY = 3 * Height / 8;
		// positionX = Width;
		CascaRect.set(0, 0, getWidth(), getHeight());

		int proporc = marcador.getWidth() * getHeight() / 20
				/ marcador.getHeight();
		if (Play1) {
			dadosDoCliente.setPosMao(2 * getWidth() / 3);

			rectMarcador = new Rect(7 * getWidth() / 10, 6 * getHeight() / 20,
					7 * getWidth() / 10 + proporc, 7 * getHeight() / 20);
		} else {
			dadosDoCliente.setPosMao(2 * getWidth() / 3);

			rectMarcador = new Rect(getWidth() / 10 - proporc,
					6 * getHeight() / 20, getWidth() / 10, 7 * getHeight() / 20);
		}

		dadosDoCliente.setX(0);
		// Carregando Bitmpas.
		fundo = BitmapManager.GetInstance().getImageFundo();
		ImgCasca = BitmapManager.GetInstance().getImageCasca();
		ImgTNT = BitmapManager.GetInstance().getImageTNT();
		ImgEnergetico = BitmapManager.GetInstance().getImageEnergitco();
		patente = BitmapManager.GetInstance().getImagePatente();
		Z = BitmapManager.GetInstance().getImageZ();
		divisor = BitmapManager.GetInstance().getImageDivisor();

		intensManager.setDim(this, Play1, ImgEnergetico, ImgTNT, ImgCasca);

		// Carregando rects.
		rectFundo = BitmapManager.GetInstance().getRectFundo();
		rectPatente = BitmapManager.GetInstance().getRectPatente();
		rectZ = BitmapManager.GetInstance().getRectZ();
		rectDivisor = BitmapManager.GetInstance().getRectDivisor();

		// dadosDoCliente.setX((int)positionX);
		// dadosDoCliente.setY((int)positionY);

	}

	public void createBarrinhas(int larguraBarra, int alturaBarra) {

		Barrinhas[0] = new Rect(3 * larguraBarra, 2 * alturaBarra,
				(int) (4.5f * larguraBarra), (int) (2.5f * alturaBarra));
		Barrinhas[1] = new Rect(larguraBarra, 3 * alturaBarra,
				(int) (1.5f * larguraBarra), (int) (3.5f * alturaBarra));
		Barrinhas[2] = new Rect(larguraBarra, (4 * alturaBarra),
				(int) (1.5f * larguraBarra), (int) (4.5f * alturaBarra));
		dadosDoCliente.setImpX(0);
		dadosDoCliente.setMasX(Barrinhas[2].right);
		dadosDoCliente.setVelX(Barrinhas[1].right);
	}

	public boolean onTouchEvent(MotionEvent event) {
		fila.add(event);
		if (autorizado) {
			processEventQueue();
		}
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

					BotItemEsp();

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

						BotItemEsp();

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

								calcularItemEsp();

							} else {
								impp = false;
								PriTouch = SegTouch;
								SegTouch = "";

							}
						}
						if (point.x == segTouchX && SegTouch != "corda") {
							intensManager.detectar(p, t);
							if (intensManager.Detectado()) {

								calcularItemEsp();

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
									if (maoPlayer.contains(
											(int) event.getX(id),
											(int) event.getY(id))) {
										dadosDoCliente
												.setPosMao(event.getX(id) - 10);
									} else {
										aplicarForca((int) ((event.getX(id) - q) / 3 * Num_impulso));
										possivel = false;
										dedos.remove(id);
									}
								}
								if (!Play1 && q > event.getX(id)) {
									if (maoPlayer.contains(
											(int) event.getX(id),
											(int) event.getY(id))) {
										dadosDoCliente
												.setPosMao(event.getX(id) + 10);
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
									dadosDoCliente
											.setPosMao(event.getX(id) - 10);
								} else {
									aplicarForca((int) ((event.getX(id) - segTouchX) / 3));
									possivel = false;
									dedos.remove(id);
								}

							}
							if (!Play1 && segTouchX > event.getX(id)) {
								if (maoPlayer.contains((int) event.getX(id),
										maoPlayer.centerY())) {
									dadosDoCliente
											.setPosMao(event.getX(id) + 10);
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

							Log.i(TAG, "Entrou no up do item especial ");
							CoolD.CoolDown = true;
							CoolD.coolDownTime = 10;
							Log.i(TAG, "Entrou1:" + CoolD.CoolDown);

							calcularItemEsp();
							Log.i(TAG, "Entrou2:" + CoolD.CoolDown);

						} else {
							impp = false;
							PriTouch = SegTouch;
							SegTouch = "";

						}
					}
					if (point.x == segTouchX && SegTouch != "corda") {

						intensManager.detectar(a, b);
						if (intensManager.Detectado()) {

							calcularItemEsp();

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
		intensManager.updateItensManager(SegTouch, PriTouch, Barrinhas,
				dadosDoCliente, 3, status, Energetico, Casca, TNT);
		Toast.makeText(getContext(), "FOOI", Toast.LENGTH_LONG);
		// Log.i("partida", "" + n);
		if (status > 3) {
			if (CoolD.item_Selecionado == 0) {
				Num_impulso = 10000;
				CoolD.CoolDown = true;
				CoolD.coolDownTime = 2;
				dadosDoCliente.setItemEsp(CoolD.item_Selecionado);

			}
		}
		if (status > 7) {
			if (CoolD.item_Selecionado == 1) {
				Num_impulso = 20000;
				CoolD.CoolDown = true;
				CoolD.coolDownTime = 2;
				dadosDoCliente.setItemEsp(CoolD.item_Selecionado);

			}
		}
		if (status > 10) {
			if (CoolD.item_Selecionado == 2) {
				Num_impulso = 30000;
				CoolD.CoolDown = true;
				CoolD.coolDownTime = 3;
				dadosDoCliente.setItemEsp(CoolD.item_Selecionado);
			}
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

		if (Play1) {
			dadosDoCliente.setPosMao(2 * getWidth() / 3);
		} else {
			dadosDoCliente.setPosMao(getWidth() / 3);

		}
		if (Play1) {
			if (itemEspecial == 0) {
				dadosDoCliente.setX(posicaoCorda + 3);
			} else {
				dadosDoCliente.setX(posicaoCorda + 1);
			}
		} else {
			if (itemEspecial == 0) {
				dadosDoCliente.setX(posicaoCorda - 3);
			} else {
				dadosDoCliente.setX(posicaoCorda - 1);
			}
		}
		status++;
		if (status < 12) {

			Forca = (int) (Forca * 1.3f);
			dadosDoCliente.setImpX(status);

		}
		Forca = i;
		possivel = false;
	}

	public void calcularItens() {
		impp = false;
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

		rectFundo.set(0, 0, getWidth(), getHeight());

		while (iterator.hasNext()) {
			String keey = iterator.next();
			Jogador jogadorLindu = jogadores.get(keey);
			if (jogadores.get(nick) != null) {
				if (!jogadores.get(nick).isVisible()
						&& !jogadores.get(nick).getVitoria()
						&& !jogadores.get(nick).Perdeu()) {
					canvas.drawBitmap(Serverfundo, null, rectFundo, paint);

					paint.setColor(Color.BLACK);
					paint.setTextSize(25);
					canvas.drawText(RedeUtil.getLocalIpAddress(),
							getWidth() / 10, 12 * getHeight() / 30, paint);
				}
			}
			if (jogadores.get(nick).getVitoria()) {
				paint.setColor(Color.MAGENTA);
				canvas.drawBitmap(Vitoria, null, rectFundo, paint);

				// como se
				// fosse
				// a
				// tela
				// de
				// vitória(fim
				// de
				// jogo)
			} else {
				if (jogadores.get(nick).Perdeu()) {

					canvas.drawBitmap(Derrota, null, rectFundo, paint);

				}else{
				if (jogadorLindu.getFechar()) {

					killMeSoftly();
				}
				}
			}

			if (jogadorLindu.isVisible()) {

				if (jogadorLindu.getX() != posicaoCorda
						&& jogadorLindu.getX() != -70) {
					posicaoCorda = jogadorLindu.getX();
					Corda.left = posicaoCorda * getWidth() / 40;
					Corda.right = (int) (Corda.left + getWidth());
					dadosDoCliente.setX(-70);
				}

				Jogador jogador = jogadores.get(nick);

				positionX = jogador.getPosMao();
				// PLAYER1
				int Prop = mao.getHeight() * 7 * getWidth() / 20
						/ (mao.getWidth() / 4);

				if (Play1) {
					// maoPlayer.set((int) positionX, (int) positionY,
					// (int) positionX + 200, (int) positionY + 100);

					maoPlayer.set((int) positionX, (int) Corda.centerY() - 8
							* Prop / 10, (int) positionX + 7 * getWidth() / 20,
							(int) (Corda.centerY() + Prop));
					if (jogadores.get("Player 2") != null) {

						int newProp = (int) (jogadores.get("Player 2")
								.getPosMao() * getWidth() / jogadores.get(
								"Player 2").getWidthfull());
						maoPlayerAdv.set((int) newProp - 7 * getWidth() / 20,
								(int) Corda.centerY() - 8 * Prop / 10,
								(int) newProp, (int) (Corda.centerY() + Prop));
					}
					if (posicaoCorda >= 20) {
						dadosDoCliente.finalizar();
					}
				} else {
					maoPlayer.set((int) positionX - 7 * getWidth() / 20,
							(int) Corda.centerY() - 8 * Prop / 10,
							(int) positionX, (int) (Corda.centerY() + Prop));
					if (jogadores.get("Player 1") != null) {
						int newProp = (int) (jogadores.get("Player 1")
								.getPosMao() * getWidth() / jogadores.get(
								"Player 1").getWidthfull());
						maoPlayerAdv.set((int) newProp, (int) Corda.centerY()
								- 8 * Prop / 10, newProp + 7 * getWidth() / 20,
								(int) (Corda.centerY() + Prop));
					}
					if (posicaoCorda <= -20) {
						dadosDoCliente.finalizar();
					}

				}

				canvas.drawBitmap(fundo, null, rectFundo, paint);
				canvas.drawBitmap(ImgCasca, null,
						intensManager.rectsItens.get(0), paint);
				canvas.drawBitmap(ImgTNT, null,
						intensManager.rectsItens.get(2), paint);
				canvas.drawBitmap(ImgEnergetico, null,
						intensManager.rectsItens.get(3), paint);

				canvas.drawBitmap(patente, null, rectPatente, paint);
				canvas.drawBitmap(Z, null, rectZ, paint);
				canvas.drawBitmap(divisor, null, rectDivisor, paint);

				canvas.drawBitmap(ImgCordaF, null, CordaFull, paint);
				canvas.drawBitmap(ImgCorda, null, Corda, paint);
				canvas.drawBitmap(marcador, null, rectMarcador, paint);
				if (itemEspecial == 0) {

					canvas.drawBitmap(ImgMaoRosa, null, maoPlayer, paint);
				} else {
					sprite.Draw(canvas, maoPlayer);
				}
				spriteMaoAdv.Draw(canvas, maoPlayerAdv);
				if (spriteCasca2.status) {
					spriteCasca2.Draw(canvas, CascaRect);
				}
				if (spriteCasca1.status) {
					spriteCasca1.Draw(canvas, CascaRect);
				}

				if (spriteTNT2.status) {
					spriteTNT2.Draw(canvas, CascaRect);
				}
				if (spriteTNT1.status) {
					spriteTNT1.Draw(canvas, CascaRect);
				}

				Iterator<String> iterato = jogadores.keySet().iterator();

				Barrinhas[0].right = Barrinhas[0].left + jogador.getImpX()
						* getWidth() / 36;
				// Barrinhas[2].right = jogador.getMasX();
				// Barrinhas[1].right = jogador.getVelX();

				for (int i = 0; i < Barrinhas.length; i++) {
					// canvas.drawRect(Barrinhas[i], paint);

				}
				status = jogador.getImpX();
				canvas.drawBitmap(barra, null, Barrinhas[0], paint);

				if (itemEspecial < 0 && jogador.getItemEspecial() != -1) {

					itemEspecial = jogador.getItemEspecial();
					if (itemEspecial == 0) {

						ImgEnergetico = BitmapManager.GetInstance()
								.getImageEnergUso();
						Energetico = false;
					}
					if (itemEspecial == 1) {

						ImgCasca = BitmapManager.GetInstance()
								.getImageCascaUso();
						Casca = false;
						if (Play1) {
							// chamar o sprite o Spritecasca2
							spriteCasca2.status = true;

						} else {
							// chamar o spritecasca1 de tras para frente
							spriteCasca1.Modificar(13);

							spriteCasca1.status = true;
						}
					}
					if (itemEspecial == 2) {

						ImgTNT = BitmapManager.GetInstance().getImageTNTUso();
						Casca = false;

						if (Play1) {
							// chamar o sprite o Spritecasca2
							spriteTNT1.Modificar(11);

							spriteTNT1.status = true;
						} else {
							// chamar o spritecasca1 de tras para frente

							spriteTNT2.status = true;
						}
					}
					// Aqui vem o trecho em que trata o item especial , as
					// modificacoes que ocorrerao no jogador
				}
				if (acionar <= 0 && jogador.getItemAcionado() != -1) {

					acionar = jogador.getItemAcionado();
					autorizado = false;
					if (acionar == 1) {
						if (Play1) {
							// chamar o spritecasca1 de tras para frente
							spriteCasca1.Modificar(13);
							spriteCasca1.status = true;
						} else {
							spriteCasca2.status = true;
						}
					}

					if (acionar == 2) {
						if (Play1) {
							// chamar o spritecasca1 de tras para frente

							spriteTNT2.status = true;
						} else {
							spriteTNT1.Modificar(11);
							spriteTNT1.status = true;
						}
					}

					// if(acionar==1){
					// spriteCascaLanc.status=true;
					// }
					// else{
					// spriteCascaLanc.status=false;
					// }

				}
				if (itemEspecial >= 0 && jogador.getItemEspecial() < 0) {

					itemEspecial = jogador.getItemEspecial();
				}
				if (acionar >= 0 && jogador.getItemAcionado() < 0) {

					acionar = jogador.getItemAcionado();
					autorizado = true;
				}

				paint.setTextSize(20);

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
		this.spriteMaoAdv.Update(this.deltaTime);

		if (spriteCasca2.status) {
			spriteCasca2.Voltar(this.deltaTime);

		}
		if (spriteCasca1.status) {
			spriteCasca1.iniciar(this.deltaTime);
		}
		if (spriteTNT2.status) {
			spriteTNT2.Voltar(this.deltaTime);
		}
		if (spriteTNT1.status) {
			spriteTNT1.iniciar(this.deltaTime);
		}

		if (period != 0) {
			counter++;
		}
		coolD.updateCoolD(dadosDoCliente);

		if (counter == 1000) {
			period--;
			counter = 0;
			current += 1000;
			if (current - counter >= 100) {

			}

		}
		if (status > 3) {
			if (itemEspecial != 0) {
				ImgEnergetico = BitmapManager.GetInstance().getImageEnergitco();
				Energetico = true;
			} else {
			}
		} else {
			ImgEnergetico = BitmapManager.GetInstance().getImageEnergitcoPB();
			Energetico = false;
		}
		if (status > 7) {
			if (itemEspecial != 1) {

				ImgCasca = BitmapManager.GetInstance().getImageCasca();
				Casca = true;
			} else {

			}
		} else {
			ImgCasca = BitmapManager.GetInstance().getImageCascaPB();
			Casca = false;
		}
		if (status > 10) {

			if (itemEspecial != 2) {
				ImgTNT = BitmapManager.GetInstance().getImageTNT();
				TNT = true;
			} else {
			}

		} else {
			ImgTNT = BitmapManager.GetInstance().getImageTNTPB();
			TNT = false;
		}

	}

	public void killMeSoftly() {
		 ativo = false;
		this.cliente.killMeSoftly();

		dadosDoCliente.killMeSoftly();
		espera.finish();
	}

	public void OnBackPressed() {

		Jogador jogador = jogadores.get(nick);

		killMeSoftly();
		//espera.finish();
	}
}
