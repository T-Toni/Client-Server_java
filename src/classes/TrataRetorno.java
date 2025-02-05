package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

//USUARIO

public class TrataRetorno {

	//informações do servidor
    GerenciadorDeInterfaces g = null;	
    
    
    
    public TrataRetorno(GerenciadorDeInterfaces g) {
		super();
		this.g = g;
	}
	
    public void analiza(String retorno) throws IOException
    {
    	
    	if(retorno == null)
    	{
    		//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAnull");
    		return;
    	}
    	
    	Gson gson = new Gson();
    	Mensagem mensagem = gson.fromJson(retorno, Mensagem.class);
    	
    	
    	//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaqui");
    	
    	if (mensagem.response.equals("010"))
    	{
    		
    		g.loga(false); //troca para a tela de login
    		g.desconecta();
    		//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    	}
    	
    	//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAfim");
    	
    }
    
}
