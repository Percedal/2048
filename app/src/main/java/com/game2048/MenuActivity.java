package com.game2048;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
	private Button btnGrid = null;
	private Button btnGridUp = null;
	private Button btnGridDown = null;
	private TextView textGridSize = null;
	private static int gridSize = 4;
	private int maxGridSize;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		maxGridSize = getResources().getInteger(R.integer.max_grid_size);
		
		btnGrid = findViewById(R.id.btnGrid);
		btnGrid.setOnClickListener(v -> {
			Intent intent = new Intent(MenuActivity.this, GameActivity.class);
			intent.putExtra("gridSize", gridSize);
			startActivity(intent);
		});
		
		btnGridUp = findViewById(R.id.btnGridSizeUp);
		btnGridUp.setOnClickListener(v -> {
			gridSize++;
			updateGridSizeTextView();
		});
		
		btnGridDown = findViewById(R.id.btnGridSizeDown);
		btnGridDown.setOnClickListener(v -> {
			gridSize--;
			updateGridSizeTextView();
		});
		
		//Init de la taille de grille choisie
		textGridSize = findViewById(R.id.textGridSize);
		updateGridSizeTextView();
	}
	
	private void updateGridSizeTextView() {
		if (gridSize < 3)
			gridSize = 3;
		else if (gridSize > maxGridSize)
			gridSize = maxGridSize;
		textGridSize.setText(gridSize + " x " + gridSize);
	}
}






