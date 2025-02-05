package classes;

import com.google.gson.Gson;

public class Requisicao {
	private String token;
	private String name;	
	private String op;
	private String user;
	private String password;
	
	
	public Requisicao(String opicao, String nome, String usuario, String senha, String token) {
		super();
		this.op = opicao;
		this.name = nome;
		this.user = usuario;
		this.password = senha;
		this.token = token;
	}
	
	public Requisicao(String opicao, String token) {
		super();
		this.op = opicao;
		this.token = token;
	}
	
	public String Padroniza() 
	{
		Gson gson = new Gson();
		String jsonString = gson.toJson(this);
		return jsonString;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	
}
