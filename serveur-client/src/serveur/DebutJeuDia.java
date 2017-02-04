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
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import jeux.Metier;
import jeux.Tour;

public class DebutJeuDia implements Runnable {
	private HashMap<String,Socket> clients ;
	private Metier jeu ;
	
	public DebutJeuDia(HashMap<String,Socket> clients){
		this.clients = clients ;
		
	}
	public void run() {
		
		// creation d'un tableau de chaines 
		String action = "" ; 
		String[] Joueurs ;
		Joueurs = new String[2] ;
		int i = 0 ;
		
		// remplissage du tableau de chaines 
		for(String s : clients.keySet()){
			Joueurs[i] = s ;
			i++ ;
		}
		
		String arret = null ;
		try {
			// instanciation des points de sortie du j1 et j2
			PrintWriter msgJ1 = new PrintWriter(clients.get(Joueurs[0]).getOutputStream());
			PrintWriter msgJ2 = new PrintWriter(clients.get(Joueurs[1]).getOutputStream());
			
			// instanciation des points d'entré du j1 et j2
			BufferedReader lectureJ1 = new BufferedReader(new InputStreamReader(clients.get(Joueurs[0]).getInputStream()));
			BufferedReader lectureJ2 = new BufferedReader(new InputStreamReader(clients.get(Joueurs[1]).getInputStream()));	
			
			// affectation des entré sortie par defaut 
			PrintWriter out = msgJ1 ;
			BufferedReader in = lectureJ1 ;


			Tour tour;
			
			Scanner sc = new Scanner(System.in) ;
			
			// on demande le style de jeu pour le j1
			System.out.println("Voulez vous jouer en mode variante ? (oui/non/init) : " );


			// on lis le choix 
			String choix = sc.nextLine() ;
			
			// verification du choix
			while(!choix.matches("^oui$") && !choix.matches("^non$") && !choix.matches("^init$") )
			{
				System.out.println("Syntaxe invalide.\n");
				
				System.out.println("Voulez vous jouer en mode variante ? (oui/non/init) : " );
				
				choix = sc.nextLine() ;
			}
			// creation de la partie selon le choix du joueur 
			if(choix.equals("non"))
			{
				jeu = new Metier(0 , Joueurs );
			}
			if(choix.equals("init"))
				jeu = new Metier(3, Joueurs );
			else
			{
				jeu = new Metier(1 , Joueurs );
			}
			
			
			
			tour = new Tour(jeu.getJoueur());
			
			System.out.println(jeu.toString());
			
			// tant qu'aucun des joueurs n'a gagné
			while(!jeu.gagner())
			{	
				
				// si ta passser change de joueurs 
				if(!jeu.getJoueur().equals(tour.getJoueur())){
					tour = new Tour(jeu.getJoueur());
				}
				
				// si il na plus de deplacement changement automatique
				if(tour.getDeplacements()==0 && tour.getPasse() ==0)
				{
					jeu.changerJoueur();
					tour = new Tour(jeu.getJoueur());
					if(out == msgJ1){
						out = msgJ2 ;
						in = lectureJ2 ;
					}
					else{
						out = msgJ1 ;
						in = lectureJ1 ;
					}
				}
				
				
				// affichage du tableau de base 
				//msgJ1.println(jeu.toString());
				//msgJ1.flush() ;
				
				//msgJ2.println(jeu.toString());
				//msgJ2.flush() ;
				
				
				
				// info joueur 
				out.println(tour.toString());
				out.flush() ;
				
				
				// lire le choix et verifiaction du choix 
				choix = tour.erreur(in.readLine()) ;
				
				
				// tant que choi mauvais on relis 
				while(choix!="deplacement"&&choix!="passe"&&choix!="main")
				{
					out.println(choix);
					out.flush();
					
					out.println(tour.toString());
					out.flush();
					
					choix = tour.erreur(in.readLine()) ;
				}
				
				
				String erreur = "";
				
				if(choix.equals("deplacement"))
				{
					
					action = action + "1 " ;
					// choix de piece a jouer 
					out.println("\nQuel socle voulez vous deplacer ? (ex:A2/a2 0=retour) : ");
					out.flush() ;
					
					// lecture de piece
					String socle = in.readLine().toUpperCase() ;
										
					// revenir au menu si le choi est 0
					if(!socle.equals("0"))
					{
						// VERIFIACTION DE L'EXISTANCE du socle 
						erreur = jeu.existe(socle);
						
						
						// si c'est ni ok ni stop on boucle 
						while(!(erreur.equals("ok")||erreur.equals("stop")))
						{
							out.println(erreur);
							out.flush() ;
							
							out.println("Quel socle voulez vous deplacer ? (ex:A2/a2 0=retour) : ");
							out.flush() ;
							
							socle = in.readLine().toUpperCase() ;
							
							erreur = jeu.existe(socle);
							
							if(socle.equals("0"))
								erreur="stop";
						}
						
						
						// si c'est stop 
						if(!erreur.equals("stop"))
						{
							
							action = action + socle + " " ;
							
							// choix de la direction 
							out.println("\nDans quelle direction voulez vous déplacer votre socle ? (N-E-S-O - 0=retour) : ");
							out.flush() ;
						
							choix = in.readLine().toUpperCase() ;
							//choix = sc.nextLine().toUpperCase();
							
							
							// si c'est diferent de 0
							if(!choix.equals("0"))
							{
								// deplacement 
								erreur = jeu.deplacer(socle,choix);
								// si c'est ni  ok ni stop  on boucle 
								while(!(erreur.equals("ok")||erreur.equals("stop")))
								{
									out.println(erreur);
									out.flush() ;
									
									out.println("\nDans quelle direction voulez vous déplacer votre socle ? (N-E-S-O - 0=retour) : ");
									out.flush() ;
									
									choix = in.readLine().toUpperCase() ;
									//choix = sc.nextLine().toUpperCase();
									
									erreur = jeu.deplacer(socle,choix);
									if(choix.equals("0"))
										erreur="stop";
								}
								// deplacement 
								if(!erreur.equalsIgnoreCase("stop"))
								{
									jeu.deplacer(socle,choix.charAt(0));
									tour.deplacer();
									
									action = action + choix ;
									msgJ1.println(action);
									msgJ1.flush();
									msgJ2.println(action);
									msgJ2.flush();
									System.out.println(jeu.toString());
									
									action = "" ;
								}
							}
						}					
					}
				}
				// changement de place de la bille 
				if(choix.equals("passe"))
				{
					action = action + "2 " ;
					// demande de la destination
					out.println("\nVers quel socle voulez vous déplacer la bille ? (ex:A2/a2 0=retour) : ");
					out.flush() ;
					
					// lecture de destination
					String socle = in.readLine().toUpperCase();
					
					// verification de l'entrée 
					if(!socle.equals("0"))
					{
						erreur = jeu.existePasse(socle);
						while(!(erreur.equals("ok")||erreur.equals("stop")))
						{
							out.println(erreur);
							out.flush() ;
							
							out.println("Vers quel socle voulez vous déplacer la bille ? (ex:A2/a2 0=retour) : ");
							out.flush() ;
							
							
							socle = in.readLine().toUpperCase() ;
							
							erreur = jeu.existePasse(socle);
							if(socle.equals("0"))
								erreur="stop";
						}
						if(!erreur.equals("stop"))
						{
							jeu.passer(socle);
							tour.passer();
							action = action + socle ;
							msgJ1.println(action);
							msgJ1.flush();
							msgJ2.println(action);
							msgJ2.flush();
							System.out.println(jeu.toString());
							action = "" ;

						}					
					}				
				}
				// si le choix et de donner la main 
				else if(choix.equals("main"))
				{
					action = action + "3" ;
					
					msgJ1.println(action);
					msgJ1.flush();
					msgJ2.println(action);
					msgJ2.flush();
					System.out.println(jeu.toString());
					
					action = "" ;
					// changement du joueur et changement des flux d'entre/ sortie
					jeu.changerJoueur();
					if(out == msgJ1){
						out = msgJ2 ;
						in = lectureJ2 ;
					}
					else{
						out = msgJ1 ;
						in = lectureJ1 ;
					}
					
				}
			}
			
			// ecriture du resultat
			msgJ1.println("Félicitations " + jeu.getJoueur() + ", vous avez gagné !");
			msgJ1.flush();
			
			msgJ2.println("Félicitations " + jeu.getJoueur() + ", vous avez gagné !");
			msgJ2.flush();
				
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
