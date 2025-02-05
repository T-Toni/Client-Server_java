package classes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.io.*;
import java.net.*;

import com.google.gson.Gson;


public class Cliente
{
	public IG janela = Terminal("cliente");
	static public EchoClient2 echoClient = null;
	
	//informações do servidor
	Socket echoSocket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    GerenciadorDeInterfaces g = null;	//determina se deve apresentar o cliente ou o cadastro	
    ClienteLogado logado = null;
    
    public Cliente(Socket echoSocket, PrintWriter out, BufferedReader in, GerenciadorDeInterfaces g, ClienteLogado logado)
    {
    	this.echoSocket = echoSocket;
        this.out = out;
        this.in = in;
        this.g = g;
        this.logado = logado;
    }
    
    public void EnviaMensagem(String mensagem) throws IOException
    {
    	//envia para o servidor
	    out.println(mensagem);
	    //recebe mensagem do servidor
	    String retorno = in.readLine();
	    System.out.println("echo: " + retorno);
	    
	    //trata
	    Gson gson = new Gson();
	    Mensagem retorno_json = gson.fromJson(retorno, Mensagem.class);
	    if (retorno_json.response.equals("000") || retorno_json.response.equals("001"))
	    {
	    	//permite o login
	    	g.loga(true);
	    	logado.setToken(retorno_json.token);
	    }
	    
	    
	    
    }
	
    public IG Terminal(String nome)
    {
	   	IG janela = new IG(nome);
	   	janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//fecha a janela
	   
        JTextField campo_nome = new JTextField(20);
        campo_nome.setBounds(30, 40, 300, 25);
        janela.add(campo_nome);  
        
        JTextField campo_senha = new JTextField(20);
        campo_senha.setBounds(30, 70, 300, 25);
        janela.add(campo_senha);  
	   
	   	// Cria o botao de efetuar o login
	   	JButton button = new JButton("login");
	   	button.setBounds(100, 100, 160, 25);
	   	janela.add(button);
       
	   	JButton troca = new JButton("troca");
	   	troca.setBounds(280, 10, 90, 25);
	   	janela.add(troca);
	   	
	   	// Adiciona uma ação ao botão
	   	button.addActionListener(new ActionListener() {
	   		@Override
	   		public void actionPerformed(ActionEvent e) {
	   			// Pega o texto do campo de texto e exibe no console
	   			janela.input = campo_nome.getText();
	   			janela.input = campo_senha.getText();
	   			
	   			//transforma em uma string jason
	   			Login login = new Login(campo_nome.getText(), campo_senha.getText());
	   			
	   			String mensagem = login.Padroniza();
	   			
	   			//adicionar funcao
	   			try {
					EnviaMensagem(mensagem);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
	   		}
	   	});
	   
	   	troca.addActionListener(new ActionListener() {
	   		@Override
	   		public void actionPerformed(ActionEvent e) {
	   			g.troca();
	   		}
	   	});
	   	
	   JLabel pergunta = new JLabel("Digite seu usuário e senha: ");
	   pergunta.setBounds(10,10, 1000,30);
	   janela.add(pergunta);
	   
	   janela.setSize(400,200); 
	   
	   return janela;
    }
   
    
}