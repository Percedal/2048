package com.game2048.view;

import android.content.Context;
import android.widget.LinearLayout;
import com.game2048.R;
import com.game2048.core.Grid;

public class GridView extends LinearLayout {
	public GridView(Context context, Grid grid) {
		super(context);
		setOrientation(VERTICAL);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		setBackgroundResource(R.color.backgroundGridColor);
		for (int lig = 0; lig < grid.getHeight(); lig++) {
			LinearLayout line = new LinearLayout(getContext());
//			line.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			for (int col = 0; col < grid.getWidth(); col++)
				line.addView(new TileView(getContext(), grid.getCaseLigCol(lig, col)));
			addView(line);
		}
	}
}
