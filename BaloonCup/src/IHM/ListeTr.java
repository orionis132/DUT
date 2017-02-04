/*          TP BalonCup                 */
/*          ListeTr		                */
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

public class ListeTr extends JPanel
{
	private ArrayList<Trophee> tabTr = new ArrayList<Trophee>();
	
	private Joueur j1;
	private Joueur j2;
	
	// on passe ne parametre les joueurs pour voir si ils ont des trophés
	public ListeTr( Joueur j1, Joueur j2)
	{
		GridLayout gl = new GridLayout( 5,1 );
		setLayout( gl );
		
		this.j1 = j1;
		this.j2 = j2;
		
		// on ajoute tout les trophé a une liste de trophés
		tabTr.add( new Trophee('R',7));
		tabTr.add( new Trophee('J',6));
		tabTr.add( new Trophee('V',5));
		tabTr.add( new Trophee('B',4));
		tabTr.add( new Trophee('G',3));
		
		// on met les images dans le panel en fonction des symboles des trophé dans la liste
		for( int i=0; i< tabTr.size(); i++)
		{
			JLabel j = new JLabel();
			j.setIcon( new ImageIcon( "./Images/Cartes/"+tabTr.get(i)+".jpg"));
			add(j);
		}
		
		setVisible(true);
	}
	// methode qui met a jour le panel 
	public void maj()
	{
		// on vide le panel 
		removeAll();
		
		// on virifie si l'un des joueur a un trophé et si c'est le cas on l'enleve de la liste
		for( int i=0; i<3; i++ )
		{
			if ( j1.getTrophe(i) != null && j1.getTrophe(i).equals( tabTr.get(i) ) )
				tabTr.remove(i);
				
			if ( j2.getTrophe(i) != null && j2.getTrophe(i).equals( tabTr.get(i) ) )
				tabTr.remove(i);
		}
		// on redessine les images dans le panel 
		for( int i=0; i< tabTr.size(); i++)
		{
			JLabel j = new JLabel();
			j.setIcon( new ImageIcon( "./Images/Cartes/"+tabTr.get(i)+".jpg"));
			add(j);
		}
	}
}
	
	
