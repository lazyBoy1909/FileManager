package com.example.inputappex2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText mssvEditText;
    private EditText nameEditText;
    private EditText cccdEditText;
    private EditText phoneEditText;
    private EditText emailEditText ;
    private EditText homeEditText ;
    private EditText currentHomeEditText;
    private RadioGroup majorRadioGroup;
    private CheckBox CCheckBox ;
    private CheckBox javaCheckBox ;
    private CheckBox pythonCheckBox;
    private CheckBox swiftCheckBox;
    private CheckBox submitCheckBox;
    private TextView dateDisplayTextView;
    private Button dateButton;
    private CalendarView calendarView;
    private TextView homeTextView;
    private TextView currentHomeTextView;
    private TextView majorTextView;
    private TextView languageTextView;
    private RadioButton IT1CheckBox;
    private RadioButton IT2CheckBox;
    public void initComponents()
    {
         submitButton = (Button) this.findViewById(R.id.submitButton);
     mssvEditText = (EditText) this.findViewById(R.id.mssvEditText);
     nameEditText = (EditText) this.findViewById(R.id.nameEditText);
      cccdEditText = (EditText) this.findViewById(R.id.cccdEditText);
      phoneEditText = (EditText) this.findViewById(R.id.phoneEditText);
      emailEditText = (EditText) this.findViewById(R.id.emailEditText);
      homeEditText = (EditText) this.findViewById(R.id.homeEditText);
      currentHomeEditText = (EditText) this.findViewById(R.id.currentHomeEditText);
      majorRadioGroup = (RadioGroup) this.findViewById(R.id.radioGroup);
      CCheckBox = (CheckBox) this.findViewById(R.id.CCheckBox);
      javaCheckBox = (CheckBox) this.findViewById(R.id.javaCheckBox);
      pythonCheckBox = (CheckBox) this.findViewById(R.id.pythonCheckBox);
      swiftCheckBox = (CheckBox) this.findViewById(R.id.swiftCheckBox);
      submitCheckBox = (CheckBox) this.findViewById(R.id.submitCheckBox);
      dateDisplayTextView = (TextView) this.findViewById(R.id.dateDisplayTextView);
      dateButton = (Button) this.findViewById(R.id.datePickButton);
      calendarView = (CalendarView) this.findViewById(R.id.calendarView);
      homeTextView = (TextView) this.findViewById(R.id.homeTextView);
      currentHomeTextView = (TextView) this.findViewById(R.id.currentHomeTextView);
      majorTextView = (TextView) this.findViewById(R.id.majorTextView);
      languageTextView = (TextView)  this.findViewById(R.id.languageTextView);
      IT1CheckBox = (RadioButton) this.findViewById(R.id.IT1RadioButton);
      IT2CheckBox = (RadioButton) this.findViewById(R.id.IT2RadioButton);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        calendarView.setVisibility(View.INVISIBLE);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String  curDate = String.valueOf(i2);
                String  Year = String.valueOf(i);
                String  Month = String.valueOf(i1+1);
                dateDisplayTextView.setText(curDate+"/"+Month+"/"+Year);
                Log.e("date",Year+"/"+Month+"/"+curDate);

            }
        });
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("pick Date","pickDateButton clicked");

                if(dateButton.getText().toString().equalsIgnoreCase("CHỌN NGÀY")) {
                    calendarView.setVisibility(View.VISIBLE);
                    homeEditText.setVisibility(View.INVISIBLE);
                    currentHomeEditText.setVisibility(View.INVISIBLE);
                    homeTextView.setVisibility(View.INVISIBLE);
                    currentHomeTextView.setVisibility(View.INVISIBLE);
                    majorTextView.setVisibility(View.INVISIBLE);
                    IT1CheckBox.setVisibility(View.INVISIBLE);
                    IT2CheckBox.setVisibility(View.INVISIBLE);
                    languageTextView.setVisibility(View.INVISIBLE);
                    CCheckBox.setVisibility(View.INVISIBLE);
                    javaCheckBox.setVisibility(View.INVISIBLE);
                    pythonCheckBox.setVisibility(View.INVISIBLE);
                    swiftCheckBox.setVisibility(View.INVISIBLE);
                    dateButton.setText("OK");
                }
                else
                {
                    calendarView.setVisibility(View.INVISIBLE);
                    homeEditText.setVisibility(View.VISIBLE);
                    currentHomeEditText.setVisibility(View.VISIBLE);
                    homeTextView.setVisibility(View.VISIBLE);
                    currentHomeTextView.setVisibility(View.VISIBLE);
                    majorTextView.setVisibility(View.VISIBLE);
                    IT1CheckBox.setVisibility(View.VISIBLE);
                    IT2CheckBox.setVisibility(View.VISIBLE);
                    languageTextView.setVisibility(View.VISIBLE);
                    CCheckBox.setVisibility(View.VISIBLE);
                    javaCheckBox.setVisibility(View.VISIBLE);
                    pythonCheckBox.setVisibility(View.VISIBLE);
                    swiftCheckBox.setVisibility(View.VISIBLE);
                    dateButton.setText("CHỌN NGÀY");
                }
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkValid = true;
                if (mssvEditText.getText().toString().trim().equalsIgnoreCase(""))
                {
                    mssvEditText.setError("Không thể để trống dòng này");
                    checkValid = false;
                }
                if (nameEditText.getText().toString().trim().equalsIgnoreCase(""))
                {
                    nameEditText.setError("Không thể để trống dòng này");
                    checkValid = false;
                }
                if (cccdEditText.getText().toString().trim().equalsIgnoreCase(""))
                {
                    cccdEditText.setError("Không thể để trống dòng này");
                    checkValid = false;
                }
                if (phoneEditText.getText().toString().trim().equalsIgnoreCase(""))
                {
                    phoneEditText.setError("Không thể để trống dòng này");
                    checkValid = false;
                }
                if (emailEditText.getText().toString().trim().equalsIgnoreCase(""))
                {
                    emailEditText.setError("Không thể để trống dòng này");
                    checkValid = false;
                }
                if (homeEditText.getText().toString().trim().equalsIgnoreCase(""))
                {
                    homeEditText.setError("Không thể để trống dòng này");
                    checkValid = false;
                }
                if (currentHomeEditText.getText().toString().trim().equalsIgnoreCase(""))
                {
                    currentHomeEditText.setError("Không thể để trống dòng này");
                    checkValid = false;
                }
                if(dateDisplayTextView.getText().toString().equalsIgnoreCase(""))
                {
                    dateDisplayTextView.setError("Vui lòng chọn ngày sinh của bạn");
                    checkValid = false;
                }
                else
                {
                    dateDisplayTextView.setError(null);
                }
                if(!submitCheckBox.isChecked())
                {
                    submitCheckBox.setError("Bạn cần đồng ý với các điều khoản trước");
                    checkValid = false;
                }
                else
                {
                    submitCheckBox.setError(null);
                }
                if(checkValid)
                {
                    Toast.makeText(MainActivity.this,"Bạn đã nộp thông tin thành công!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}