package classes;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class AvisosAdminUI extends JFrame {
    private JTextArea areaAvisos;
    private JButton btnCriar, btnAtualizar, btnExcluir, btnLer, btnVoltar;
    private String token;
    
    Socket echoSocket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    GerenciadorDeInterfaces g = null;
    
    public void EnviaMensagem(String mensagem) throws IOException {
        // Envia para o servidor
        out.println(mensagem);
        String s = in.readLine();
        System.out.println("echo logado: " + s);
        areaAvisos.append("enviada: " + mensagem + "\n");
        areaAvisos.append("recebida: " + s + "\n");

        // Trata a mensagem retornada (caso haja um objeto que interprete o retorno)
        TrataRetorno T = new TrataRetorno(g);
        T.analiza(s);
    }
    
    public AvisosAdminUI(String token, Socket echoSocket, PrintWriter out, BufferedReader in, GerenciadorDeInterfaces g) {
        this.out = out;
        this.echoSocket = echoSocket;
        this.in = in;
        this.g = g;
        this.token = token;
        
        //System.out.println(token + "lalala");
        
        setTitle("avisos");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Rótulo de cabeçalho
        //JLabel lblTitulo = new JLabel("Administrador: " + token);
        //lblTitulo.setBounds(10, 10, 300, 30);
        //add(lblTitulo);

        // Área para exibir mensagens do servidor
        areaAvisos = new JTextArea();
        areaAvisos.setEditable(false);
        areaAvisos.setLineWrap(true);
        areaAvisos.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(areaAvisos);
        scrollPane.setBounds(20, 50, 450, 200);
        add(scrollPane);



        // --- Operações sobre Avisos (Anúncios) ---
        // Botão para Criar Aviso (op "11")
        JButton btnCriarAviso = new JButton("Criar Aviso");
        btnCriarAviso.setBounds(180, 310, 140, 25);
        add(btnCriarAviso);
        btnCriarAviso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog(AvisosAdminUI.this, "Digite o título do aviso:");
                String text = JOptionPane.showInputDialog(AvisosAdminUI.this, "Digite o texto do aviso:");
                String categoryId = JOptionPane.showInputDialog(AvisosAdminUI.this, "Digite o ID da categoria do aviso:");
                if (title == null || title.trim().isEmpty() ||
                    text == null || text.trim().isEmpty() ||
                    categoryId == null || categoryId.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AvisosAdminUI.this, "Title, text e category ID são obrigatórios.");
                    return;
                }
                Requisicao req = new Requisicao();
                
                req.setOp("11");
                req.setToken(token);
                req.title = title;
                req.text = text;
                req.categoryId = categoryId;
                
                try {
                    EnviaMensagem(req.Padroniza());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Botão para Atualizar Aviso (op "13")
        JButton btnAtualizaAviso = new JButton("Atualizar Aviso");
        btnAtualizaAviso.setBounds(340, 310, 140, 25);
        add(btnAtualizaAviso);
        btnAtualizaAviso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(AvisosAdminUI.this, "Digite o ID do aviso a atualizar:");
                String novo_title = JOptionPane.showInputDialog(AvisosAdminUI.this, "Digite o novo título do aviso (deixe vazio para manter):");
                String novo_text = JOptionPane.showInputDialog(AvisosAdminUI.this, "Digite o novo texto do aviso (deixe vazio para manter):");
                String novo_categoryId = JOptionPane.showInputDialog(AvisosAdminUI.this, "Digite o novo ID da categoria do aviso (deixe vazio para manter):");
                if (id == null || id.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AvisosAdminUI.this, "ID é obrigatório para atualizar o aviso.");
                    return;
                }
                // Cria a requisição de atualização (op "13")
                Requisicao req = new Requisicao("13", token, id, novo_title, novo_text, novo_categoryId);
                // Injetar manualmente o campo "id" na requisição
                try {
                    EnviaMensagem(req.Padroniza());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Botão para Excluir Aviso (op "14")
        JButton btnExcluirAviso = new JButton("Excluir Aviso");
        btnExcluirAviso.setBounds(20, 350, 140, 25);
        add(btnExcluirAviso);
        btnExcluirAviso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(AvisosAdminUI.this, "Digite o ID do aviso a excluir:");
                if (id == null || id.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AvisosAdminUI.this, "ID é obrigatório.");
                    return;
                }
                Requisicao req = new Requisicao();
                
                req.setOp("14");
                req.setToken(token);
                req.id = id;
                try {
                    EnviaMensagem(req.Padroniza());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Botão para Ler Avisos (op "12")
        JButton btnLerAvisos = new JButton("Ler Avisos");
        btnLerAvisos.setBounds(180, 350, 140, 25);
        add(btnLerAvisos);
        btnLerAvisos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Requisicao req = new Requisicao("12", token);
                try {
                    EnviaMensagem(req.Padroniza());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Botão para Inscrever em Categorias (op "15")
        JButton btnInscrever = new JButton("Inscrever");
        btnInscrever.setBounds(20, 390, 140, 25);
        add(btnInscrever);
        btnInscrever.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String categoryId = JOptionPane.showInputDialog(AvisosAdminUI.this, "Digite o ID da categoria para se inscrever:");
                if (categoryId == null || categoryId.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AvisosAdminUI.this, "Category ID é obrigatório.");
                    return;
                }                
                Requisicao req = new Requisicao();
                
                req.setOp("15");
                req.setToken(token);
                req.categoryId = categoryId;
                
                
                try {
                    EnviaMensagem(req.Padroniza());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Botão para Desinscrever de Categorias (op "16")
        JButton btnDesinscrever = new JButton("Desinscrever");
        btnDesinscrever.setBounds(340, 390, 140, 25);
        add(btnDesinscrever);
        btnDesinscrever.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String categoryId = JOptionPane.showInputDialog(AvisosAdminUI.this, "Digite o ID da categoria para desinscrever:");
                if (categoryId == null || categoryId.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AvisosAdminUI.this, "Category ID é obrigatório.");
                    return;
                }
                Requisicao req = new Requisicao();
                
                req.setOp("16");
                req.setToken(token);
                req.categoryId = categoryId;
                try {
                    EnviaMensagem(req.Padroniza());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        setLayout(null);
        setSize(500, 500);
    }
}
