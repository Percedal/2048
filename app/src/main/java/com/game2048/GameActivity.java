package com.game2048;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;
import com.game2048.core.Grid;
import com.game2048.core.util.Observable;
import com.game2048.core.util.Observer;
import com.game2048.view.GridView;
import com.game2048.view.OnSwipeTouchListener;

import static com.game2048.core.util.EnumDirection.*;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends Activity implements Observer {
	private View gridView;
	private Grid grid;
	private TextView scoreView;
	private Chronometer timerView;
	//Temps de jeu en µs
	private long timeElapsed = 0;
	
	
	@Override
	protected void onResume() {
		timerView.setBase(SystemClock.elapsedRealtime() - timeElapsed);
		timerView.start();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		timerView.stop();
		timeElapsed = SystemClock.elapsedRealtime() - timerView.getBase();
		super.onPause();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		scoreView = findViewById(R.id.scoreView);
		scoreView.setText("0");
		timerView = findViewById(R.id.timerView);
		
		grid = new Grid(getIntent().getIntExtra("gridSize", 0));
		grid.addObserver(this);
		gridView = new GridView(this, grid);
		
		ViewGroup container = findViewById(R.id.gridContainer);
		container.removeAllViews();
		container.setOnTouchListener(new OnSwipeTouchListener(this) {
			public void onSwipeRight() {
				grid.swipe(EST);
			}
			
			public void onSwipeLeft() {
				grid.swipe(WEST);
			}
			
			public void onSwipeTop() {
				grid.swipe(NORTH);
			}
			
			public void onSwipeBottom() {
				grid.swipe(SOUTH);
			}
		});
		container.addView(gridView);
		
		findViewById(R.id.btnReload).setOnClickListener(v -> {
			grid.reset();
			timeElapsed = 0;
			timerView.setBase(SystemClock.elapsedRealtime());
		});
	}
	
	@Override
	public void update(Observable observable) {
		Grid g = (Grid) observable;
		if (g.isFull()) {
			this.finish();
			if (timeElapsed == 0)
				timeElapsed = SystemClock.elapsedRealtime() - timerView.getBase();
			Intent intent = new Intent(this, ScoreActivity.class);
			intent.putExtra("score", grid.getScore());
			intent.putExtra("timerInMilis", timeElapsed);
			intent.putExtra("biggestTile", grid.getValueBiggestTile());
			startActivity(intent);
		}
		scoreView.setText(String.valueOf(g.getScore()));
	}
}
