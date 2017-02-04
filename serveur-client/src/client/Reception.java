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
 
public class Reception implements Runnable
{
    private BufferedReader in;
    private String message = null;
 
    public Reception(BufferedReader in)
    {
        this.in = in;
    }
 
    public void run()
    {
    	// tant que le socket existe on lis les messages recus 
        while (in !=  null )
        {
            try
            {
                message = in.readLine();
                System.out.println(message);
            }
            catch (IOException e)
            {
            	System.out.println("connexion impossible avec le serveur distant");
            }
        }
    }
 
}