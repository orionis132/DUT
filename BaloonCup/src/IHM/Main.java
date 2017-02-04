/*          TP BalonCup                 */
/*          Main		                */
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

public class Main extends JPanel implements MouseListener
{
	/* Tableau des cubes */
	private String[] tabCube = new String[]{"CR","CJ","CV","CB","CG"};
	
	/* ArrayList de main */
	private ArrayList<CarteBallon> main;
	
	private int carteSelect = -1;
	
	/* Les différents grid */
	private JPanel cartes;
	private JPanel tabCubes;
	private JPanel nbCubes;
	private JPanel cubes;
	private JPanel trophees;
	private JLabel affNom;
	private Joueur joueur ; 
	private IHMBallonCup ibc;
	private int idJoueur;
	
	public Main( Joueur j, int id, IHMBallonCup ibc )
	{
		this.joueur = j;
		this.idJoueur = id;
		this.ibc = ibc;
		
		// on instancie les differents composants de du panel
		
		cartes		= new JPanel();
		nbCubes		= new JPanel();
		cubes 		= new JPanel();
		trophees 	= new JPanel();
		tabCubes	= new JPanel();
		affNom		= new JLabel();
		main		= new ArrayList<CarteBallon>();
		
		// on change les layout pour chaque panel
		cartes.		setLayout( new GridLayout( 1,8));
		nbCubes.	setLayout( new GridLayout( 1,4));
		cubes.		setLayout( new GridLayout( 1,4));
		trophees.	setLayout( new GridLayout( 1,3));
		tabCubes.	setLayout( new GridLayout( 2,1));
		
		// on ajoute l'ecoute des evenements
		cartes.addMouseListener( this );
		
		// on appel une methode qui initialise
		init();
		
		// on ajoute les differents composants du panel 
		add(affNom);
		add(cartes);
		add(tabCubes);
		add(trophees);
		
		setVisible(true);
	}
	// methode qui initialise le panel 
	private void init()
	{
		// on affiche le nom du joueur sinon on affiche joueur 
		if ( joueur != null ) 
		{
			main = joueur.getObjMain();
			affNom.setText( joueur.getNom() );
		}
		else
			affNom.setText("Joueur ");
		
		// on met les images des cartes sur le panel 
		for( int i=0; i<8; i++ )
		{
			JLabel j = new JLabel();
			//on met une taille adaptée
			j.setPreferredSize( new Dimension(70,100));
			
			// on met une bordure rouge pour la carte selectionnée
			if ( i == carteSelect )
				j.setBorder(BorderFactory.createLineBorder(Color.RED));
			else
				j.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			if ( i < main.size() )
				j.setIcon( new ImageIcon( "./Images/Cartes/"+main.get(i).toString()+".jpg"));
				
			cartes.add(j);
		}
		
		// on cree un tableau qui contient le nombre de chaque type de cubes 
		int[] nbCube = new int[]{ 	joueur.getNbCubeRouge(), 
									joueur.getNbCubeJaune(), 
									joueur.getNbCubeVert(), 
									joueur.getNbCubeBleu(), 
									joueur.getNbCubeGris()
								};
								
		// on boucle se tableau et on met le nombres et l'image des different cubes 
		for( int i=0; i<5; i++ )
		{
			JLabel j = new JLabel();
			j.setText( nbCube[ i ] + "/"+(7-i));
			nbCubes.add(j);
			
			j = new JLabel();
			j.setPreferredSize( new Dimension(30,30));
			j.setIcon( new ImageIcon( "./Images/Cubes/" +tabCube[i]+".jpg"));
			cubes.add(j);
		}
		
		tabCubes.add(nbCubes);
		tabCubes.add(cubes);
		
		// on regarde les trophé obtenus par les joueurs et on les met dans le panel 
		for( int i=0; i<3; i++ )
		{
			JLabel j = new JLabel();
			j.setPreferredSize( new Dimension(70,100));
			
			if ( i < joueur.getNbTrophe() )
			{
				j.setIcon( new ImageIcon( "./Images/Cartes/"+joueur.getTrophe( i ).toString()+".jpg"));
				j.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
				
			trophees.add(j);
		}
	}
	// methode qui met a jour 
	public void maj()
	{
		//on vide le panel 
		cartes.removeAll();
		nbCubes.removeAll();
		cubes.removeAll();
		trophees.removeAll();
		
		// et on remet le contenu
		init();
	}
	// methode appelé quand on clik sur une carte 
	public void mouseClicked(MouseEvent e)
	{
		// variable qui permet de mettre une bordure rouge a la carte 
		carteSelect = (e.getX()/70);
		
		// on met a jour pour que la bordure apparaise
		ibc.maj();
		
		// on met l'indice de la carte selectionner 
		ibc.setIndCarteJoueur( e.getX()/70 );
		carteSelect = -1;
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e)
	{
	}
	public void mousePressed(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e)
	{
	}
}
	
	
