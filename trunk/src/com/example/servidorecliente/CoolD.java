package com.example.servidorecliente;

import java.util.Random;

import com.example.servidorecliente.rede.DadosDoCliente;

import android.util.Log;

// Sum�rio - Itens
// 0 - Energ�tico
// 1 - Casca de banana
// 2 - Dinamite

public class CoolD {

    public static boolean CoolDown;
    public static int coolDownTime = -2;
    public int counterIten = 1000;
    public static int item_Selecionado = -1;
    public boolean ok = false;
    public Random rnd = new Random();

    public void updateCoolD(DadosDoCliente dadosdoCliente) {
//          Log.i("coolDown", "Status:" + CoolDown);
//          Log.i("coolDown", "coolDownTime:" + coolDownTime);
//          Log.i("coolDown", "CounterIten:" + counterIten);
//          Log.i("coolDown", "AC:" + item_Selecionado);
//          Log.i("coolDown", "OK:" + ok);
            
            if (CoolDown == true) {
                    Log.i("coolDown", "Cooldown � true!!!!");

                    if (coolDownTime > 0) {
                            counterIten--;
                            if (counterIten == 0) {
                                    Log.i("coolDown", "aaa Cooldown diminuido");
                                    coolDownTime--;
                                    dadosdoCliente.setItemEsp(-1);


                                    counterIten = 1000;
                            }
                    }
            }

            
            // Log.i("coolDown", "" + coolDown);
            if (coolDownTime == 0 && CoolDown == true) {
                    ok = true;
            }

            if (coolDownTime == -2 && ok == false) {
                    Log.i("coolDown", "entrou na 1� condicao");
                    item_Selecionado = rnd.nextInt(3);
                    coolDownTime = -1;
            }

            if (coolDownTime == 0 && ok == true) {
                    Log.i("coolDown", "entrou na 2� condicao");
                    CoolDown = false;
                    ok = false;
                    coolDownTime = -2;

            } else {
    //              item_Selecionado = -1;
            }
    }

}
