package com.cristischeye.simpletodo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<TodoItem> items;
    ArrayAdapter<TodoItem> itemsAdapter;
    ArrayList<Boolean> itemsChecked;
    ListView lvItems;
    EditText etNewItem;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("My To Do List");
        }

        readItems();

        etNewItem = (EditText) findViewById(R.id.etNewItem);

        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        itemsAdapter = new ArrayAdapter<TodoItem>(this, android.R.layout.simple_list_item_multiple_choice, items);
        lvItems.setAdapter(itemsAdapter);
        checkItems();

        setupViewListeners();
    }

    public void onAddItem(View view) {
        String newItem = etNewItem.getText().toString();
        TodoItem newTodoItem = new TodoItem(newItem, false);
        itemsAdapter.add(newTodoItem);
        etNewItem.setText("");
        writeItems();
    }

    private void setupViewListeners(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View itemView, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        checkItems();
                        writeItems();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        TodoItem item = items.get(pos);
                        item.toggleIsCompleted();
                        lvItems.setItemChecked(pos, item.getIsCompleted());
                        writeItems();
                    }
                }
        );
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try {
            ArrayList<String> itemStrings = new ArrayList<String>(FileUtils.readLines(todoFile));
            items = new ArrayList<TodoItem>();
            for (String itemString: itemStrings) {
                TodoItem newTodo = new TodoItem(itemString.substring(1), itemString.startsWith("c"));
                items.add(newTodo);
            }
        }
        catch (IOException e){
            items = new ArrayList<TodoItem>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try {
            FileUtils.writeLines(todoFile, getItemStrings());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private ArrayList<String> getItemStrings() {
        ArrayList<String> itemStrings = new ArrayList<String>();
        for (TodoItem item: items) {
            itemStrings.add(item.toFileString());
        }
        return itemStrings;
    }

    private void checkItems() {
        for (int i = 0; i < items.size(); i++) {
            lvItems.setItemChecked(i, items.get(i).getIsCompleted());
        }
    }
}
