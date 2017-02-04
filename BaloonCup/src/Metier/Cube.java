/*          TP BalonCup                 */
/*          Classe Cube                 */
/*                                      */
/* @authors : RATEL    Florient         */
/*            ZERHOUNI Youssef          */
/*            SOUDAY   Florian          */
/* @date : 06/06/2014                   */
/****************************************/

package metier;

public class Cube
{
	String coul;

	public Cube(String coul)
	{
		this.coul = coul;
	}

	public String getCouleur() { return "C"+ Character.toUpperCase( coul.charAt(0)); }
	// retourne le symbole du cube sou forme de CX où x est la premiére lettre de la couleur 
}
