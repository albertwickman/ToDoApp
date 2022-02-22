package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.mytest.Adapter.RecyclerViewAdapter;
import com.example.mytest.Database.DatabaseHelper;
import com.example.mytest.Model.ItemModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.file.FileSystemLoopException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, DialogCloseListener{

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<ItemModel> itemsList;
    List<ItemModel> favoritesList;
    FloatingActionButton floatingAddButton;
    FloatingActionButton floatingDeleteButton;
    ImageView showOnlyFavoritesButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        itemsList = new ArrayList<>();
        db = new DatabaseHelper(this);
        db.openDatabase();

        showOnlyFavoritesButton = findViewById(R.id.showOnlyButton);
        floatingAddButton = findViewById(R.id.floatingAddButton);
        floatingDeleteButton = findViewById(R.id.floatingDeleteButton);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(db, this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        itemsList = db.getAllItems();
        Collections.reverse(itemsList);
        adapter.setItemsList(itemsList);

        showOnlyFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

        floatingDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!itemsList.isEmpty()) {
                    db.deleteAll();
                    itemsList.clear();
                    Toast.makeText(getApplicationContext(), "You deleted all items!", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Your list is already empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewItem.newInstance().show(getSupportFragmentManager(), AddNewItem.TAG);
            }
        });


    }

    @Override
    public void onItemClick(View view, int position) {
        int incrementedPos = position+1;
        Toast.makeText(this, "You clicked on " + itemsList.get(position).getTitle() + " at position: " + incrementedPos +
                " out of " + adapter.getItemCount() + " items!", Toast.LENGTH_SHORT).show();
        final Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("idText", itemsList.get(position).getTitle());
        intent.putExtra("idImage", itemsList.get(position).getImageRes());
        startActivity(intent);
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        itemsList = db.getAllItems();
        Collections.reverse(itemsList);
        adapter.setItemsList(itemsList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

}