package technology.nine.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText nameView;
    EditText phoneNumberView;
    Button loginBtn;
    String name;
    String phoneNumber;
    SharedPreferences preferences;
    Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nameView = findViewById(R.id.nameET);
        phoneNumberView = findViewById(R.id.phone_numberET);
        loginBtn = findViewById(R.id.button);
        spinner = findViewById(R.id.spinner);
        spinnerFun();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

    }

    private void spinnerFun() {
        List<String> categories = new ArrayList<String>();
        categories.add("+91");
        categories.add("+1");
        categories.add("+23");
        categories.add("+38");
        categories.add("+41");
        categories.add("+51");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
                spinner.setSelection(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        dataAdapter.notifyDataSetChanged();
    }

    public void checkLogin() {
        nameView.setError(null);
        phoneNumberView.setError(null);
        name = nameView.getText().toString();
        phoneNumber = phoneNumberView.getText().toString();
        View focusView = null;
        boolean cancel = false;
        if (TextUtils.isEmpty(name)) {
            nameView.setError("Name is Empty");
            focusView = nameView;
            cancel = true;
        }
        if (!TextUtils.isEmpty(phoneNumber) && !isPhoneNumberValid(phoneNumber)) {
            phoneNumberView.setError("Error Phone Number is inValid");
            focusView = phoneNumberView;
            cancel = true;
        }
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phoneNumber) && isPhoneNumberValid(phoneNumber)) {
            preferences = getApplicationContext().getSharedPreferences("LoginValues", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Name", name);
            editor.putString("Number", phoneNumber);
            editor.apply();
            Intent intent = new Intent(this, VerificationActivity.class);
            startActivity(intent);
            finish();
        }


    }

    private boolean isPhoneNumberValid(String number) {
        return number.length() == 10;
    }
}
