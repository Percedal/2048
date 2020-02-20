package com.game2048.view;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import com.game2048.R;
import com.game2048.core.Tile;
import com.game2048.core.util.Observable;
import com.game2048.core.util.Observer;


public class TileView extends TextView implements Observer {
	Tile tile = null;

	public TileView(Context context, Tile tile) {
		super(context);
		this.tile = tile;
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
//		setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		setBackgroundResource(R.color.backgroundTileColor);
		setTextColor(context.getResources().getColor(R.color.textTileColor, null));
		setTextSize(50);
		setText(tile.getValue() + "");
	}
	
	@Override
	public void update(Observable observable) {
		Log.d("Observer", "Listener");
		setText(tile.getValue()+"");
	}
}
