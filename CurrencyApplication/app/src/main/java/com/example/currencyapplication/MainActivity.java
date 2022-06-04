package com.example.currencyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements KeyboardAdapter.ItemClickInterface {
    List<String> currency = Arrays.asList("USD","EUR","JPY","GBP","CHF","CAD", "AUD", "ZAR", "VND", "RUB");
    List<String> button = Arrays.asList("7","8","9","4","5","6","1","2","3","0",".","CE","x");
    Map<String, Double> currencyRate = new HashMap<String, Double>();
    private String working = "";
    TextView workingTextView;
    TextView resultTextView;
    private void addWorkings(String givenValue)
    {
        if(working == "0")
        {
            working = "";
        }
        working += givenValue;
        workingTextView.setText(working);
        convertCurrency();
    }

    protected void convertCurrency()
    {
        try {
            Double res = Double.parseDouble(working);
            if(workingTextView == (TextView) findViewById(R.id.firstValue))
            {
                Spinner workingSpinner = findViewById(R.id.firstSpinner);
                String working = (String) workingSpinner.getSelectedItem();

                Spinner resultSpinner = findViewById(R.id.secondSpinner);
                String result = (String) resultSpinner.getSelectedItem();

                Double value = currencyRate.get(result) / currencyRate.get(working) * res;
                resultTextView.setText(value.toString());
            }
        }
        catch (NumberFormatException e)
        {
            Toast.makeText(getApplicationContext(),"INVALID INPUT!", Toast.LENGTH_SHORT).show();
            deleteWorkings();
        }
    }

    private void deleteWorkings()
    {
        if(working.length() == 0 || (working == "0")) return;
        else
        {
            working = working.substring(0, working.length()-1);
            workingTextView.setText(working);
        }
        if(working.length() == 0)
        {
            working = "0";
            workingTextView.setText(working);
        }
        convertCurrency();
    }
    private void deleteAllWorkings()
    {
        working = "0";
        workingTextView.setText(working);
        convertCurrency();
    }
    protected void addCurrency()
    {
        currencyRate.put("USD",1.0);
        currencyRate.put("EUR",0.94);
        currencyRate.put("JPY",130.06);
        currencyRate.put("GBP",0.8);
        currencyRate.put("CHF",0.96);
        currencyRate.put("CAD",1.27);
        currencyRate.put("AUD",1.4);
        currencyRate.put("ZAR",15.61);
        currencyRate.put("VND",23207.4);
        currencyRate.put("RUB",62.03);
    }

    protected void addClickEventListenerForTextView()
    {
        TextView firstTextView = (TextView) findViewById(R.id.firstValue);
        TextView secondTextView = (TextView) findViewById(R.id.secondValue);
        firstTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstTextView.setTypeface(null, Typeface.BOLD);
                secondTextView.setTypeface(null, Typeface.NORMAL);
                workingTextView = firstTextView;
                resultTextView = secondTextView;
            }
        });
        secondTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondTextView.setTypeface(null, Typeface.BOLD);
                firstTextView.setTypeface(null, Typeface.NORMAL);
                workingTextView = secondTextView;
                resultTextView = firstTextView;
            }
        });
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        workingTextView = findViewById(R.id.firstValue);
        resultTextView = findViewById(R.id.secondValue);
        addCurrency();
        addClickEventListenerForTextView();
        Spinner firstSpinner = (Spinner) findViewById(R.id.firstSpinner);
        Boolean firstSpinnerInit = true;
        ArrayAdapter<String>  firstAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,currency);
        firstSpinner.setAdapter(firstAdapter);
        firstSpinner.setSelection(0,false);
        firstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                convertCurrency();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner secondSpinner = (Spinner) findViewById(R.id.secondSpinner);
        Boolean secondSpinnerInit = true;
        ArrayAdapter<String>  secondAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,currency);
        secondSpinner.setAdapter(secondAdapter);
        secondSpinner.setSelection(0,false);
        secondSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                convertCurrency();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.keyboard);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        KeyboardAdapter adapter = new KeyboardAdapter(button,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void OnItemClick(int position) {
        if(position <= 10) {
            addWorkings(button.get(position));
        }
        else if(position == 12)
        {
            deleteWorkings();
        }
        else
        {
            deleteAllWorkings();
        }
    }
}