package com.example.a2048;

import android.widget.TextView;

import java.util.Random;

public class Case {
	private final Grille grille;
	private int valeur = 0;
	private int x, y;
	private TextView view;
	private Case nord, sud, est, ouest;
	
	
	public Case(Grille _grille, int x, int y, TextView _view) {
		this.grille = _grille;
		this.x = x;
		this.y = y;
		this.valeur = 0;
		this.view = _view;


//		this.nord = y == 0 ? null : grille.getCaseLigCol(y-1, x);
//		this.sud = y == grille.getHauteur()-1 ? null : grille.getCaseLigCol(y+1, x);
//		this.ouest = x == 0 ? null : grille.getCaseLigCol(y, x-1);
//		this.est = x == grille.getLargeur()-1 ? null : grille.getCaseLigCol(y, x+1);

//		view.setGravity(Gravity.CENTER);
		view.setTextSize(50);
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
		updateView();
	}
	
	public void updateVoisins() {
		this.nord = y == 0 ? null : grille.getCaseLigCol(y - 1, x);
		this.sud = y == grille.getHauteur() - 1 ? null : grille.getCaseLigCol(y + 1, x);
		this.ouest = x == 0 ? null : grille.getCaseLigCol(y, x - 1);
		this.est = x == grille.getLargeur() - 1 ? null : grille.getCaseLigCol(y, x + 1);
	}
	
	/**
	 * Met a jour le texte de du textView associé à la case
	 */
	public void updateView() {
		String text = " ";
		if (valeur != 0) {
			text = String.valueOf(valeur);
		}
		view.setText(text);
	}
	
	/**
	 * Une case est vide si elle contient la valeur 0
	 *
	 * @return Retour Vrai si la case contient 0, Faux sinon
	 */
	public boolean estVide() {
		return valeur == 0;
	}
	
	public void spawn(Random rand) {
		valeur = (rand.nextInt(2) + 1) * 2;
		updateView();
	}
	
	public void reset() {
		valeur = 0;
		updateView();
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
				if (est != null && !this.estVide()) {
					//Cherche la tuile non vide la plus proche sur la droite
					Case c = est;
					while (c.estVide()) {
						if (c.est == null)
							break;
						c = c.est;
					}
					
					//si la prochaine case a droite possede la meme valeur, fusionne les cases
					if (c.valeur == this.valeur) {
						fusionner(c);
					}
					//sinon deplace la tuile le plus a droite possible
					else if (!c.estVide() && c.ouest != this) {
						swap(c.ouest);
					} else if (c.estVide()) {
						swap(c);
					}
				}
				if (ouest != null)
					ouest.swipe(direction);
				break;
			case OUEST:
				if (ouest != null && !this.estVide()) {
					//Cherche la tuile non vide la plus proche sur la gauche
					Case c = ouest;
					while (c.estVide()) {
						if (c.ouest == null)
							break;
						c = c.ouest;
					}
					
					//si la prochaine case a gauche possede la meme valeur, fusionne les cases
					if (c.valeur == this.valeur) {
						fusionner(c);
					}
					//sinon deplace la tuile le plus a gauche possible
					else if (!c.estVide() && c.ouest != this) {
						swap(c.est);
					} else if (c.estVide()) {
						swap(c);
					}
				}
				if (est != null)
					est.swipe(direction);
				break;
			case NORD:
				if (nord != null && !this.estVide()) {
					//Cherche la tuile non vide la plus proche vers le haut
					Case c = nord;
					while (c.estVide()) {
						if (c.nord == null)
							break;
						c = c.nord;
					}
					
					//si la prochaine case vers le haut possede la meme valeur, fusionne les cases
					if (c.valeur == this.valeur) {
						fusionner(c);
					}
					//sinon deplace la tuile le plus a droite possible
					else if (!c.estVide() && c.sud != this) {
						swap(c.sud);
					} else if (c.estVide()) {
						swap(c);
					}
				}
				if (sud != null)
					sud.swipe(direction);
				break;
			case SUD:
				if (sud != null && !this.estVide()) {
					//Cherche la tuile non vide la plus proche vers le bas
					Case c = sud;
					while (c.estVide()) {
						if (c.sud == null)
							break;
						c = c.sud;
					}
					
					//si la prochaine case vers le bas possede la meme valeur, fusionne les cases
					if (c.valeur == this.valeur) {
						fusionner(c);
					}
					//sinon deplace la tuile le plus a droite possible
					else if (!c.estVide() && c.nord != this) {
						swap(c.nord);
					} else if (c.estVide()) {
						swap(c);
					}
				}
				if (nord != null)
					nord.swipe(direction);
				break;
		}
		updateView();
	}
	
	/**
	 * La case (this) echange de valeur avec celle d'arrivée (donnée),
	 *
	 * @param c La case avec laquelle this change de valeur
	 */
	private void swap(Case c) {
		int tempVal = this.valeur;
		this.valeur = c.valeur;
		c.valeur = tempVal;
	}
	
	/**
	 * La case (this) fusionne dans la case d'arrivée (donnée)
	 * dans l'unique cas ou les deux case ont la meme valeur.
	 * this est alors vide (valeur 0)
	 *
	 * @param c La case avec laquelle this fusionne
	 */
	private void fusionner(Case c) {
		if (this.valeur == c.valeur) {
			c.valeur *= 2;
			this.valeur = 0;
		}
	}
	
	@Override
	public String toString() {
		return "Case{" +
				"grille=" + grille +
				", valeur=" + valeur +
				", x=" + x +
				", y=" + y +
				", nord=" + nord +
				", sud=" + sud +
				", est=" + est +
				", ouest=" + ouest +
				'}';
	}
}
