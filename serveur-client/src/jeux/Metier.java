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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Metier
{
	private Case[][] grille;
	private String joueur;
	private Tour tour;
	private String[] joueurs ;
	
	public Metier(int i , String[] joueurs ) 
	{
		this.joueur = "Blanc";
		this.grille = new Case[7][7];
		this.initialiserGrille(i);
		this.joueurs = joueurs ;
	}
	
	public void changerJoueur()
	{
		if(this.joueur.equals("Blanc"))this.joueur="Noir";
		else this.joueur="Blanc";
	}
	
	public Case getBille()
	{
		for(int i=0;i<this.grille.length;i++)
		{
			for(int j=0;j<grille[0].length;j++)
			{
				if(this.grille[i][j].isOccupe() && this.grille[i][j].getCouleur().equals(this.joueur))
				{
					return this.grille[i][j];
				}
			}
		}
		return null;
	}
	
	public String existePasse(String socle)
	{		
		if(!socle.matches("^[A-Z][0-9]$"))
			return "La saisie n'est pas valide. Exemple : A2/a2";					//Mauvaise saisie
		
		int x = 0, y = 0;			//coordonnées de la bille du joueur actif
		
		for(int i=0;i<this.grille.length;i++)
		{
			for(int j=0;j<grille[0].length;j++)
			{
				if(this.grille[i][j].isOccupe() && this.grille[i][j].getCouleur().equals(this.joueur))
				{
					x = j;
					y = i;
				}
			}
		}
		
		String erreur = "ok";
		
		int i = socle.charAt(0)-'A';
		int j = Integer.parseInt(""+socle.charAt(1));
		
		if(i==y && j==x)
			return "Vous devez selectionner une case differente à celle de la bille."; //Case choisie = bille

		if(i<0||i>this.grille.length-1||j<0||j>this.grille[0].length-1)
			erreur = "Les Coordonnées ne correspondent pas au plateau du jeu";		//Mauvaises coordonnées
		if(this.grille[i][j].getType().equals(""))
			erreur = "Il n'y a pas de socle sur la case sélectionnée.";				//Pas de socle
		if(!erreur.equals("ok"))return erreur;
		Case temp = this.grille[i][j];
		if(!temp.getCouleur().equals(this.joueur))
			erreur = "Ce n'est pas une pièce de votre couleur";						//Mauvais joueur
		if(!isaligne(y,x,i,j))
			erreur = "Vous ne pouvez déplacer la bille que sur un socle qui est aligne horizontalement/vertcialement ou en diagonale.";		//Socle pas aligné
		if(!peutAcceder(y,x,i,j))
			erreur = "Une piece de l'adversaire empêche cette action.";				//Socle pas aligné
		return erreur;
	}
	
	public boolean peutAcceder(int ybille, int xbille, int ysocle, int xsocle)
	{		
		boolean retour = true;
		int vert = 1, horiz = 1;				//deplacement vers la droite (1) ou la gauche (-1) pour horiz
		if(xsocle < xbille) horiz = -1;
		if(ysocle < ybille) vert  = -1;
		if(xsocle== xbille) horiz = 0;
		if(ysocle== ybille) vert  = 0;
		
		for(int i=0;i<this.grille.length;i++)
		{
			if(xbille+horiz*i>0&&xbille+horiz*i<this.grille.length&&ybille+vert*i>0&&ybille+vert*i<this.grille.length && this.grille[ybille+vert*i][xbille+horiz*i]!=this.grille[ysocle][xsocle])
			{
				if(!this.grille[ybille+vert*i][xbille+horiz*i].getType().equals("") && !this.grille[ybille+vert*i][xbille+horiz*i].getCouleur().equals(this.joueur))
				{
					retour = false;
				}
			}
				
		}
		
		return retour;
	}
	
	public boolean isaligne(int ybille, int xbille, int ysocle, int xsocle)
	{		
		boolean retour = false;
		if(ybille==ysocle || xbille==xsocle)
		{
			retour = true;
		}
		for(int i=0;i<this.grille.length;i++)
		{
			if(xbille-i>=0 && ybille-i>=0)
			{
				if(xbille-i==xsocle && ybille-i==ysocle) retour = true;
			}
			if(xbille-i>=0 && ybille+i<this.grille.length)
			{
				if(xbille-i==xsocle && ybille+i==ysocle) retour = true;
			}
			if(xbille+i<this.grille.length && ybille+i<this.grille.length)
			{
				if(xbille+i==xsocle && ybille+i==ysocle) retour = true;
			}
			if(xbille+i<this.grille.length && ybille-i>=0)
			{
				if(xbille+i==xsocle && ybille-i==ysocle) retour = true;
			}
		}
		return retour;
	}
	
	public String existe(String socle)
	{
		String erreur = "ok";
		
		if(!socle.matches("^[A-Z][0-9]$"))
			return "La saisie n'est pas valide. Exemple : A2/a2";					//Mauvaise saisie
		
		int i = socle.charAt(0)-'A';
		int j = Integer.parseInt(""+socle.charAt(1));
		
		if(i<0||i>this.grille.length-1||j<0||j>this.grille[0].length-1)
			erreur = "Les Coordonnées ne correspondent pas au plateau du jeu";		//Mauvaises coordonnées
		if(this.grille[i][j].getType().equals(""))
			erreur = "Il n'y a pas de socle sur la case sélectionnée.";				//Pas de socle
		if(!erreur.equals("ok"))return erreur;
		Case temp = this.grille[i][j];
		if(!temp.getCouleur().equals(this.joueur))
			erreur = "Ce n'est pas une pièce de votre couleur";						//Mauvais joueur
		if(temp.isOccupe())
			erreur = "Vous ne pouvez pas déplacer un socle qui porte la bille";		//Bille != Socle
		return erreur;
	}
	
	public void deplacer(String socle, char dest)
	{
		int i = socle.charAt(0)-'A';
		int j = Integer.parseInt(""+socle.charAt(1));
		
		int i2 = i;
		int j2 = j;
		
		switch(dest)
		{
			case 'N' :
			{
				i2--;
				break;
			}
			case 'E' :
			{
				j2++;
				break;
			}
			case 'S' :
			{
				i2++;
				break;
			}
			case 'O' :
			{
				j2--;
				break;
			}
		}
		
		grille[i2][j2].set(grille[i][j]);
		grille[i][j].detruire();
	}
	
	public void passer(String socle)
	{
		int i = socle.charAt(0)-'A';
		int j = Integer.parseInt(""+socle.charAt(1));
		
		getBille().passer();
		this.grille[i][j].passer();
	}
	
	public String deplacer(String socle, String dest)
	{		
		String erreur = "ok";
		
		if(!dest.matches("^[NESO]$")){
			erreur = "La saisie n'est pas valide. (N-E-S-O)";
			return erreur ;
		}

		if(dest.charAt(0)=='A')return erreur;
		
		int i = socle.charAt(0)-'A';
		int j = Integer.parseInt(""+socle.charAt(1));
		
		switch(dest.charAt(0))
		{
			case 'N' :
			{
				if(i-1<0)
					erreur = "Vous ne pouvez pas déplacer ce socle vers le Nord, il sortirait du tableau.";
				else if(!grille[i-1][j].getType().equals(""))
					erreur = "Vous ne pouvez pas déplacer ce socle vers le Nord, il y a déjà une pièce.";
				break;
			}
			case 'E' :
			{
				if(j+1>this.grille[0].length-1)
					erreur = "Vous ne pouvez pas déplacer ce socle vers l'Est, il sortirait du tableau.";
				else if(!grille[i][j+1].getType().equals(""))
					erreur = "Vous ne pouvez pas déplacer ce socle vers l'Est, il y a déjà une pièce.";
				break;
			}
			case 'S' :
			{
				if(i+1>this.grille.length-1)
					erreur = "Vous ne pouvez pas déplacer ce socle vers le Sud, il sortirait du tableau.";
				else if(!grille[i+1][j].getType().equals(""))
					erreur = "Vous ne pouvez pas déplacer ce socle vers le Sud, il y a déjà une pièce.";
				break;
			}
			case 'O' :
			{
				if(j-1<0)
					erreur = "Vous ne pouvez pas déplacer ce socle vers l'Ouest, il sortirait du tableau.";
				else if(!grille[i][j-1].getType().equals(""))
					erreur = "Vous ne pouvez pas déplacer ce socle vers l'Ouest, il y a déjà une pièce.";
				break;
			}
			default :
			{
				break;
			}
		}
		return erreur;
	}
	
	public void initialiserGrille(int mode)
	{
		if(mode == 3)
			this.init();
		else
			for(int i=0;i<this.grille.length;i++)									//Lignes
			{
				for(int j=0;j<this.grille[0].length;j++)							//Colonnes
				{
					if		(i==0)
					{
						this.grille[i][j] = new Case("Socle","Noir");
						if(j==3)
							this.grille[i][j].position();							//Bille sur la case
					}
					else if	(i==6)
					{
						this.grille[i][j] = new Case("Socle","Blanc");
						if(j==3)
							this.grille[i][j].position();
					}
					else
						this.grille[i][j] = new Case("","");
				}
			}
		
		if(mode==1)
		{
			Case 	temp = this.grille[0][0];
			this.grille[6][1].set(temp);
			this.grille[6][5].set(temp);
					temp = this.grille[6][0];
			this.grille[0][1].set(temp);
			this.grille[0][5].set(temp);
		}
	}
	
	public int voisin(String coul, int y, int x)
	{
		if(coul.equals("Blanc"))
			coul = "Noir";
		else
			coul = "Blanc";
		int cpt = 0;
		if(y>0)
		{
			if(this.grille[y-1][x].getCouleur().equals(coul))
			{
				cpt++;
			}
		}
			
		if(y<this.grille.length-1)
		{
			if(this.grille[y+1][x].getCouleur().equals(coul))
			{
				cpt++;
			}
		}				
		return cpt;
	}
	
	public boolean colles(String coul)
	{
		int cpt = 0;
		
		int y = 0;						//Coordonnées y du socle actif
		boolean trouve = false;
		
		for(int i=0;i<this.grille.length&&!trouve;i++)
		{
			if(this.grille[i][0].getCouleur().equals(coul))
			{
				y = i;
				trouve = true;
			}
		}
		
		cpt+=voisin(coul, y,0);
				
		for(int i=1;i<this.grille.length&&trouve;i++)
		{
			trouve = false;
			for(int j=y-1;j<y+2&&!trouve;j++)
			{
				if(j>-1&&j<this.grille.length)
				{
					if(this.grille[j][i].getCouleur().equals(coul))
					{
						y = j;
						trouve = true;
						cpt+=voisin(coul, y,i);
					}
				}
			}
		}
			
		if(cpt>2)
			return true;
		else 
			return false;
	}
	
	public boolean gagner()
	{
		int y = 0;						//Coordonnées y du socle actif
		String coul = "Blanc";
		boolean trouve = false;
		
		for(int test=0;test<2;test++)
		{
			for(int i=0;i<this.grille.length&&!trouve;i++)
			{
				if(this.grille[i][0].getCouleur().equals(coul))
				{
					y = i;
					trouve = true;
				}
			}
			if(trouve)					//Si on ne trouve pas de case du joueur Blanc on arrete le test pour le joueur.
			{
				for(int i=1;i<this.grille.length&&trouve;i++)
				{
					trouve = false;
					for(int j=y-1;j<y+2&&!trouve;j++)
					{
						if(j>-1&&j<this.grille.length)
						{
							if(this.grille[j][i].getCouleur().equals(coul))
							{
								y = j;
								trouve = true;
							}
						}
					}
				}
				
				if(trouve)
				{
					if(colles(coul))
					{
						System.out.println("GAGNE PAR LIGNE");
						return true;
					}
				}
			}
			coul = "Noir";
			trouve = false;
		}
		
		
		int xb = 0, yb = 0, xn = 0, yn = 0;							//xb = coordonnee x du joueur blanc ..
		for(int i=0;i<this.grille.length;i++)
		{
			for(int j=0;j<grille[0].length;j++)
			{
				if(this.grille[i][j].isOccupe() && this.grille[i][j].getCouleur().equals("Blanc"))
				{
					yb = i;
					xb = j;
				}
				if(this.grille[i][j].isOccupe() && this.grille[i][j].getCouleur().equals("Noir"))
				{
					yn = i;
					xn = j;
				}
			}
		}
		
		if(yb == 0 || yn == 6)
		{
			System.out.println("GAGNE PAR NORMAL");
			return true;
		}
		
		return false;
	}
	
	public Case[][] getGrille(){return this.grille;}
	public String getJoueur(){return this.joueur;}
	
	public void init(){
		String nomFichier = System.getProperty("user.dir");
		File fichier = new File(nomFichier + "/initialisation/init.txt");
		try {
			Scanner sc = new Scanner(fichier);
			int lignes = 0 ;
			while(sc.hasNextLine()){
				String ligne = sc.nextLine() ;
				String[] tab = ligne.split(":");
				for(int i = 0 ; i < 7 ; i++){

					if(tab[i].equals("sb"))
						this.grille[lignes][i] = new Case("Socle" , "Blanc") ;
					else
						if(tab[i].equals("sn"))
							this.grille[lignes][i] = new Case("Socle","Noir");
						else
							if(tab[i].equals("bn")){
								this.grille[lignes][i] = new Case("Socle" , "Noir") ;
								this.grille[lignes][i].position();
							}
							else
								if(tab[i].equals("bb")){
									this.grille[lignes][i] = new Case("Socle" , "Blanc") ;
									this.grille[lignes][i].position();
								}
								else
									this.grille[lignes][i] = new Case("","");
					
						
				
				}
				lignes++;
			}
			
			
			
		} catch (FileNotFoundException e) {
			System.out.println("fichier d'initialisation incorrect");
		}
	}
	public String toString()
	{
		String retour = " ";
		
		//Noms des colonnes
		for(int i=0;i<7;i++) // Taille d'affichage d'une case quelconque = 1(|)+5(type)+1(/)+5(couleur)+1(|) = 13
		{
			retour+=String.format("%7s", i);
			retour+=String.format("%6s", "");
		}
		retour+="\n ";
		
		//Separation des lignes
		for(int n=0;n<this.grille[0][0].toString().length()*7;n++) //Taille d'affichage d'une case quelconque * 7
		{
			retour += "_";
		}
		retour+="\n";
		
		for(int i=0;i<this.grille.length;i++)
		{
			//Temp = Noms des lignes A puis B puis C etc...
			char temp = (char)('A'+i);
			retour+=temp;
			for(int j=0;j<this.grille[0].length;j++)
			{
				retour+=this.grille[i][j];
			}
			retour+="\n ";
			
			//Separation des lignes
			for(int n=0;n<this.grille[0][0].toString().length()*7;n++)
			{
				retour += "_";
			}
			retour+="\n";
		}
		return retour;
	}
}
