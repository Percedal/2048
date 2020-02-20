package com.game2048.view;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import com.game2048.R;
import com.game2048.core.Grid;
import com.game2048.core.Tile;

public class GridView extends LinearLayout {
	public GridView(Context context, Grid grid) {
		super(context);
		setOrientation(VERTICAL);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setBackgroundResource(R.color.backgroundGridColor);
		for (int lig = 0; lig < grid.getHeight(); lig++) {
			LinearLayout line = new LinearLayout(getContext());
			line.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
			for (int col = 0; col < grid.getWidth(); col++) {
				Tile t = grid.getCaseLigCol(lig, col);
				TileView v = new TileView(getContext(), t);
				t.addObserver(v);
				line.addView(v);
			}
			addView(line);
		}
	}
}
