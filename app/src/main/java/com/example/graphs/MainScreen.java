package com.example.graphs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {

    Button btn_adding_points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        btn_adding_points = (Button)findViewById(R.id.add_points);
        btn_adding_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent points = new Intent(MainScreen.this,MainActivity.class);
                startActivity(points);
            }
        });
    }
}
