package classes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GerenciadorDeUsuarios {

	String arquivo_usuarios = "usuarios.json";	//guarda o nome do arquivo onde serão guardados os usuários
	//File file = new File("usuarios.json");
	
	//String arquivo_usuarios = "D:/Facul/Distribuidos/SistemasDistribuidos/usuarios.json";	//guarda o nome do arquivo onde serão guardados os usuários
	static private Gson gson = new Gson();		//cria um gson para padronizar os usuários

	static List<Usuario> listaUsuarios = new ArrayList<>();


	public String confereUsuario(String string_json) {
		Usuario usuario = gson.fromJson(string_json, Usuario.class);

		if (usuario.user == null || usuario.password == null) {
			Mensagem mensagem = new Mensagem("002", "Fields missing", null);
			return mensagem.Padroniza();
		}

		//atualiza a lista de usuarios
		atualizaLista();

		// Conferir usuários na lista carregada
		for (Usuario item : listaUsuarios) {
			// Confere o nome
			if (item.user.equals(usuario.user)) {
				// Confere a senha
				if (item.password.equals(usuario.password)) {
					// Confere se é administrador ou usuário
					if (item.adminstrador) {
						Mensagem mensagem = new Mensagem("001", "Successful login", usuario.user);
						return mensagem.Padroniza();
					} else {
						Mensagem mensagem = new Mensagem("000", "Successful login", usuario.user);
						return mensagem.Padroniza();
					}
				}
			}
		}
		Mensagem mensagem = new Mensagem("003", "Login failed", null);
		return mensagem.Padroniza();
	}

	//atualiza "lista usuários" com todos os usuários do arquivo
	public void atualizaLista() {
		
		 // Ler e imprimir o conteúdo do arquivo após adicionar o usuário
	    try (FileReader reader = new FileReader(arquivo_usuarios)) {
	        Type listType = new TypeToken<ArrayList<Usuario>>(){}.getType();
	        List<Usuario> usuariosLidos = gson.fromJson(reader, listType);
	        
	        if (usuariosLidos == null) {
	            //System.out.println("Nenhum usuário encontrado no arquivo.");
	        } else {
	            //System.out.println("Conteúdo do arquivo: " + usuariosLidos);
	            listaUsuarios = usuariosLidos;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
			
		}
		
	public String cadastraUsuario(String string_json) {
	    Usuario usuario = gson.fromJson(string_json, Usuario.class);

	    // Confere se os campos estão preenchidos
	    if (usuario.name == null || usuario.password == null || usuario.user == null) {
	        System.out.println("nome: " + usuario.name + " senha: " + usuario.password + " usuario: " + usuario.user);
	        Mensagem mensagem = new Mensagem("101", "Fields missing", null);
	        return mensagem.Padroniza();
	    }

	    // Confere se os campos se enquadram no padrão
	    if (usuario.name.length() > 40 || usuario.user.length() != 7 || usuario.password.length() != 4) {
	        Mensagem mensagem = new Mensagem("102", "Invalid information inserted: user or password", null);
	        return mensagem.Padroniza();
	    }

	    // Atualiza a lista de usuários antes de adicionar um novo usuário
	    atualizaLista();		//IMPORTANTE_ atualiza antes de abrir o arquivo o que o apaga

	    // Confere se já existe usuário com o mesmo user
	    for (Usuario item : listaUsuarios) {
	        if (item.user.equals(usuario.user)) {
	            Mensagem mensagem = new Mensagem("103", "Already exists an account with the username", null);
	            return mensagem.Padroniza();
	        }
	    }

	    // Adiciona usuário na lista
	    listaUsuarios.add(usuario);

	    // Escreve a lista de usuários atualizada no arquivo usuarios.json
	    try (FileWriter writer = new FileWriter(arquivo_usuarios)) {
	        gson.toJson(listaUsuarios, writer);

	        // Ler e imprimir o conteúdo do arquivo após adicionar o usuário para depuração
	        try (FileReader reader = new FileReader(arquivo_usuarios)) {
	            Type listType = new TypeToken<ArrayList<Usuario>>() {}.getType();
	            List<Usuario> usuariosLidos = gson.fromJson(reader, listType);

	            /*
	            if (usuariosLidos == null) {
	                System.out.println("Nenhum usuário encontrado no arquivo.");
	            } else {
	                System.out.println("Usuários carregados na função cadastraUsuario: " + usuariosLidos);
	            }
	            */
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        Mensagem mensagem = new Mensagem("100", "Successful account creation", usuario.user);
	        return mensagem.Padroniza();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return new Mensagem("104", "Unknown error", null).Padroniza();
	    }
	}

	static public String logout(Requisicao requisicao)
	{
		if(requisicao.getToken() != null)
		{
			Mensagem mensagem = new Mensagem("010", "Sucessful logout");
            return mensagem.Padroniza();
		}
		else
		{
			Mensagem mensagem = new Mensagem("011", "Fields missing");
            return mensagem.Padroniza();
		}
	}

	public String trataRequisicao(String string_json)
	{
		
		String jsonString = string_json;
		
		
		//pega a requisição
        Gson gson = new Gson();
        Requisicao requisicao = gson.fromJson(jsonString, Requisicao.class);
        
        //pega o usuário que enviou
        //Usuario user = gson.fromJson(mandante, Usuario.class);
        
        //descobre qual ação deve ser tomada
        String op = requisicao.getOp();
        
        //mensagem caso algo ocorra mal
        Mensagem mensagem = new Mensagem("-001", "Errorr", null);
        
        //criação do usuario para bom funcionamento das funções
        Cadastro usuario = new Cadastro(requisicao.getName(), requisicao.getUser(), requisicao.getPassword());
        
        System.out.println(string_json + "------" + op);
        
        // Atualiza a lista de usuários antes de verificar permissões
        atualizaLista();
        
        //confere se o usuario é admin ou não
		boolean isAdmin = listaUsuarios.stream().anyMatch(user -> user.user.equals(requisicao.getToken()) && user.adminstrador);
        
        if(op.equals("1"))			//Create
        {
        	return cadastraUsuario(usuario.Padroniza());
        }
        else if(op.equals("2"))		//Read
        {
        	//confere se o token está correto
        	if(requisicao.getToken().length() != 7 || requisicao.getToken() == null)
        	{
        		return new Mensagem("112", "Invalid or Empty Token", null).Padroniza(); 
        		//return saida.Padroniza();
        	}
        	//confere se o token está completp
        	/*if(requisicao.getUser() == null)
        	{
        		return new Mensagem("116", "Fields Missing", null).Padroniza();	//NÃO EXISTE NA DOCUMENTAÇÃO
        	}
        	else*/
        	return consultaUsuario(requisicao.Padroniza());
        }
        else if(op.equals("3"))		//Update
        {
        	//confere se o token está correto
        	if(requisicao.getToken().length() != 7 || requisicao.getToken() == null)
        	{
        		return new Mensagem("121", "Invalid or Empty Token", null).Padroniza(); 
        		//return saida.Padroniza();
        	}
        	//confere se o token está completp
        	if(requisicao.getUser() == null)
        	{
        		return new Mensagem("126", "Fields Missing", null).Padroniza();	//NÃO EXISTE NA DOCUMENTAÇÃO
        	}
        	else
        		return atualizaUsuario(requisicao.Padroniza());
        }
        else if(op.equals("4"))		//Delete
        {
        	
        	//confere se o token está correto
        	if(requisicao.getToken().length() != 7 || requisicao.getToken() == null)
        	{
        		return new Mensagem("132", "Invalid Token", null).Padroniza(); 
        		//return saida.Padroniza();
        	}
        	//confere se o token está completp
        	if(requisicao.getUser() == null)
        	{
        		return new Mensagem("131", "Fields Missing", null).Padroniza();
        	}
        	else
        		return excluiUsuario(requisicao.Padroniza());
        }
        else if(op.equals("5"))		//login
        {
        	return confereUsuario(usuario.Padroniza());
        }
        else if(op.equals("6"))		//logout
        {
        	return logout(requisicao);
        }
        //CATEGORIAS DE AVISOS		obs:conferir se o usuário é adminstrador aqui
        else if(op.equals("7"))		//create
        {
        	if (isAdmin)
        		return cadastraCategoriaAviso(requisicao.Padroniza());
        	else
        		return new Mensagem("203", "Unknown Error", null).Padroniza();
        }
        else if(op.equals("8"))		//read
        {
        	if (isAdmin)
            	return lerCategoriasAviso(requisicao.Padroniza());
        	else
        		return new Mensagem("213", "Unknown Error", null).Padroniza();

        }
        else if(op.equals("9"))		//update
        {
        	if (isAdmin)
        		return atualizaCategoria(requisicao.Padroniza());
        	else
        		return new Mensagem("224", "Unknown Error", null).Padroniza();
        	
        }
        else if(op.equals("10"))	//delete
        {
        	if (isAdmin)
        		return excluiCategoria(requisicao.Padroniza());
        	else
        		return new Mensagem("235", "Unknown Error", null).Padroniza();
        }
        

        return mensagem.Padroniza();	
	}

	
	//AVISOS
	
	//create
	public String cadastraCategoriaAviso(String string_json) {
	    String arquivo_categorias = "categorias.json";
	    
	    // Converte a string JSON para um objeto Requisicao
	    Requisicao reqCat = gson.fromJson(string_json, Requisicao.class);
	    
	    // Valida o token (supondo que o token deva ter 7 caracteres)
	    if (reqCat.getToken() == null || reqCat.getToken().length() != 7) {
	        return new Mensagem("207", "Invalid token", null).Padroniza();
	    }
	    
	    // Obtém o vetor de categorias enviado na requisição
	    ArrayList<CategoriaDeAvisos> novasCategorias = reqCat.getCategories();
	    if (novasCategorias == null || novasCategorias.isEmpty()) {
	        return new Mensagem("208", "Nenhuma categoria informada", null).Padroniza();
	    }
	    
	    // Valida os campos obrigatórios de cada categoria (neste exemplo, somente 'name' é obrigatório)
	    for (CategoriaDeAvisos cat : novasCategorias) {
	        if (cat.name == null || cat.name.trim().isEmpty()) {
	            return new Mensagem("201", "Missing fields in one or more categories", null).Padroniza();
	        }
	    }
	    
	    // Lê o arquivo "categorias.json" para obter a lista de categorias já cadastradas
	    ArrayList<CategoriaDeAvisos> listaCategorias;
	    try (FileReader reader = new FileReader(arquivo_categorias)) {
	        java.lang.reflect.Type listType = new com.google.gson.reflect.TypeToken<ArrayList<CategoriaDeAvisos>>(){}.getType();
	        listaCategorias = gson.fromJson(reader, listType);
	        if (listaCategorias == null) {
	            listaCategorias = new ArrayList<>();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Caso o arquivo não exista ou ocorra erro na leitura, inicia uma nova lista
	        listaCategorias = new ArrayList<>();
	    }
	    
	    // Determina o próximo ID sequencial com base nos IDs existentes
	    int novoId = 1;
	    if (!listaCategorias.isEmpty()) {
	        int maiorId = listaCategorias.stream()
	            .mapToInt(cat -> {
	                try {
	                    return Integer.parseInt(cat.id);
	                } catch (NumberFormatException ex) {
	                    return 0;
	                }
	            })
	            .max()
	            .orElse(0);
	        novoId = maiorId + 1;
	    }
	    
	    // Para cada nova categoria, atribui um novo ID e adiciona à lista
	    for (CategoriaDeAvisos cat : novasCategorias) {
	        cat.id = String.valueOf(novoId);
	        novoId++;
	        listaCategorias.add(cat);
	    }
	    
	    // Reescreve o arquivo "categorias.json" com a lista atualizada
	    try (FileWriter writer = new FileWriter(arquivo_categorias)) {
	        gson.toJson(listaCategorias, writer);
	        writer.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return new Mensagem("203", "Unknown Error", null).Padroniza();
	    }
	    
	    return new Mensagem("200", "Successful category creation", "Categorias adicionadas: " + novasCategorias.size()).Padroniza();
	}


	
	public String excluiCategoria(String string_json) {
	    String arquivo_categorias = "categorias.json";
	    
	    // Converte a string para o objeto Requisicao
	    Requisicao reqCat = gson.fromJson(string_json, Requisicao.class);
	    
	    // Valida o token (supondo que o token deva ter 7 caracteres)
	    if (reqCat.getToken() == null || reqCat.getToken().length() != 7) {
	        return new Mensagem("232", "Invalid token", null).Padroniza();
	    }
	    
	    // Atualiza a lista de categorias lendo o arquivo Categorias.json
	    ArrayList<CategoriaDeAvisos> listaCategorias;
	    try (FileReader reader = new FileReader(arquivo_categorias)) {
	        java.lang.reflect.Type listType = new com.google.gson.reflect.TypeToken<ArrayList<CategoriaDeAvisos>>(){}.getType();
	        listaCategorias = gson.fromJson(reader, listType);
	        if (listaCategorias == null) {
	            listaCategorias = new ArrayList<>();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return new Mensagem("235", "Unknown Error", null).Padroniza();
	    }
	    
	    // Obtém a lista de IDs para exclusão a partir da requisição
	    ArrayList<String> idsParaExcluir = reqCat.getIDs();
	    if (idsParaExcluir == null || idsParaExcluir.isEmpty()) {
	        return new Mensagem("233", "No IDs provided", null).Padroniza();
	    }
	    
	    // Verifica se todas as categorias informadas existem
	    for (String id : idsParaExcluir) {
	        boolean existe = false;
	        for (CategoriaDeAvisos cat : listaCategorias) {
	            if (cat.id != null && cat.id.equals(id)) {
	                existe = true;
	                break;
	            }
	        }
	        if (!existe) {
	            return new Mensagem("233", "Invalid information inserted: category id " + id + " does not exist", null).Padroniza();
	        }
	    }
	    
	    // Remove as categorias correspondentes aos IDs fornecidos e conta quantas foram removidas
	    int removidas = 0;
	    for (String id : idsParaExcluir) {
	        // O método removeIf retorna true se pelo menos um elemento for removido
	        if (listaCategorias.removeIf(cat -> cat.id.equals(id))) {
	            removidas++;
	        }
	    }
	    
	    // Reescreve o arquivo Categorias.json com a lista atualizada
	    try (FileWriter writer = new FileWriter(arquivo_categorias)) {
	        gson.toJson(listaCategorias, writer);
	        writer.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return new Mensagem("235", "Unknown Error", null).Padroniza();
	    }
	    
	    return new Mensagem("230", "Successful category deletion", "Categorias removidas: " + removidas).Padroniza();
	}



	public String lerCategoriasAviso(String string_json) {
	    String arquivo_categorias = "categorias.json";
	    
	    // Converte a string para o objeto Requisicao
	    Requisicao reqCat = gson.fromJson(string_json, Requisicao.class);
	    
	    // Valida o token (supondo que o token deva ter 7 caracteres)
	    if (reqCat.getToken() == null || reqCat.getToken().length() != 7) {
	        return new Mensagem("212", "Invalid token", null).Padroniza();
	    }
	    
	    // Atualiza a lista de categorias lendo o arquivo Categorias.json
	    ArrayList<CategoriaDeAvisos> listaCategorias;
	    try (FileReader reader = new FileReader(arquivo_categorias)) {
	        Type listType = new TypeToken<ArrayList<CategoriaDeAvisos>>(){}.getType();
	        listaCategorias = gson.fromJson(reader, listType);
	        if (listaCategorias == null) {
	            listaCategorias = new ArrayList<>();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return new Mensagem("213", "Unknown Error", null).Padroniza();
	    }
	    
	    // Converte a lista de categorias para uma string JSON
	    //String jsonCategorias = gson.toJson(listaCategorias);
	    
	    return new Mensagem("210", "Successful category read", listaCategorias, false).Padroniza();
	}

	public String atualizaCategoria(String string_json) {
	    String arquivo_categorias = "categorias.json";
	    
	    // Converte a string para o objeto Requisicao
	    Requisicao reqCat = gson.fromJson(string_json, Requisicao.class);
	    
	    // Valida o token (supondo que o token deva ter 7 caracteres)
	    if (reqCat.getToken() == null || reqCat.getToken().length() != 7) {
	        return new Mensagem("222", "Invalid token", null).Padroniza();
	    }
	    
	    // Obtém o vetor de categorias a serem atualizadas
	    ArrayList<CategoriaDeAvisos> categoriasAtualizadas = reqCat.getCategories();
	    if (categoriasAtualizadas == null || categoriasAtualizadas.isEmpty()) {
	        return new Mensagem("221", "No categories provided", null).Padroniza();
	    }
	    
	    // Valida os campos obrigatórios de cada categoria (aqui, 'id' e 'name' são obrigatórios)
	    for (CategoriaDeAvisos catAtualizada : categoriasAtualizadas) {
	        if (catAtualizada.id == null || catAtualizada.name == null || catAtualizada.name.trim().isEmpty()) {
	            return new Mensagem("221", "Missing fields in one or more categories", null).Padroniza();
	        }
	    }
	    
	    // Lê o arquivo "categorias.json" para obter a lista de categorias já cadastradas
	    ArrayList<CategoriaDeAvisos> listaCategorias;
	    try (FileReader reader = new FileReader(arquivo_categorias)) {
	        java.lang.reflect.Type listType = new com.google.gson.reflect.TypeToken<ArrayList<CategoriaDeAvisos>>(){}.getType();
	        listaCategorias = gson.fromJson(reader, listType);
	        if (listaCategorias == null) {
	            listaCategorias = new ArrayList<>();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return new Mensagem("224", "Unknown Error", null).Padroniza();
	    }
	    
	    // Atualiza cada categoria presente na requisição na lista de categorias
	    int atualizadas = 0;
	    for (CategoriaDeAvisos catAtualizada : categoriasAtualizadas) {
	        boolean encontrada = false;
	        for (CategoriaDeAvisos cat : listaCategorias) {
	            if (cat.id.equals(catAtualizada.id)) {
	                // Atualiza os valores da categoria
	                cat.name = catAtualizada.name;
	                cat.description = catAtualizada.description;
	                encontrada = true;
	                atualizadas++;
	                break;
	            }
	        }
	        // Se a categoria não for encontrada, pode-se optar por ignorar ou adicionar como nova.
	        // Aqui optamos por ignorar.
	    }
	    
	    // Reescreve o arquivo "categorias.json" com a lista atualizada
	    try (FileWriter writer = new FileWriter(arquivo_categorias)) {
	        gson.toJson(listaCategorias, writer);
	        writer.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return new Mensagem("224", "Unknown Error", null).Padroniza();
	    }
	    
	    return new Mensagem("220", "Successful category update", "Categorias atualizadas: " + atualizadas).Padroniza();
	}



	public String excluiUsuario(String usuarioASerDeletado_str) {
	    // Cria a requisição a partir do JSON recebido
		
	    Requisicao requisicao = gson.fromJson(usuarioASerDeletado_str, Requisicao.class);

	    // Atualiza a lista de usuários antes de verificar permissões
	    atualizaLista();

	    // Verifica se o usuário que fez a requisição (token) é administrador
	    boolean isAdmin = listaUsuarios.stream()
	        .anyMatch(user -> user.user.equals(requisicao.getToken()) && user.adminstrador);

	    // Determina qual usuário será deletado
	    String usuarioASerDeletado_user;
	    
	    if (!requisicao.getToken().equals(requisicao.getUser())) { 
	        // Caso o usuário esteja tentando deletar outra conta
	        if (!isAdmin) {
	            return new Mensagem("133", "Invalid Permission, User does not have permission to delete account", null).Padroniza();
	        }
	        usuarioASerDeletado_user = requisicao.getUser();
	    } else {
	        // Caso o usuário esteja tentando deletar a própria conta
	        usuarioASerDeletado_user = requisicao.getToken();
	    }

	    // Remove o usuário da lista
	    boolean usuarioRemovido = listaUsuarios.removeIf(item -> item.user.equals(usuarioASerDeletado_user));

	    if (!usuarioRemovido) {
	        // Se o usuário não foi encontrado, retorna erro
	        if (isAdmin)
	            return new Mensagem("134", "User not Found (Admin Only)", null).Padroniza();
	        else
	            return new Mensagem("135", "Unknown error", null).Padroniza();
	    }

	    // Atualiza o arquivo usuarios.json após a remoção
	    try (FileWriter writer = new FileWriter(arquivo_usuarios)) {
	        gson.toJson(listaUsuarios, writer);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return new Mensagem("-001", "Unknown error", null).Padroniza();
	    }

	    return new Mensagem("130", "Account successfully deleted", null).Padroniza();
	}


	public String consultaUsuario(String string_json) {
	    // Cria a requisição a partir do JSON recebido
	    Requisicao usuario = gson.fromJson(string_json, Requisicao.class);
	    
	    //CONFERE SE USUÁRIO QUER SE ENCONTRAR
	    if (usuario.getUser() == null || usuario.getUser().equals(""))
	    {
	    	usuario.setUser(usuario.getToken());
	    }

	    // Atualiza a lista de usuários antes da consulta
	    atualizaLista();

	    // Verifica se o usuário que fez a requisição (token) é administrador
	    boolean isAdmin = listaUsuarios.stream()
	        .anyMatch(user -> user.user.equals(usuario.getToken()) && user.adminstrador);

	    // Procura o usuário na lista
	    for (Usuario item : listaUsuarios) {
	        if (item.user.equals(usuario.getUser())) {
	            // Se o usuário solicitante não for admin, só pode ver seus próprios dados
	            if (!isAdmin && !usuario.getToken().equals(usuario.getUser())) {
	                return new Mensagem("111", "Invalid Permission, user does not have permission to visualize others users data").Padroniza();
	            }
	            if(isAdmin)
	            	return new Mensagem("111", item.user, item.password, item.name, "Returns all information of the account").Padroniza();
	            else
	            	return new Mensagem("110", item.user, item.password, item.name, "Returns all information of the account").Padroniza();

	        }
	    }

	    // Se não encontrar o usuário, retorna erro
	    return isAdmin
	        ? new Mensagem("114", "User not found", null).Padroniza()
	        : new Mensagem("115", "Unknown Error", null).Padroniza();
	}


	public String atualizaUsuario(String string_json) {
	    // Cria a requisição a partir do JSON recebido
	    Requisicao requisicao = gson.fromJson(string_json, Requisicao.class);

	    // Atualiza a lista de usuários antes da modificação
	    atualizaLista();

	    // Confere se os campos estão no formato correto
	    if (requisicao.getName().length() > 40 || requisicao.getPassword().length() != 4) {
	        return new Mensagem("102", "Invalid information inserted: user or password", null).Padroniza();
	    }

	    if (requisicao.getToken() == null || requisicao.getToken().length() != 7) {
	        return new Mensagem("123", "Invalid or Empty Token", null).Padroniza();
	    }

	    // Verifica se o usuário que fez a requisição (token) é administrador
	    boolean isAdmin = listaUsuarios.stream()
	        .anyMatch(user -> user.user.equals(requisicao.getToken()) && user.adminstrador);

	    // Procura o usuário na lista
	    for (Usuario item : listaUsuarios) {
	        if (item.user.equals(requisicao.getUser())) {
	            // Apenas admins podem modificar outros usuários
	            if (!isAdmin && !requisicao.getToken().equals(requisicao.getUser())) {
	                return new Mensagem("122", "User does not have permission to modify others users data").Padroniza();
	            }

	            // Atualiza os dados do usuário
	            item.name = requisicao.getName();
	            item.password = requisicao.getPassword();

	            // Reescreve o arquivo atualizado
	            try (FileWriter writer = new FileWriter(arquivo_usuarios)) {
	                gson.toJson(listaUsuarios, writer);
	                return new Mensagem("120", "Account successfully updated").Padroniza();
	            } catch (IOException e) {
	                e.printStackTrace();
	                return new Mensagem("124", "Unknown error").Padroniza();
	            }
	        }
	    }

	    // Se não encontrar o usuário, retorna erro
	    return isAdmin
	        ? new Mensagem("123", "No user or Token found", null).Padroniza()
	        : new Mensagem("124", "Unknown error").Padroniza();
	}


}