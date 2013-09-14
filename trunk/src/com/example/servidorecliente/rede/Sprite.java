package com.example.servidorecliente.rede;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Sprite {
	private static final String TAG = "SPRITE SHEET DRAW";

	protected Bitmap bitmap;
	protected Rect selectRect;
	private int frameNr;
	public int currentFrame;
	private long spriteTime;
	private double framePeriod;

	protected int spriteWidth;
	protected int spriteHeight;
	private long cronometro;

	protected Paint paint;

	public Boolean status = false;

	public Sprite(Bitmap bitmap, int frameNr, int fps) {
		this.spriteWidth = bitmap.getWidth() / frameNr;
		this.spriteHeight = bitmap.getHeight();
		this.frameNr = frameNr;
		this.framePeriod = 1000 / fps;
		this.spriteTime = 00;
		this.currentFrame = 0;
		this.selectRect = new Rect(0, 0, spriteWidth / frameNr, spriteHeight);
		this.bitmap = bitmap;

		paint = new Paint();
	}

	public void setConfig(Bitmap bitmap, int frameNr, int fps) {
		this.spriteWidth = bitmap.getWidth() / frameNr;
		this.spriteHeight = bitmap.getHeight();
		this.frameNr = frameNr;
		this.framePeriod = 1000 / fps;
		this.selectRect = new Rect(0, 0, spriteWidth / frameNr, spriteHeight);
		this.bitmap = bitmap;
	}

	public void Update(long deltaTime) {
		this.cronometro += deltaTime;

		if (cronometro >= this.spriteTime + framePeriod) {
			this.spriteTime = cronometro;

			currentFrame++;

			if (currentFrame >= frameNr) {
				currentFrame = 0;
			}

			this.selectRect.left = this.currentFrame * this.spriteWidth;
			this.selectRect.right = this.selectRect.left + this.spriteWidth;
		}

	}

	public void iniciar(long deltaTime) {

		
		this.cronometro += deltaTime;

		if (cronometro >= this.spriteTime + framePeriod) {
			this.spriteTime = cronometro;
			currentFrame--;
			if (currentFrame <= 0) {
				currentFrame=0;
			}

			this.selectRect.left = this.currentFrame * this.spriteWidth;
			this.selectRect.right = this.selectRect.left + this.spriteWidth;
			if (currentFrame <= 0) {
				currentFrame = 1;


				this.selectRect.left = this.currentFrame * this.spriteWidth;
				this.selectRect.right = this.selectRect.left + this.spriteWidth;
				status=false;
				
			}
		}

	}

	public void Voltar(long deltaTime) {

		this.cronometro += deltaTime;

		
		if (cronometro >= this.spriteTime + framePeriod) {
			this.spriteTime = cronometro;

			currentFrame++;
			if (currentFrame >= frameNr-1) {

				currentFrame=frameNr-1;
			}
			
			
			this.selectRect.left = this.currentFrame * this.spriteWidth;
			this.selectRect.right = this.selectRect.left + this.spriteWidth;
			if (currentFrame >= frameNr-1) {
				status=false;
			}
		}

	}

	public void Modificar(int frame) {

		currentFrame = frame;

		if (currentFrame >= frameNr) {
			currentFrame = 0;
		}

		this.selectRect.left = this.currentFrame * this.spriteWidth;
		this.selectRect.right = this.selectRect.left + this.spriteWidth;

	}

	public void Draw(Canvas canvas, Rect destRect) {
		canvas.drawBitmap(bitmap, selectRect, destRect, paint);
	}

}