/*
 * Charles Rouillard
 * Charles Poure
 * Tom Etienne 
 * Florient Ratel
 * Clement Toulgoat 
 * Youssef Zerhouni
 */
package jeux;

import java.awt.Color;

public class Case 
{
	private String type;
	private String couleur;
	private boolean occupe;
	
	public String getType() {return type;}
	public String getCouleur() {return couleur;}
	public boolean isOccupe() {return occupe;}
	
	public void detruire()
	{
		this.type = "";
		this.couleur = "";
		this.occupe = false;
	}
	
	public void passer()
	{
		this.occupe = !this.occupe;
	}
	
	public void set(Case autre)
	{
		this.type = autre.type;
		this.couleur = autre.couleur;
		this.occupe = autre.occupe;
	}

	public Case(String type, String couleur) 
	{
		this.type 		= type;
		this.couleur 	= couleur;
	}
	
	public void position()
	{
		this.occupe = true;
	}

	public String toString()
	{
		String retour = "|";
		if(occupe) 
			retour += String.format("%-5s","Bille");
		else
			retour += String.format("%-5s",this.type);
		if(!this.type.equals(""))
			retour += "/";
		else
			retour += " ";
		retour+= String.format("%5s",this.couleur) + "|";
		
		return retour;
	}
}
