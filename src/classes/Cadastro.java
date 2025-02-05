package classes;

import com.google.gson.Gson;

public class Cadastro {
	private String name;	
	private String op;
	private String user;
	private String password;
	
	public Cadastro(String name, String user, String password) {
		this.op = "1";
		this.name = name;
		this.user = user;
		this.password = password;
	}
	
	public String Padroniza() {
		Gson gson = new Gson();
		String jsonString = gson.toJson(this);
		return jsonString;
	}
}

