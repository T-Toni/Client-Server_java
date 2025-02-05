package classes;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.*;
import java.net.*;


public class ClienteLogado
{
	public IG janela = Terminal("cliente");
	static public EchoClient2 echoClient = null;
	
	//informações do servidor
	Socket echoSocket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    GerenciadorDeInterfaces g = null;	//determina se deve apresentar o cliente ou o cadastro
    //guarda o token
    String token = null;
    
    public ClienteLogado(Socket echoSocket, PrintWriter out, BufferedReader in, GerenciadorDeInterfaces g, String token)
    {
    	this.echoSocket = echoSocket;
        this.out = out;
        this.in = in;
        this.g = g;
        this.token = token;
    }
    
    public ClienteLogado(Socket echoSocket, PrintWriter out, BufferedReader in, GerenciadorDeInterfaces g)
    {
    	this.echoSocket = echoSocket;
        this.out = out;
        this.in = in;
        this.g = g;
    }

    
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void EnviaMensagem(String mensagem) throws IOException
    {
    	//envia para o servidor
	    out.println(mensagem);
	    String s = in.readLine();
	    System.out.println("echo logado: " + s);
	    janela.area.append(s);
	    
	    //trata a mensagem
	    TrataRetorno T = new TrataRetorno(g);
	    T.analiza(s);
	    
    }
	
    public IG Terminal(String nome)
    {
    	//Cria objeto:
	   	IG janela = new IG(nome);
	   	janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//fecha a janela
	  
        
	   	// Cria a área de texto:
        //JTextArea areaDeTexto = new JTextArea("Texto exibido aqui.");
        janela.area.setBounds(10, 50, 360, 150);
        janela.area.setEditable(false); // Torna a área de texto não editável
        janela.area.setLineWrap(true); // Quebra de linha automática
        janela.area.setWrapStyleWord(true); // Quebra linhas por palavras inteiras
        janela.add(janela.area);
	   	
	   	JButton troca = new JButton("logout");
	   	troca.setBounds(100, 220, 160, 25);
	   	janela.add(troca);
	   	
	   	troca.addActionListener(new ActionListener() 
	   	{
	   		@Override
	   		public void actionPerformed(ActionEvent e) 
	   		{
	   			//cria requisição logout
	   			Requisicao logout = new Requisicao("6", token);
	   			
	   			String mensagem = logout.Padroniza();
	   			//envia a requisição de logout
	   			try {
					EnviaMensagem(mensagem);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	   			
	   			
	   			//pega o retorno e trata
	   			
	   			
	   		}
	   	});
	   
	   JLabel pergunta = new JLabel("logado");
	   pergunta.setBounds(10,10, 1000,30);
	   janela.add(pergunta);
	   
	   janela.setSize(400,300); 
	   
	   return janela;
    }
}
