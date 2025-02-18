package classes;

import com.google.gson.Gson;

public class Aviso {
	
	String title;
	String text;
	String categoryId;
	String id;
	
	public Aviso(String title, String text, String categoryId) {
		super();
		this.title = title;
		this.text = text;
		this.categoryId = categoryId;
	}
	
	public String Padroniza() 
	{
		Gson gson = new Gson();
		String jsonString = gson.toJson(this);
		return jsonString;
	}

}
