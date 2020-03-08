package com.game2048.core;

import com.game2048.core.util.EnumDirection;
import com.game2048.core.util.Observable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Grid extends Observable {
	private int width, height;
	private int score = 0;
	private Tile[][] grid;
	private Random rand = new Random();
	private boolean gridFull = false;
	
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
	
	/**
	 * La grille revient à un nouvel état de début de jeu.
	 */
	public void reset() {
		for (Tile[] cs : grid) {
			for (Tile c : cs) {
				c.reset();
			}
		}
		score = 0;
		spawn();
	}
	
	
	/**
	 * @return Retourne la valeur de la nouvelle tuile,
	 * 0 si aucune n'a pu apparaitre.
	 * Met à jour le score;
	 */
	public int spawn() {
		List<Tile> casesVides = casesVides();
		if (casesVides.isEmpty()) {
			gridFull = true;
			notifyObservers();
			return 0;
		}
		
		Tile caseAlea = casesVides.get(rand.nextInt(casesVides.size()));
		int val = caseAlea.spawn(rand);
		score += val;
		notifyObservers();
		return val;
	}
	
	public void swipe(EnumDirection direction) {
		int col, lig;
		switch (direction) {
			case EST:
				col = width - 1;
				for (lig = 0; lig < height; lig++) {
					grid[lig][col].swipe(direction);
				}
				break;
			case WEST:
				col = 0;
				for (lig = 0; lig < height; lig++) {
					grid[lig][col].swipe(direction);
				}
				break;
			case NORTH:
				lig = 0;
				for (col = 0; col < width; col++) {
					grid[lig][col].swipe(direction);
				}
				break;
			case SOUTH:
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
	
	public Tile getTileLigCol(int lig, int col) {
		return grid[lig][col];
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean isFull() {
		return gridFull;
	}
	
	public void updateScore(int val) {
		score += val;
		notifyObservers();
	}
}
