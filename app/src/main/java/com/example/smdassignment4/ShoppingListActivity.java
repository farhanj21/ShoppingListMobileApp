package com.example.smdassignment4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ShoppingListActivity extends AppCompatActivity {

    private RecyclerView shoppingRecyclerView;
    private ShoppingListAdapter adapter;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        firestore = FirebaseFirestore.getInstance();

        shoppingRecyclerView = findViewById(R.id.shoppingRecyclerView);
        shoppingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button addNewItemButton = findViewById(R.id.addNewItemButton);
        addNewItemButton.setOnClickListener(view -> {
            startActivity(new Intent(ShoppingListActivity.this, AddItemActivity.class));
        });

        // Query Firestore and set up RecyclerView
        Query query = firestore.collection("shoppingLists").orderBy("itemName");
        FirestoreRecyclerOptions<ShoppingItem> options = new FirestoreRecyclerOptions.Builder<ShoppingItem>()
                .setQuery(query, ShoppingItem.class)
                .build();

        adapter = new ShoppingListAdapter(options);
        shoppingRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();  // Start listening for changes in the Firestore database
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();  // Stop listening when the activity stops
    }
}
