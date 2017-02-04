/*          TP BalonCup                 */
/*          Plateau                     */
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

// Cette classe hérite de JPanel
public class Plateau extends JPanel
{
	private ArrayList<Paysage> tabPays;

	private IHMBallonCup ibc;
	private IHMPaysage   pays1;
	private IHMPaysage   pays2;
	private IHMPaysage   pays3;
	private IHMPaysage   pays4;

	// CONSTRUCTEUR //
	public Plateau( ArrayList<Paysage> tabPays, IHMBallonCup ibc )
	{
		// Modification de la mise en page du panel principal
		setLayout( new GridLayout( 1,4 ));

		this.ibc = ibc;
		this.tabPays = tabPays;

		// Appel de la méthode d'initialisation du plateau
		init();

		// Ajout des paysages au panel principal
		add(pays1);
		add(pays2);
		add(pays3);
		add(pays4);

		setVisible(true);
	}

	// Initialisation du plateau
	public void init()
	{
		pays1 = new IHMPaysage( tabPays.get( 0 ), ibc);
		pays2 = new IHMPaysage( tabPays.get( 1 ), ibc);
		pays3 = new IHMPaysage( tabPays.get( 2 ), ibc);
		pays4 = new IHMPaysage( tabPays.get( 3 ), ibc);
	}

	// Mise à jour de plateau
	public void maj()
	{
		pays1.maj( tabPays.get( 0 ) );
		pays2.maj( tabPays.get( 1 ) );
		pays3.maj( tabPays.get( 2 ) );
		pays4.maj( tabPays.get( 3 ) );
	}

}
