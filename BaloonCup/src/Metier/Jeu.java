/*          TP BalonCup                 */
/*          Classe Jeu                  */
/*                                      */
/* @authors : RATEL    Florient         */
/*            ZERHOUNI Youssef          */
/*            SOUDAY   Florian          */
/* @date : 06/06/2014                   */
/****************************************/

package metier;

import java.util.*;
import java.io.*;

public class Jeu
{
	// jeu contient la pioche et la defausse
	private ArrayList<CarteBallon> pioche;
	private ArrayList<CarteBallon> defausse;

	public Jeu()
	{
		pioche = new ArrayList<CarteBallon>();
		defausse = new ArrayList<CarteBallon>();

		// init() appele une methode qui initialise la pioche
		this.init();
	}

	public void init()
	{
		try
		{
			// les cartes sont au format CDD (R01 pour carte rouge de valeur 1) dans ce fichier
			Scanner sc = new Scanner(new File("cartes.data"));
			String ligne;
			Scanner scLigne;
			while(sc.hasNext())
			{
				ligne = sc.nextLine();
				int chiffre = Integer.parseInt( new String(""+ligne.charAt(1)+ligne.charAt(2)));
				pioche.add(new CarteBallon(ligne.charAt(0) , chiffre ));
			}
			sc.close();
		}
		catch(IOException e){}

		// appele une methode qui melange aleatoirement les cartes de la pioche
		melangerPioche();
	}

	// methode qui retourne la premiére carte du lot et la suprime de la pioche
	public CarteBallon getCarte()
	{
		CarteBallon temp = null;

		if ( pioche.size() > 0 )
		{
			temp = pioche.get(0);
			pioche.remove(0);
		}

		if( pioche.size() == 0 )
			this.renouveler();

		return temp;
	}

	// methode qui met la defausse dans la piche et mélange la nouvelle pioche
	public void renouveler()
	{
		int carte;
		pioche = defausse;

		// vider la defausse
		defausse = new ArrayList<CarteBallon>();
		melangerPioche();
	}

	// methode qui melange la pioche
	public void melangerPioche()
	{
		int indCarte;

		// liste de cartes temporaire
		ArrayList<CarteBallon> temp = new ArrayList<CarteBallon>();
	
		while ( pioche.size() > 0 )
		{
			// trouve un entier au hasard inferieur a la taille de l'arrayliste
			indCarte = (int)(Math.random()*pioche.size());
			temp.add( pioche.get( indCarte ));
			pioche.remove( indCarte );
		}
		pioche = temp;
	}

	// ajoute la carte a la defausse
	public void defausser(CarteBallon carte)
	{
		if ( carte != null ) defausse.add(carte);
	}

	// aficher le contenu de la pioche
	public String toString()
	{
		return pioche.toString();
	}
}
