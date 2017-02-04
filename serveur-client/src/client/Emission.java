/*
 * Charles Rouillard
 * Charles Poure
 * Tom Etienne 
 * Florient Ratel
 * Clement Toulgoat 
 * Youssef Zerhouni
 */
package client;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
 
public class Emission implements Runnable
{
    private PrintWriter out;
    private String login = null, message = null;
    private Scanner sc = null;
 
    public Emission(PrintWriter out)
    {
        this.out = out;
    }
 
    public void run()
    {
    	// lecture des messages 
        sc = new Scanner(System.in);
        
        // tant que le socket existe on envoi les message ecris par le client 
        while (out != null )
        {
            message = sc.nextLine();
            out.println(message);
            out.flush();
        }
    }
}