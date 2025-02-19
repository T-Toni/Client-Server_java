package classes;

import java.util.ArrayList;

import com.google.gson.Gson;

public class requisicaoAvisos {
	
	private String token;
	private String op;
	
	//para avisos
	String id;
	String title;
	String text;
	String categoryId;
	
	
	public requisicaoAvisos(String op, String token, String title, String text, String categoryId) {
		super();
		this.token = token;
		this.op = op;
		this.title = title;
		this.text = text;
		this.categoryId = categoryId;
	}


	public requisicaoAvisos(String op, String id, String token) {
		super();
		this.token = token;
		this.op = op;
		this.id = id;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getOp() {
		return op;
	}


	public void setOp(String op) {
		this.op = op;
	}
	
	public String Padroniza() 
	{
		Gson gson = new Gson();
		String jsonString = gson.toJson(this);
		return jsonString;
	}

}
