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
	private Bitmap fundoMenu;
	private Bitmap opcaoNovoJogo;
	private Bitmap opcaoInstrucoes;
	private Bitmap opcaoCreditos;
	private Bitmap fundoCreditos;
	private Bitmap fundoInstrucoes;
	
	ImageManager img;
	
	int altura;
	int largura;
	
	private Rect rectPatente = new Rect();
	private Rect rectZ = new Rect();
	private Rect rectDivisor = new Rect();
	private Rect rectFundoJogo = new Rect();

	public BitmapManager(Context context)
	{
		
		
		Instance = this;
		
		img = new ImageManager(context);
		
		// Carregando as imagens da pasta assets
		fundo = img.ImageManager("background.png");
		massa = img.ImageManager("massa.png");
		impulso = img.ImageManager("impulso.png");
		velocidade = img.ImageManager("velocidade.png");
		energetico = img.ImageManager("energetico.png");
		divisor = img.ImageManager("parte_do_meio.png");
		patente = img.ImageManager("patente.png");
		z = img.ImageManager("z.png");
		fundoMenu = img.ImageManager("menuprincipal_fundo.png");
		opcaoNovoJogo = img.ImageManager("menuprincipal_Batalha.png");
		opcaoInstrucoes = img.ImageManager("menuprincipal_Instrucoes.png");
		opcaoCreditos = img.ImageManager("menuprincipal_Creditos.png");
		fundoCreditos = img.ImageManager("creditos_quadro.png");
		fundoInstrucoes = img.ImageManager("instrucoes_quadro.png");
		
	
	}
	public void setRect(ViewDeRede view){
		altura = view.alturaView;
		largura = view.larguraView;
		rectPatente.set((int)(largura/1.5f), altura/20, (int)(largura/1.02), altura/4);
		rectZ.set(largura/30, altura/20, (int)(largura/5.7f), (int)(altura/3.4f));
		rectDivisor.set((int)(largura/2.15),(int)(altura/1.78),(int)(largura/1.7),(int)(altura/1.4f));	
		rectFundoJogo.set(0,0,largura,altura);
	}
	
	public static BitmapManager GetInstance(){
		return Instance;
	}
	
	// Bitmaps
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

	public Bitmap getFundoMenu(){
		return fundoMenu;
	}
	
	public Bitmap getFundoInstrucoes(){
		return fundoInstrucoes;
	}

	public Bitmap getFundoCreditos(){
		return fundoCreditos;
	}

	public Bitmap getOpcaoBatalha()
	{
		return opcaoNovoJogo;
	}

	public Bitmap getOpcaoCreditos()
	{
		return opcaoCreditos;
	}
	
	
	public Bitmap getOpcaoInstrucoes()
	{
		return opcaoInstrucoes;
	}
	
	// Rects
	public Rect getRectFundo(){
		return rectFundoJogo;
	}
	
	public Rect getRectPatente(){
		return rectPatente;
	}
	
	public Rect getRectZ(){
		return rectZ;
	}
	
	public Rect getRectDivisor(){
		return rectDivisor;
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
