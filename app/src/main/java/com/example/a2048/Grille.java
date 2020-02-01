package com.example.a2048;

import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class Grille {
	private Case[][] grille;
	private LinearLayout[] lignes;
	private Random rand = new Random();
	
	
	/**
	 * Créé uen nouvelle grille carrée
	 *
	 * @param taille Hauteur et Largeur de la grille a créer
	 */
	public Grille(final int taille, LinearLayout grille) {
		this.grille = new Case[taille][taille];
		int len = grille.getChildCount();
		lignes = new LinearLayout[len];
		for (int i = 0; i < len; i++) {
//			Log.d("____DEBUG", String.valueOf(grille.getChildAt(i).getClass()));
			lignes[i] = (LinearLayout) grille.getChildAt(i);
			for (int j = 0; j < lignes[i].getChildCount(); j++) {
				this.grille[i][j] = new Case(j, i, (TextView) lignes[i].getChildAt(j));
			}
		}
	}
	
	
	public void spawn() {
		int randLigne = rand.nextInt(lignes.length);
		int randCol = rand.nextInt(lignes[randLigne].getChildCount());
		
		Case caseAlea = grille[randCol][randLigne];
		if (caseAlea.estVide())
			caseAlea.spawn(rand);
	}
}
