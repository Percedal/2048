package com.example.a2048;

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
	private int gridSize = 4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		btnGrid = findViewById(R.id.btnGrid);
		btnGrid.setOnClickListener(v -> {
			Intent mainActivity = new Intent(MenuActivity.this, MainActivity.class);
			startActivity(mainActivity);
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
		
		textGridSize = findViewById(R.id.textGridSize);
		
		updateGridSizeTextView();
	}
	
	private void updateGridSizeTextView() {
		if (gridSize < 3)
			gridSize = 3;
		else if (gridSize > 8)
			gridSize = 8;
		textGridSize.setText(gridSize + " x " + gridSize);
	}
}






