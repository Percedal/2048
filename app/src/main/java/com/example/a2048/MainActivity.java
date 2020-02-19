package com.example.a2048;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import static com.example.a2048.SwipeDirectionEnum.*;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends Activity {
	private static final String DEBUG_TAG = "____DEBUG____";
	
	//	private boolean isSystemUIVisible;
	private View gridView;
	private Grille grille;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		gridView = findViewById(R.id.grille);
		gridView.setOnTouchListener(new TouchListener(this));
		
		grille = new Grille(4, (LinearLayout) findViewById(R.id.grille));
		
		findViewById(R.id.btnUp).setOnClickListener(v -> grille.swipe(NORD));
		findViewById(R.id.btnDown).setOnClickListener(v -> grille.swipe(SUD));
		findViewById(R.id.btnLeft).setOnClickListener(v -> grille.swipe(OUEST));
		findViewById(R.id.btnRigth).setOnClickListener(v -> grille.swipe(EST));
		findViewById(R.id.btnReload).setOnClickListener(v -> grille.reset());
		findViewById(R.id.btnMenu).setOnClickListener(v -> {
			Intent mainActivity = new Intent(MainActivity.this, MenuActivity.class);
			startActivity(mainActivity);
		});
	}
	
	public void onSwipeRight() {
		Log.d(DEBUG_TAG, "Swipe Right !*---------");
		grille.swipe(EST);
	}
	
	public void onSwipeLeft() {
		Log.d(DEBUG_TAG, "Swipe Left !*---------");
		grille.swipe(OUEST);
	}
	
	public void onSwipeTop() {
		Log.d(DEBUG_TAG, "Swipe Top !*---------");
		grille.swipe(NORD);
	}
	
	public void onSwipeBottom() {
		Log.d(DEBUG_TAG, "Swipe Bottom !*---------");
		grille.swipe(SUD);
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
