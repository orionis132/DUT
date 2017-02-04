/*
 * Charles Rouillard
 * Charles Poure
 * Tom Etienne 
 * Florient Ratel
 * Clement Toulgoat 
 * Youssef Zerhouni
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connexion implements Runnable
{
 
    private Socket socket = null;
    public static Thread t2;
    public static String login = null, pass = null, message1 = null, message2 = null, message3 = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Scanner sc = null;
    private boolean connect = false;
 
    public Connexion(Socket s)
    {
        this.socket = s;
    }
 
    public void run()
    {
        try
        {
        	// instanciantion des flux d'entrée et de sortie
        	
            this.out = new PrintWriter(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.sc = new Scanner(System.in);
 
            while (!connect)
            {
            	// ecriture du message du serveur
                System.out.println(in.readLine());
                
                // lecture du login ecris par le client
                this.login = sc.nextLine();
                
                // envoi du client au serveur 
                this.out.println(login);
                this.out.flush();
                
                // ecriture du message du serveur 
                System.out.println(in.readLine());
                
                // lecture du mot de passe ecris pas le client
                this.pass = sc.nextLine();
                
                // envoi du mot de passe au serveur
                this.out.println(pass);
                this.out.flush();
 
                
                // verification si le client a reussi l'authentification
                if (in.readLine().equals("connecte"))
                {
                    System.out.println("Je suis connecte ");
                    this.connect = true;
                }
                else
                {
                    System.err.println("Vos informations sont incorrectes ");
                }
           }
 
            // si connexion reussi creation du thread qui gere l'echange de flux entre le serveur et client
            t2 = new Thread(new FluxServeurClient(socket));
            t2.start();
 
        }
        catch (IOException e)
        {
            System.err.println("Le serveur ne répond plus ");
        }
    }
 
}