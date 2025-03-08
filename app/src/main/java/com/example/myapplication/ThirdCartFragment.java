package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThirdCartFragment extends Fragment {

    private Button btnBack, btnCheckout;
    private TextView txtItems, txtAddress, txtPhoneNumber, txtTotalPrice;
    private RadioGroup rgPayment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_third, container, false);

        initViews(view);

        Bundle bundle = getArguments();
        if (null != bundle) {
            final String jsonOrder = bundle.getString(SecondCartFragment.ORDER_KEY);
            if (null != jsonOrder) {
                Gson gson = new Gson();
                Type type = new TypeToken<Order>() {}.getType();
                final Order order = gson.fromJson(jsonOrder, type);
                if (null != order) {
                    StringBuilder items = new StringBuilder();
                    for (GroceryItem i : order.getItems()) {
                        items.append("\n\t").append(i.getName());
                    }

                    txtItems.setText(items.toString());
                    txtAddress.setText(order.getAddress());
                    txtPhoneNumber.setText(order.getPhoneNumber());
                    txtTotalPrice.setText(String.valueOf(order.getTotalPrice()));

                    btnBack.setOnClickListener(v -> {
                        Bundle backBundle = new Bundle();
                        backBundle.putString(SecondCartFragment.ORDER_KEY, jsonOrder);
                        SecondCartFragment secondCartFragment = new SecondCartFragment();
                        secondCartFragment.setArguments(backBundle);

                        FragmentTransaction transaction = null;
                        if (getActivity() != null) {
                            transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        }
                        if (transaction != null) {
                            transaction.replace(R.id.container, secondCartFragment);
                            transaction.commit();
                        }
                    });

                    btnCheckout.setOnClickListener(v -> {
                        int selectedId = rgPayment.getCheckedRadioButtonId();
                        if (selectedId == R.id.rbPayPal) {
                            order.setPaymentMethod("PayPal");
                        } else if (selectedId == R.id.rbCreditCard) {
                            order.setPaymentMethod("Credit Card");
                        } else {
                            order.setPaymentMethod("Unknown");
                        }

                        order.setSuccess(true);


                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY);

                        OkHttpClient client = new OkHttpClient.Builder()
                                .addInterceptor(interceptor)
                                .build();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://jsonplaceholder.typicode.com/")  // Update with actual URL
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(client)
                                .build();

                        OrderEndPoint endPoint = retrofit.create(OrderEndPoint.class);
                        Call<Order> call = endPoint.newOrder(order);
                        call.enqueue(new Callback<>() {
                            @Override
                            public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                                if (response.isSuccessful()) {
                                    Bundle resultBundle = new Bundle();
                                    resultBundle.putString(SecondCartFragment.ORDER_KEY, gson.toJson(response.body()));
                                    PaymentResultFragment paymentResultFragment = new PaymentResultFragment();
                                    paymentResultFragment.setArguments(resultBundle);

                                    FragmentTransaction transaction = null;
                                    if (getActivity() != null) {
                                        transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    }
                                    if (transaction != null) {
                                        transaction.replace(R.id.container, paymentResultFragment);
                                        transaction.commit();
                                    }
                                } else {
                                    FragmentTransaction transaction = null;
                                    if (getActivity() != null) {
                                        transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    }
                                    if (transaction != null) {
                                        transaction.replace(R.id.container, new PaymentResultFragment());
                                        transaction.commit();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                                Log.e("PaymentError", "Error sending order request", t);
                            }
                        });
                    });
                }
            }
        }

        return view;
    }

    private void initViews(View view) {
        rgPayment = view.findViewById(R.id.rgPaymentMethod);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        txtItems = view.findViewById(R.id.txtItems);
        txtTotalPrice = view.findViewById(R.id.txtPrice);
        btnBack = view.findViewById(R.id.btnBack);
        btnCheckout = view.findViewById(R.id.btnCheckout);
    }
}
