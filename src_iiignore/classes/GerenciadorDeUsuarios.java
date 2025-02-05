package classes;

import java.util.LinkedList;
import com.google.gson.Gson;

public class GerenciadorDeUsuarios {

	static LinkedList<Usuario> listaUsuarios = new LinkedList<>();

	public GerenciadorDeUsuarios()
	{
		//Adicionando usuários na lista ao gerar instancia da classe
	    listaUsuarios.add(new Usuario("Admin", "admin", "1234", true));
	    listaUsuarios.add(new Usuario("Freddy", "freddy", "1234"));
	    listaUsuarios.add(new Usuario("Chica", "chica", "1234"));
	}
	
	static public String confereUsuario(String string_json)
	{
		String jsonString = string_json;
		
        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(jsonString, Usuario.class);
        
        if (usuario.user == null || usuario.password == null)
        {
        	Mensagem mensagem = new Mensagem("002", "Fields missing", null);
        	return mensagem.Padroniza();
        }
        
        for (Usuario item : listaUsuarios) {

        	//confere o nome
            if (item.user.equals(usuario.user)) {
            	//confere a senha
                if (item.password.equals(usuario.password))
                {
                	//confere se é adminstrador ou usuario
                	if(usuario.adminstrador)
                	{
                		Mensagem mensagem = new Mensagem("001", "Sucessful login", usuario.user);
                        return mensagem.Padroniza();
                	}
                	else
                	{
                		Mensagem mensagem = new Mensagem("000", "Sucessful login", usuario.user);
                        return mensagem.Padroniza();
                	}
       
                }
            }
        }
        Mensagem mensagem = new Mensagem("003", "Login failed", null);
        return mensagem.Padroniza();
	}
	
	static public String cadastraUsuario(String string_json)
	{
		String jsonString = string_json;
		
        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(jsonString, Usuario.class);
        
        //confere se os campos estão preenchidos
        if (usuario.name == null || usuario.password == null || usuario.user == null)
        {
        	System.out.println("nome: " + usuario.name + "senha: " + usuario.password + "usuario: " + usuario.user);
        	Mensagem mensagem = new Mensagem("101", "Fields missing", null);
        	return mensagem.Padroniza();
        }
        
        //confere se os campos se enquadram no padrão
        if (usuario.name.length() > 40 || usuario.user.length() != 7 || usuario.password.length() != 4)
        {
        	Mensagem mensagem = new Mensagem("102", "Invalid information inserted: user or password", null);
        	return mensagem.Padroniza();
        }
        
        //confere se ja existe usuario com o mesmo nome
        for (Usuario item : listaUsuarios) {
            if (item.name.equals(usuario.name)) 
            {
            	Mensagem mensagem = new Mensagem("103", "Already exists an account with the username	", null);
            	return mensagem.Padroniza();
            }
        }
        
        listaUsuarios.add(usuario);
        

		Mensagem mensagem = new Mensagem("100", "Sucessful account creation", usuario.user);
        return mensagem.Padroniza();
    	
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
		
        Gson gson = new Gson();
        Requisicao requisicao = gson.fromJson(jsonString, Requisicao.class);
        
        //descobre qual ação deve ser tomada
        String op = requisicao.getOp();
        
        //mensagem caso algo ocorra mal
        Mensagem mensagem = new Mensagem("-001", "Error", null);
        
        //criação do usuario para bom funcionamento das funções
        Cadastro usuario = new Cadastro(requisicao.getName(), requisicao.getUser(), requisicao.getPassword());
        
        System.out.println(string_json + "------" + op);
        
        if(op.equals("1"))
        {
        	return cadastraUsuario(usuario.Padroniza());
        }
        else if(op.equals("5"))
        {
        	return confereUsuario(usuario.Padroniza());
        }
        else if(op.equals("6"))
        {
        	return logout(requisicao);
        }
        
        
        return mensagem.Padroniza();	
	}
			
}