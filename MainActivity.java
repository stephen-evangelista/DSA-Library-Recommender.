package com.example.librarybookrecommnder; 

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends Activity {

    private EditText searchBar;
    private TextView label;
    private ListView listView;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.searchBar);
        label = findViewById(R.id.label);
        listView = findViewById(R.id.listView);
        
        dataManager = new DataManager(this);
        loadRecommendations();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    label.setText("Top Recommended Books");
                    loadRecommendations();
                } else {
                    label.setText("Search Results");
                    List<Book> results = dataManager.searchBooks(s.toString().trim());
                    BookAdapter adapter = new BookAdapter(MainActivity.this, results);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadRecommendations() {
        List<Book> recommended = dataManager.getRecommendations(10);
        BookAdapter adapter = new BookAdapter(this, recommended);
        listView.setAdapter(adapter);
    }
}
