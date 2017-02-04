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
import java.util.Scanner;
 
public class FluxServeurClient implements Runnable
{
    private Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Scanner sc;
    private Thread t3, t4;
 
    public FluxServeurClient(Socket s)
    {
        this.socket = s;
    }
 
    public void run()
    {
        try
        {
        	// instanciation des flux entree sortie 
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //sc = new Scanner(System.in);
            
            // creation du thread qui permet d'ecrire au serveur 
            Thread t4 = new Thread(new Emission(out));
            t4.start();
            // creation du thread qui permet de recevoir des message du serveur
            Thread t3 = new Thread(new Reception(in));
            t3.start();
 
        }
        catch (IOException e)
        {
            System.err.println("Le serveur distant s'est déconnecté !");
        }
    }
 
}