package com.example.mytest.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mytest.Database.DatabaseHelper;
import com.example.mytest.FavoritesActivity;
import com.example.mytest.MainActivity;
import com.example.mytest.Model.ItemModel;
import com.example.mytest.R;
import java.util.List;

public class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<FavoritesRecyclerViewAdapter.ViewHolder> {

    private List<ItemModel> favoritesList;
    private FavoritesActivity favoritesActivity;
    private ItemClickListener myClickListener;
    private DatabaseHelper db;

    // data is passed into the constructor
    public FavoritesRecyclerViewAdapter(DatabaseHelper db, FavoritesActivity favoritesActivity) {
        this.db = db;
        this.favoritesActivity = favoritesActivity;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_favorites, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setFavoritesList(List<ItemModel> favoritesList) {
        this.favoritesList = favoritesList;
        notifyDataSetChanged();
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        db.openDatabase();
        final ItemModel item = favoritesList.get(position);
        viewHolder.itemTextView.setText(item.getTitle());
        Glide.with(getContext()).load(item.getImageRes()).into(viewHolder.itemImageView);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public void updateFavoritesList(List<ItemModel> favoritesList)
    {
        this.favoritesList = favoritesList;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return favoritesActivity;
    }

    public void addItem(int position, ItemModel item)
    {
        favoritesList.add(position, item);
        notifyItemInserted(position);
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemTextView;
        ImageView itemImageView;

        ViewHolder(View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.textCardView);
            itemImageView = itemView.findViewById(R.id.imageCardView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (myClickListener != null) myClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.myClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}