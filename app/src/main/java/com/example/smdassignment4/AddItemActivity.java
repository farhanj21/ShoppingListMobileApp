package com.example.smdassignment4;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItemActivity extends AppCompatActivity {

    private EditText itemNameField, quantityField, priceField;
    private Button saveItemButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("shoppingLists");

        // Link UI elements
        itemNameField = findViewById(R.id.itemNameField);
        quantityField = findViewById(R.id.quantityField);
        priceField = findViewById(R.id.priceField);
        saveItemButton = findViewById(R.id.saveItemButton);

        // Set up Save Item Button
        saveItemButton.setOnClickListener(v -> saveItem());
    }

    private void saveItem() {
        // Get data from the UI
        String itemName = itemNameField.getText().toString().trim();
        String quantityStr = quantityField.getText().toString().trim();
        String priceStr = priceField.getText().toString().trim();

        // Validation and conversion
        if (TextUtils.isEmpty(itemName)) {
            Toast.makeText(this, "Please enter an item name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(quantityStr) || TextUtils.isEmpty(priceStr)) {
            Toast.makeText(this, "Please enter quantity and price", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(quantityStr);
        double price = Double.parseDouble(priceStr);

        // Generate a unique ID for the item
        String itemId = databaseReference.push().getKey();

        // Create a new ShoppingItem object
        ShoppingItem item = new ShoppingItem(itemName, quantity, price);

        // Save the item to Firebase Realtime Database
        if (itemId != null) {
            databaseReference.child(itemId).setValue(item)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Go back to the previous screen
                        } else {
                            Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Failed to generate item ID", Toast.LENGTH_SHORT).show();
        }
    }
}
