package com.example.a2048;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    
    private Button btnGrid = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        
        btnGrid = findViewById(R.id.buttonGrid);
        
        btnGrid.setOnClickListener(v -> {
            Intent mainActivity = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(mainActivity);
        });
    }
}






