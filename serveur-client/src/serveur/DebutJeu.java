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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import jeux.Morpion;

public class DebutJeu implements Runnable {
	private HashMap<String,Socket> clients ;
	private Morpion jeu ;
	
	public DebutJeu(HashMap<String,Socket> clients){
		this.clients = clients ;
	}
	@Override
	public void run() {
		
		// creation du tableau de chaines 
		String[] Joueurs ;
		Joueurs = new String[2] ;
		int i = 0 ;
		
		// remplissage du tableau de chaines a partir de la hashMap
		for(String s : clients.keySet()){
			Joueurs[i] = s ;
			i++ ;
		}
		
		String arret = null ;
		try {
			
			// instanciation des flux de sortie du j1 et j2
			PrintWriter msgJ1 = new PrintWriter(clients.get(Joueurs[0]).getOutputStream());
			PrintWriter msgJ2 = new PrintWriter(clients.get(Joueurs[1]).getOutputStream());
			
			// instanciation des flux d'entrée du j1 et j2
			BufferedReader lectureJ1 = new BufferedReader(new InputStreamReader(clients.get(Joueurs[0]).getInputStream()));
			BufferedReader lectureJ2 = new BufferedReader(new InputStreamReader(clients.get(Joueurs[1]).getInputStream()));		
			

			
			// si le nombre de jeu est 2
			if(clients.size() == 2){
				
				// creation du jeu
				jeu = new Morpion(Joueurs) ;
				
				while(true){
					
					// si le joueur est le premier on lui demande de jouer
					if(jeu.getJoueur() == 1){
						msgJ1.println("A vous de jouer :") ;
						msgJ1.flush();
						
						// tant que l'entrée est invalide on redemande 
						while(!jeu.jouer(lectureJ1.readLine()) ){
							msgJ1.println("veuillez rejoué :") ;
							msgJ1.flush();
						}
						// affichage du jeu pour les 2 joueurs 
						msgJ1.println(jeu.afficher()) ;
						msgJ1.flush();
						msgJ2.println(jeu.afficher()) ;
						msgJ2.flush();
					}
					else{
						// joueur 2
						msgJ2.println("A vous de jouer :") ;
						msgJ2.flush();
						
						// tant que l'entrée est invalide on redemande 
						while(!jeu.jouer(lectureJ2.readLine()) ){
							msgJ2.println("veuillez rejoué :") ;
							msgJ2.flush();
						}
						// affichage du jeu pour les 2 joueurs 
						msgJ1.println(jeu.afficher()) ;
						msgJ1.flush();
						msgJ2.println(jeu.afficher()) ;
						msgJ2.flush();
					}
					// le cas du math null 
					if(jeu.estRempli() && !jeu.aGagner()){
						
						// on ecris le message pour les 2 joueurs avec le score 
						
						msgJ1.println("match null !! \n") ;
						msgJ1.println("Score de "+ jeu.getJoueur1() +" : "+ jeu.getScoreJ1() + "\n" +
								  "Score de "+ jeu.getJoueur2() +" : "+ jeu.getScoreJ2() + "\n") ;
						
						msgJ2.println("match null !! \n") ;
					
						msgJ1.flush();
						msgJ2.println("Score de "+ jeu.getJoueur1() +" : "+ jeu.getScoreJ1() + "\n" +
							  "Score de "+ jeu.getJoueur2() +" : "+ jeu.getScoreJ2() + "\n") ;
				
						msgJ2.flush();
						
						// demande d'arret jeu 
						msgJ1.println("Voullez vous continuez ? (non pour arreter)") ;
						msgJ1.flush();
						
						// lecture du choix 
						if(lectureJ1.readLine().equals("non")){
							
							// si il refuse de continuer on arrete 
							msgJ1.println("Partie terminé") ;
							msgJ1.flush();
							msgJ2.println("Votre adversaire est parti") ;
							msgJ2.flush();
							clients.remove(jeu.getJoueur1());
							clients.get(jeu.getJoueur1()).close() ;
							break ;
							
						}
						
						// demande d'arret jeu
						msgJ2.println("Voullez vous continuez ? (non pour arreter)") ;
						msgJ2.flush();
						if(lectureJ2.readLine().equals("non")){
							msgJ2.println("Partie terminé") ;
							msgJ2.flush();
							msgJ1.println("Votre adverssaire est parti") ;
							msgJ1.flush();
							clients.remove(jeu.getJoueur2());
							clients.get(jeu.getJoueur2()).close() ;
							break ;
						}
						// reinitialisation du jeu 
						jeu.initialiser() ;
					}
					// si le joueur en cour a gagné 
					if(jeu.aGagner()){
						// ajout de point 
						jeu.ajouterPoint() ;
						
						// ecriture du message au joueur aproprié
						if(jeu.getJoueur() == 1){
							msgJ1.println("Vous avez gagné !!!") ;
							msgJ1.flush();
						}
						else{
							msgJ2.println("Vous avez gagné !!!") ;
							msgJ2.flush();
						}
						// affichage des score 
						msgJ1.println("Score de "+ jeu.getJoueur1() +" : "+ jeu.getScoreJ1() + "\n" +
									  "Score de "+ jeu.getJoueur2() +" : "+ jeu.getScoreJ2() + "\n") ;
						
						msgJ1.flush();
						msgJ2.println("Score de "+ jeu.getJoueur1() +" : "+ jeu.getScoreJ1() + "\n" +
								  "Score de "+ jeu.getJoueur2() +" : "+ jeu.getScoreJ2() + "\n") ;
					
						msgJ2.flush();
						
						// demande si il veut continuer
						msgJ1.println("Voullez vous continuez ? (non pour arreter)") ;
						msgJ1.flush();
						
						// si il refuse on arrete 
						if(lectureJ1.readLine().equals("non")){
							msgJ1.println("Partie terminé") ;
							msgJ1.flush();
							
							msgJ2.println("Votre adversaire est parti") ;
							msgJ2.flush();
							
							clients.remove(jeu.getJoueur1());
							clients.get(jeu.getJoueur1()).close() ;
							break ;
							
						}
						// meme chose pour joueur 2
						msgJ2.println("Voullez vous continuez ? (non pour arreter)") ;
						msgJ2.flush();
						if(lectureJ2.readLine().equals("non")){
							msgJ2.println("Partie terminé") ;
							msgJ2.flush();
							
							msgJ1.println("Votre adverssaire est parti") ;
							msgJ1.flush();
							
							clients.remove(jeu.getJoueur2());
							clients.get(jeu.getJoueur2()).close() ;
							
							break ;
						}
						// reinitialisation du jeu 
						jeu.initialiser() ;
					}
					
					
					jeu.changerJoueur();
					// changement de joueur
					
				}			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}