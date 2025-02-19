package classes;

import com.google.gson.Gson;

public class requisicaoInscricao {

	
	private String op;
	String categoryId;
	private String token;
	
	public requisicaoInscricao(String op, String categoryId, String token) 
	{
		super();
		this.op = op;
		this.categoryId = categoryId;
		this.token = token;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String Padroniza() 
	{
		Gson gson = new Gson();
		String jsonString = gson.toJson(this);
		return jsonString;
	}
}
