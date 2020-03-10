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

		int biggestTile = intent.getIntExtra("biggestTile", 1);
		int score = intent.getIntExtra("score", 0);
		long time = intent.getLongExtra("timerInMilis", 1);
		long min = time/60000;
		String strMin = min > 9 ? String.valueOf(min) : "0"+min;
		long sec = (time/1000)%60;
		String strSec = sec > 9 ? String.valueOf(sec) : "0"+sec;
		
		findViewById(R.id.btnMenu).setOnClickListener(v -> finish());
		((TextView) findViewById(R.id.scoreBox)).setText("" + score);
		((TextView) findViewById(R.id.timerBox)).setText(strMin+":"+strSec);
		((TextView) findViewById(R.id.finalScoreBox)).setText("" + Math.round(score / Math.log10(time) * Math.log(biggestTile)/Math.log(2)));
	}
}
