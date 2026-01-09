package ru.mirea.khasanova.scrollviewapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout wrapper = findViewById(R.id.wrapper);
        LayoutInflater inflater = getLayoutInflater();

        BigInteger value = BigInteger.valueOf(2);

        for (int i = 1; i <= 100; i++) {
            View view = inflater.inflate(R.layout.item, wrapper, false);
            TextView text = view.findViewById(R.id.textView);
            text.setText(i + ": " + value.toString());
            wrapper.addView(view);
            value = value.multiply(BigInteger.valueOf(2));
        }
    }
}