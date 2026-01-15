package com.example.lab2;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);
        Button addCityButton = findViewById(R.id.add_city_button);
        Button deleteCityButton = findViewById(R.id.delete_city_button);

        String [] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((parent, view, position, id) -> selectedPosition = position);

        addCityButton.setOnClickListener(view -> showAddCityDialog());

        deleteCityButton.setOnClickListener(view -> {
            if (selectedPosition < 0 || selectedPosition >= dataList.size()) {
                Toast.makeText(this, "Select a city to delete", Toast.LENGTH_SHORT).show();
                return;
            }
            dataList.remove(selectedPosition);
            selectedPosition = -1;
            cityAdapter.notifyDataSetChanged();
        });
    }

    private void showAddCityDialog() {
        EditText input = new EditText(this);
        input.setHint("City name");

        new AlertDialog.Builder(this)
                .setTitle("Add City")
                .setView(input)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    String cityName = input.getText().toString().trim();
                    if (TextUtils.isEmpty(cityName)) {
                        Toast.makeText(this, "City name cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dataList.add(cityName);
                    cityAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}