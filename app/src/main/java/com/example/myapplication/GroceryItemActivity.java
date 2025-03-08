package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;


import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview {
    private static final String TAG = "GroceryItemActivity";

    private boolean isBound;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackUserTime.LocalBinder binder = (TrackUserTime.LocalBinder) service;
            TrackUserTime mService = binder.getService();
            isBound = true;
            mService.setItem(incomingItem);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    public void onAddReviewResult(Review review) {
        Log.d(TAG, "onAddReviewResult: new review: " + review);
        Utils.addReview(this, review);
        Utils.changeUserPoint(this, incomingItem, 3);
        ArrayList<Review> reviews = Utils.getReviewById(this, review.getGroceryItemId());
        if (reviews != null) {
            adapter.setReviews(reviews);
        }
    }

    public static final String GROCERY_ITEM_KEY = "incoming_item";

    private RecyclerView reviewsRecView;
    private TextView txtName, txtPrice, txtDescription, txtAddReview;
    private ImageView itemImage;
    private Button btnAddToCart;
    private MaterialToolbar toolbar;
    private RatingBar ratingBar;
    private GroceryItem incomingItem;

    private ReviewsAdapter adapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);
        initViews();

        setSupportActionBar(toolbar);

        adapter = new ReviewsAdapter();

        final Intent intent = getIntent();
        if (intent != null) {
            incomingItem = intent.getParcelableExtra(GROCERY_ITEM_KEY);
            if (incomingItem != null) {
                Utils.changeUserPoint(this, incomingItem, 1);
                txtName.setText(incomingItem.getName());
                txtDescription.setText(incomingItem.getDescription());
                txtPrice.setText("Rs. " + incomingItem.getPrice());
                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageUrl())
                        .into(itemImage);

                ArrayList<Review> reviews = Utils.getReviewById(this, incomingItem.getId());
                reviewsRecView.setAdapter(adapter);
                reviewsRecView.setLayoutManager(new LinearLayoutManager(this));
                if (reviews != null && !reviews.isEmpty()) {
                    adapter.setReviews(reviews);
                }

                btnAddToCart.setOnClickListener(v -> {
                    Utils.addItemToCart(GroceryItemActivity.this, incomingItem);
                    Intent cartIntent = new Intent(GroceryItemActivity.this, CartActivity.class);
                    cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(cartIntent);
                });

                txtAddReview.setOnClickListener(v -> {
                    AddReviewDialog dialog = new AddReviewDialog();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(GROCERY_ITEM_KEY, incomingItem);
                    dialog.setArguments(bundle);
                    dialog.show(getSupportFragmentManager(), "add review");
                });

                handleRating();
            }
        }
    }

    private void handleRating() {
        ratingBar.setRating(incomingItem.getRate());

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), (int) rating);
                Utils.changeUserPoint(GroceryItemActivity.this, incomingItem, (int) ((rating - incomingItem.getRate()) * 2));
                incomingItem.setRate((int) rating);
            }
        });
    }

    private void initViews() {
        txtName = findViewById(R.id.txtName);
        txtDescription = findViewById(R.id.txtDescription);
        txtPrice = findViewById(R.id.txtPrice);
        txtAddReview = findViewById(R.id.txtAddReview);
        itemImage = findViewById(R.id.itemImage);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        reviewsRecView = findViewById(R.id.reviewsRecView);
        ratingBar = findViewById(R.id.ratingBar);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TrackUserTime.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(connection);
        }
    }
}
