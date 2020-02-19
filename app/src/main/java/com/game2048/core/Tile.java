package com.game2048.core;

import com.game2048.SwipeDirectionEnum;

import java.util.Observable;
import java.util.Random;

public class Tile extends Observable {
	private final Grid grid;
	private int val = 0;
	private int x, y;
	private Tile north, south, est, west;
	
	
	public Tile(Grid _grid, int x, int y) {
		this.grid = _grid;
		this.x = x;
		this.y = y;
		this.val = 0;

//		view.setGravity(Gravity.CENTER);
//		view.setTextSize(50);
//		ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
//		margins.setMargins(5, 5, 5, 5);
//		LayoutParams params = new LayoutParams(
//				LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT
//		);
//		params.setMargins(left, top, right, bottom);
//		view.setLayoutParams(params);
//		view.getLayoutParams().

//		view.setBackgroundColor(view.getContext().getResources().getColor(R.color.backgroundTileColor, null));
//		view.setTextColor(view.getContext().getResources().getColor(R.color.textTileColor, null));
//		updateView();
	}
	
	public void updateNeibourgh() {
		this.north = y == 0 ? null : grid.getCaseLigCol(y - 1, x);
		this.south = y == grid.getHeight() - 1 ? null : grid.getCaseLigCol(y + 1, x);
		this.west = x == 0 ? null : grid.getCaseLigCol(y, x - 1);
		this.est = x == grid.getWidth() - 1 ? null : grid.getCaseLigCol(y, x + 1);
	}
	
	/**
	 * Une case est vide si elle contient la valeur 0
	 *
	 * @return Retour Vrai si la case contient 0, Faux sinon
	 */
	public boolean isEmpty() {
		return val == 0;
	}
	
	public void spawn(Random rand) {
		val = (rand.nextInt(2) + 1) * 2;
		notifyObservers();
	}
	
	public int getValue() {
		return val;
	}
	
	public void reset() {
		val = 0;
		notifyObservers();
	}
	
	/**
	 * La case effectue un swipe dans la direction donnée.
	 * Lorsqu'une case effectue un swipe, elle notifie les autres cases directement adjascentes
	 * (appel de la méthode swipe)
	 *
	 * @param direction Direction dans laquelle effectuer le swipe
	 */
	public void swipe(SwipeDirectionEnum direction) {
		switch (direction) {
			case EST:
				if (est != null && !this.isEmpty()) {
					//Cherche la tuile non vide la plus proche sur la droite
					Tile c = est;
					while (c.isEmpty()) {
						if (c.est == null)
							break;
						c = c.est;
					}
					
					//si la prochaine case a droite possede la meme valeur, fusionne les cases
					if (c.val == this.val) {
						merge(c);
					}
					//sinon deplace la tuile le plus a droite possible
					else if (!c.isEmpty() && c.west != this) {
						swap(c.west);
					} else if (c.isEmpty()) {
						swap(c);
					}
				}
				if (west != null)
					west.swipe(direction);
				break;
			case WEST:
				if (west != null && !this.isEmpty()) {
					//Cherche la tuile non vide la plus proche sur la gauche
					Tile c = west;
					while (c.isEmpty()) {
						if (c.west == null)
							break;
						c = c.west;
					}
					
					//si la prochaine case a gauche possede la meme valeur, fusionne les cases
					if (c.val == this.val) {
						merge(c);
					}
					//sinon deplace la tuile le plus a gauche possible
					else if (!c.isEmpty() && c.west != this) {
						swap(c.est);
					} else if (c.isEmpty()) {
						swap(c);
					}
				}
				if (est != null)
					est.swipe(direction);
				break;
			case NORTH:
				if (north != null && !this.isEmpty()) {
					//Cherche la tuile non vide la plus proche vers le haut
					Tile c = north;
					while (c.isEmpty()) {
						if (c.north == null)
							break;
						c = c.north;
					}
					
					//si la prochaine case vers le haut possede la meme valeur, fusionne les cases
					if (c.val == this.val) {
						merge(c);
					}
					//sinon deplace la tuile le plus a droite possible
					else if (!c.isEmpty() && c.south != this) {
						swap(c.south);
					} else if (c.isEmpty()) {
						swap(c);
					}
				}
				if (south != null)
					south.swipe(direction);
				break;
			case SOUTH:
				if (south != null && !this.isEmpty()) {
					//Cherche la tuile non vide la plus proche vers le bas
					Tile c = south;
					while (c.isEmpty()) {
						if (c.south == null)
							break;
						c = c.south;
					}
					
					//si la prochaine case vers le bas possede la meme valeur, fusionne les cases
					if (c.val == this.val) {
						merge(c);
					}
					//sinon deplace la tuile le plus a droite possible
					else if (!c.isEmpty() && c.north != this) {
						swap(c.north);
					} else if (c.isEmpty()) {
						swap(c);
					}
				}
				if (north != null)
					north.swipe(direction);
				break;
		}
		notifyObservers();
	}
	
	/**
	 * La case (this) echange de valeur avec celle d'arrivée (donnée),
	 *
	 * @param c La case avec laquelle this change de valeur
	 */
	private void swap(Tile c) {
		int tempVal = this.val;
		this.val = c.val;
		c.val = tempVal;
	}
	
	/**
	 * La case (this) fusionne dans la case d'arrivée (donnée)
	 * dans l'unique cas ou les deux case ont la meme valeur.
	 * this est alors vide (valeur 0)
	 *
	 * @param c La case avec laquelle this fusionne
	 */
	private void merge(Tile c) {
		if (this.val == c.val) {
			c.val *= 2;
			this.val = 0;
		}
	}
	
	@Override
	public String toString() {
		return "Tile{" +
				"grille=" + grid +
				", valeur=" + val +
				", x=" + x +
				", y=" + y +
				", nord=" + north +
				", sud=" + south +
				", est=" + est +
				", ouest=" + west +
				'}';
	}
}
