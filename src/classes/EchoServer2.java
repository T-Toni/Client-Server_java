package classes;

import com.google.gson.Gson;

import java.net.*;
import java.io.*; 

public class EchoServer2 extends Thread
{
    protected Socket clientSocket;
    GerenciadorDeUsuarios g = new GerenciadorDeUsuarios();

    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = null;

        int porta = -1;

        ServidorInicia ig_inicio = new ServidorInicia();

        //tela de inicio para configurar a porta
        ig_inicio.janela.setVisible(true);

        while(ig_inicio.getPorta() == -1)
        {
            System.out.println("aguardando porta");

        }

        ig_inicio.janela.setVisible(false);

        try {
             serverSocket = new ServerSocket(ig_inicio.getPorta());
             System.out.println ("Connection Socket Created");
             try {
                  while (true)
                     {
                      System.out.println ("Waiting for Connection");
                      new EchoServer2 (serverSocket.accept());
                     }
                 }
             catch (IOException e)
                 {
                  System.err.println("Accept failed.");
                  System.exit(1);
                 }
            }
        catch (IOException e)
            {
             System.err.println("Could not listen on port: 10008.");
             System.exit(1);
            }
        finally
            {
             try {
                  serverSocket.close();
                 }
             catch (IOException e)
                 {
                  System.err.println("Could not close port: 10008.");
                  System.exit(1);
                 }
            }
        }

    private EchoServer2 (Socket clientSoc)
    {
        clientSocket = clientSoc;
        start();
        }

    public void run()
    {
        System.out.println ("New Communication Thread Started");


        try {
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true); //define a saida (socket do cliente)
             BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream())); //define a entrada das requisições feitas pelo cliente

             String inputLine;

             Servidor ig = new Servidor();  //Servidos = ig

             ig.janela.setVisible(true);    //torna a janela visivel

             //atribui o valor de entrada a inputline
             while ((inputLine = in.readLine()) != null && ig.janela.isVisible())
             {

                 //GerenciadorDeUsuarios g = new GerenciadorDeUsuarios();   //mudado para atributo da classe

                 String mensagem = g.trataRequisicao(inputLine);

                 ig.imprime(mensagem);

                 System.out.println ("Server: " + mensagem);
                 out.println(mensagem);
                 /*

                 if (inputLine.equals("Bye."))
                 break;
                 */

             }

             out.close();
             in.close();
             clientSocket.close();
            }
        catch (IOException e)
            {
             System.err.println("Problem with Communication Server");
             System.exit(1);
            }
        }
} 