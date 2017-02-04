/*
 * Charles Rouillard
 * Charles Poure
 * Tom Etienne 
 * Florient Ratel
 * Clement Toulgoat 
 * Youssef Zerhouni
 */
package serveur;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
 
public class TimeOut implements Runnable
{
    private Boolean b;
    private String message = null;
    private Socket s ;
 
    public TimeOut(Boolean b , Socket s )
    {
        this.b = b ;
        this.s = s ; 
    }
 
    public void run()
    {
    	// on att 10 sec et on change le booleen et on expulse le joueur
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	b = true ;
    	try {
			s.shutdownInput();
			s.shutdownOutput();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("temp ecoule expulsion du joueur");
		}
    }
 
}