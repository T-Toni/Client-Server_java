package classes;

public class GerenciadorDeInterfaces {
	boolean login = true;
	boolean entrou = false;	//controla se o usuario está logado
	boolean desconectar = false; //encerra a conexão caso true
	
	
	public void troca()
	{
		login = !login;
	}
	
	public void loga(boolean b)
	{
		entrou = b;
	}
	
	public void desconecta()
	{
		this.desconectar = true;
		//System.out.println("AAAAAAAAAAAAAAAAAAAAAAA");
	}
	
	public boolean getDesconectar()
	{
		return desconectar;
	}
}
