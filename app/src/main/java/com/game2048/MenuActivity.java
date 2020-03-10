package com.game2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.game2048.view.OnSwipeTouchListener;

public class MenuActivity extends AppCompatActivity {
	private TextView textGridSize = null;
	private static int gridSize = 4;
	private int MAX_GRID_SIZE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		MAX_GRID_SIZE = getResources().getInteger(R.integer.max_grid_size);

		findViewById(R.id.btnGridSizeUp).setOnClickListener(v -> {
			gridSize++;
			updateGridSizeView();
		});

		findViewById(R.id.btnGridSizeDown).setOnClickListener(v -> {
			gridSize--;
			updateGridSizeView();
		});

		View container = findViewById(R.id.gridPreviewContainer);
		container.setOnTouchListener(new OnSwipeTouchListener(this) {
			@Override
			public void onSwipeRight() {
				gridSize--;
				updateGridSizeView();
			}

			@Override
			public void onSwipeLeft() {
				gridSize++;
				updateGridSizeView();
			}

			@Override
			public void onSwipeTop() {
				return;
			}

			@Override
			public void onSwipeBottom() {
				return;
			}
		});

		findViewById(R.id.btnLaunchGame).setOnClickListener(v -> {
			Intent intent = new Intent(MenuActivity.this, GameActivity.class);
			intent.putExtra("gridSize", gridSize);
			startActivity(intent);
		});
		
		//Init de la taille de grille choisie
		textGridSize = findViewById(R.id.textGridSize);
		updateGridSizeView();
	}
	
	private void updateGridSizeView() {
		if (gridSize < 3)
			gridSize = 3;
		else if (gridSize > MAX_GRID_SIZE)
			gridSize = MAX_GRID_SIZE;
		textGridSize.setText(gridSize + " x " + gridSize);
	}
}






