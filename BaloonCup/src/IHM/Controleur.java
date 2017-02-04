/*				TP BalonCup				*/
/*				Classe Controleur		*/
/*										*/
/* @authors : 	RATEL		Florient	*/
/*				ZERHOUNI  	Youssef		*/
/*				SOUDAY		Florian		*/
/* @date : 06/06/2014					*/
/****************************************/

package ihm;

import metier.*;
import java.util.*;

public class Controleur
{
	private IHMBallonCup ihm ;
	private BalloonCup bc ;
	
	public Controleur(String nomJ1, String nomJ2)
	{	
		// on passe les noms des joueurs en parametre de l'objet BallonCup
		bc = new BalloonCup(nomJ1,nomJ2) ;	
		
		BalloonCup bc = new BalloonCup("Test1","Test2");
		
		/* INITIALISATION COMME ON LE SOUHAITE */
		
		
		// On vide toutes les arraylist
		
		bc.j1.getObjMain().clear();
		bc.j2.getObjMain().clear();
		
		// On initialise comme l'on veut.

		
		// Les joueur pioche des cartes
		bc.j1.ajouterCarteBallon( new CarteBallon('R' ,3) );
		bc.j1.ajouterCarteBallon( new CarteBallon('B' ,11) );
		bc.j1.ajouterCarteBallon( new CarteBallon('V' ,4) );
		bc.j1.ajouterCarteBallon( new CarteBallon('J' ,6) );
		bc.j1.ajouterCarteBallon( new CarteBallon('G' ,13) );
		bc.j1.ajouterCarteBallon( new CarteBallon('R' ,10) );
		bc.j1.ajouterCarteBallon( new CarteBallon('V' ,2) );
		bc.j1.ajouterCarteBallon( new CarteBallon('V' ,6) );
		
		bc.j2.ajouterCarteBallon( new CarteBallon('R' ,1) );
		bc.j2.ajouterCarteBallon( new CarteBallon('J' ,9) );
		bc.j2.ajouterCarteBallon( new CarteBallon('V' ,7) );
		bc.j2.ajouterCarteBallon( new CarteBallon('J' ,3) );
		bc.j2.ajouterCarteBallon( new CarteBallon('G' ,1) );
		bc.j2.ajouterCarteBallon( new CarteBallon('R' ,4) );
		bc.j2.ajouterCarteBallon( new CarteBallon('V' ,8) );
		bc.j2.ajouterCarteBallon( new CarteBallon('V' ,1) );
		
		// On leur attribue des cubes
		bc.j1.setNbCubeRouge( 6 );
		bc.j1.setNbCubeVert( 4 );
		bc.j1.setNbCubeJaune( 8 );
		bc.j1.setNbCubeGris(2) ;
		
		bc.j2.setNbCubeRouge( 6 );
		bc.j2.setNbCubeVert( 3 );
		bc.j2.setNbCubeJaune( 1 );

		
		
		// on passe l'objet ballonCup en parametre de l'ihm pour controler plus facilement 
		ihm = new IHMBallonCup( bc ) ;			
	}
	public static void main(String[] arg )
	{	
		// les noms par defauts sont joueur1 et joueur2	
		String nomJ1 = "Joueur1";				
		String nomJ2 = "Joueur2";
		
		if ( arg.length == 2 )					
		{
			// on met les noms des joueurs en parametre apres la comande de lancement
			nomJ1 = arg[0];
			nomJ2 = arg[1];
		}
		new Controleur( nomJ1, nomJ2 ) ;
	}
}
