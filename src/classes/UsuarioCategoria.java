package classes;

import java.util.ArrayList;

public class UsuarioCategoria {
	
	String user;
	ArrayList<String> ids;
	
	public UsuarioCategoria(String user, ArrayList<String> ids) {
		super();
		this.user = user;
		this.ids = ids;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public ArrayList<String> getIds() {
		return ids;
	}

	public void setIds(ArrayList<String> ids) {
		this.ids = ids;
	}
	
	public void addId(String id)
	{
		this.ids.add(id);
	}
}
