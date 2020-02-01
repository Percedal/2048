package com.example.a2048;

import android.widget.TextView;

import java.util.Random;

public class Case {
	private int valeur = 0;
	private int x, y;
	private TextView view;
	private Case nord, sud, est, ouest;
	
	
	public Case(int x, int y, TextView _view) {
		this.x = x;
		this.y = y;
		this.valeur = 0;
		this.view = _view;

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
		valeur = (rand.nextInt(1) + 1) * 2;
		updateView();
	}
}
