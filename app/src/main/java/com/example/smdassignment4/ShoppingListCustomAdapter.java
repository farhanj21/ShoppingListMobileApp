package com.example.smdassignment4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ShoppingListCustomAdapter extends RecyclerView.Adapter<ShoppingListCustomAdapter.ShoppingListViewHolder> {

    private List<ShoppingItem> shoppingItems;
    private DatabaseReference databaseReference;

    public ShoppingListCustomAdapter(List<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("shoppingLists");
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

        holder.deleteButton.setOnClickListener(view -> {
            // Remove the item from Firebase Realtime Database
            String key = item.getKey(); // Ensure ShoppingItem has a `key` field
            if (key != null) {
                databaseReference.child(key).removeValue()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(view.getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(view.getContext(), "Failed to delete item", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(view.getContext(), "Failed to find item key", Toast.LENGTH_SHORT).show();
            }
        });

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
        Button deleteButton;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
