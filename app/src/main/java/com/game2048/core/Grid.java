package com.game2048.core;

import android.util.Log;
import com.game2048.SwipeDirectionEnum;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Grid {
	private int width, height;
	private Tile[][] grid;
	private Random rand = new Random();
	
	/**
	 * Créé uen nouvelle grille carrée
	 *
	 * @param taille Hauteur et Largeur de la grille a créer
	 */
	public Grid(final int taille) {
		this.width = taille;
		this.height = taille;
		this.grid = new Tile[height][width];
		
		for (int lig = 0; lig < taille; lig++) {
			for (int col = 0; col < taille; col++) {
				grid[lig][col] = new Tile(this, col, lig);
			}
		}
		
		for (Tile[] ts : grid) {
			for (Tile t : ts) {
				t.updateNeibourgh();
			}
		}
		
		spawn();
	}
	
	
	public void reset() {
		for (Tile[] cs : grid) {
			for (Tile c : cs) {
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
		List<Tile> casesVides = casesVides();
		if (casesVides.isEmpty())
			return false;
		
		Tile caseAlea = casesVides.get(rand.nextInt(casesVides.size()));
		
		caseAlea.spawn(rand);
		return true;
	}
	
	public void swipe(SwipeDirectionEnum direction) {
		int col, lig;
		switch (direction) {
			case EST:
				Log.d("swipeGrille", "Swipe droit !");
				col = width - 1;
				for (lig = 0; lig < height; lig++) {
					grid[lig][col].swipe(direction);
				}
				break;
			case WEST:
				Log.d("swipeGrille", "Swipe gauche !");
				col = 0;
				for (lig = 0; lig < height; lig++) {
					grid[lig][col].swipe(direction);
				}
				break;
			case NORTH:
				Log.d("swipeGrille", "Swipe haut !");
				lig = 0;
				for (col = 0; col < width; col++) {
					grid[lig][col].swipe(direction);
				}
				break;
			case SOUTH:
				Log.d("swipeGrille", "Swipe bas !");
				lig = height - 1;
				for (col = 0; col < width; col++) {
					grid[lig][col].swipe(direction);
				}
				break;
		}
		spawn();
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public Tile getCaseLigCol(int lig, int col) {
		return grid[lig][col];
	}
	
	private List<Tile> casesVides() {
		List<Tile> lst = new LinkedList<>();
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				Tile c = grid[i][j];
				if (c.isEmpty())
					lst.add(c);
			}
		return lst;
	}
}
