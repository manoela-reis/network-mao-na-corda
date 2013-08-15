package com.example.servidorecliente;

import java.util.Random;

import android.util.Log;

// Sumário - Itens
// 0 - Dinamite
// 1 - Casca de banana
// 2 - Energético

public class CoolD {

	public static boolean CoolDown;
	public static int coolDownTime = -2;
	public int counterIten = 1000;
	public static int item_Selecionado = -1;
	public boolean ok = false;
	public Random rnd = new Random();

	public void update2() {
		Log.i("coolDown", "Status:" + CoolDown);
		Log.i("coolDown", "coolDownTime:" + coolDownTime);
		Log.i("coolDown", "CounterIten:" + counterIten);
		Log.i("coolDown", "AC:" + item_Selecionado);
		Log.i("coolDown", "OK:" + ok);
		
		if (CoolDown == true) {
			Log.i("coolDown", "Cooldown é true!!!!");

			if (coolDownTime > 0) {
				counterIten--;
				if (counterIten == 0) {
					Log.i("coolDown", "aaa Cooldown diminuido");
					coolDownTime--;
					counterIten = 1000;
				}
			}
		}
		// Log.i("coolDown", "" + coolDown);
		if (coolDownTime == 0 && CoolDown == true) {
			ok = true;
		}

		if (coolDownTime == -2 && ok == false) {
			Log.i("coolDown", "entrou na 1º condicao");
			item_Selecionado = rnd.nextInt(3);
			coolDownTime = -1;
		}

		if (coolDownTime == 0 && ok == true) {
			Log.i("coolDown", "entrou na 2º condicao");
			CoolDown = false;
			ok = false;
		} else {
	//		item_Selecionado = -1;
		}
	}

}
