package com.example.hellotoast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private  int mCount =0;
    private TextView mShowCount;
    private Button ResetBtn;
    private Button countBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
        ResetBtn = (Button) findViewById(R.id.reset_btn);
        countBtn = findViewById(R.id.button_count);
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this,R.string.toast_message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp(View view) {
        mCount++;
        if(mShowCount != null)
        {
            mShowCount.setText(Integer.toString(mCount));
        }
        if (mCount % 2 == 0) {
            (view).setBackgroundColor(getResources().getColor(R.color.purple_500));
        } else {
            (view).setBackgroundColor(getResources().getColor(R.color.teal_200));
        }

    }

    public void resetCount(View view) {
        if(mCount == 0) return;
        mCount = 0;
        if (mShowCount != null) {
            mShowCount.setText(Integer.toString(mCount));
        }

        if(this.countBtn != null) {
            this.countBtn.setBackgroundColor(getResources().getColor(R.color.purple_500));
        }
    }
}