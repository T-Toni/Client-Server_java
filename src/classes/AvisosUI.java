package classes;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class AvisosUI extends JFrame {
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
        // Se desejar, pode atualizar a área de texto localmente
        areaAvisos.append("enviada: " + mensagem + "\n");
        areaAvisos.append("recebida: " + s + "\n");

        // Trata a mensagem retornada (caso haja um objeto que interprete o retorno)
        TrataRetorno T = new TrataRetorno(g);
        T.analiza(s);
    }
    
    public AvisosUI(String token, Socket echoSocket, PrintWriter out, BufferedReader in, GerenciadorDeInterfaces g) {
        this.out = out;
        this.echoSocket = echoSocket;
        this.in = in;
        this.g = g;
        
        this.token = token;
        setTitle("Categorias de Avisos");
        setSize(500, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Rótulo de cabeçalho
        JLabel lblTitulo = new JLabel("Categorias de Avisos");
        lblTitulo.setBounds(20, 10, 300, 30);
        add(lblTitulo);

        // Área para exibir as categorias de avisos (apenas texto, por enquanto)
        areaAvisos = new JTextArea();
        areaAvisos.setEditable(false);
        areaAvisos.setLineWrap(true);
        areaAvisos.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(areaAvisos);
        scrollPane.setBounds(20, 50, 450, 200);
        add(scrollPane);

        // Botão para Criar Categoria de Avisos (op "7")
        btnCriar = new JButton("Criar Categoria");
        btnCriar.setBounds(20, 270, 140, 25);
        add(btnCriar);
        btnCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Solicita ao usuário as informações para criar uma nova categoria
                String nome = JOptionPane.showInputDialog(AvisosUI.this, "Digite o nome da categoria:");
                String descricao = JOptionPane.showInputDialog(AvisosUI.this, "Digite a descrição da categoria:");
                
                // Verifica se os dados foram informados
                if (nome == null || nome.trim().isEmpty() ||
                    descricao == null || descricao.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AvisosUI.this, "Nome e descrição são obrigatórios.");
                    return;
                }
                
                // Cria a requisição para criação de categoria (op "7")
                // Supondo que o construtor da Requisicao para op "7" seja: 
                // Requisicao(String op, String nome, String descricao, String token)
                Requisicao req = new Requisicao("7", nome, descricao, token); 
                
                // Envia a requisição para o servidor
                try {
                    EnviaMensagem(req.Padroniza());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Botão para Atualizar Categoria de Avisos (op "9")
        btnAtualizar = new JButton("Atualizar Categoria");
        btnAtualizar.setBounds(180, 270, 140, 25);
        add(btnAtualizar);
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Solicita as informações necessárias para atualizar uma categoria
                String id = JOptionPane.showInputDialog(AvisosUI.this, "Digite o ID da categoria a atualizar:");
                String novo_nome = JOptionPane.showInputDialog(AvisosUI.this, "Digite o novo nome da categoria:");
                String nova_descricao = JOptionPane.showInputDialog(AvisosUI.this, "Digite a nova descrição da categoria:");
                
                // Verifica se os dados foram informados
                if (id == null || id.trim().isEmpty() ||
                    novo_nome == null || novo_nome.trim().isEmpty() ||
                    nova_descricao == null || nova_descricao.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AvisosUI.this, "ID, nome e descrição são obrigatórios para atualizar.");
                    return;
                }
                
                // Cria a requisição de atualização (op "9")
                // Supondo que o construtor seja: 
                // Requisicao(String op, String novo_nome, String nova_descricao, String id, String token, boolean ignore)
                Requisicao req = new Requisicao("9", id, novo_nome, nova_descricao, token, false);
                
                // Envia a requisição para o servidor
                try {
                    EnviaMensagem(req.Padroniza());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Botão para Excluir Categoria de Avisos (op "10")
        btnExcluir = new JButton("Excluir Categoria");
        btnExcluir.setBounds(340, 270, 140, 25);
        add(btnExcluir);
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Solicita ao usuário os IDs das categorias a excluir (vários separados por vírgula)
                String idsStr = JOptionPane.showInputDialog(AvisosUI.this, "Digite os IDs das categorias a excluir (separados por vírgula):");
                if (idsStr == null || idsStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AvisosUI.this, "Informe ao menos um ID.");
                    return;
                }
                // Divide a string e adiciona os IDs a um ArrayList
                ArrayList<String> ids = new ArrayList<>();
                String[] split = idsStr.split(",");
                for (String s : split) {
                    if (!s.trim().isEmpty()) {
                        ids.add(s.trim());
                    }
                }
                // Cria a requisição de exclusão (op "10")
                
                Requisicao req = new Requisicao("10", token, ids);
                
                // Envia a requisição para o servidor
                try {
                    EnviaMensagem(req.Padroniza());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Botão para Ler Categorias (op "8")
        btnLer = new JButton("Ler Categorias");
        btnLer.setBounds(20, 310, 140, 25);
        add(btnLer);
        btnLer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Cria a requisição para ler as categorias (op "8")
                // Supondo que o construtor seja: Requisicao(String op, String token)
                Requisicao req = new Requisicao("8", token);
                try {
                    EnviaMensagem(req.Padroniza());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Botão para Voltar (fechar a janela)
        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(180, 310, 140, 25);
        add(btnVoltar);
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
