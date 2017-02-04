/*
 * Charles Rouillard
 * Charles Poure
 * Tom Etienne 
 * Florient Ratel
 * Clement Toulgoat 
 * Youssef Zerhouni
 */
package serveur;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;

public class Authentification implements Runnable {

	private Socket socket;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String login = "", pass =  "";
	public boolean authentifier = false;
	public Thread t2;
	private HashMap<String,Socket> clients ;
	
	public Authentification(Socket s, HashMap<String,Socket> clients){
		 socket = s;
		 this.clients = clients ;
		}
	public void run() {
	
		try {
			// instanciation des des entrées sorties 
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
			// tant que le joueur n'est pas connecté on boucle 
			while(!authentifier){
				
				// ecriture d'un message au client et lecture du login 
				out.println("Entrez votre login :");
				out.flush();
				login = in.readLine();
				
				// ecriture d'un message au client et lecture du mot de passe
				out.println("Entrez votre mot de passe  :");
				out.flush();
				pass = in.readLine();
				
				// appel de la methode qui verifie la validité des id 
				if(isValid(login, pass)){
					clients.put(login, socket) ;
					out.println("connecte");
					System.out.println(login +" vient de se connecter ");
					out.flush();
					authentifier = true;	
				}
				else {out.println("erreur"); out.flush();}
	
				// si on atteint le nombre de joueurs voulu on cree la thread qui gere le jeu 
				if(clients.size() == 2){
					if(Serveur.jeu.equals("morpion")){
						t2 = new Thread(new DebutJeu(clients)) ;
						t2.start();
					}
					else{
						t2 = new Thread(new DebutJeuDia(clients)) ;
						t2.start();
					}
				}
		 }
			
		} catch (IOException e) {
			
			System.err.println(login+" ne répond pas !");}

	}
	
	private static boolean isValid(String login, String pass) {
		
		boolean valid = false;
		// verification des id dans un fichier
		try {
			String nomFichier = System.getProperty("user.dir");
			FileReader fr = new FileReader( nomFichier + "/logins/logins.txt" );
			Scanner sc = new Scanner(fr);
			
			while( sc.hasNext() && !valid )
			{
				String[] ligne = sc.nextLine().split(":");
				if( ligne[0].equals( login ) && ligne[1].equals(pass) )
					valid = true;
			}
		}
		catch( Exception e ){System.out.println("Erreur d'acces au fichier d'authendification.");} ;
		return valid;
		
	}

}