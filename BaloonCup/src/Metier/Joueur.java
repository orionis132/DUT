/*          TP BalonCup                 */
/*          Classe Joueur               */
/*                                      */
/* @authors : RATEL    Florient         */
/*            ZERHOUNI Youssef          */
/*            SOUDAY   Florian          */
/* @date : 06/06/2014                   */
/****************************************/

package metier;

import java.util.LinkedList;
import java.util.ArrayList;

public class Joueur
{
	/* Variables */
	private String                 nom;
	private ArrayList<CarteBallon> main;

	private int                    nbCubeRouge;
	private int                    nbCubeVert;
	private int                    nbCubeJaune;
	private int                    nbCubeBleu;
	private int                    nbCubeGris;
	private int                    nbTrophe;
	private Trophee[]              tabTrophe;

	/* Constructeur */
	public Joueur( String nom )
	{
		/* Initialisation des variables */
		this.nbCubeRouge = 0;
		this.nbCubeVert  = 0;
		this.nbCubeJaune = 0;
		this.nbCubeBleu  = 0;
		this.nbCubeGris  = 0;
		this.nbTrophe    = 0;
		this.tabTrophe   = new Trophee[3];
		this.nom         = nom;
		this.main        = new ArrayList<CarteBallon>();
	}

	/* ------------------------------- Accesseurs ------------------------------- */
	public int     		getNbCubeRouge 		()          { return this.nbCubeRouge;                                            				}
	public int     		getNbCubeVert  		()          { return this.nbCubeVert;                                             				}
	public int     		getNbCubeJaune 		()          { return this.nbCubeJaune;                                            				}
	public int     		getNbCubeBleu  		()          { return this.nbCubeBleu;                                             				}
	public int     		getNbCubeGris  		()          { return this.nbCubeGris;                                             				}
	public int     		getNbTrophe    		()          { return this.nbTrophe;                                               				}
	public Trophee 		getTrophe      		( int ind ) { return ( ind >= 0 && ind < nbTrophe ? tabTrophe[ ind ] : null );    				}
	public String  		getNom         		()          { return this.nom;                                                    				}
	public ArrayList<CarteBallon> getObjMain()			{ return this.main;																	}
	public String  		getMain        		()          { String s = ""; for ( CarteBallon c : main ) s += c + " "; return s; 				}
	public Character    getCouleurCarteAt	( int ind ) { return ( ind >= 0 && ind < main.size() ? main.get( ind ).getCouleur() : null ); 	}

	/* ------------------------------ Modificateurs ------------------------------ */
	public void ajouterCarteBallon( CarteBallon c )
	{
		if ( c != null )
			main.add( c );
	}

	public CarteBallon poserCarteBallon( int ind )
	{
		/* On vérifie si l'indice est valable
		 * Si oui on met la carte dans une variable temporaire,
		 * On supprime la carte de la main du joueur,
		 * On retourne la carte.
		*/
		if ( ind >= 0 && ind < main.size() )
		{
			CarteBallon temp = main.get( ind );
			main.remove( ind );
			return temp;
		}
		return null;
	}

	/* Utilisé pour l'initialisation manuelle */
	public void ajouterTrophe( Trophee t )
	{
		if ( t != null && nbTrophe < 3 )
			tabTrophe[ nbTrophe++ ] = t;
	}
	
	/* Attribution des trophées */
	public boolean gagneTrophe()
	{
		boolean ret = false ;
		/* Pour chaque cubes on vérifie si le joueur à assez de cubes.
		 * Si oui on l'ajoute à son tableau et on incrémente son nombre de trophée.
		*/
		if(nbCubeRouge >= 7)
		{
			tabTrophe[nbTrophe] = new Trophee('R',7) ;
			nbCubeRouge = 0 ;
			nbTrophe++ ;
			ret = true;
		}
		if(nbCubeJaune >= 6)
		{
			tabTrophe[nbTrophe] = new Trophee('J',6) ;
			nbCubeJaune = 0 ;
			nbTrophe++ ;
			ret = true;
		}
		if(nbCubeVert >= 5)
		{
			tabTrophe[nbTrophe] = new Trophee('V',5) ;
			nbCubeVert = 0 ;
			nbTrophe++ ;
			ret = true;
		}
		if(nbCubeBleu >= 4)
		{
			tabTrophe[nbTrophe] = new Trophee('B',4) ;
			nbCubeBleu = 0 ;
			nbTrophe++ ;
			ret = true;
		}
		if(nbCubeGris >= 3)
		{
			tabTrophe[nbTrophe] = new Trophee('G',3) ;
			nbCubeGris = 0 ;
			nbTrophe++ ;
			ret = true;
		}
		return ret;
	}

	/* Modificateur */
	public void setNbCubeRouge ( int i ) { this.nbCubeRouge = i; }
	public void setNbCubeVert  ( int i ) { this.nbCubeVert  = i; }
	public void setNbCubeJaune ( int i ) { this.nbCubeJaune = i; }
	public void setNbCubeBleu  ( int i ) { this.nbCubeBleu  = i; }
	public void setNbCubeGris  ( int i ) { this.nbCubeGris  = i; }
	public void setNbTrophe    ( int i ) { this.nbTrophe    = i; }

	/* Non utilisé */
	public String toString()
	{
		return nom;
	}
}
