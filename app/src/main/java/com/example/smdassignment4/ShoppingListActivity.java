package com.example.smdassignment4;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    private RecyclerView shoppingRecyclerView;
    private ShoppingListCustomAdapter adapter;
    private List<ShoppingItem> shoppingItemList = new ArrayList<>();
    private DatabaseReference shoppingListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        shoppingListRef = database.getReference("shoppingLists");

        shoppingRecyclerView = findViewById(R.id.shoppingRecyclerView);
        shoppingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ShoppingListCustomAdapter(shoppingItemList);
        shoppingRecyclerView.setAdapter(adapter);

        Button addNewItemButton = findViewById(R.id.addNewItemButton);
        addNewItemButton.setOnClickListener(view -> showAddItemDialog());

        fetchShoppingList();
    }

    private void fetchShoppingList() {
        shoppingListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shoppingItemList.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ShoppingItem item = dataSnapshot.getValue(ShoppingItem.class);
                    if (item != null) {
                        item.setKey(dataSnapshot.getKey()); // Ensure key is set
                        shoppingItemList.add(item);
                    } else {
                        Log.e("FetchShoppingList", "Failed to parse item: " + dataSnapshot.getKey());
                    }
                }
                adapter.setShoppingItems(shoppingItemList); // Update the adapter with new data
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ShoppingListActivity", "Failed to fetch data: " + error.getMessage());
            }
        });
    }




    private void showAddItemDialog() {
        // Inflate the custom layout
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_item, null);

        // Link the fields from the custom layout
        final EditText itemNameField = dialogView.findViewById(R.id.itemNameField);
        final EditText quantityField = dialogView.findViewById(R.id.quantityField);
        final EditText priceField = dialogView.findViewById(R.id.priceField);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Item");
        builder.setView(dialogView); // Set the custom layout

        // Set dialog buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String itemName = itemNameField.getText().toString().trim();
            String quantityStr = quantityField.getText().toString().trim();
            String priceStr = priceField.getText().toString().trim();

            // Validate input fields
            if (TextUtils.isEmpty(itemName)) {
                Toast.makeText(this, "Please enter an item name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(quantityStr) || TextUtils.isEmpty(priceStr)) {
                Toast.makeText(this, "Please enter both quantity and price", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);

                // Create a new ShoppingItem object
                String itemId = shoppingListRef.push().getKey();
                if (itemId != null) {
                    ShoppingItem item = new ShoppingItem(itemName, quantity, price);
                    shoppingListRef.child(itemId).setValue(item)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            } catch (NumberFormatException e) {
                Log.e("AddItemDialog", "Invalid number format", e);
                Toast.makeText(this, "Please enter valid numbers for quantity and price", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        builder.create().show();
    }

}
