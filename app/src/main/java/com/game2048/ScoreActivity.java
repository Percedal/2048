package com.game2048;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		
		Intent intent = getIntent();
		
		int score = intent.getIntExtra("score", 0);
		long timer = intent.getLongExtra("timerInMilis", 1);
		
		findViewById(R.id.btnMenu).setOnClickListener(v -> finish());
		((TextView) findViewById(R.id.scoreBox)).setText("" + score);
		((TextView) findViewById(R.id.timerBox)).setText("" + timer);
		((TextView) findViewById(R.id.finalScoreBox)).setText("" + Math.round(score * Math.log10(timer)));
	}
}
