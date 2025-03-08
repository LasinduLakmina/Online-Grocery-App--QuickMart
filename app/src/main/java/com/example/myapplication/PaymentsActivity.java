package com.example.myapplication;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.GroceryItem;
import com.example.myapplication.Utils;
import com.example.myapplication.service.GroceryItemService;

import java.util.ArrayList;

import okhttp3.internal.Util;

public class PaymentsActivity extends AppCompatActivity {

    GroceryItemService groceryItemService;
    ArrayList<GroceryItem> cartItems;
    double totalBill = 0.0;

    private EditText edtAddress, edtCardNumber, edtExpiry, edtCardHolder, edtCVC;
    private TextView txtTotalBill;
    private RadioGroup paymentOptions;
    private View cardDetails;
    private Button btnPlaceOrder;
    private RadioButton rbCash, rbCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payments);

        groceryItemService = new GroceryItemService(this.getBaseContext());

        // UI Elements
        edtAddress = findViewById(R.id.edtAddress);
        paymentOptions = findViewById(R.id.paymentOptions);
        cardDetails = findViewById(R.id.cardDetails);
        rbCash = findViewById(R.id.rbCash);
        rbCard = findViewById(R.id.rbCard);
        txtTotalBill = findViewById(R.id.txtTotalBill);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        edtCardNumber = findViewById(R.id.edtCardNumber);
        edtExpiry = findViewById(R.id.edtExpiry);
        edtCardHolder = findViewById(R.id.edtCardHolder);
        edtCVC = findViewById(R.id.edtCVC);

        loadCartItems();

        paymentOptions.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbCard) {
                cardDetails.setVisibility(View.VISIBLE);
            } else {
                cardDetails.setVisibility(View.GONE);
            }
        });

        // Place order
        btnPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    private void loadCartItems() {
        cartItems = Utils.getCartItems(this.getBaseContext());
        totalBill = 0.0;
        for (GroceryItem item : cartItems) {
            totalBill += item.getPrice();
        }
        txtTotalBill.setText("Total: Rs. " + totalBill);
    }

    private void placeOrder() {
        if (edtAddress.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rbCard.isChecked()) {
            if (edtCardNumber.getText().toString().isEmpty() || edtExpiry.getText().toString().isEmpty() ||
                    edtCardHolder.getText().toString().isEmpty() || edtCVC.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill in all card details", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        for (GroceryItem item : cartItems) {
            groceryItemService.updateAvailableAmount(item.getId(), item.getAvailableAmount() - 1);
        }

        Utils.clearCart(this.getBaseContext());
        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show();
        finish(); // Close activity
    }
}
