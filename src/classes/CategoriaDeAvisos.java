package classes;

import com.google.gson.Gson;

public class CategoriaDeAvisos {

	String name = null;
	String description = null;
	String id = null;
	String subscribed = "true";
	
	public CategoriaDeAvisos(String name, String description, String id) {
		super();
		this.name = name;
		this.description = description;
		this.id = id;
		subscribed = "true";
	}
	
	public String Padroniza() 
	{
		Gson gson = new Gson();
		String jsonString = gson.toJson(this);
		return jsonString;
	}
}
