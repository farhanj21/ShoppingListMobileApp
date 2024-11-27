package com.example.smdassignment4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ShoppingListAdapter extends FirestoreRecyclerAdapter<ShoppingItem, ShoppingListAdapter.ShoppingListViewHolder> {

    public ShoppingListAdapter(@NonNull FirestoreRecyclerOptions<ShoppingItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position, @NonNull ShoppingItem model) {
        // Bind data from Firestore to views
        holder.itemNameTextView.setText(model.getItemName());
        holder.quantityTextView.setText(String.valueOf(model.getQuantity()));
        holder.priceTextView.setText(String.format("$%.2f", model.getPrice()));
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_list, parent, false);
        return new ShoppingListViewHolder(view);
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        TextView itemNameTextView, quantityTextView, priceTextView;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }
    }
}
