/*          TP BalonCup                 */
/*          Classe Paysage              */
/*                                      */
/* @authors : RATEL    Florient         */
/*            ZERHOUNI Youssef          */
/*            SOUDAY   Florian          */
/* @date : 06/06/2014                   */
/****************************************/

package metier;

import java.util.ArrayList;

public class Paysage
{
	private int             valeur;
	private CarteBallon[]   poseJ1;  // tableau de cartes du coté du joueur 1
	private CarteBallon[]   poseJ2;  // tableau de cartes du coté du joueur 2
	private boolean         montagne;
	private ArrayList<Cube> tabCube;

	// CONSTRUCTEUR //
	public Paysage( int val )
	{
		// Nombre de place pour les cartes et aussi nombre de cube sur le Paysage
		this.valeur  = val;

		// On instancie les tableaux avec la variable valeur
		this.poseJ1  = new CarteBallon[ val ];
		this.poseJ2  = new CarteBallon[ val ];
		this.tabCube = new ArrayList<Cube>();

		// Choix aléatoire du paysage
		int i = (int)(Math.random()*2);
		if(i == 1)
			montagne = true;
		else
			montagne = false;
	}

	// Renvoit le cube d'index i
	public Cube getCube(int i)
	{
		if ( i>=0 && i<tabCube.size() )
			return tabCube.get(i);
		return null;
	}

	// Ajouter un cube
	public void addCube( Cube c )
	{
		if ( c != null )
		{
			tabCube.add( c );
		}
	}

	// Ajouter des cartes
	public boolean ajouterCarte( CarteBallon c, int ind, boolean j1 )
	{
		boolean result = false;

		// vérification de l'indice et vérification de la carte
		if ( c != null && ind >= 0 && ind < this.valeur )
		{
			CarteBallon[] temp;

			// temp prend soit le tableau de j1 ou de j2 selon le parametre
			if ( j1 )
				temp = poseJ1;
			else
				temp = poseJ2;

			// Vérification que la cellule ciblée est vide
			if ( temp[ ind ] == null )
			{
				temp[ ind ] = c;
				result = true ;
			}

			if ( j1 )
				poseJ1 = temp;
			else
				poseJ2 = temp;
		}
		return result;
	}

	// Retourne l'entier de la variable valeur
	public int getValeur() { return this.valeur; }

	// Renvoit la somme
	public int getSomme( int i)
	{
		int ret = 0;
		CarteBallon[] temp = poseJ2;

		if ( i == 1 )
			temp = poseJ1;

		for( int j=0; j < valeur; j++)
			ret += temp[ j ].getValeur();

		return ret;
	}

	public boolean estMontagne() { return montagne; }

	// Méthode utilisé quand les 2 cotés du paysage sont plein pour remettre à 0
	public void vider( Jeu jeu )
	{
		for ( int i=0; i < valeur; i++ )
		{
			// Met les cartes coté j1 et j2 dans la defausse
			jeu.defausser( poseJ1[ i ] );
			jeu.defausser( poseJ2[ i ] );
		}

		// Vide les emplacements de cartes
		poseJ1 = new CarteBallon[ this.valeur ];
		poseJ2 = new CarteBallon[ this.valeur ];
	}

	// Méthode qui verifie si le paysage est plein
	public boolean estPlein()
	{
		for ( int i=0; i < valeur; i++ )
			// S'il y a une seule carte null il retourne faux
			if ( poseJ1[ i ] == null || poseJ2[ i ] == null )
				return false;

		return true;
	}

	// Permet d'attribuer une couleur de cube à un joueur donné en paramètre
	public void attribuerCube( Joueur j )
	{
		for( Cube c : tabCube )
		{
			switch( c.getCouleur() )
			{
				case "CR" : j.setNbCubeRouge( j.getNbCubeRouge()+1 ); break;
				case "CV" : j.setNbCubeVert ( j.getNbCubeVert ()+1 ); break;
				case "CB" : j.setNbCubeBleu ( j.getNbCubeBleu ()+1 ); break;
				case "CJ" : j.setNbCubeJaune( j.getNbCubeJaune()+1 ); break;
				case "CG" : j.setNbCubeGris ( j.getNbCubeGris ()+1 ); break;
			}
		}
	}

	// Vérifie si l'indice est valide
	public boolean verifIndice ( int ind, boolean coteJ1 )
	{
		if ( ind >= 0 && ind < this.valeur )
		{
			if ( coteJ1 )
				return poseJ1[ ind ] == null;
			else
				return poseJ2[ ind ] == null;
		}
		else
			return false;
	}

	// Vérifie si la carte ballon placé en paramètre est placable
	public boolean estPlacable( CarteBallon c )
	{
		char coul = c.getCouleur();
		int  nbCube = 0;
		int  nbCarteCouleur = 0;

		for ( Cube cube : tabCube )
		{
			if ( Character.toUpperCase( cube.getCouleur().charAt(0) ) == coul )
				nbCube++;
		}

		for ( int i=0; i < valeur; i++ )
		{
			if ( poseJ1[i] != null && Character.toUpperCase( poseJ1[i].getCouleur()) ==  coul )
				nbCarteCouleur++;
			if ( poseJ2[i] != null && Character.toUpperCase( poseJ2[i].getCouleur()) ==  coul )
				nbCarteCouleur++;
		}

		return nbCube*2 > nbCarteCouleur;
	}

	// Vérifie si la carte choisie correspond au slot choisit
	public boolean verifBonneCarte( Character coul, boolean coteJ1 )
	{
		if ( coul == null ) return false;

		boolean ret = false;
		boolean aCube = false;
		CarteBallon[] temp = ( coteJ1 ? poseJ1 : poseJ2 );

		for ( Cube c : tabCube )
		{
			if ( Character.toUpperCase( c.getCouleur().charAt(1) ) ==  coul )
				aCube = true;
		}

		if ( aCube )
		{
			int nbCubeCouleur  = 0;
			int nbCarteCouleur = 0;

			for ( Cube c : tabCube )
				if ( Character.toUpperCase( c.getCouleur().charAt(1) ) == coul )
					nbCubeCouleur++;

			for ( int i=0; i < valeur; i++ )
			{
				if ( temp[i] != null && Character.toUpperCase( temp[i].getCouleur()) ==  coul )
					nbCarteCouleur++;
			}

			if ( nbCarteCouleur < nbCubeCouleur )
				ret = true;
		}

		return ret;
	}

	// Renvoi une chaine qui représente le coté du j1
	public CarteBallon getPoseJ1( int i )
	{
		return poseJ1[i];
	}

	// Renvoi une chaine qui représente le coté du j2
	public CarteBallon getPoseJ2( int i )
	{
		return poseJ2[i];
	}

	// Permet l'affichage
	public String toString()
	{
		return ( montagne ? "M" : "P" ) + this.valeur;
	}
}
