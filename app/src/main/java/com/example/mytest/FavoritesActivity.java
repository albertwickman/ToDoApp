package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mytest.Adapter.FavoritesRecyclerViewAdapter;
import com.example.mytest.Adapter.RecyclerViewAdapter;
import com.example.mytest.Database.DatabaseHelper;
import com.example.mytest.Model.ItemModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements FavoritesRecyclerViewAdapter.ItemClickListener {
    List<ItemModel> favoritesList;
    DatabaseHelper db;
    RecyclerView recyclerView;
    FavoritesRecyclerViewAdapter favoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().hide();

        favoritesList = new ArrayList<>();
        db = new DatabaseHelper(this);
        db.openDatabase();

        recyclerView = findViewById(R.id.favoritesRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoritesAdapter = new FavoritesRecyclerViewAdapter(db, this);
        favoritesAdapter.setClickListener(this);
        recyclerView.setAdapter(favoritesAdapter);

        favoritesList = db.getFavorites();
        Collections.reverse(favoritesList);
        favoritesAdapter.setFavoritesList(favoritesList);

        System.out.println(db.getAllItems());
        System.out.println(db.getFavorites());
        System.out.println(db.getAllItems().get(0).isFavorite());

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}