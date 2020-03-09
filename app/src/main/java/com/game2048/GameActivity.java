package com.game2048;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
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
			Log.d("Grille", "You Lose");
		}
		scoreView.setText(String.valueOf(g.getScore()));
	}
	
	/**
	 * Listener des swipe
	 */
	private abstract class OnSwipeTouchListener implements View.OnTouchListener {
		private final GestureDetector swipeGesture;
		
		public OnSwipeTouchListener(Context ctx) {
			swipeGesture = new GestureDetector(ctx, new SwipeListener());
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return swipeGesture.onTouchEvent(event);
		}
		
		abstract public void onSwipeRight();
		
		abstract public void onSwipeLeft();
		
		abstract public void onSwipeTop();
		
		abstract public void onSwipeBottom();
		
		/**
		 * Gestionnaire des swipe
		 */
		private final class SwipeListener extends GestureDetector.SimpleOnGestureListener {
			private static final int SWIPE_THRESHOLD = 100;
			private static final int SWIPE_VELOCITY_THRESHOLD = 100;
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				boolean result = false;
				try {
					float diffY = e2.getY() - e1.getY();
					float diffX = e2.getX() - e1.getX();
					if (Math.abs(diffX) > Math.abs(diffY)) {
						if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
							if (diffX > 0) {
								onSwipeRight();
							} else {
								onSwipeLeft();
							}
							result = true;
						}
					} else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
						if (diffY > 0) {
							onSwipeBottom();
						} else {
							onSwipeTop();
						}
						result = true;
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				return result;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				return true;
			}
		}
	}
}
