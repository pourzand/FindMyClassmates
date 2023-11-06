package com.example.findmyclassmates;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class ClassDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        ClassModel classModel = (ClassModel) getIntent().getSerializableExtra("classModel");

        // Assuming you have a TextView in your layout with id detailTextView
        TextView detailTextView = findViewById(R.id.detailTextView);
        detailTextView.setText(classModel.getClassName());
        // Set other details from classModel to views...
    }
}
