package technology.nine.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VerificationActivity extends AppCompatActivity {
    EditText mOTP;
    Button mSubmitBtn;
    String mOTPText;
    SharedPreferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        mOTP = findViewById(R.id.otp_view);
        mOTP.setGravity(Gravity.CENTER_HORIZONTAL);
        mSubmitBtn = findViewById(R.id.submitBtn);
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOTP();
            }
        });
    }

    private void checkOTP() {
        preferences = getApplicationContext().getSharedPreferences("LoginValues",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        mOTPText = mOTP.getText().toString();
        mOTP.setError(null);
        boolean cancel =false;
        View focusView = null;
        if (!TextUtils.isEmpty(mOTPText) && !isOTPValid(mOTPText)) {
          mOTP.setError("Please enter the correct OTP ");
          focusView = mOTP;
          cancel = true;
        }
        if (!TextUtils.isEmpty(mOTPText) && isOTPValid(mOTPText)){
            if (mOTPText.equals("0000")){
                editor.putBoolean("Login",true);
                editor.apply();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();

            }
            else {
                mOTP.setError("Please enter the correct OTP ");
                focusView = mOTP;
                cancel = true;
            }
        }

    }

    private boolean isOTPValid(String otp) {
        return otp.length() == 4;
    }
}
