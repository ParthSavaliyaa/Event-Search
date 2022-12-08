package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
      ActivityMainBinding binding;
    ImageView ParthImg, SonikaImg, JashanImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParthImg = findViewById(R.id.ParthImg);
        SonikaImg = findViewById(R.id.SonikaImg);
        JashanImg = findViewById(R.id.JashanImg);

        ParthImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Parth_MainActivity.class));
            }
        });
        SonikaImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Sonika_Main_Activity.class));
            }
        });

        JashanImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, .class));
            }
        });

    }

}