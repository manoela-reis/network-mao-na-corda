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

public class BitmapManager {

	public static BitmapManager Instance;
	public static final String TAG = "GerenteCenas";

	private Bitmap fundo;
	private Bitmap casca;
	private Bitmap tnt;
	private Bitmap energetico;

	private Bitmap cascaPB;
	private Bitmap tntPB;
	private Bitmap energeticoPb;
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
	private Bitmap cascaSprite1;
	private Bitmap cascaSprite2;
	private Bitmap TNTSprite1;
	private Bitmap TNTSprite2;
	private Bitmap MaoRosa1;
	private Bitmap MaoRosa2;
	private Bitmap CordaFull;
	private Bitmap tntUso;
	private Bitmap cascaUso;
	private Bitmap energeticoUso;
	private Bitmap marcador;
	private Bitmap fundoServer;
	private Bitmap vitoria;
	private Bitmap derrota;

	public BitmapManager(Context context) {

		Instance = this;

		img = new ImageManager(context);

		// Carregando as imagens da pasta assets

		fundo = img.ImageManager("BG_Mao.png");
		tnt = img.ImageManager("dinamite.png");
		casca = img.ImageManager("casca_de_banana.png");
		MaoRosa1 = img.ImageManager("garrafafx1.png");
		MaoRosa2 = img.ImageManager("garrafafx2.png");
		CordaFull = img.ImageManager("cordafull.png");
		marcador = img.ImageManager("SetaMarcaPlayer.png");

		cascaSprite1 = img.ImageManager("Banana_sprite1.png");
		cascaSprite2 = img.ImageManager("Banana_sprite2.png");
		vitoria = img.ImageManager("vitoria.png");
		derrota = img.ImageManager("derrota.png");
		TNTSprite1 = img.ImageManager("bomba1.png");
		TNTSprite2 = img.ImageManager("bomba2.png");


		energetico = img.ImageManager("energetico.png");
		tntPB = img.ImageManager("dinamite_pb.png");
		cascaPB = img.ImageManager("casca_de_banana_pb.png");
		energeticoPb = img.ImageManager("energetico_pb.png");
		tntUso = img.ImageManager("dinamite_usado.png");
		cascaUso = img.ImageManager("casca_de_banana_usado.png");
		energeticoUso = img.ImageManager("energetico_usado.png");
		divisor = img.ImageManager("parte_do_meio.png");
		patente = img.ImageManager("patente.png");
		z = img.ImageManager("z.png");
		fundoServer = img.ImageManager("codigo_quadro_novoserv.png");
		fundoMenu = img.ImageManager("menuprincipal_fundo.png");
		opcaoNovoJogo = img.ImageManager("menuprincipal_Batalha.png");
		opcaoInstrucoes = img.ImageManager("menuprincipal_Instrucoes.png");
		opcaoCreditos = img.ImageManager("menuprincipal_Creditos.png");
		fundoCreditos = img.ImageManager("creditos_quadro.png");
		fundoInstrucoes = img.ImageManager("instrucoes_quadro.png");

	}

	public void setRect(ViewDeRede view) {
		altura = view.alturaView;
		largura = view.larguraView;
		rectPatente.set((int) (largura / 1.5f), altura / 20,
				(int) (largura / 1.02), altura / 4);
		rectZ.set(largura / 30, altura / 20, (int) (largura / 5.7f),
				(int) (altura / 3.4f));
		rectDivisor.set((int) (largura / 2.15), (int) (altura / 1.85),
				(int) (largura / 1.7), (int) (altura / 1.5f));
		rectFundoJogo.set(0, 0, largura, altura);
	}

	public static BitmapManager GetInstance() {
		return Instance;
	}

	// Bitmaps
	public Bitmap getImageFundo() {
		return fundo;
	}

	public Bitmap getImageTNT() {
		return tnt;
	}

	public Bitmap getImageCasca() {
		return casca;
	}

	public Bitmap getImageEnergUso() {
		return energeticoUso;
	}

	public Bitmap getImageTNTUso() {
		return tntUso;
	}

	public Bitmap getImageCascaUso() {
		return cascaUso;
	}

	public Bitmap getImageMarcador() {
		return marcador;
	}

	public Bitmap getImageCascaSprite1() {
		return cascaSprite1;
	}

