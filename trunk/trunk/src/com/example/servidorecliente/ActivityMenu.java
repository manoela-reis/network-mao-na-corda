package com.example.servidorecliente;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.TelasDeSelecao.Creditos;
import com.TelasDeSelecao.Instrucoes;
import com.example.servidorecliente.R;

public class ActivityMenu extends Activity {

	View creditos;
	View instrucoes;
	public static String name ="padrao";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (name.equals("creditos"))
		{
			creditos = new Creditos(this);
			setContentView(creditos);
		}
		
		else 
		{
			instrucoes = new Instrucoes(this);
			setContentView(instrucoes);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_teste, menu);
		return true;
	}	

}
