package classes;

public class Usuario {

	String name = null;
	String user = null;
	String password = null;
	boolean adminstrador = false;
	boolean logado = false;
	
	public void set_logado(boolean logado)
	{
		this.logado = logado;
	}
	
	public Usuario(String nome, String usuario, String senha) {
		super();
		this.name = nome;
		this.user = usuario;
		this.password = senha;
	}
	
	public Usuario(String nome, String usuario, String senha, boolean admin) {
		super();
		this.name = nome;
		this.user = usuario;
		this.password = senha;
		this.adminstrador = admin;
	}

	public boolean isAdminstrador() {
		return adminstrador;
	}

	public void setAdminstrador(boolean adminstrador) {
		this.adminstrador = adminstrador;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
