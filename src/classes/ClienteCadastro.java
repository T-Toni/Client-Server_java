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


public class ClienteCadastro
{
	public IG janela = Terminal("cliente");
	static public EchoClient2 echoClient = null;
	
	//informações do servidor
	Socket echoSocket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    GerenciadorDeInterfaces g = null;	//determina se deve apresentar o cliente ou o cadastro	
    
    public ClienteCadastro(Socket echoSocket, PrintWriter out, BufferedReader in, GerenciadorDeInterfaces g)
    {
    	this.echoSocket = echoSocket;
        this.out = out;
        this.in = in;
        this.g = g;
    }
    
    public void EnviaMensagem(String mensagem) throws IOException
    {
    	//envia para o servidor
	    out.println(mensagem);

	    System.out.println("echo: " + in.readLine());
    }
	
    public IG Terminal(String nome)
    {
    	//Cria objeto:
	   	IG janela = new IG(nome);
	   	janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//fecha a janela
	   
	   	// Cria um campo de texto
        JTextField campo_nome = new JTextField(20);
        campo_nome.setBounds(30, 115, 300, 25);
        janela.add(campo_nome);  
        
        // Cria um campo de texto
        JTextField campo_usuario = new JTextField(20);
        campo_usuario.setBounds(30, 150, 300, 25);
        janela.add(campo_usuario);  
        
        // Cria um campo de texto
        JTextField campo_senha = new JTextField(20);
        campo_senha.setBounds(30, 185, 300, 25);
        janela.add(campo_senha);  
	   
	   	// Cria um botão
	   	JButton button = new JButton("Cadastrar");
	   	button.setBounds(100, 220, 160, 25);
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
	   			Cadastro cadastro = new Cadastro(campo_nome.getText(), campo_usuario.getText(), campo_senha.getText());
	   			
	   			String mensagem = cadastro.Padroniza();
	   			
	   			//adicionar funcao
	   			try {
					EnviaMensagem(mensagem);
				} catch (IOException e1) {

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
	   
	   JLabel pergunta = new JLabel("Digite seu nome, usuário e senha: ");
	   pergunta.setBounds(10,10, 1000,30);
	   janela.add(pergunta);
	   
	   janela.setSize(400,300); 
	   
	   return janela;
    }
}