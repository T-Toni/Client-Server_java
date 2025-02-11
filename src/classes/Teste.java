package classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Teste {
    public static void main(String[] args) {
    	
    	/*
    	String arquivo_usuarios = "usuarios.json";	
    	Gson gson = new Gson();	
    	
    	// Ler e imprimir o conteúdo do arquivo após adicionar o usuário
	    try (FileReader reader = new FileReader(arquivo_usuarios)) {
	        Type listType = new TypeToken<ArrayList<Usuario>>(){}.getType();
	        List<Usuario> usuariosLidos = gson.fromJson(reader, listType);
	        
	        if (usuariosLidos == null) {
	            System.out.println("Nenhum usuário encontrado no arquivo.");
	        } else {
	            System.out.println("Conteúdo do arquivo após adicionar o usuário: " + usuariosLidos);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
    	
    	
        // Criar uma instância do GerenciadorDeUsuarios
        GerenciadorDeUsuarios gerenciador = new GerenciadorDeUsuarios();

        
        // Criar um usuário
        Usuario user1 = new Usuario("Adminx", "2459578", "1234", false);
        Usuario user2 = new Usuario("Adminzaz", "2459579", "1234", true);

		
        // Converter o usuário para JSON
        Gson gson = new Gson();
        String user1_json = gson.toJson(user1);
        String user2_json = gson.toJson(user2);

        
        // Cadastrar o usuário
        String resposta = gerenciador.cadastraUsuario(user1_json);
        String resposta2 = gerenciador.cadastraUsuario(user2_json);


        // Exibir resposta
        System.out.println(resposta);
        System.out.println(resposta2);
        */
    	
    	GerenciadorDeUsuarios gerenciador = new GerenciadorDeUsuarios();
    	
    	// Criar um usuário
        Usuario user1 = new Usuario("Adminx", "2459578", "1234", false);
        Usuario user2 = new Usuario("Adminzaz", "2459579", "1234", true);
    	
    	// Converter o usuário para JSON
        Gson gson = new Gson();
        String user1_json = gson.toJson(user1);
        String user2_json = gson.toJson(user2);
        
        //cria requisição para teste String opcao, String id, String name, String description, String token, boolean ignore			
        //Requisicao req = new Requisicao("7", "aviso10", "blablablarrrr", user2.getUser());	
        
        //Requisicao req = new Requisicao("10", user2.user, new ArrayList<>(Arrays.asList("2", "3")));
        
        //(String opcao, String id, String name, String description, String token, boolean ignore)
        Requisicao req = new Requisicao("9", "4", "a", "a", user2.user, false);  
        
        String resposta3 = gerenciador.trataRequisicao(req.Padroniza());	
        
        System.out.println(resposta3);
        
        
        
        
    }
}
