/*          TP BalonCup                 */
/*          IHMPaysage	                */
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

public class IHMPaysage extends JPanel implements MouseListener
{	
	/* Composants */
	private JPanel nord;
	private JPanel sud;
	private JPanel coteJ1;
	private JPanel coteJ2;
	private JPanel centrePaysage;
	private JPanel tabCube;
	private JPanel listeCube;
	private JLabel imagePaysage;
	
	/* Variables */
	private Paysage pays;
	private IHMBallonCup ibc;

	/* Constructeur */
	public IHMPaysage( Paysage p, IHMBallonCup ibc )
	{
		/* Changement du Layout */
		setLayout( new BorderLayout() );
		
		/* Initialisation des variables */
		this.ibc = ibc;
		this.pays = p;
		
		/* Initialisations des Composants */
		nord			= new JPanel();
		sud 			= new JPanel();
		coteJ1			= new JPanel();
		coteJ2 			= new JPanel();
		centrePaysage 	= new JPanel();
		tabCube			= new JPanel();
		listeCube		= new JPanel();
		imagePaysage	= new JLabel();
		
		/* Changement des Layouts des Composants */
		coteJ1.			setLayout( new GridLayout	( 1,pays.getValeur()));
		coteJ2.			setLayout( new GridLayout	( 1,pays.getValeur()));
		centrePaysage.	setLayout( new OverlayLayout( centrePaysage 	));
		tabCube.		setLayout( new GridLayout	( 1,pays.getValeur()));
		
		/* Ajout des événements */
		coteJ1.addMouseListener( this );
		coteJ2.addMouseListener( this );
		
		/* Initialisation du Panel */
		init();
				
		/* Ajout des composants */
		nord.add(coteJ1);
		sud.add(coteJ2);
		
		add(nord,"North");
		add(sud,"South");
		add(centrePaysage);
		
		setVisible(true);
	}
	
	/* Initialisation des Composants */
	public void init()
	{
		/* Ajout des carte coté J1 
		 * Si la carte est selectionnée le bord est rouge.
		 * Sinon il est noir.
		 * On met l'image correspondante.
		*/
		for( int i=0; i<pays.getValeur(); i++ )
		{
			JLabel j = new JLabel();
			j.setPreferredSize( new Dimension(70,100));
			j.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			if ( i < pays.getValeur() && pays.getPoseJ1(i) != null )
				j.setIcon( new ImageIcon( "./Images/Cartes/"+pays.getPoseJ1(i).toString()+".jpg"));
				
			coteJ1.add(j);
		}
		
		/* Ajout des cartes coté j2 ( Même méthode que pour j1) */
		for( int i=0; i<pays.getValeur(); i++ )
		{
			JLabel j = new JLabel();
			j.setPreferredSize( new Dimension(70,100));
			j.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			if ( i < pays.getValeur() && pays.getPoseJ2(i) != null )
				j.setIcon( new ImageIcon( "./Images/Cartes/"+pays.getPoseJ2(i).toString()+".jpg"));
				
			coteJ2.add(j);
		}
		
		/* Ajout des cubes dans les Paysages */
		for( int i=0; i<pays.getValeur(); i++)
		{
			JLabel cube = new JLabel();
			cube.setPreferredSize( new Dimension( 30,30));
			cube.setIcon( new ImageIcon( "./Images/Cubes/"+pays.getCube(i).getCouleur()+".jpg"));
			tabCube.add(cube);
		}
		
		/* Changement de l'image du Paysage */
		imagePaysage.setIcon( new ImageIcon( "./Images/Paysages/"+pays.toString()+".jpg"));
		
		/* On met les panneaux cubes Opaque pour voir l'image du paysage */
		listeCube.setOpaque( false);
		tabCube.setOpaque( false);
		
		/* On ajoute le panel tabCube en gridLayout dans le pane ListeCube en FlowLayout */
		listeCube.add(tabCube);
		
		/* On ajoute l'image du paysage au second plan et la liste de cube au premier */
		centrePaysage.add(listeCube,0);
		centrePaysage.add(imagePaysage,1);
	}
	
	/* Mise à jour du panel */
	public void maj( Paysage p )
	{
		/* On change le Paysage */
		this.pays = p;
		
		/* On vide les COmposants */
		coteJ1.			removeAll();
		coteJ2.			removeAll();
		centrePaysage.	removeAll();
		tabCube.		removeAll();
		listeCube.		removeAll();
		imagePaysage.	removeAll();
		
		/* On reremplis les Composants */
		init();
	}
	
	/* Evenement de la Souris */
	public void mouseClicked(MouseEvent e)
	{
		/* Pour chaque cotés on attribut :
		 * - Le numéro du pays,
		 * - Le coté,
		 * - La posisiton sur le paysage,
		 * En fonction de l'endroit où l'on à cliqué.
		*/
		
		/* Si on clique sur le panel coteJ1 */
		if ( e.getSource() == coteJ1 )
		{
			ibc.setNumPays( pays.getValeur()-1 );
			ibc.setCote( true );
			ibc.setPosPays( e.getX()/70 );
		}
		
		/* Si on clique sur le panel coteJ2 */
		if ( e.getSource() == coteJ2 )
		{
			ibc.setNumPays( pays.getValeur()-1 );
			ibc.setCote( false );
			ibc.setPosPays( e.getX()/70 );
		}
		
		ibc.jouer();
	}
	
	/* Méthodes non utilisée mais nécessaire */
	public void mouseEntered	(MouseEvent e)	{}
	public void mouseExited		(MouseEvent e)	{}
	public void mousePressed	(MouseEvent e)	{}
	public void mouseReleased	(MouseEvent e)	{}
}
	
	
