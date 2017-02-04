/*
 * Charles Rouillard
 * Charles Poure
 * Tom Etienne 
 * Florient Ratel
 * Clement Toulgoat 
 * Youssef Zerhouni
 */
package client;
 
import java.io.*;
import java.net.*;
 
public class Client
{
    public static Socket socket = null;
    public static Thread t1;
    public static String adresse ;
 
    public static void main(String[] args)
    {
    	
        try
        {
        	// le main prend un parametre qui est l'adresse ip sinon c'est localhost par defaut
        	if(args.length == 1)
        		adresse = args[0] ;
        	else
        		adresse = "localHost" ;
   
        	// creation du socket
            System.out.println("Demande de connexion");
            socket = new Socket( adresse, 2009);
            System.out.println("Connexion établie avec le serveur, authentification :");
            
            // Thread qui s'occupe de l'authentification
            t1 = new Thread(new Connexion(socket));
            t1.start();
        }
        catch (UnknownHostException e)
        {
            System.err.println("Impossible de se connecter à l'adresse " + socket.getLocalAddress());
        }
        catch (IOException e)
        {
            System.err.println("Aucun serveur à l'écoute du port " + socket.getLocalPort());
        }
 
    }
 
}