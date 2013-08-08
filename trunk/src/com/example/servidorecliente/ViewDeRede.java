package com.example.servidorecliente;

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
import com.example.servidorecliente.bean.Jogador;
import com.example.servidorecliente.rede.ControleDeUsuariosCliente;
import com.example.servidorecliente.rede.DadosDoCliente;
import com.example.servidorecliente.rede.Killable;
import com.example.servidorecliente.rede.Protocolo;

public class ViewDeRede extends View implements Runnable, Killable {

	// acho q é aqui q o jogo "acontece" -> acho q aqui q entrará o código das
	// meninas.

	// private Paint paint;
	private long time = 1;

	// meninas
	int q;
	int r;
	int p;
	int t;
	int a;
	int b;
	int c = -1;
	int n = 0;

	private int counter;
	private int coolDown = 0;
	private int counterIten = 1000;
	private int period = 100;
	private int current;
	int Forca;
	int Num_impulso;
	private int segTouchX;
	private int segTouchY;
	private static final int UPDATE_TIME = 100;

	static Rect atual = new Rect();
	static Rect corda = new Rect();
	static Rect Impulso = new Rect();
	static Rect Massa = new Rect();
	static Rect Velocidade = new Rect();
	static Rect ItemEsp = new Rect();
	static Rect BarrinhaImpulso = new Rect();
	static Rect BarrinhaVelocidade = new Rect();
	static Rect BarrinhaMassa = new Rect();

	Paint paint = new Paint();

	static float positionX = 40;
	private static float positionY = 30;
	private static float Width = 30;
	private static float Height = 30;

	ImageManager img;
	Conexao cliente;
	Jogador jogadoor;
	private ControleDeUsuariosCliente tratadorDeDadosDoCliente;
	private DadosDoCliente dadosDoCliente;

	Bitmap imagem;
	Bitmap impulso;
	Bitmap velocidade;
	Bitmap massa;
	Bitmap itemEsp;

	Boolean CoolDown = false;
	Boolean impp = false;
	Boolean possivel = false;
	private boolean ok;
	private boolean ativo = true;

	String SegTouch;
	String PriTouch;
	private static final String TAG = "view-rede";

	Random rnd = new Random();

	private ConcurrentHashMap<String, Jogador> jogadores;
	private Queue<MotionEvent> fila;
	private SparseArray<PointF> dedos = new SparseArray<PointF>();

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
		itemEsp = img.ImageManager("amarelo.png", context);

		Thread processo = new Thread(this);
		processo.start();

	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		int larguraItem = (int) getWidth() / 10;
		int alturaItem = (int) getHeight() / 10;
		CreateItens(larguraItem, alturaItem);

		int larguraBarra = (int) getWidth() / 15;
		int alturaBarra = (int) getHeight() / 15;
		CreateBarrinhas(larguraBarra, alturaBarra);

