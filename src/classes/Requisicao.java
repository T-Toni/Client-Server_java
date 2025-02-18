package classes;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.Gson;

public class Requisicao {
	private String token;
	private String name;	
	private String op;
	private String user;
	private String password;
	
	//para categorias
	private ArrayList<CategoriaDeAvisos> categories = new ArrayList<CategoriaDeAvisos>();
	private ArrayList<String> categoryIds = new ArrayList<String>();	//Guarda os ids dos recedos a deletar
	
	//para avisos
	String id;
	String title;
	String text;
	String categoryId;
	
	//CATEGORIAS DE AVISOS
	
	//construtor para recados 	(criar)
	public Requisicao(String opcao, String name, String description, String token)
	{
		this.op = opcao;
		
		//cria a categoria de avisos para atribuir a categories
		CategoriaDeAvisos category = new CategoriaDeAvisos(name, description, null);
		
		//adiciona o json a categories
		this.categories.add(category);
		
		this.token = token;
	}
	
	//constructor para recados	(atualizar)											boolean adicionado para diferenciar criador
	public Requisicao(String opcao, String id, String name, String description, String token, boolean ignore)
	{
		this.op = opcao;
		
		//cria a categoria de avisos para atribuir a categories
		CategoriaDeAvisos category = new CategoriaDeAvisos(name, description, id);
		
		//adiciona o json a categories
		this.categories.add(category);
		
		this.token = token;
	}
	
	//constructor para recados (excluir)						boolean adicionado para diferenciar criador
	public Requisicao(String opcao, String token, ArrayList<String> IDs) {
	    this.op = opcao;
	    this.token = token;
	    this.categoryIds = IDs;
	}
	
	public Requisicao(String opcao, String nome, String usuario, String senha, String token) {
		super();
		this.op = opcao;
		this.name = nome;
		this.user = usuario;
		this.password = senha;
		this.token = token;
	}
	
	public Requisicao(String opcao, String token) {
		super();
		this.op = opcao;
		this.token = token;
	}

	
	//AVISOS
	
	public Requisicao(String opcao, String user, String token) {	//para leitura
		super();
		this.op = opcao;
		this.user = user;
		this.token = token;
	}
	
	public Requisicao(String opcao, String id, String token, int ignore) {	//para excluir E SE INSCREVER/SAIR DE UMA CATEGORIA DE AVISO
		super();
		this.op = opcao;
		this.id = id;
		this.token = token;
	}

	public Requisicao(String opcao, String token, String titulo, String texto, String categoryId, int ignore) {	//para criação de aviso
		this.op = opcao;
		this.token = token;
		this.title = titulo;
		this.text = texto;
		this.categoryId = categoryId;
	} 	

	public Requisicao(String opcao, String token,  String id, String titulo, String texto, String categoryId) {	//para atualização de aviso
		this.op = opcao;
		this.token = token;
		this.title = titulo;
		this.text = texto;
		this.id = id;
		this.categoryId = categoryId;
	} 	
	
	
	
	//Demais
	
	public ArrayList<CategoriaDeAvisos> getCategories() {		//ja vem padronizado
		return categories;
	}

	public void setCategories(ArrayList<CategoriaDeAvisos> categories) {
		this.categories = categories;
	}

	public ArrayList<String> getIDs() {
		return categoryIds;
	}

	public void setIDs(ArrayList<String> iDs) {
		this.categoryIds = iDs;
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
