package com.MaoNaCorda;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Struct;

import com.example.servidorecliente.rede.ViewDeRede;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class BitmapManager{

	public static BitmapManager Instance;
	public static final String TAG = "GerenteCenas";
	
	private Bitmap fundo;
	private Bitmap impulso;
	private Bitmap massa;
	private Bitmap energetico;
	private Bitmap velocidade;
	private Bitmap divisor;
	private Bitmap patente;
	private Bitmap z;
	
	ImageManager img;
	
	int altura;
	int largura;
	
	private Rect rectPatente = new Rect();
	private Rect rectZ = new Rect();
	
	private Rect fundoRect;

	public BitmapManager(Context context)
	{
		altura = ViewDeRede.alturaView;
		largura = ViewDeRede.larguraView;
		
		Instance = this;
		
		img = new ImageManager(context);
		
		fundo = img.ImageManager("background.png");
		massa = img.ImageManager("massa.png");
		impulso = img.ImageManager("impulso.png");
		velocidade = img.ImageManager("velocidade.png");
		energetico = img.ImageManager("energetico.png");
		divisor = img.ImageManager("parte_do_meio.png");
		patente = img.ImageManager("patente.png");
		z = img.ImageManager("z.png");
	
	
		
		rectPatente.set((int)(largura/1.5f), altura/20, (int)(largura/1.02), altura/4);
		rectZ.set(largura/30, altura/20, (int)(largura/5.7f), (int)(altura/3.4f));
		
	}
	
	public static BitmapManager GetInstance(){
		return Instance;
	}
	
	public Bitmap getImageFundo(){
		return fundo;
	}
	
	public Bitmap getImageMassa(){
		return massa;
	}
	
	public Bitmap getImageImpulso(){
		return impulso;
	}
	
	public Bitmap getImageVelocidade(){
		return velocidade;
	}
	
	public Bitmap getImageEnergitco(){
		return energetico;
	}
	
	public Bitmap getImageDivisor(){
		return divisor;
	}
	
	public Bitmap getImagePatente(){
		return patente;
	}
	
	public Bitmap getImageZ(){
		return z;
	}
		
	
	public Rect getRectPatente(){
		return rectPatente;
	}
	
	public Rect getRectZ(){
		return rectZ;
	}

/*	
	public void CenaMenu(){

		Log.i("MenuActivity", "Call");

		Intent intent = new Intent(GerenciadorActivity.this, MenuActivity.class);
		startActivity(intent);
	}
	
	public void CenaConect(){

		Intent intent = new Intent(GerenciadorActivity.this, ConectActivity.class);
		startActivity(intent);
	}
	
	public void CenaCreditos(){
		Intent intent = new Intent(GerenciadorActivity.this,CreditosActivity.class);
		startActivity(intent);
	}
	
	public void CenaDicas(){
		Intent intent = new Intent(GerenciadorActivity.this,DicasActivity.class);
		startActivity(intent);
	}
	
	public void CenaBatalha(){
		Intent intent = new Intent(GerenciadorActivity.this, BatalhaActivity.class);
		startActivity(intent);
	}
	
	public void CenaJogoOnline(View view){
		setContentView(view);
	}
	
	public void CenaFinalJogo(){
		Intent intent = new Intent(GerenciadorActivity.this, FimDeJogoActivity.class);
		startActivity(intent);
	}
	
	public void CenaEscolhaTime(){
		Intent intent = new Intent(GerenciadorActivity.this, TelaEscolhaDeTimes_Activity.class);
		startActivity(intent);
	}
	
	/*
	public void onBackPressed() {
		Log.i(TAG,"--------- back pressed");

		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("abandonando o barco")
				.setMessage(
						"Tem certeza que vai embora ? vou sentir sua falta ...")
				.setPositiveButton("Adeus",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								killMeSoftly();
							}

						}).setNegativeButton("Entao ta, fico + um pouco", null)
				.show();
	}
*/
	/**
	 * realiza limpeza dos threads em funcionamento
	 */
}
