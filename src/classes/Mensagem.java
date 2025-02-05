package classes;

import java.util.ArrayList;

import com.google.gson.Gson;

public class Mensagem {

	String response = "999";
	String message = null;	//ou usuario
	String token = null;	//ou senha
	
	String user = null;
	String password = null;
	String name = null;
	//String nome = null;
	
	//para avisos
	ArrayList<CategoriaDeAvisos>categories = null;
	
	public Mensagem(String tipo, String mensagem, String token) 
	{
		super();
		this.response = tipo;
		this.message = mensagem;
		this.token = token;
	}
	
	public Mensagem(String response, String mensagem, ArrayList<CategoriaDeAvisos> categories, boolean ignore) 
	{
		super();
		this.response = response;
		this.message = mensagem;
		this.categories = categories;
	}
	
	
	public Mensagem(String tipo, String user, String password, String name, String message) 	//caso de read
	{
		super();
		this.response = tipo;
		
		this.user = user;
		this.password = password;
		this.name = name;
		
		this.message = message;
	}
	
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
