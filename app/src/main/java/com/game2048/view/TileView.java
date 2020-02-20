package com.game2048.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game2048.R;
import com.game2048.core.Tile;
import com.game2048.core.util.Observable;
import com.game2048.core.util.Observer;


public class TileView extends TextView implements Observer {
	public TileView(Context context, Tile tile) {
		super(context);
		setGravity(Gravity.CENTER);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
		params.setMargins(5, 5,5 ,5);
		setLayoutParams(params);
		setBackgroundResource(R.color.backgroundTileColor);
		setTextColor(context.getResources().getColor(R.color.textTileColor, null));
		setTextSize(50);
		update(tile);
	}
	
	@Override
	public void update(Observable observable) {
		int val = ((Tile)observable).getValue();
//		Color color;
//		switch (val) {
//			case 2:
//				color = Color.rgb();
//		}
		setText(val == 0 ? " " : String.valueOf(val));
	}
}
