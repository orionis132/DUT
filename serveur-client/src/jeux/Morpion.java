/*
 * Charles Rouillard
 * Charles Poure
 * Tom Etienne 
 * Florient Ratel
 * Clement Toulgoat 
 * Youssef Zerhouni
 */
package jeux;


import java.util.*;

public class Morpion
{
	private final int TAILLE = 3;
	private char[][] morpion;
	private boolean joueur1;
	private String[] joueurs ;
	private int scoreJ1 , scoreJ2 ;
	
	public Morpion(String[] joueurs)
	{
		this.joueurs = joueurs ;
		this.morpion = new char[TAILLE][TAILLE];
		this.initialiser();
		this.scoreJ1 = 0 ;
		this.scoreJ2 = 0 ;
	}
	
	public String afficher()
	{
		String s = "";
		
		for(int i=0;i<TAILLE;i++)
		{			
			s += "+---+---+---+\n";
			
			for(int j=0;j<TAILLE;j++)
			{
				
				s += "| " + morpion[i][j] + " ";
			}
			s += "|\n";		
		}
		s += "+---+---+---+\n";
		return s;
	}
	public void ajouterPoint(){
		if(this.joueur1)
			scoreJ1++ ;
		else
			scoreJ2++ ;
	}
	
	public void initialiser()
	{
		for(int i=0;i<TAILLE;i++)
			for(int j=0;j<TAILLE;j++)
				this.morpion[i][j] = ' ';
		this.joueur1 = false;
	}
	
	public char getSymbole(int x, int y)
	{
		return morpion[x][y];
	}
	
	public void changerJoueur()
	{
		this.joueur1 = !this.joueur1;
	}
	
	public boolean placerPiece(int i, int j)
	{
		if(i < 3 && i >= 0 && j<3 && j>=0)
			if(this.morpion[i][j] == ' ')
			{
				if(this.joueur1)
					this.morpion[i][j] = 'O';
				else
					this.morpion[i][j] = 'X';
				return true;
			}
		return false;

	}
	public boolean jouer(String action){
		boolean retour = false ;
		if(action.matches("^[0-2] [0-2]$")){
		Scanner sc = new Scanner(action) ;
		retour = placerPiece(sc.nextInt(), sc.nextInt()) ;
		}
		return retour ;
		
	}
	public String getJoueur1(){
		return joueurs[0];
	}
	public String getJoueur2(){
		return joueurs[1];
	}
	
	public int getJoueur()
	{
		if(this.joueur1)
			return 1;
		return 2;
	}
	
	public boolean estRempli()
	{
		for(int i=0;i<TAILLE;i++)
			for(int j=0;j<TAILLE;j++)
				if(this.morpion[i][j] == ' ')
					return false;
		return true;
	}
	
	public boolean aGagner()
	{
		char symbole = 'X';
		if(this.joueur1)
			 symbole = 'O';
		for(int x=0;x<3;x++)
		{
			if(this.morpion[0][x] == symbole && this.morpion[1][x] == symbole && this.morpion[2][x] == symbole)
				return true;
			if(this.morpion[x][0] == symbole && this.morpion[x][1] == symbole && this.morpion[x][2] == symbole)
				return true;
		}
		if(this.morpion[0][0] == symbole && this.morpion[1][1] == symbole && this.morpion[2][2] == symbole)
			return true;
		if(this.morpion[2][0] == symbole && this.morpion[1][1] == symbole && this.morpion[0][2] == symbole)
			return true;
		return false;
	}

	public int getScoreJ1() {
		return scoreJ1;
	}

	public void setScoreJ1(int scoreJ1) {
		this.scoreJ1 = scoreJ1;
	}

	public int getScoreJ2() {
		return scoreJ2;
	}

	public void setScoreJ2(int scoreJ2) {
		this.scoreJ2 = scoreJ2;
	}
}
