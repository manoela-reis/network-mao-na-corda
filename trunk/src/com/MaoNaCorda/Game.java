package com.MaoNaCorda;

import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.Queue;



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

public class Game extends View implements Runnable

{
	int q;
	int r;
	int p;
	int t;
	int a;
	int b;
	Boolean possivel = false;
	static Rect atual = new Rect();
	static Rect corda = new Rect();
	static Rect Impulso = new Rect();

	private int counter;
	private int period = 100;
	private int current;
	Paint paint = new Paint();
	static float positionX = 40;
	private static float positionY = 30;
	private static float Width = 30;
	private static float Height = 30;
	ImageManager img;
	Bitmap imagem;
	Bitmap impulso;
	int Forca;
	int Num_impulso;
	Boolean impp = false;
	private int segTouchX;
	private int segTouchY;
	String SegTouch;
	String PriTouch;
	private Queue<MotionEvent> fila;
	private SparseArray<PointF> dedos = new SparseArray<PointF>();

	public Game(Context context) {
		super(context);
		setFocusableInTouchMode(true);
		setClickable(true);
		setLongClickable(true);
		fila = new LinkedList<MotionEvent>();
		paint.setColor(Color.BLACK);
		paint.setTextSize(20);
		//img = new ImageManager((int) positionY * 2, (int) positionY * 2);
		//imagem = img.ImageManager("Game_Guerra.jpg", context);
		//impulso = img.ImageManager("amarelo.png", context);

		Thread processo = new Thread(this);
		processo.start();
	}

	public boolean onTouchEvent(MotionEvent event) {
		fila.add(event);
		return super.onTouchEvent(event);
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Impulso.set((int) getWidth() / 10, (int) 6 * getHeight() / 10, (int) 3
				* getWidth() / 10, (int) 8 * getHeight() / 10);

		Width = getWidth() / 2;
		Height = getHeight() / 2;
		positionX = Width;
		positionY = Height;

	}

	public void processEventQueue() {

		MotionEvent event = (MotionEvent) fila.poll();
		if (event != null) {

			int action = MotionEventCompat.getActionMasked(event);

			if (action == MotionEvent.ACTION_DOWN) {
				Log.i("foi", "down baby down !! ");
				int id = event.getPointerId(event.getActionIndex());

				PointF point = new PointF(event.getX(id), event.getY(id));

				q = (int) event.getX(id);
				r = (int) event.getY(id);
				Log.d("vamos", "" + q);
				if (Impulso.contains(q, r)) {
					impp = true;
					Num_impulso++;
					current = period;
					PriTouch = "Impulso";
				}
				if (corda.contains(q, r)) {
					possivel = true;
					impp = false;
					PriTouch = "corda";

				}
				dedos.put(id, point);

			}
			if (action == MotionEvent.ACTION_POINTER_DOWN) {
				Log.i("foi", "dsegundo !! ");
				int id = event.getPointerId(event.getActionIndex());

				PointF point = new PointF(event.getX(id), event.getY(id));

				segTouchX = (int) event.getX(id);
				segTouchY = (int) event.getY(id);

				Log.d("vamos", "" + segTouchX);
				if (impp == false) {
					if (Impulso.contains(segTouchX, segTouchY)) {
						impp = true;
						Num_impulso++;
						current = period;
						SegTouch = "Impulso";
					}
				}
				if (possivel == false) {
					if (corda.contains(segTouchX, segTouchY)) {
						SegTouch = "corda";
						possivel = true;

					}
				}
				dedos.put(id, point);

			}

			if (action == MotionEvent.ACTION_POINTER_UP) {
				Log.i("foi", "dsegundo UP!! ");
				int id = event.getPointerId(event.getActionIndex());
				p = (int) event.getX(id);
				t = (int) event.getY(id);
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
					if (Impulso.contains(p, t)) {
						Num_impulso = (current - period) * 10;
						impp = false;
					}
				}
				dedos.remove(id);
			}

			if (action == MotionEvent.ACTION_MOVE) {
				for (int i = 0; i < event.getPointerCount(); i++) {
					int id = event.getPointerId(i);

					PointF point = (PointF) dedos.get(id);
					if (point != null) {
						if ((int) point.x == q) {
							if (possivel == true && q < event.getX(id)
									&& PriTouch == "corda") {
								if (corda.contains((int) event.getX(id),
										(int) event.getY(id))) {
									positionX = event.getX(id);
								} else {
									aplicarForca((int) ((event.getX(id) - q) / 3 * Num_impulso));
									possivel = false;
									dedos.remove(id);
								}
							}
						}

						if ((int) point.x == segTouchX) {
							if (possivel == true && segTouchX < event.getX(id)
									&& SegTouch == "corda") {
								if (corda.contains((int) event.getX(id),
										(int) event.getY(id))) {
									positionX = event.getX(id);
								} else {
									aplicarForca((int) ((event.getX(id) - segTouchX) / 3 * Num_impulso));
									possivel = false;
									dedos.remove(id);
								}

							}
						}
					}
				}
			}
			if (action == MotionEvent.ACTION_UP) {
				Log.i("foi", "UP !!!");
				int id = event.getPointerId(event.getActionIndex());

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
					if (Impulso.contains(a, b)) {
						Num_impulso = (current - period) * 10;
						impp = false;
					}
				}
				dedos.remove(id);
			}
		}

	}

	private void aplicarForca(int i) {

		// positionX+=i;

		Forca = i;
		// TODO Auto-generated method stub
		positionX = Width;
	}

	public void draw(Canvas canvas) {
		super.draw(canvas);

		atual.set(0, 0, (int) Width * 2, (int) Height * 2);
		corda.set((int) positionX, (int) positionY, (int) positionX + 200,
				(int) positionY + 100);
		canvas.drawBitmap(imagem, null, atual, paint);
		canvas.drawRect(corda, paint);
		canvas.drawText("forcaa" + Forca + "impulsooo" + Num_impulso, 50, 30,
				paint);
		canvas.drawRect(Impulso, paint);

	}

	@Override
	public void run() {
		while (true) {

			try {

				Thread.sleep(1);
			} catch (Exception e) {
				Log.e("Deu erro", "Quem sabe mete o pe");
			}

			update();
			postInvalidate();
		}

		// TODO Auto-generated method stub

	}

	public void update() {
		if (period != 0) {
			counter++;
		}

		if (counter == 1000) {
			period -= 1;
			counter = 0;
		}
		processEventQueue();
	}

}
