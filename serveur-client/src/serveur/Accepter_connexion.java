/*
 * Charles Rouillard
 * Charles Poure
 * Tom Etienne 
 * Florient Ratel
 * Clement Toulgoat 
 * Youssef Zerhouni
 */
package serveur;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Accepter_connexion implements Runnable {

	private ServerSocket socketserver = null;
	private Socket socket = null;
	private HashMap<String,Socket> clients;

	public Thread t1, t2;

	public Accepter_connexion(ServerSocket ss) {
		socketserver = ss;
		
		// insantations d'une hashmap avec le nom du joueurs et le socket pour faire le passe des sockets 
		clients = new HashMap<String,Socket>() ;
	}

	public void run() {

		try {
			// tant que le nombre de client et diferent de 2 on accepte les client 
			// ici on a mis parceque les jeux qu'on gere pour l'instant on 2 joueurs 
			while (clients.size() != 2) {
				
				// le serveur accepte la connexion
				socket = socketserver.accept();

				// creation d'une Thread qui s'ocupe de l'Authentification
				t1 = new Thread(new Authentification(socket,clients));
				t1.start();
			}

			
		} catch (IOException e) {

			System.err.println("Erreur serveur");
		}

	}
}
