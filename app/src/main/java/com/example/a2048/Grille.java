package com.example.a2048;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Grille {
	private int largeur, hauteur;
	private Case[][] grille;
	private LinearLayout[] lignes;
	private Random rand = new Random();
	
	/**
	 * Créé uen nouvelle grille carrée
	 *
	 * @param taille Hauteur et Largeur de la grille a créer
	 */
	public Grille(final int taille, LinearLayout layout) {
		this.largeur = taille;
		this.hauteur = taille;
		this.grille = new Case[hauteur][largeur];
		int len = layout.getChildCount();
		lignes = new LinearLayout[len];
		for (int lig = 0; lig < len; lig++) {
			lignes[lig] = (LinearLayout) layout.getChildAt(lig);
			for (int col = 0; col < lignes[lig].getChildCount(); col++) {
				this.grille[lig][col] = new Case(this, col, lig, (TextView) lignes[lig].getChildAt(col));
			}
		}
		
		for (Case[] cs : grille) {
			for (Case c : cs) {
				c.updateVoisins();
			}
		}
		
		spawn();
	}
	
	
	public void reset() {
		for (Case[] cs : grille) {
			for (Case c : cs) {
				c.reset();
			}
		}
		spawn();
	}
	
	
	/**
	 * @return Retourne Vrai si au moins une nouvelle tuile a pu apparaitre.
	 * Retourne faux si plus aucune place de dispo pour faire apparaitre de nouvelle tuile.
	 */
	public boolean spawn() {
		List<Case> casesVides = casesVides();
		if (casesVides.isEmpty())
			return false;
		
		Case caseAlea = casesVides.get(rand.nextInt(casesVides.size()));
		
		caseAlea.spawn(rand);
		return true;
	}
	
	public void swipe(SwipeDirectionEnum direction) {
		int col, lig;
		switch (direction) {
			case EST:
				Log.d("swipeGrille", "Swipe droit !");
				col = largeur - 1;
				for (lig = 0; lig < hauteur; lig++) {
					grille[lig][col].swipe(direction);
				}
				break;
			case OUEST:
				Log.d("swipeGrille", "Swipe gauche !");
				col = 0;
				for (lig = 0; lig < hauteur; lig++) {
					grille[lig][col].swipe(direction);
				}
				break;
			case NORD:
				Log.d("swipeGrille", "Swipe haut !");
				lig = 0;
				for (col = 0; col < largeur; col++) {
					grille[lig][col].swipe(direction);
				}
				break;
			case SUD:
				Log.d("swipeGrille", "Swipe bas !");
				lig = hauteur - 1;
				for (col = 0; col < largeur; col++) {
					grille[lig][col].swipe(direction);
				}
				break;
		}
		spawn();
	}
	
	public int getHauteur() {
		return hauteur;
	}
	
	public int getLargeur() {
		return largeur;
	}
	
	public Case getCaseLigCol(int lig, int col) {
		return grille[lig][col];
	}
	
	private List<Case> casesVides() {
		List<Case> lst = new LinkedList<>();
		for (int i = 0; i < hauteur; i++)
			for (int j = 0; j < largeur; j++) {
				Case c = grille[i][j];
				if (c.estVide())
					lst.add(c);
			}
		return lst;
	}
}
