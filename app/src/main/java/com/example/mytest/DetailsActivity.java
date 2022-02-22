package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);
        getSupportActionBar().hide();

        imageView = findViewById(R.id.detailsImageView);
        textview = findViewById(R.id.detailsTextView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String idText = bundle.getString("idText");
            textview.setText(idText);
            String idImage = bundle.getString("idImage");
            Glide.with(getApplicationContext()).load(idImage).into(imageView);
        }

    }
}