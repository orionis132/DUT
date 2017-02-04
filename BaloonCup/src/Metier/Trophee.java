/*          TP BalonCup                 */
/*          Classe Trophee              */
/*                                      */
/* @authors : RATEL    Florient         */
/*            ZERHOUNI Youssef          */
/*            SOUDAY   Florian          */
/* @date : 06/06/2014                   */
/****************************************/

package metier;

// Cette clase hérite de la classe CarteBallon
public class Trophee extends CarteBallon
{
	public Trophee( char coul, int val )
	{
		super( coul, val );
	}

	public String toString()
	{
		return "" + getCouleur() + getValeur();
	}
	public boolean equals( Trophee t )
	{
		return (t.toString().equals( this.toString() ));
	}
}
