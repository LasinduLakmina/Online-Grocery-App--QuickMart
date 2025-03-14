package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements AllCategoriesDialog.GetCategory {
    private static final String TAG = "SearchActivity";

    @Override
    public void onGetCategoryResult(String category) {
        ArrayList<GroceryItem> items = Utils.getItemsByCategory(this, category);
        if (null != items) {
            adapter.setItems(items);
            increaseUserPoint(items);
        }
    }

    private MaterialToolbar toolbar;
    private EditText searchBox;
    private ImageView btnSearch;
    private TextView firstCat, secondCat, thirdCat, txtAllCategories;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;

    private GroceryItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        initBottomNavView();

        setSupportActionBar(toolbar);

        adapter = new GroceryItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Intent intent = getIntent();
        if (null != intent) {
            String category = intent.getStringExtra("category");
            if (null != category) {
                ArrayList<GroceryItem> items = Utils.getItemsByCategory(this, category);
                if (null != items) {
                    adapter.setItems(items);
                    increaseUserPoint(items);
                }
            }
        }

        btnSearch.setOnClickListener(v -> initSearch());

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ArrayList<String> categories = Utils.getCategories(this);
        if (null != categories && !categories.isEmpty()) {
            if (categories.size() == 1) {
                showCategories(categories, 1);
            } else if (categories.size() == 2) {
                showCategories(categories, 2);
            } else {
                showCategories(categories, 3);
            }
        }

        txtAllCategories.setOnClickListener(v -> {
            AllCategoriesDialog dialog = new AllCategoriesDialog();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(AllCategoriesDialog.ALL_CATEGORIES, Utils.getCategories(SearchActivity.this));
            bundle.putString(AllCategoriesDialog.CALLING_ACTIVITY, "search_activity");
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), "all categories dialog");
        });
    }

    private void increaseUserPoint(ArrayList<GroceryItem> items) {
        for (GroceryItem i : items) {
            Utils.changeUserPoint(this, i, 1);
        }
    }

    private void showCategories(final ArrayList<String> categories, int i) {
        switch (i) {
        case 1:
            firstCat.setVisibility(View.VISIBLE);
            firstCat.setText(categories.get(0));
            secondCat.setVisibility(View.GONE);
            thirdCat.setVisibility(View.GONE);
            firstCat.setOnClickListener(v -> {
                ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(0));
                if (null != items) {
                    adapter.setItems(items);
                    increaseUserPoint(items);
                }
            });
            break;
        case 2:
            firstCat.setVisibility(View.VISIBLE);
            firstCat.setText(categories.get(0));
            secondCat.setVisibility(View.VISIBLE);
            secondCat.setText(categories.get(1));
            thirdCat.setVisibility(View.GONE);
            firstCat.setOnClickListener(v -> {
                ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(0));
                if (null != items) {
                    adapter.setItems(items);
                    increaseUserPoint(items);
                }
            });
            secondCat.setOnClickListener(v -> {
                ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(1));
                if (null != items) {
                    adapter.setItems(items);
                    increaseUserPoint(items);
                }
            });
            break;
        case 3:
            firstCat.setVisibility(View.VISIBLE);
            firstCat.setText(categories.get(0));
            secondCat.setVisibility(View.VISIBLE);
            secondCat.setText(categories.get(1));
            thirdCat.setVisibility(View.VISIBLE);
            thirdCat.setText(categories.get(2));
            firstCat.setOnClickListener(v -> {
                ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(0));
                if (null != items) {
                    adapter.setItems(items);
                    increaseUserPoint(items);
                }
            });
            secondCat.setOnClickListener(v -> {
                ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(1));
                if (null != items) {
                    adapter.setItems(items);
                    increaseUserPoint(items);
                }
            });
            thirdCat.setOnClickListener(v -> {
                ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(2));
                if (null != items) {
                    adapter.setItems(items);
                    increaseUserPoint(items);
                }
            });
            break;
        default:
            firstCat.setVisibility(View.GONE);
            secondCat.setVisibility(View.GONE);
            thirdCat.setVisibility(View.GONE);
            break;
        }
    }

    private void initSearch() {
        if (!searchBox.getText().toString().isEmpty()) {
            String name = searchBox.getText().toString();
            ArrayList<GroceryItem> items = Utils.searchForItems(this, name);
            if (null != items) {
                adapter.setItems(items);
                increaseUserPoint(items);
            }
        }
    }

    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else if (itemId == R.id.cart) {
                Intent cartIntent = new Intent(SearchActivity.this, CartActivity.class);
                cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cartIntent);
            }
            return true;
        });
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        searchBox = findViewById(R.id.searchBox);
        btnSearch = findViewById(R.id.btnSearch);
        firstCat = findViewById(R.id.txtFirstCat);
        secondCat = findViewById(R.id.txtSecondCat);
        thirdCat = findViewById(R.id.txtThirdCat);
        txtAllCategories = findViewById(R.id.txtAllCategories);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        recyclerView = findViewById(R.id.recyclerView);
    }
}
