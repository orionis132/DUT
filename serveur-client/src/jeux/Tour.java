/*
 * Charles Rouillard
 * Charles Poure
 * Tom Etienne 
 * Florient Ratel
 * Clement Toulgoat 
 * Youssef Zerhouni
 */
package jeux;

public class Tour 
{
	private String joueur;
	private int deplacements;
	private int passe;

	public Tour(String joueur)
	{
		this.deplacements 	= 2;
		this.passe 			= 1;
		this.joueur 		= joueur;
	}

	public int getDeplacements() 	{return deplacements;	}
	public int getPasse() 			{return passe;			}
	public String getJoueur()		{return joueur;			}
	
	public void deplacer(){deplacements--;}
	public void passer(){passe--;}
	
	public String erreur(String choix)
	{
		String erreur = "";
	
		if(!choix.matches("^[123]$"))
			return "Vous devez rentrer un nombre entre 1 et 3";
		
		int i = Integer.parseInt(choix);
		
		if((i==1 && deplacements>0 && passe >0) || (i==1 && deplacements >0 && passe ==0))
		{
			if(deplacements==0)	erreur = "Vous n'avez plus de deplacement disponible";
			else 				erreur = "deplacement";
		}
		else if((i==2 && passe>0 && deplacements>0) || ( i == 1 && deplacements == 0 && passe>0))
		{
			if(passe==0)		erreur = "Vous n'avez plus de passe disponible";
			else 				erreur = "passe";
		}
		else if((i==3 && passe>0 && deplacements >0) || (i==2 && passe==0 && deplacements>0) || (i==2 && deplacements==0 && passe>0) || (i==1 && deplacements==0 && passe==0))
								erreur = "main";
		else
								erreur = "La saisie ne correspond pas aux choix proposés.";
		return erreur;
	}
	
	public String toString()
	{
		String retour = "" ;
		
		if		(deplacements>0 && passe>0	) 		retour += "1 : Deplacement / 2 : Passe / 3 : Passer la main : ";
		else if	(deplacements>0 && passe==0	) 		retour += "1 : Deplacement / 2 : Passer la main : ";
		else if	(deplacements==0 && passe>0	) 		retour += "1 : Passe / 2 : Passer la main : ";
		else 										retour += "1 : Passer la main : ";
		
		return retour;
	}
}
