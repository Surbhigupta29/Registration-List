package com.example.registrationlist;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button DisplayButton;
    Button CreatePageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayButton = (Button)findViewById(R.id.DisplayButton);
        CreatePageButton = (Button)findViewById(R.id.CreatePageButton);

        CreatePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateResidentsActivity.class);
                startActivity(intent);
            }
        });
        DisplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowResidentDetailsActivity.class);
                    startActivity(intent);
            }
        });
    }
}
