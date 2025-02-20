package classes;

import com.google.gson.Gson;

public class Aviso {
	
	String title;
	String text;
	String categoryId;
	String id;
	String date;
	
	public Aviso(String title, String text, String categoryId) {
		super();
		this.title = title;
		this.text = text;
		this.categoryId = categoryId;
		this.date = "20/02/2025";
	}
	
	public String Padroniza() 
	{
		Gson gson = new Gson();
		String jsonString = gson.toJson(this);
		return jsonString;
	}

}