	public Bitmap getImageCascaSprite2() {
		return cascaSprite2;
	}

	public Bitmap getImageVitoria() {
		return vitoria;
	}

	public Bitmap getImageDerrota() {
		return derrota;
	}

	public Bitmap getImageTNTSprite1() {
		return TNTSprite1;
	}

	public Bitmap getImageMaoRosa1() {
		return MaoRosa1;
	}

	public Bitmap getImageMaoRosa2() {
		return MaoRosa2;
	}

	public Bitmap getImageTNTSprite2() {
		return TNTSprite2;
	}

	public Bitmap getImageEnergitco() {
		return energetico;
	}

	public Bitmap getImageTNTPB() {
		return tntPB;
	}

	public Bitmap getImageCascaPB() {
		return cascaPB;
	}

	public Bitmap getImageEnergitcoPB() {
		return energeticoPb;
	}

	public Bitmap getImageDivisor() {
		return divisor;
	}

	public Bitmap getImagePatente() {
		return patente;
	}

	public Bitmap getImageZ() {
		return z;
	}

	public Bitmap getFundoMenu() {
		return fundoMenu;
	}

	public Bitmap getFundoInstrucoes() {
		return fundoInstrucoes;
	}

	public Bitmap getFundoCreditos() {
		return fundoCreditos;
	}

	public Bitmap getOpcaoBatalha() {
		return opcaoNovoJogo;
	}

	public Bitmap getOpcaoCreditos() {
		return opcaoCreditos;
	}

	public Bitmap getOpcaoInstrucoes() {
		return opcaoInstrucoes;
	}

	public Bitmap getfundoServer() {
		return fundoServer;
	}

	// Rects
	public Rect getRectFundo() {
		return rectFundoJogo;
	}

	public Rect getRectPatente() {
		return rectPatente;
	}

	public Bitmap getimgCordaFull() {
		return CordaFull;
	}

	public Rect getRectZ() {
		return rectZ;
	}

	public Rect getRectDivisor() {
		return rectDivisor;
	}

	/*
	 * public void CenaMenu(){
	 * 
	 * Log.i("MenuActivity", "Call");
	 * 
	 * Intent intent = new Intent(GerenciadorActivity.this, MenuActivity.class);
	 * startActivity(intent); }
	 * 
	 * public void CenaConect(){
	 * 
	 * Intent intent = new Intent(GerenciadorActivity.this,
	 * ConectActivity.class); startActivity(intent); }
	 * 
	 * public void CenaCreditos(){ Intent intent = new
	 * Intent(GerenciadorActivity.this,CreditosActivity.class);
	 * startActivity(intent); }
	 * 
	 * public void CenaDicas(){ Intent intent = new
	 * Intent(GerenciadorActivity.this,DicasActivity.class);
	 * startActivity(intent); }
	 * 
	 * public void CenaBatalha(){ Intent intent = new
	 * Intent(GerenciadorActivity.this, BatalhaActivity.class);
	 * startActivity(intent); }
	 * 
	 * public void CenaJogoOnline(View view){ setContentView(view); }
	 * 
	 * public void CenaFinalJogo(){ Intent intent = new
	 * Intent(GerenciadorActivity.this, FimDeJogoActivity.class);
	 * startActivity(intent); }
	 * 
	 * public void CenaEscolhaTime(){ Intent intent = new
	 * Intent(GerenciadorActivity.this, TelaEscolhaDeTimes_Activity.class);
	 * startActivity(intent); }
	 * 
	 * /* public void onBackPressed() { Log.i(TAG,"--------- back pressed");
	 * 
	 * new AlertDialog.Builder(this)
	 * .setIcon(android.R.drawable.ic_dialog_alert)
	 * .setTitle("abandonando o barco") .setMessage(
	 * "Tem certeza que vai embora ? vou sentir sua falta ...")
	 * .setPositiveButton("Adeus", new DialogInterface.OnClickListener() {
	 * public void onClick(DialogInterface dialog, int which) { killMeSoftly();
	 * }
	 * 
	 * }).setNegativeButton("Entao ta, fico + um pouco", null) .show(); }
	 */
	/**
	 * realiza limpeza dos threads em funcionamento
	 */
}
