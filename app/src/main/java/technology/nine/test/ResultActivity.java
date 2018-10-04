package technology.nine.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
     TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView = findViewById(R.id.barcode_value);
        Intent intent = getIntent();
        if (intent != null){
            String value = intent.getStringExtra("Value");
            textView.setText(value);
        }
    }
}
