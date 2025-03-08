package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE = 0;
    private static final int BUTTON_VIEW_TYPE = 1;

    public interface TotalPrice {
        void getTotalPrice(double price);
    }

    public interface DeleteItem {
        void onDeleteResult(GroceryItem item);
    }

    public interface CheckoutButtonClick {
        void onCheckoutClicked();
    }

    private DeleteItem deleteItem;
    private CheckoutButtonClick checkoutButtonClick;
    private ArrayList<GroceryItem> items = new ArrayList<>();
    private final Context context;
    private final Fragment fragment;

    public CartAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;

        try {
            checkoutButtonClick = (CheckoutButtonClick) fragment;

        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == items.size() && !items.isEmpty()) ? BUTTON_VIEW_TYPE : ITEM_VIEW_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BUTTON_VIEW_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_checkout_button, parent, false);
            return new CheckoutViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof ItemViewHolder) {
            GroceryItem item = items.get(position);
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.txtName.setText(item.getName());
            itemHolder.txtPrice.setText("Rs." + item.getPrice());

            // Delete item click listener
            itemHolder.txtDelete.setOnClickListener(v -> {
                if (deleteItem != null) {
                    deleteItem.onDeleteResult(items.get(position));
                }
            });
        } else if (holder instanceof CheckoutViewHolder) {

                CheckoutViewHolder checkoutHolder = (CheckoutViewHolder) holder;
                checkoutHolder.btnGoToCheckout.setOnClickListener(v -> {
                    if (checkoutButtonClick != null) {
                        checkoutButtonClick.onCheckoutClicked();
                    }
                });

        }
    }

    @Override
    public int getItemCount() {
        return items.isEmpty() ? 0 : items.size() + 1;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
        calculateTotalPrice();
        notifyDataSetChanged();
    }

    private void calculateTotalPrice() {
        double price = 0;
        for (GroceryItem item : items) {
            price += item.getPrice();
        }
        price = Math.round(price * 100.0) / 100.0;

        try {
            TotalPrice totalPrice = (TotalPrice) fragment;
            totalPrice.getTotalPrice(price);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtName;
        private final TextView txtPrice;
        private final ImageView txtDelete;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDelete = itemView.findViewById(R.id.txtDelete);
        }
    }

    public static class CheckoutViewHolder extends RecyclerView.ViewHolder {
        private final Button btnGoToCheckout;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            btnGoToCheckout = itemView.findViewById(R.id.btnGoToCheckout);
        }
    }
}
