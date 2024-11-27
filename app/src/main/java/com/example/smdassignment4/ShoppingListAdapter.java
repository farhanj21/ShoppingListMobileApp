package com.example.smdassignment4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShoppingListAdapter extends FirestoreRecyclerAdapter<ShoppingItem, ShoppingListAdapter.ShoppingItemViewHolder> {

    public ShoppingListAdapter(@NonNull FirestoreRecyclerOptions<ShoppingItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ShoppingItemViewHolder holder, int position, @NonNull ShoppingItem model) {
        holder.itemName.setText(model.getItemName());
        holder.itemQuantity.setText("Qty: " + model.getQuantity());
        holder.itemPrice.setText("$" + model.getPrice());

        holder.deleteButton.setOnClickListener(v -> {
            FirebaseFirestore.getInstance().collection("shoppingLists")
                    .document(getSnapshots().getSnapshot(position).getId())
                    .delete();
        });
    }

    @NonNull
    @Override
    public ShoppingItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping_list, parent, false);
        return new ShoppingItemViewHolder(view);
    }

    static class ShoppingItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemQuantity, itemPrice;
        Button deleteButton;

        public ShoppingItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
