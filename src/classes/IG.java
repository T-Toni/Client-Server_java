package classes;
import java.awt.Dimension;

import javax.swing.*;  // Para JFrame, JLabel, JTextField, JButton
import java.awt.event.ActionEvent;  // Para ActionEvent
import java.awt.event.ActionListener;  // Para ActionListener

 
public class IG extends JFrame{

	//string de teste
	public String input = "default";
	static JTextArea area = new JTextArea();
	public JScrollPane sp = null; 
 
	public IG(String nome){
		//Define o título da janela
		super(nome);
		//this.montaJanela(); 
		this.setLayout(null);  
	} 
}

