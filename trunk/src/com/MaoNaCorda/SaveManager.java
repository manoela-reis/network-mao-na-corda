package com.MaoNaCorda;

import java.util.Set;


import android.content.SharedPreferences;
import android.text.TextUtils.StringSplitter;
/*
///
	Classe pega do Thauan que fará uma guardará a patente do jogador na memória do celular sem precisar usar banco de dados.
///

public class SaveManager {

	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	private SaveManager() {
		sp = MinhasCoisas.getCurrentActivity().getPreferences(
				MinhasCoisas.getCurrentActivity().MODE_PRIVATE);
		editor = sp.edit();
	}

	private static SaveManager instance;

	public static SaveManager getInstance() {
		if (instance == null) {
			instance = new SaveManager();
		}
		return instance;
	}

	// ------------------------ -----------------------
	// --------------------------- ----------------------

	public void SaveInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	public void SaveFloat(String key, float value) {
		editor.putFloat(key, value);
		editor.commit();
	}

	public void SaveBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	public void SaveString(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	public void SaveStringSet(String key, Set<String> values) {
		editor.putStringSet(key, values);
		editor.commit();
	}

	// ---------------
	int defValue = 0;
	boolean defbool = false;

	// ---------------
	
	public int LoadInt(String key)
	{
		return sp.getInt(key, defValue);
	}	
	public float LoadFloat(String key)
	{
		return sp.getFloat(key, defValue);
	}	
	public boolean LoadBool(String key)
	{
		return sp.getBoolean(key, defbool);
	}
	public String LoadString(String key)
	{
		return sp.getString(key, "default");
	}
	
}
*/