package com.example.servidorecliente.rede;

public interface Protocolo {

	public static final String PROTOCOL_ID = "ID";
	public static final String PROTOCOL_MOVE = "MOVE";
	public static final String PROTOCOL_INICIAR = "INICIAR";
	public static final String PROTOCOL_ITENS = "ITENS";
	public static final String PROTOCOL_FINALIZAR = "FINALIZAR";
	public static final String PROTOCOL_ITEMESP = "ITEM_ESPECIAL";
	public static final String PROTOCOL_ACIONAR = "ACIONAR ITEM";
	public static final String PROTOCOL_PERDEU = "PERDEU";
	public static final String PROTOCOL_SAIU = "SAIU";
}
