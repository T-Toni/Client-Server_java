package classes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.*;
import java.net.*;



public class Servidor
{
	static public IG janela = Terminal("servidor");
	
	
    public void imprime(String mensagem)
    {
    	//debug
    	System.out.print(mensagem);

        //adiciona
        janela.area.append(": " + mensagem.toUpperCase() + "\n");
    }
    
    static public IG Terminal(String nome)
    {
        IG janela = new IG(nome);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fecha a janela

        //seta o jtext Area
        janela.area.setLineWrap(true);
        janela.area.setWrapStyleWord(true);
        janela.area.setEditable(false);

        //cria o JScrollPane com osiJTextArea
        janela.sp = new JScrollPane(janela.area);
        
        //define o layout da janela
        janela.setLayout(new BorderLayout());
        
        //adiciona o JScrollPane à janela usando BorderLayout.CENTER
        janela.add(janela.sp, BorderLayout.CENTER);
        
        //define a localização e o tamanho da janela
        janela.setLocation(800, 0);
        janela.setSize(640, 480);
        
        return janela;
    }
}