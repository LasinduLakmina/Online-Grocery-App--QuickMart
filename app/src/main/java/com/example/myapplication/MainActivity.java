package com.example.myapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        Utils.initSharedPreferences(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.cart) {
                Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
                cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cartIntent);
            } else if (itemId == R.id.categories) {
                AllCategoriesDialog dialog = new AllCategoriesDialog();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(AllCategoriesDialog.ALL_CATEGORIES, Utils.getCategories(MainActivity.this));
                bundle.putString(AllCategoriesDialog.CALLING_ACTIVITY, "main_activity");
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "all categories dialog");
            } else if (itemId == R.id.about) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("About Us")
                        .setMessage("Designed and Developed by Lasindu Lakmina\n" +
                                "Visit for more")
                        .setPositiveButton("Visit", (dialog, which) -> {
                            Intent websiteIntent = new Intent(MainActivity.this, WebsiteActivity.class);
                            startActivity(websiteIntent);
                        }).create().show();
            } else if (itemId == R.id.terms) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Terms")
                        .setMessage("There are no terms, Enjoy using the application :)")
                        .setPositiveButton("Dismiss", (dialog, which) -> dialog.dismiss()).create().show();
            } else if (itemId == R.id.licences) {
                LicencesDialog licencesDialog = new LicencesDialog();
                licencesDialog.show(getSupportFragmentManager(), "licences");
            }

            return false;
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new MainFragment());
        transaction.commit();
    }

    private void initViews() {
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
    }
}
