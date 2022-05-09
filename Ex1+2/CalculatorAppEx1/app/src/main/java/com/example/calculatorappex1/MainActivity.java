package com.example.calculatorappex1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {
    private Button CButton;
    private TextView workingTextView;
    private TextView resultTextView;
    private Button CEButton;
    private Button BSButton;
    private Button divideButton;
    private Button zeroButton;
    private Button oneButton;
    private Button twoButton;
    private Button threeButton;
    private Button fourButton;
    private Button fiveButton;
    private Button sixButton;
    private Button sevenButton;
    private Button eightButton;
    private Button nineButton;
    private Button xButton;
    private Button subButton;
    private Button addButton;
    private Button equalButton;
    private Button addAndSubButton;
    String working = "";
    void initComponents()
    {

        workingTextView = (TextView) this.findViewById(R.id.workingTextView);
        resultTextView = (TextView) this.findViewById(R.id.resultTextView);
        CButton = (Button)this.findViewById(R.id.CButton);
        CEButton = (Button)this.findViewById(R.id.CEButton);
        BSButton = (Button)this.findViewById(R.id.BSButton);
        divideButton = (Button)this.findViewById(R.id.divideButton);
        addButton = (Button) this.findViewById(R.id.addButton);
        xButton = (Button)this.findViewById(R.id.xButton);
        subButton = (Button)this.findViewById(R.id.subButton);
        addAndSubButton= (Button)this.findViewById(R.id.addAndSubButton);
        equalButton = (Button) this.findViewById(R.id.equalButton);
        oneButton = (Button)this.findViewById(R.id.oneButton);
        twoButton = (Button)this.findViewById(R.id.twoButton);
        threeButton = (Button)this.findViewById(R.id.threeButton);
        fourButton = (Button)this.findViewById(R.id.fourButton);
        fiveButton = (Button)this.findViewById(R.id.fiveButton);
        sixButton = (Button)this.findViewById(R.id.sixButton);
        sevenButton = (Button)this.findViewById(R.id.sevenButton);
        eightButton = (Button)this.findViewById(R.id.eightButton);
        nineButton = (Button)this.findViewById(R.id.nineButton);
        zeroButton = (Button) this.findViewById(R.id.zeroButton);
    }
    private void setWorkings(String givenValue)
    {
        working += givenValue;
        workingTextView.setText(working);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        divideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("/");
            }
        });
        BSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("action click", "Delete last character");
                if(working.equalsIgnoreCase(""))
                    return;
                else working = working.substring(0,working.length()-1);
                workingTextView.setText(working);
            }
        });
        oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("1");
            }
        });
        twoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("2");
            }
        });
        threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("3");
            }
        });
        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("4");
            }
        });
        fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("5");
            }
        });
        sixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("6");
            }
        });
        sevenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("7");
            }
        });
        eightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("8");
            }
        });
        nineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("9");
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("+");
            }
        });
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("-");
            }
        });
        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("*");
            }
        });
        CButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workingTextView.setText("");
                working = "";
                resultTextView.setText("");
            }
        });
        zeroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWorkings("0");
            }
        });
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double result = null;
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
                try {
                    result = (double) engine.eval(working);
                } catch (ScriptException e) {
                    Toast.makeText(getApplicationContext(),"INVALID CALCULATION!", Toast.LENGTH_SHORT).show();
                }
                if(result != null)
                {
                    resultTextView.setText(String.valueOf(result.doubleValue()));
                }
            }
        });
    }
}