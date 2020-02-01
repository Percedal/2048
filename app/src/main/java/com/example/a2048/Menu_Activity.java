package com.example.a2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Menu_Activity extends AppCompatActivity {
    
    private Button btnGrid = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_);
        
        btnGrid = findViewById(R.id.buttonGrid);
        
        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(Menu_Activity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }
}






