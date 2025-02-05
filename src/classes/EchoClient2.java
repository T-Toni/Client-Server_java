package classes;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;

public class EchoClient2 {
	
	static String token = null;
	
    public static void main(String[] args) throws IOException {
    	
    	//tela que pede o ip e a porta
    	ClienteInicia ig_inicio = new ClienteInicia();
    	ig_inicio.janela.setVisible(true);
    	
    	//looping para garantir que os valores foram pegos
    	while(ig_inicio.getIp() == null || ig_inicio.getPorta() == -1)
    	{
    		System.out.println("aguardando ip e porta");
    	}
    	
    	//fecha a janela caso os valores tenham sido iseridos
    	ig_inicio.janela.setVisible(false);
    	
    	
    	//IP do servidor padrão = localhost
        String serverHostname = new String (ig_inicio.getIp());	//MUDAR IP AQUI!!!
        //caso argumento no terminal, substitui o host pelo dado
        if (args.length > 0)
           serverHostname = args[0];
        
        
        System.out.println ("Attemping to connect to host " +
                serverHostname + " on port " + ig_inicio.getPorta());  //ip pego a partir da ig_inicio

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        //tenta a conexão
        try {
        	
            echoSocket = new Socket(serverHostname, ig_inicio.getPorta());		//porta pega a partir da ig_inicio
            out = new PrintWriter(echoSocket.getOutputStream(), true);						
            //OUT = out;
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));	
            //IN = in;
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to: " + serverHostname);
            System.exit(1);
        }
        
        //cria o gerenciador de interfaces
        GerenciadorDeInterfaces g = new GerenciadorDeInterfaces();
        
        //cria as interfaces
        ClienteLogado ig_logado = new ClienteLogado(echoSocket,out,in, g);
        Cliente ig_login = new Cliente(echoSocket,out,in, g, ig_logado);
        ClienteCadastro ig_cadastro = new ClienteCadastro(echoSocket,out,in, g);
        
        //exibe a interface
        ig_login.janela.setVisible(true);
        //System.out.println ("Type Message (\"Bye.\" to quit)");
        
        //enquanto alguma interface estiver visível
        while((ig_login.janela.isVisible() || ig_cadastro.janela.isVisible() || ig_logado.janela.isVisible()) && !g.getDesconectar())
        {        

        	//atualiza o token quando o usuário for logado
			if (ig_login.token != null && token == null)
			{
					token = ig_login.token;
					ig_logado.setToken(token);
			}
				
			//System.out.println(g.getDesconectar());
			//determina qual tela estará sendo exibida
			if (!g.entrou)
			{	

				ig_logado.janela.setVisible(false);
				if (g.login)
				{
					ig_cadastro.janela.setVisible(false);
					if (ig_login.janela.isVisible());
					else
					{
						ig_login.janela.setVisible(true);
					}
				}
				else
				{
					ig_login.janela.setVisible(false);
					if (ig_cadastro.janela.isVisible());
					else
					{
						ig_cadastro.janela.setVisible(true);
					}
				}
			}
			else
			{
				ig_logado.janela.setVisible(true);
				ig_cadastro.janela.setVisible(false);
				ig_login.janela.setVisible(false);
			}
		

        		
        }
        
        //fecha todas as janelas
        ig_logado.janela.setVisible(false);
		ig_cadastro.janela.setVisible(false);
		ig_login.janela.setVisible(false);
		
		out.close();
		in.close();
		echoSocket.close();
    }
}
