/*				TP BalonCup				*/
/*				Classe CarteBallon		*/
/*										*/
/* @authors : 	RATEL		Florient	*/
/*				ZERHOUNI  	Youssef		*/
/*				SOUDAY		Florian		*/
/* @date : 06/06/2014					*/
/****************************************/

package metier;

public class CarteBallon
{
	private char couleur;
	private int  valeur;
	
	public CarteBallon( char coul, int val )
	{
		this.couleur = coul;
		this.valeur  = val;
	}
	
	public char 	getCouleur	() { return this.couleur; 										}
	public int  	getValeur 	() { return this.valeur;  										}
	public String 	toString	() { return this.couleur + String.format("%02d",this.valeur);	}
											
	//retourne le symbole de la carte sous forme : CDD et de la couleur correspondante
}
