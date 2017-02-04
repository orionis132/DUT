/*          TP BalonCup                 */
/*          Classe BalloonCup           */
/*                                      */
/* @authors : RATEL    Florient         */
/*            ZERHOUNI Youssef          */
/*            SOUDAY   Florian          */
/* @date : 06/06/2014                   */
/****************************************/

package metier;

import java.util.ArrayList;

public class BalloonCup
{
	/* Variables */
	public  Jeu                jeu;
	public  Joueur             j1;
	public  Joueur             j2;
	private ArrayList<Cube>    sacCubes;
	private ArrayList<Paysage> tabPays;
	
	public BalloonCup(String nomJ1, String nomJ2)
	{
		/* Initialisations des variables */
		jeu 		= new Jeu();
		j1  		= new Joueur( nomJ1 );
		j2  		= new Joueur( nomJ2 );
		sacCubes 	= new ArrayList<Cube>   ();
		tabPays  	= new ArrayList<Paysage>();

		/* Initialisation du sac de cubes */
		initSacCubes();

		/* Remplissage des paysages avec des cubes */
		for( int i=1; i < 5; i++ )
		{
			/* On crée un Paysage temporaire auquel on ajoute des cubes du sac de cubes.
			 * On supprime du sac de cube les cube que l'on ajoute sur le payasage.
			 * On ajoute le Paysage dans le tableau de Paysage.*/
			Paysage temp = new Paysage( i );
			for( int j=0; j < i; j++)
			{
				int indice = (int)(Math.random()*sacCubes.size() );
				temp.addCube( sacCubes.get( indice ));
				sacCubes.remove( indice );
			}
			tabPays.add( temp );
		}

		/* On donne 8 cartes à chaques joueur. */
		for ( int i=0; i<8; i++ )
		{
			j1.ajouterCarteBallon( jeu.getCarte() );
			j2.ajouterCarteBallon( jeu.getCarte() );
		}
	}

	public void initSacCubes()
	{
		/* Initialisation du sac de cube */
		for(int i = 0 ; i < 13 ; i++)
		{
			if( i < 5 )
				sacCubes.add(new Cube("gris")) ;
			if( i < 7 )
				sacCubes.add(new Cube("bleu")) ;
			if( i < 9 )
				sacCubes.add(new Cube("vert")) ;
			if( i < 11 )
				sacCubes.add(new Cube("jaune")) ;

			sacCubes.add(new Cube("rouge"));
		}
	}
	
	public boolean joueurPeutJouer( int numJ )
	{
		Joueur temp = ( numJ == 1 ? j1 : j2 );
		
		/* On regarde pour toutes les cartes du joueur si il peut poser la carte */
		for( CarteBallon c : temp.getObjMain() )
			for( Paysage p : tabPays )
				if ( p.estPlacable( c ) ) return true;
		return false;
	}

	public boolean jouer( int numPays, boolean coteJ1, int posPays, int j, int indCarteJoueur )
	{
		/* Si le numéro du pays de correspond pas on renvois false */
		if ( numPays < 0 || numPays >= tabPays.size() ) return false;
		
		/* Initialisation de variables */
		boolean ret 	= false;
		Joueur  jTemp 	= ( j==1 ? j1 : j2 );
		Paysage pays 	= tabPays.get( numPays );

		/* On vérifi si la carte est posable avec sa couleur et si l'indice pour poser la carte est valable. */
		if ( pays.verifBonneCarte( jTemp.getCouleurCarteAt( indCarteJoueur ), coteJ1 ) && pays.verifIndice( posPays, coteJ1 ))
		{
			// Selon le joueur on modifie les mains.
			if ( j == 1 )
			{
				//On vérifie si la carte à été ajouté. Si oui on pioche.
				if( pays.ajouterCarte( j1.poserCarteBallon( indCarteJoueur ), posPays, coteJ1 ))
				{
					j1.ajouterCarteBallon( jeu.getCarte() );
					ret = true;
				}
			}
			else
			{
				//On vérifie si la carte à été ajouté. Si oui on pioche.
				if(pays.ajouterCarte( j2.poserCarteBallon( indCarteJoueur ), posPays, coteJ1 ));
				{
					j2.ajouterCarteBallon( jeu.getCarte() );
					ret = true;
				}
			}
		}

		//Si le paysage est plein on défausse les cartes qui sont posées dessus.
		if ( pays.estPlein() )
		{
			/* Initialisation des variables */
			Joueur gagnant = null;
			int ptsJ1 = pays.getSomme(1);
			int ptsJ2 = pays.getSomme(2);

			/* Si EGALITE */
			if ( ptsJ1 == ptsJ2 )
			{
				if( j == 1 )
					gagnant = j1;
				else
					gagnant = j2;
			}
			else
			{
				/* Si MONTAGE */
				if ( pays.estMontagne() )
				{
					/* Gagnant Joueur 1 */
					if ( ptsJ1 > ptsJ2 )
						gagnant = j1;
					else
						/* Gagnant Joueur */
						if ( ptsJ2 > ptsJ1  )
							gagnant = j2;
				}
				else
				{
				/* Si PLAINE */
					/* Gagnant Joueur 1 */ 
					if ( ptsJ1 < ptsJ2 )
						gagnant = j1;
					else
					/* Gagnant Joueur 2 */
						if ( ptsJ2 < ptsJ1 )
							gagnant = j2;
				}
			}

			/* On attribue les cubes au gagnant */
			pays.attribuerCube( gagnant );
			
			/* On vide le Paysage dans la defausse */
			pays.vider( jeu );
			
			/* On créer un nouveau Paysage à l'indice de l'ancien */
			tabPays.set( numPays, new Paysage( pays.getValeur() ));
			
			/* On remplis le nouveau Paysage avec des cubes pris dans le sac 
			 * que l'on supprime au fur et à mesure */
			for( int x=0; x < tabPays.get( numPays).getValeur(); x++)
			{
				int indice = (int)(Math.random()*sacCubes.size() );
				tabPays.get( numPays).addCube( sacCubes.get( indice ));
				sacCubes.remove( indice );
			}
			
			/* Si le gagnant gagne un trophée on lui donne */
			gagnant.gagneTrophe() ;
		}
		return ret;
	}
	
	/* On regarde si un joueur à gagné. Si oui on le retourne */
	public Joueur gagner()
	{
		if(j1.getNbTrophe() == 3)
			return j1 ;
		if(j2.getNbTrophe() == 3)
			return j2 ;
		return null ; 
	}
	
	/* Accesseur a l'ArrayList de Paysage */
	public ArrayList<Paysage> getPays() { return tabPays; }

	/* Initiliser dans le Livrable 3 */
	public String toString()
	{
		String s="";

		s += j1.toString()+"\n\n";
		for ( Paysage p : tabPays )
			s+= p.toString() + "\n";
		s += "\n"+j2.toString()+"\n\n";
		return s;
	}
}
