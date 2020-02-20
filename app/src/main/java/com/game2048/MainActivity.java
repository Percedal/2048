package com.game2048;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.game2048.core.Grid;
import com.game2048.view.GridView;

import static com.game2048.SwipeDirectionEnum.*;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends Activity {
	private static final String DEBUG_TAG = "____DEBUG____";
	
	//	private boolean isSystemUIVisible;
	private View gridView;
	private Grid grid;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		grid = new Grid(getIntent().getIntExtra("gridSize", 0));
		gridView = new GridView(this, grid);
		
		ViewGroup container = findViewById(R.id.gridContainer);
		container.removeAllViews();
		container.setOnTouchListener(new OnSwipeTouchListener(this) {
			public void onSwipeRight() {
				Log.d(DEBUG_TAG, "Swipe Right !*---------");
				grid.swipe(EST);
			}

			public void onSwipeLeft() {
				Log.d(DEBUG_TAG, "Swipe Left !*---------");
				grid.swipe(WEST);
			}

			public void onSwipeTop() {
				Log.d(DEBUG_TAG, "Swipe Top !*---------");
				grid.swipe(NORTH);
			}

			public void onSwipeBottom() {
				Log.d(DEBUG_TAG, "Swipe Bottom !*---------");
				grid.swipe(SOUTH);
			}
		});
		container.addView(gridView);
		
		findViewById(R.id.btnUp).setOnClickListener(v -> grid.swipe(NORTH));
		findViewById(R.id.btnDown).setOnClickListener(v -> grid.swipe(SOUTH));
		findViewById(R.id.btnLeft).setOnClickListener(v -> grid.swipe(WEST));
		findViewById(R.id.btnRigth).setOnClickListener(v -> grid.swipe(EST));
		findViewById(R.id.btnReload).setOnClickListener(v -> grid.reset());
		findViewById(R.id.btnMenu).setOnClickListener(v -> {
			Intent mainActivity = new Intent(MainActivity.this, MenuActivity.class);
			startActivity(mainActivity);
		});
	}
	
	/**
	 * Listener des swipe
	 */
	private class OnSwipeTouchListener implements View.OnTouchListener {
		private final GestureDetector swipeGesture;
		
		public OnSwipeTouchListener(Context ctx) {
			swipeGesture = new GestureDetector(ctx, new SwipeListener());
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return swipeGesture.onTouchEvent(event);
		}

		public void onSwipeRight() {

		}

		public void onSwipeLeft() {

		}

		public void onSwipeTop() {

		}

		public void onSwipeBottom() {

		}
		
		/**
		 * Gestionnaire des swipe
		 */
		private final class SwipeListener extends GestureDetector.SimpleOnGestureListener {
			private static final int SWIPE_THRESHOLD = 100;
			private static final int SWIPE_VELOCITY_THRESHOLD = 100;
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				Log.d("Swipe", "Swing !");
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
