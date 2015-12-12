package com.cristischeye.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText etItem;
    Button btnEditItem;
    String originalText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etItem = (EditText) findViewById(R.id.etItem);
        btnEditItem = (Button) findViewById(R.id.btnEditItem);

        originalText = getIntent().getStringExtra("originalText");
        position = getIntent().getIntExtra("position", 0);

        etItem.setText(originalText);
    }

    public void onEditItem(View v) {
        Intent data = new Intent();

        data.putExtra("editedItemText", etItem.getText().toString());
        data.putExtra("position", position);

        setResult(RESULT_OK, data);
        this.finish();
    }

}
