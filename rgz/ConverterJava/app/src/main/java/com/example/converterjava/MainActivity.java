package com.example.converterjava;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private EditText etAmount;
    private Spinner spFromCurrency;
    private Spinner spToCurrency;
    private TextView tvResult;
    private CurrencyAPI.CurrencyService currencyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount = findViewById(R.id.et_amount);
        spFromCurrency = findViewById(R.id.sp_from_currency);
        spToCurrency = findViewById(R.id.sp_to_currency);
        tvResult = findViewById(R.id.tv_result);
        Button btnConvert = findViewById(R.id.btn_convert);

        currencyService = CurrencyAPI.getClient().create(CurrencyAPI.CurrencyService.class);

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });
    }

    private void convertCurrency() {
        String apiKey = CurrencyAPI.API_KEY;

        String fromCurrency = spFromCurrency.getSelectedItem().toString();
        String toCurrency = spToCurrency.getSelectedItem().toString();
        if (fromCurrency.equals(toCurrency)) {
            Toast.makeText(MainActivity.this, "Заданы одинаковые валюты", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(etAmount.getText().toString())) {
            Toast.makeText(MainActivity.this, "Задайте значение > 0", Toast.LENGTH_SHORT).show();
            return;
        }
        double amount = Double.parseDouble(etAmount.getText().toString());


        Call<Currency> call = currencyService.getExchangeRates(apiKey);
        call.enqueue(new Callback<Currency>() {
            @Override
            public void onResponse(Call<Currency> call, Response<Currency> response) {
                if (response.isSuccessful()) {
                    Currency currency = response.body();
                    double fromRate = currency.getRates().get(fromCurrency);
                    double toRate = currency.getRates().get(toCurrency);
                    double result = (amount / fromRate) * toRate;
                    tvResult.setText(String.format("%.2f", result));
                } else {
                    Toast.makeText(MainActivity.this, "Ошибка интеграции: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Currency> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка со связью с API " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
