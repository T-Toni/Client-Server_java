package classes;

import com.google.gson.Gson;

public class Mensagem {

	String response = "999";
	String message = null;	//ou usuario
	String token = null;	//ou senha
	//String nome = null;
	
	public Mensagem(String tipo, String mensagem, String token) 
	{
		super();
		this.response = tipo;
		this.message = mensagem;
		this.token = token;
	}
	
	/*
	public Mensagem(int tipo, String mensagem, String token, String nome) 
	{
		super();
		this.tipo = tipo;
		this.mensagem = mensagem;
		this.token = token;
		this.nome = nome;
	}
	*/
	
	public Mensagem(String tipo, String token) 
	{
		super();
		this.response = tipo;
		this.message = token;
	}
	
	
	
	public String Padroniza() 
	{
		Gson gson = new Gson();
		String jsonString = gson.toJson(this);
		return jsonString;
	}
}
