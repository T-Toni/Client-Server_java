package classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import javax.swing.*;

public class ClienteLogado {
    public IG janela = Terminal("cliente");
    static public EchoClient2 echoClient = null;

    // Informações do servidor
    Socket echoSocket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    GerenciadorDeInterfaces g = null; // determina se deve apresentar o cliente ou o cadastro

    // Guarda o token (identificador do usuário logado)
    String token = null;

    public ClienteLogado(Socket echoSocket, PrintWriter out, BufferedReader in, GerenciadorDeInterfaces g, String token) {
        this.echoSocket = echoSocket;
        this.out = out;
        this.in = in;
        this.g = g;
        this.token = token;
    }

    public ClienteLogado(Socket echoSocket, PrintWriter out, BufferedReader in, GerenciadorDeInterfaces g) {
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

    /**
     * Envia uma mensagem para o servidor e trata o retorno.
     */
    public void EnviaMensagem(String mensagem) throws IOException {
        // Envia para o servidor
        out.println(mensagem);
        String s = in.readLine();
        System.out.println("echo logado: " + s);
        janela.area.append(s + "\n");

        // Trata a mensagem retornada (caso haja um objeto que interprete o retorno)
        TrataRetorno T = new TrataRetorno(g);
        T.analiza(s);
    }

    /**
     * Cria a interface gráfica para o cliente logado, com botões para logout, consultar,
     * atualizar e excluir usuário e, agora, para acessar a área de categorias de avisos.
     */
    public IG Terminal(String nome) {
        // Cria objeto IG (supondo que IG seja uma subclasse de JFrame com um JTextArea público "area")
        IG janela = new IG(nome);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configura a área de texto para exibir respostas
        janela.area.setBounds(10, 50, 360, 150);
        janela.area.setEditable(false);
        janela.area.setLineWrap(true);
        janela.area.setWrapStyleWord(true);
        janela.add(janela.area);

        // Botão para Logout (op "6")
        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(100, 220, 160, 25);
        janela.add(btnLogout);
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cria requisição de logout utilizando o token (op "6")
                Requisicao logout = new Requisicao("6", token);
                String mensagem = logout.Padroniza();
                try {
                    EnviaMensagem(mensagem);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Botão para Consultar Usuário (op "2")
        JButton btnConsulta = new JButton("Consultar Usuário");
        btnConsulta.setBounds(10, 260, 160, 25);
        janela.add(btnConsulta);
        btnConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userAlvo = JOptionPane.showInputDialog(janela, "Digite o user do usuário a consultar:");
                //if (userAlvo != null && !userAlvo.trim().isEmpty()) {
                // Cria a requisição para consulta (op "2")
                // token indica o usuário logado; o campo "user" é o alvo da consulta
                Requisicao req = new Requisicao("2", userAlvo, token);	
                String jsonRequest = req.Padroniza();
                try {
                    EnviaMensagem(jsonRequest);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
            }
        });

        // Botão para Atualizar Usuário (op "3")
        JButton btnAtualiza = new JButton("Atualizar Usuário");
        btnAtualiza.setBounds(180, 260, 160, 25);
        janela.add(btnAtualiza);
        btnAtualiza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userAlvo = JOptionPane.showInputDialog(janela, "Digite o user do usuário a atualizar:");
                String novoNome = JOptionPane.showInputDialog(janela, "Digite o novo nome:");
                String novaSenha = JOptionPane.showInputDialog(janela, "Digite a nova senha (4 dígitos):");
                if (userAlvo != null && novoNome != null && novaSenha != null) {
                    // Cria a requisição de atualização (op "3")
                    Requisicao req = new Requisicao("3", novoNome, userAlvo, novaSenha, token);
                    String jsonRequest = req.Padroniza();
                    try {
                        EnviaMensagem(jsonRequest);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Botão para Excluir Usuário (op "4")
        JButton btnExcluir = new JButton("Excluir Usuário");
        btnExcluir.setBounds(10, 300, 160, 25);
        janela.add(btnExcluir);
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userAlvo = JOptionPane.showInputDialog(janela, "Digite o user do usuário a excluir:");
                //if (userAlvo != null && !userAlvo.trim().isEmpty()) {
                // Cria a requisição de exclusão (op "4")
                Requisicao req = new Requisicao("4", userAlvo, token);
                String jsonRequest = req.Padroniza();
                try {
                    EnviaMensagem(jsonRequest);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            
            }
        });
        
        // Botão para acessar a Área de Avisos (categorias de avisos)
        JButton btnAvisos = new JButton("Avisos");
        btnAvisos.setBounds(180, 300, 160, 25);
        janela.add(btnAvisos);
        btnAvisos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de categorias de avisos
                AvisosUI avisos = new AvisosUI(token, echoSocket, out, in, g);
                avisos.setVisible(true);
            }
        });

        // Rótulo de informação na parte superior
        JLabel lblInfo = new JLabel("Cliente Logado: " + token);
        lblInfo.setBounds(10, 10, 300, 30);
        janela.add(lblInfo);

        // Define layout absoluto e ajusta o tamanho da janela
        janela.setLayout(null);
        janela.setSize(400, 400);

        return janela;
    }
}
