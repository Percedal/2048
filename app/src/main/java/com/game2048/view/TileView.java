package com.game2048.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.game2048.R;
import com.game2048.core.Tile;
import com.game2048.core.util.Observable;
import com.game2048.core.util.Observer;


public class TileView extends TextView implements Observer {
	private static final int TEXT_SIZE = 40;
	private int GRID_SIZE;
	private int baseTextSize;
	
	public TileView(Context context, Tile tile) {
		super(context);
		GRID_SIZE = tile.getGrid().getWidth();
		baseTextSize = TEXT_SIZE * 5 / GRID_SIZE;
		setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
		params.setMargins(5, 5, 5, 5);
		setLayoutParams(params);
		setBackgroundResource(R.color.backgroundTileColor);
		setTextColor(context.getResources().getColor(R.color.textTileColor, null));
		setTextSize(baseTextSize);
		update(tile);
	}
	
	@Override
	public void update(Observable observable) {
		int val = ((Tile)observable).getValue();
		if(val != 0) {
			setText(String.valueOf(val));
			double l = Math.log(val) / Math.log(2);
			setTextSize((float) (baseTextSize - 2 * l));
		} else {
			setText("");
		}
	}
}
