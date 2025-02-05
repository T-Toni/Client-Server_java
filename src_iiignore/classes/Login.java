package classes;

import com.google.gson.Gson;

public class Login {
	private String op;
	private String user;
	private String password;
	
	public Login(String usuario, String senha) 
	{
		this.op = "5";
		this.user = usuario;
		this.password = senha;
	}
	
	public String Padroniza() 
	{
		Gson gson = new Gson();
		String jsonString = gson.toJson(this);
		return jsonString;
	}
}
