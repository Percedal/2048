package com.example.a2048;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends Activity {
	private static final String DEBUG_TAG = "____DEBUG____";
	
	//	private boolean isSystemUIVisible;
	private View mContentView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		mContentView = findViewById(R.id.grille);
		mContentView.setOnTouchListener(new TouchListener(this));
	}
	
	public void onSwipeRight() {
		Log.d(DEBUG_TAG, "Swipe Right !*---------");
	}
	
	public void onSwipeLeft() {
		Log.d(DEBUG_TAG, "Swipe Left !*---------");
	}
	
	public void onSwipeTop() {
		Log.d(DEBUG_TAG, "Swipe Top !*---------");
	}
	
	public void onSwipeBottom() {
		Log.d(DEBUG_TAG, "Swipe Bottom !*---------");
	}
	
	/**
	 * Listener des swipe
	 */
	private final class TouchListener implements View.OnTouchListener {
		private final GestureDetector swipeGesture;
		
		public TouchListener(Context ctx) {
			swipeGesture = new GestureDetector(ctx, new SwipeManager());
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return swipeGesture.onTouchEvent(event);
		}
		
		/**
		 * Gestionnaire des swipe
		 */
		private final class SwipeManager extends GestureDetector.SimpleOnGestureListener {
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
		}
	}
	
	
}
