package com.game2048.view;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import com.game2048.R;
import com.game2048.core.Tile;

import java.util.Observable;
import java.util.Observer;

public class TileView extends TextView implements Observer {
	public TileView(Context context, Tile tile) {
		super(context);
		setGravity(Gravity.CENTER);

//		ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) getLayoutParams();
//		margins.setMargins(5, 5, 5, 5);
//		LayoutParams params = new LayoutParams(
//				LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT
//		);
//		params.setMargins(left, top, right, bottom);
//		view.setLayoutParams(params);
//		view.getLayoutParams().
		setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		setBackgroundResource(R.color.backgroundTileColor);
		setTextColor(context.getResources().getColor(R.color.textTileColor, null));
		setTextSize(50);
		setText(tile.getValue() + "");
		tile.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		setText(((Tile) o).getValue());
	}
}