		Width = getWidth() / 2;
		Height = getHeight() / 2;
		positionX = Width;
		positionY = Height;
		// dadosDoCliente.setX((int)positionX);
		// dadosDoCliente.setY((int)positionY);

	}

	public void CreateBarrinhas(int larguraBarra, int alturaBarra) {
		BarrinhaImpulso.set(larguraBarra, 2 * alturaBarra,
				(int) (1.5f * larguraBarra), (int) (2.5f * alturaBarra));
		BarrinhaVelocidade.set(larguraBarra, 3 * alturaBarra,
				(int) (1.5f * larguraBarra), (int) (3.5f * alturaBarra));
		BarrinhaMassa.set(larguraBarra, (4 * alturaBarra),
				(int) (1.5f * larguraBarra), (int) (4.5f * alturaBarra));
	}

	public void CreateItens(int larguraItem, int alturaItem) {
		Impulso.set((int) 2f * larguraItem, 8 * alturaItem,
				(int) (3.3f * larguraItem), (int) (9.5f * alturaItem));
		Velocidade.set((int) (3.5f * larguraItem), 8 * alturaItem,
				(int) (4.8f * larguraItem), (int) (9.5f * alturaItem));
		Massa.set((int) (5f * larguraItem), 8 * alturaItem,
				(int) (6.3f * larguraItem), (int) (9.5f * alturaItem));
		ItemEsp.set((int) (6.5f * larguraItem), 8 * alturaItem,
				(int) (7.8f * larguraItem), (int) (9.5f * alturaItem));

	}

	public boolean onTouchEvent(MotionEvent event) {
		fila.add(event);
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

				if (Impulso.contains(q, r)) {
					BotImpulso();

					PriTouch = "Impulso";

					PointF point = new PointF(event.getX(id), event.getY(id));
					dedos.put(id, point);

				}
				if (Velocidade.contains(q, r)) {
					BotVelocidade();
					PriTouch = "Velocidade";

					PointF point = new PointF(event.getX(id), event.getY(id));
					dedos.put(id, point);

				}
				if (Massa.contains(q, r)) {
					BotMassa();
					PriTouch = "Massa";

					PointF point = new PointF(event.getX(id), event.getY(id));
					dedos.put(id, point);

				}
				if (ItemEsp.contains(q, r)) {
					BotIten();
					PriTouch = "ItemEsp";

					PointF point = new PointF(event.getX(id), event.getY(id));
					dedos.put(id, point);

				}

				if (corda.contains(q, r)) {
					possivel = true;

					PriTouch = "corda";

					PointF point = new PointF(event.getX(id), event.getY(id));
					dedos.put(id, point);

				}

				Log.i("foi", "down baby down !! " + PriTouch);

			}
			if (action == MotionEvent.ACTION_POINTER_DOWN) {

				int id = event.getPointerId(event.getActionIndex());

				segTouchX = (int) event.getX(id);
				segTouchY = (int) event.getY(id);

				Log.d("vamos", "" + segTouchX + impp.booleanValue());
				if (impp == false) {
					if (Impulso.contains(segTouchX, segTouchY)) {
						BotImpulso();
						SegTouch = "Impulso";

						PointF point = new PointF(event.getX(id),
								event.getY(id));

						dedos.put(id, point);
					}
					if (Velocidade.contains(segTouchX, segTouchY)) {
						BotVelocidade();
						SegTouch = "Velocidade";

						PointF point = new PointF(event.getX(id),
								event.getY(id));

						dedos.put(id, point);

					}
					if (Massa.contains(segTouchX, segTouchY)) {
						BotMassa();
						SegTouch = "Massa";

						PointF point = new PointF(event.getX(id),
								event.getY(id));

						dedos.put(id, point);
					}
					if (ItemEsp.contains(segTouchX, segTouchY)) {
						BotIten();
						SegTouch = "ItemEsp";

						PointF point = new PointF(event.getX(id),
								event.getY(id));

						dedos.put(id, point);
					}

				}
				if (possivel == false) {
					if (corda.contains(segTouchX, segTouchY)) {
						SegTouch = "corda";
						possivel = true;

						PointF point = new PointF(event.getX(id),
								event.getY(id));

						dedos.put(id, point);
					}
				}
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
						if (corda.contains(p, corda.centerY())) {

							AplicarCorda();

						}
					}
					if (impp) {
						if (point.x == q && PriTouch != "corda") {
							if (Impulso.contains(a, Impulso.centerY())
									|| Velocidade.contains(a,
											Velocidade.centerY())
									|| Massa.contains(a, Massa.centerY())
									|| ItemEsp.contains(a, ItemEsp.centerY())) {
								if (Impulso.contains(a, Impulso.centerY())) {
									CalcularImpulso();
								}
								if (Velocidade
										.contains(a, Velocidade.centerY())) {
									CalcularVelocidade();
								}
								if (Massa.contains(a, Massa.centerY())) {
									CalcularMassa();
								}
								if (ItemEsp.contains(a, ItemEsp.centerY())) {
									CalcularIten();
									CoolDown = true;
								}

							} else {
								impp = false;
								PriTouch = SegTouch;
								SegTouch = "";

							}
						}
						if (point.x == segTouchX && SegTouch != "corda") {
							if (Impulso.contains(a, Impulso.centerY())
									|| Velocidade.contains(a,
											Velocidade.centerY())
									|| Massa.contains(a, Massa.centerY())
									|| ItemEsp.contains(a, ItemEsp.centerY())) {
								if (Impulso.contains(a, Impulso.centerY())) {
									CalcularImpulso();
								}
								if (Velocidade
										.contains(a, Velocidade.centerY())) {
									CalcularVelocidade();
								}
								if (Massa.contains(a, Massa.centerY())) {
									CalcularMassa();

								}
								if (ItemEsp.contains(a, ItemEsp.centerY())) {
									CalcularIten();

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

				int d = event.getPointerId(event.getActionIndex());
				for (int i = 0; i < event.getPointerCount(); i++) {
					int id = event.getPointerId(i);

					PointF point = (PointF) dedos.get(id);
					if (point != null) {
						if (possivel) {
							if ((int) point.x == q && PriTouch == "corda") {
								if (q < event.getX(id)) {
									if (corda.contains((int) (event.getX(id)),
											corda.centerY())) {
										positionX = event.getX(id) - 20;
									} else {
										aplicarForca((int) ((event.getX(id) - q) / 3));
										possivel = false;
										dedos.remove(id);
									}
								} else {
									aplicarForca((int) ((event.getX(id) - q) / 3));
									possivel = false;
									dedos.remove(id);
								}
							}
							if ((int) point.x == segTouchX
									&& SegTouch == "corda") {
								if (segTouchX < event.getX(id)) {
									if (corda.contains((int) event.getX(id),
											corda.centerY())) {
										positionX = event.getX(id) - 20;
									} else {
										aplicarForca((int) ((event.getX(id) - segTouchX) / 3));
										possivel = false;
										dedos.remove(id);
									}

								} else {
									aplicarForca((int) ((event.getX(id) - q) / 3));
									possivel = false;
									dedos.remove(id);
								}
							}
						}
						if (impp) {
							if ((int) point.x == q && PriTouch != "corda") {
								if (Impulso.contains((int) event.getX(id),
										Impulso.centerY())
										|| Massa.contains((int) event.getX(id),
												Massa.centerY())
										|| Velocidade.contains(
												(int) event.getX(id),
												Velocidade.centerY())
										|| ItemEsp.contains(
												(int) event.getX(id),
												ItemEsp.centerY())) {
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
								if (Impulso.contains((int) event.getX(id),
										Impulso.centerY())
										|| Massa.contains((int) event.getX(id),
												Massa.centerY())
										|| Velocidade.contains(
												(int) event.getX(id),
												Velocidade.centerY())
										|| ItemEsp.contains(
												(int) event.getX(id),
												ItemEsp.centerY())) {
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
			if (action == MotionEvent.ACTION_UP) {
				int id = event.getPointerId(event.getActionIndex());

				PointF point = (PointF) dedos.get(id);
				a = (int) event.getX(id);
				b = (int) event.getY(id);
				if (possivel) {
					if (corda.contains(a, corda.centerY())) {

						AplicarCorda();

					}

				}
				if (impp) {
					if (point.x == q && PriTouch != "corda") {
						if (Impulso.contains(a, b) || Velocidade.contains(a, b)
								|| Massa.contains(a, b)
								|| ItemEsp.contains(a, b)) {
							if (Impulso.contains(a, b)) {
								CalcularImpulso();
							}
							if (Velocidade.contains(a, b)) {
								CalcularVelocidade();
							}
							if (Massa.contains(a, b)) {
								CalcularMassa();
							}
							if (ItemEsp.contains(a, b)) {
								CalcularIten();
							}

						} else {
							impp = false;
							PriTouch = SegTouch;
							SegTouch = "";

						}
					}
					if (point.x == segTouchX && SegTouch != "corda") {
						if (Impulso.contains(a, b) || Velocidade.contains(a, b)
								|| Massa.contains(a, b)
								|| ItemEsp.contains(a, b)) {
							if (Impulso.contains(a, b)) {
								CalcularImpulso();
							}
							if (Velocidade.contains(a, b)) {
								CalcularVelocidade();
							}
							if (Massa.contains(a, b)) {
								CalcularMassa();
							}
							if (ItemEsp.contains(a, b)) {
								CalcularIten();
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

	private void AplicarCorda() {
		// TODO Auto-generated method stub
		if (PriTouch == "corda") {

			aplicarForca((int) (p - q) / 3);
			possivel = false;
			PriTouch = SegTouch;
			q = segTouchX;

		} else {
			if (SegTouch == "corda") {

				aplicarForca((int) (p - segTouchX) / 3);
				possivel = false;
				SegTouch = "";

			}
		}
		dadosDoCliente.setX((int) (positionX + 10));
	}

	private void BotMassa() {
		impp = true;
		current = period;

	}

	private void BotVelocidade() {

		impp = true;
		current = period;

	}

	private void BotImpulso() {
		impp = true;
		current = period;
	}

	private void BotIten() {
		impp = true;
		// current = period;
	}

	private void aplicarForca(int i) {

		// positionX+=i;

		// dadosDoCliente.setY(5);
		/*
		 * if(cliente!=null){ //cliente.write(Protocolo.PROTOCOL_MOVE + "," +
		 * cliente.getId() + ","+positionX+20+","+positionY);
		 * dadosDoCliente.setX((int)positionX+30);
		 * jogadoor=cliente.getJogador(); }
		 * 
		 * positionX=jogadoor.getX();
		 */
		// positionX =cliente.GetX();

		positionX = Width;
		if (Num_impulso == 30000) {
			if (BarrinhaImpulso.right - BarrinhaImpulso.left >= 3) {
				Forca = (int) (Forca * 1.3f);
				BarrinhaImpulso.right += -3;
			} else {

				Forca = (int) (Forca * 1.1f);
				BarrinhaImpulso.right = BarrinhaImpulso.left;
			}

		}
		if (Num_impulso == 20000) {
			if (BarrinhaVelocidade.right - BarrinhaVelocidade.left >= 3) {
				Forca = (int) (Forca * 1.4f);
				BarrinhaVelocidade.right += -3;
			} else {

				Forca = (int) (Forca * 1.2f);
				BarrinhaVelocidade.right = BarrinhaVelocidade.left;
				paint.setColor(Color.BLUE);
			}

		}
		if (Num_impulso == 10000) {
			if (BarrinhaMassa.right - BarrinhaMassa.left >= 3) {
				Forca = (int) (Forca * 1.8f);
				BarrinhaMassa.right += -3;
			} else {

				Forca = (int) (Forca * 1.3f);
				BarrinhaMassa.right = BarrinhaImpulso.left;
			}

		}
		Forca = i;
		// TODO Auto-generated method stub

	}

	private void CalcularImpulso() {
		impp = false;
		dadosDoCliente.setX(0);

		int larguraBarra = (int) getWidth() / 15;
		int alturaBarra = (int) getHeight() / 15;

		BarrinhaImpulso.set(larguraBarra, 2 * alturaBarra,
				(int) (1.5f * larguraBarra), (int) (2.5f * alturaBarra));
		if (current - counter <= 100) {
			Num_impulso = 30000;
			current = 0;

		}
		if (PriTouch == "Impulso") {
			PriTouch = SegTouch;
			SegTouch = "";
			q = segTouchX;
		}

	}

	private void CalcularMassa() {
		impp = false;
		dadosDoCliente.setX(0);
		int larguraBarra = (int) getWidth() / 15;
		int alturaBarra = (int) getHeight() / 15;
		BarrinhaImpulso.set(larguraBarra, 2 * alturaBarra,
				(int) (1.5f * larguraBarra), (int) (2.5f * alturaBarra));
		if (current - counter <= 100) {
			Num_impulso = 10000;

			current = 0;
		}
		if (PriTouch == "Massa") {
			PriTouch = SegTouch;
			SegTouch = "";
			q = segTouchX;
		}
	}

	private void CalcularVelocidade() {

		impp = false;
		dadosDoCliente.setX(0);

		int larguraBarra = (int) getWidth() / 15;
		int alturaBarra = (int) getHeight() / 15;
		BarrinhaImpulso.set(larguraBarra, 2 * alturaBarra,
				(int) (1.5f * larguraBarra), (int) (2.5f * alturaBarra));
		if (current - counter <= 100) {
			Num_impulso = 20000;
			current = 0;
		}
		if (PriTouch == "Velocidade") {
			PriTouch = SegTouch;
			SegTouch = "";
			q = segTouchX;
		}
	}

	private void CalcularIten() {

		impp = false;

		Log.i("partida", "" + n);

		if (c == 0) {
			Num_impulso = 10000;
			CoolDown = true;
			coolDown = 10;
		}
		if (c == 1) {
			Num_impulso = 20000;
			CoolDown = true;
			coolDown = 10;
		}
		if (c == 2) {
			Num_impulso = 30000;
			CoolDown = true;
			coolDown = 10;
		}

		if (PriTouch == "Velocidade") {
			PriTouch = SegTouch;
			SegTouch = "";
			q = segTouchX;
		}

	}

	public void draw(Canvas canvas) {
		super.draw(canvas);

		atual.set(0, 0, (int) Width * 2, (int) Height * 2);
		corda.set((int) positionX, (int) positionY - getWidth() / 25,
				(int) (positionX + getWidth() / 3),
				(int) (positionY + getWidth() / 25));

		canvas.drawBitmap(imagem, null, atual, paint);
		canvas.drawBitmap(velocidade, null, Velocidade, paint);
		canvas.drawBitmap(itemEsp, null, ItemEsp, paint);
		canvas.drawBitmap(massa, null, Massa, paint);

		ConcurrentHashMap<String, Jogador> jogadores = tratadorDeDadosDoCliente
				.getJogadores();

		Iterator<String> iterator = jogadores.keySet().iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();
			Jogador jogador = jogadores.get(key);

			BarrinhaImpulso.left += jogador.getX();

			Log.e("Vieew", "" + jogador.getX());
		}

		canvas.drawRect(BarrinhaImpulso, paint);
		canvas.drawRect(BarrinhaVelocidade, paint);
		canvas.drawRect(BarrinhaMassa, paint);
		canvas.drawRect(Massa, paint);
		canvas.drawRect(corda, paint);
		canvas.drawRect(ItemEsp, paint);
		canvas.drawRect(Impulso, paint);
		canvas.drawRect(Velocidade, paint);

		paint.setTextSize(20);
		paint.setTextSize(10);

		canvas.drawText("forcaa" + Forca + "impulsooo" + Num_impulso, 50, 30,
				paint);
		canvas.drawText("Impulso", BarrinhaImpulso.left + 55,
				BarrinhaImpulso.bottom, paint);
		canvas.drawText("Velocidade", BarrinhaVelocidade.left + 55,
				BarrinhaVelocidade.bottom, paint);
		canvas.drawText("Massa", BarrinhaMassa.left + 55, BarrinhaMassa.bottom,
				paint);
		// positionX=dadosDoCliente.se();
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

		// tratadorDeDadosDoCliente.execute(cliente, cliente + "," +
		// dadosDoCliente.getX() + "," +dadosDoCliente.getY() +";");

		// jogadores=tratadorDeDadosDoCliente.getJogadores();
		// jogadoor=jogadores.get(cliente.getId());

		// positionX=cliente.GetX();
		// positionX=dadosDoCliente.getX();
		// positionY= dadosDoCliente.getY();

		if (CoolDown == true) {
			if (coolDown != 0) {
				counterIten--;
				if (counterIten == 0) {
					coolDown--;
					counterIten = 1000;
					Log.i("coolDown", "" + coolDown);
				}
			}
		}
		Log.i("coolDown", "" + coolDown);
		if (coolDown == 0 && CoolDown == true) {
			ok = true;
		}

		if (coolDown == 0 || ok == true) {
			c = rnd.nextInt(3);
			CoolDown = false;
			ok = false;
			// coolDown = 30;
		} else {
			c = -1;
		}

		if (period != 0) {
			counter++;
		}

		if (counter == 1000) {
			period--;
			counter = 0;
			current += 1000;
			// if(impp){
			if (current - counter >= 100) {

				if (SegTouch == "Impulso" || PriTouch == "Impulso") {
					if (BarrinhaImpulso.right - BarrinhaImpulso.left < 50) {
						if (BarrinhaImpulso.right + 3 - BarrinhaImpulso.left > 50) {
							BarrinhaImpulso.right = 50 + BarrinhaImpulso.left;

						} else {
							// BarrinhaImpulso.right += 3;
							dadosDoCliente.setX(3);
						}

					}
				}
				if (SegTouch == "Velocidade" || PriTouch == "Velocidade") {
					if (BarrinhaVelocidade.right - BarrinhaVelocidade.left < 50) {
						if (BarrinhaVelocidade.right + 3
								- BarrinhaVelocidade.left > 50) {
							BarrinhaVelocidade.right = 50 + BarrinhaVelocidade.left;

						} else {

							// BarrinhaVelocidade.right += 3;
							dadosDoCliente.setX(3);
						}
					}
				}
				if (SegTouch == "Massa" || PriTouch == "Massa") {
					if (BarrinhaMassa.right - BarrinhaMassa.left < 50) {
						if (BarrinhaMassa.right + 3 - BarrinhaMassa.left > 50) {
							BarrinhaMassa.right = 50 + BarrinhaMassa.left;

						} else {
							// BarrinhaMassa.right += 3;
							dadosDoCliente.setX(3);
						}
					}
				}
			}

		}
		// }
		processEventQueue();

	}

	/*
	 * public boolean onTouchEvent(MotionEvent event) { int action =
	 * event.getAction(); Log.i(TAG, "ontouch: " + action);
	 * 
	 * int id = event.getPointerId(event.getActionIndex());
	 * dadosDoCliente.setX((int) event.getX(id)); dadosDoCliente.setY((int)
	 * event.getY(id));
	 * 
	 * return super.onTouchEvent(event); }
	 */

	public void killMeSoftly() {
		ativo = false;
	}

}
