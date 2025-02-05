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


public class ClienteInicia
{
	public IG janela = Terminal("cliente");
	static public EchoClient2 echoClient = null;
	
	private String ip = null;
	private int porta = -1;

	
	
    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}



	public IG Terminal(String nome)
    {
    	//Cria objeto:
	   	IG janela = new IG(nome);
	   	janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//fecha a janela
	  
        
	   	// Cria um campo de texto
        JTextField ipField = new JTextField(20);
        ipField.setBounds(30, 50, 300, 25);
        janela.add(ipField); 
        
     // Cria um campo de texto
        JTextField portaField = new JTextField(20);
        portaField.setBounds(30, 100, 300, 25);
        janela.add(portaField); 
        
	   	
	   	JButton confirma = new JButton("confirma");
	   	confirma.setBounds(100, 220, 160, 25);
	   	janela.add(confirma);
	   	
	   	confirma.addActionListener(new ActionListener() 
	   	{
	   		@Override
	   		public void actionPerformed(ActionEvent e) 
	   		{
	   			try {
                    // Captura os valores dos campos e converte-os
                    String ipTexto = ipField.getText();
                    String portaTexto = portaField.getText();

                    // Atribui os valores às variáveis
                    ip = ipTexto; // Exemplo de conversão de IP
                    porta = Integer.parseInt(portaTexto);

                    // Exibe os valores no console para validação
                    System.out.println("IP: " + ipTexto);
                    System.out.println("Porta: " + porta);

                    // Aqui você pode adicionar a lógica para conectar o cliente usando os valores
                } catch (NumberFormatException ex) {
                    System.err.println("Erro: Certifique-se de digitar um IP válido e um número de porta.");
                }
	   		}
	   	});
	   
	   JLabel pergunta = new JLabel("Digite o ip e a porta, respectivamente");
	   pergunta.setBounds(10,10, 1000,30);
	   janela.add(pergunta);
	   
	   janela.setSize(400,300); 
	   
	   return janela;
    }
}
