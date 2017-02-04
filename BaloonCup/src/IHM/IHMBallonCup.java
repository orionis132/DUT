/*          TP BalonCup                 */
/*          IHMBallonCup                */
/*                                      */
/* @authors : RATEL    Florient         */
/*            ZERHOUNI Youssef          */
/*            SOUDAY   Florian          */
/* @date : 06/06/2014                   */
/****************************************/

package ihm;

import metier.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class IHMBallonCup extends JFrame implements ActionListener
{
	/* Panels dans Frame */
	private Main 			paneJ1;
	private Main 			paneJ2;
	private ListeTr 		paneTro;
	private Plateau 		plateau;
	private BalloonCup 		bc;
	private int 			idJoueur = 1;
	
	/* Variables */
	public int 		numPays;
	public boolean 	cote1;
	public int 		indicePosPays;
	public int 		indiceCarteJoueur;
	
	public IHMBallonCup(BalloonCup bc)
	{
		setTitle("BallonCup");
		setLocation(0,0);
		// represente la main du joueur1 et prend en parametre j1 de ballonCup et cette classe
		paneJ1 		= new Main(bc.j1, 1, this);	
		// represente la main du joueur2 et prend en parametre j1 de ballonCup et cette classe
		paneJ2 		= new Main(bc.j2, 2, this);			
		// represente la liste de trophés laterale 
		paneTro		= new ListeTr(bc.j1, bc.j2);			
		// represente la plateau qui contient toutes les tuiles 
		plateau 	= new Plateau(bc.getPays(), this);		
		this.bc = bc;
		
		// on met une bordure rouge pour savoir que c'est au tour de j1 de jouer
		paneJ1.setBorder(BorderFactory.createLineBorder(Color.RED));	
		
		// on ajoute les differents composants de la Frame
		add(paneTro,"East");
		add(paneJ1,"North");
		add(paneJ2,"South");
		add(plateau);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation( EXIT_ON_CLOSE );
	}
	
	public void maj()
	{
		if ( idJoueur == 1 || idJoueur > 2 )
		{
			// on verifie si c'est au tour de J1 et on lui met une bordure rouge
			paneJ1.setBorder(BorderFactory.createLineBorder(Color.RED));	
			paneJ2.setBorder(null);
		}
		else
		{
			// sinon on met la bordure pour J2
			paneJ1.setBorder(null);
			paneJ2.setBorder(BorderFactory.createLineBorder(Color.RED));	// 
		}
		// on Actualise tous les panels de cette Frame
		paneJ1.	maj();
		paneJ2.	maj();
		plateau.maj();
		paneTro.maj();
		// on change la taille pour actualiser (petit beug de java )
		pack();
	}
	
	public void setNumPays			( int i 	)	{ this.numPays 				= i;	}
	public void setCote				( boolean b ) 	{ this.cote1 				= b;	}
	public void setPosPays			( int i		) 	{ this.indicePosPays 		= i;	}
	public void	setIndCarteJoueur	( int i 	) 	{ this.indiceCarteJoueur	= i; 	}
	
	public void jouer()
	{
		// on verifie si c'est au joueur1 ou au joueur2 de jouer
		if ( idJoueur > 2 ) idJoueur = 1;
		
		/* appel de la methode jouer de Ballooncup et retourne si l'action est poisble ou pas
		si c'est le cas on change le joueur*/
		if ( bc.jouer( numPays, cote1, indicePosPays, idJoueur, indiceCarteJoueur) )
			idJoueur++;
		// verification si l'un des joueur a gagner et si c'est le cas on affiche l'ecran de fin 
		if(bc.gagner() != null)
			ecranDeFin() ;
		else
		{	
			// sinon on met la frame a jour 
			maj();
		}
	}
	public void ecranDeFin()
	{	
		// on vide la frame 
		paneJ1.	removeAll() ;
		paneJ2.	removeAll() ;
		plateau.removeAll() ;
		paneTro.removeAll() ;
		
		// on ajoute un label qui anonce le gagnant et un bouton qui permet de rejouer 
		add(new JLabel(bc.gagner().getNom()+" a gagné !!!!")) ;
		
		JButton bouton = new JButton("Rejouer") ;
		bouton.addActionListener(this) ;
		add(bouton , "South") ;
		
		pack();
	}
	public void actionPerformed(ActionEvent e )
	{
		// si on clic sur le bouton on vide la frame 
		
		Container cp = this.getContentPane();
		cp.removeAll();		
		
		// on comence un nouveau jeu
		bc = new BalloonCup( bc.j1.getNom(), bc.j2.getNom());
		
		// on remet tout le elements en place 
		setTitle("BallonCup");
		setLocation(0,0);
		setSize(1200,750);
		
		paneJ1 		= new Main(bc.j1, 1, this);
		paneJ2 		= new Main(bc.j2, 2, this);
		paneTro		= new ListeTr(bc.j1, bc.j2);
		plateau 	= new Plateau(bc.getPays(), this);
		
		add(paneTro,"East");
		add(paneJ1,"North");
		add(paneJ2,"South");
		add(plateau);
		
		maj();
	}
}
	
	
