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
import java.util.Scanner;

public class Serveur {
	public static ServerSocket ss = null;
	public static Thread t;
	public static String jeu ="" ;
 

 
	public static void main(String[] args) {
		
		try {
			// demande du jeu voulu 
			do{
				System.out.println("quel jeu voulez vous jouer ? (morpion ou diabalik)");
				Scanner sc = new Scanner(System.in) ;
				Serveur.jeu = sc.next() ;
			}while(!jeu.equals("morpion") && !jeu.equals("diabalik")) ;
			// creation du socker server
			ss = new ServerSocket(2009);
			System.out.println("Le serveur est à l'écoute du port "+ss.getLocalPort());
			
			
			// creation du thread qui accepte les connexion de clients
			t = new Thread(new Accepter_connexion(ss));
			t.start();
			
		} catch (IOException e) {
			System.err.println("Le port "+ss.getLocalPort()+" est déjà utilisé !");
		}
	
	}

	
}