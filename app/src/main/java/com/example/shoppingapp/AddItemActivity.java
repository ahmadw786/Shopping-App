package com.example.shoppingapp;



import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddItemActivity extends AppCompatActivity {
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        database = FirebaseDatabase.getInstance().getReference("shoppingItems");

        EditText nameInput = findViewById(R.id.itemName);
        EditText quantityInput = findViewById(R.id.itemQuantity);
        EditText priceInput = findViewById(R.id.itemPrice);
        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String quantity = quantityInput.getText().toString();
            String price = priceInput.getText().toString();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(quantity) || TextUtils.isEmpty(price)) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = database.push().getKey();
            Map<String, Object> item = new HashMap<>();
            item.put("id", id);
            item.put("name", name);
            item.put("quantity", quantity);
            item.put("price", price);

            database.child(id).setValue(item)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
