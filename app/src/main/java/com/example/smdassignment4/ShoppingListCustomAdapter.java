package com.example.smdassignment4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShoppingListCustomAdapter extends RecyclerView.Adapter<ShoppingListCustomAdapter.ShoppingListViewHolder> {

    private List<ShoppingItem> shoppingItems;

    public ShoppingListCustomAdapter(List<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_list, parent, false);
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        ShoppingItem item = shoppingItems.get(position);
        holder.itemNameTextView.setText(item.getItemName());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));
        holder.priceTextView.setText(String.format("$%.2f", item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    public void setShoppingItems(List<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
        notifyDataSetChanged();
    }

    static class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        TextView itemNameTextView, quantityTextView, priceTextView;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }
    }
}
