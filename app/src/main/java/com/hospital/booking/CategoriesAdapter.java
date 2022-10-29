package com.hospital.booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    private List<CategoryModel> moviesList;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, year, genre;
        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            genre = view.findViewById(R.id.genre);
        }
    }
    public CategoriesAdapter(List<CategoryModel> moviesList) {
        this.moviesList = moviesList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_category, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CategoryModel movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
    }
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}