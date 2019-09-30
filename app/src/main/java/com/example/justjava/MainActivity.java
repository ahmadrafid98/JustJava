package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if(quantity == 100) {
            Toast.makeText(this, getString(R.string.more_than_100_coffees), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        display(quantity);
    }

    public void decrement(View view) {
        if(quantity == 1) {
            Toast.makeText(this, getString(R.string.less_than_1_coffee), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        display(quantity);
    }

    private int calculatePrice(boolean addwhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if(addwhippedCream) {
            basePrice += 1;
        }

        if(addChocolate) {
            basePrice += 2;
        }

        return quantity*basePrice;
    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String priceMassage = getString(R.string.order_summary_name, name);
        priceMassage += "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream);
        priceMassage += "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        priceMassage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMassage += "\n" + getString(R.string.order_summary_price, price);
        priceMassage += "\n" + getString(R.string.thank_you);

        return priceMassage;
    }

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMassage = createOrderSummary(price, hasWhippedCream, hasChocolate , name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, priceMassage);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
