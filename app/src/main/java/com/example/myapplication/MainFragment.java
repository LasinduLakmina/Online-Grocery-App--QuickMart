package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.service.GroceryItemService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private GroceryItemService groceryService;
    private RecyclerView newItemsRecView, popularItemsRecView, suggestedItemsRecView;
    private GroceryItemAdapter newItemsAdapter, popularItemsAdapter, suggestedItemsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initView(view);
        initBottomNavView();
        groceryService = new GroceryItemService(requireContext());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            initRecViews();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initRecViews() {
        if (getActivity() == null) return;

        if (newItemsAdapter == null) {
            newItemsAdapter = new GroceryItemAdapter(getActivity());
            newItemsRecView.setAdapter(newItemsAdapter);
            newItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        }

        if (popularItemsAdapter == null) {
            popularItemsAdapter = new GroceryItemAdapter(getActivity());
            popularItemsRecView.setAdapter(popularItemsAdapter);
            popularItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        }

        if (suggestedItemsAdapter == null) {
            suggestedItemsAdapter = new GroceryItemAdapter(getActivity());
            suggestedItemsRecView.setAdapter(suggestedItemsAdapter);
            suggestedItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        }

        ArrayList<GroceryItem> newItems = Utils.getAllItems(getActivity());
        if (newItems == null) {
            newItems = new ArrayList<>();
        }

        List<GroceryItem> allItems = groceryService.getAllGroceryItems();
        if (allItems.isEmpty()) {
            for (GroceryItem item : newItems) {
                groceryService.insertGroceryItem(item);
            }
        }

        allItems = groceryService.getAllGroceryItems();
        if (allItems != null && !allItems.isEmpty()) {
            ArrayList<GroceryItem> arrayListItems = new ArrayList<>(allItems);

            ArrayList<GroceryItem> sortedByNew = new ArrayList<>(arrayListItems);
            sortedByNew.sort((o1, o2) -> Integer.compare(o2.getId(), o1.getId()));
            newItemsAdapter.setItems(sortedByNew);
            newItemsAdapter.notifyDataSetChanged();

            ArrayList<GroceryItem> sortedByPopularity = new ArrayList<>(arrayListItems);
            sortedByPopularity.sort((o1, o2) -> Integer.compare(o2.getPopularityPoint(), o1.getPopularityPoint()));
            popularItemsAdapter.setItems(sortedByPopularity);
            popularItemsAdapter.notifyDataSetChanged();

            ArrayList<GroceryItem> sortedByUserPoint = new ArrayList<>(arrayListItems);
            sortedByUserPoint.sort((o1, o2) -> Integer.compare(o2.getUserPoint(), o1.getUserPoint()));
            suggestedItemsAdapter.setItems(sortedByUserPoint);
            suggestedItemsAdapter.notifyDataSetChanged();
        }
    }

    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (getActivity() == null) return false;

            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                return true;
            } else if (itemId == R.id.search) {
                startActivity(new Intent(getActivity(), SearchActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                return true;
            } else if (itemId == R.id.cart) {
                startActivity(new Intent(getActivity(), CartActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                return true;
            }
            return false;
        });
    }

    private void initView(View view) {
        bottomNavigationView = view.findViewById(R.id.bottomNavView);
        newItemsRecView = view.findViewById(R.id.newItemsRecView);
        popularItemsRecView = view.findViewById(R.id.popularItemRecView);
        suggestedItemsRecView = view.findViewById(R.id.suggestedItemsRecView);
    }
}
